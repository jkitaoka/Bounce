package com.example.bounce;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarInfo extends AppCompatActivity {
    DatabaseReference mbase;
    TextView barName, reward1, reward2, body1, body2;
    Button redeem1, redeem2;
    Status status;
    boolean one;
    private static final String TAG = "BarInfo";

    public void getPosts(String barId) {
        Log.i(TAG, "Get posts from database");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.i(TAG, "Getting snapshot...");
                            status = snapshot.getValue(Status.class);
                            if (status.userID.equals(barId)) { // This post belongs to the bar!
                                Log.i(TAG, "Found bar post");
                                // alternate between reward 1 and 2
                                if (one==true) {
                                    reward1.setText(status.title); // Ideally replace with an active field, here just alternate between post 1 and 2
                                    body1.setText(status.body);
                                } else {
                                    reward2.setText(status.title);
                                    body2.setText(status.body);
                                    one=true;
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

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        redeem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Do you want to redeem this deal?").setTitle("Redeem Deal");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        redeem1.setEnabled(false);
                        redeem1.setBackgroundColor(Color.GRAY);
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