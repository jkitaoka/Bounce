package com.example.bounce;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarInfo extends AppCompatActivity {
    DatabaseReference mbase;
    TextView barName, reward1, reward2, body1, body2, statusID1, statusID2;
    Button redeem1, redeem2;
    Status status;
    boolean one;
    private static final String TAG = "BarInfo";
    FirebaseAuth mAuth;

    public void getPosts(String barId) {
        Log.i(TAG, "Get posts from database");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            status = snapshot.getValue(Status.class);
                            if (status.userID.equals(barId)) { // This post belongs to the bar!
                                Log.i(TAG, "Found bar post");
                                // alternate between reward 1 and 2
                                if (one==true) {
                                    reward1.setText(status.title); // Ideally replace with an active field, here just alternate between post 1 and 2
                                    body1.setText(status.body);
                                    statusID1.setText(status.postID);
                                    if (!status.title.equals("")) {
                                        redeem1.setEnabled(true);
                                        Log.i(TAG, "Setting background color for reward 1");
                                        redeem1.setBackgroundColor(getResources().getColor(R.color.olive_green));
                                        one = false;
                                    }
                                } else {
                                    reward2.setText(status.title);
                                    body2.setText(status.body);
                                    statusID2.setText(status.postID);
                                    if (!status.title.equals("")) {
                                        redeem2.setEnabled(true);
                                        Log.i(TAG, "Setting background color for reward 2");
                                        redeem2.setBackgroundColor(getResources().getColor(R.color.olive_green));
                                        one=true;
                                    }
                                }
                                }
                            }
                        }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mbase = FirebaseDatabase.getInstance().getReference();
        barName.setText(getIntent().getStringExtra("barName")); // Set Bar Name of page
        // Set everything else (deals, etc) here too
        String barId = getIntent().getStringExtra("barID"); // Use this bar ID to get the posts
        getPosts(barId);
    }

    private void writeNewRedemption(String postID, String barID, String userID) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("activity");
        //METHOD FOR GETTING UNIQUE ID's
        String activityID = reference.push().getKey();

        StatusRedemption sr = new StatusRedemption(activityID, postID, barID, userID);

        Log.d(TAG, activityID);
        Log.d(TAG, postID);
        Log.d(TAG, barID);
        Log.d(TAG, userID);

        reference.child(activityID).setValue(sr);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_info);
        barName = findViewById(R.id.BarName);
        redeem1 = findViewById(R.id.deal1);
        redeem2 = findViewById(R.id.deal2);
        reward1 = findViewById(R.id.reward1);
        reward2 = findViewById(R.id.reward2);
        body1 = findViewById(R.id.body1);
        body2 = findViewById(R.id.body2);
        statusID1 = findViewById(R.id.statusID1);
        statusID2 = findViewById(R.id.statusID2);


        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);


        mbase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        barName.setText(getIntent().getStringExtra("barName")); // Set Bar Name of page
        // Set everything else (deals, etc) here too
        String barId = getIntent().getStringExtra("barID"); // Use this bar ID to get the posts





        String userID = mAuth.getUid();




        redeem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Do you want to redeem this deal?").setTitle("Redeem Deal");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        redeem1.setEnabled(false);
                        redeem1.setBackgroundColor(Color.GRAY);
                        // Add to total deals and times this deal was redeemed
                        // Check every post to find the one that matches reward 1 and increase its numRedemptions by 1
                        String postID1 = statusID1.getText().toString();


                        //push to database
                        writeNewRedemption(postID1, barId, userID);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });

        redeem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Do you want to redeem this deal?").setTitle("Redeem Deal");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                redeem2.setEnabled(false);
                                redeem2.setBackgroundColor(Color.GRAY);
                                //push to database
                                String postID2 = statusID2.getText().toString();
                                writeNewRedemption(postID2, barId, userID);
                            }
                        });

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });

    }

    public void goToMainPage(View view) {
        Intent intent = new Intent(this, ContentMainPage.class);
        startActivity(intent);
    }
}