package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusIndividual extends AppCompatActivity {

    TextView statusHeadText,statusBodyText,statusDate,statusStartTime,duration, statisticsBodyText;
    String TAG = "StatusIndividual";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_individual);

        Intent intent = getIntent();

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        statusHeadText = (TextView) findViewById(R.id.statusHeadText);
        statusBodyText = (TextView) findViewById(R.id.statusBodyText);
        statusStartTime = (TextView) findViewById(R.id.statusStartTime);
        statusDate = (TextView) findViewById(R.id.statusDate);
        duration = (TextView) findViewById(R.id.duration);
        statisticsBodyText = (TextView) findViewById(R.id.statisticsBodyText) ;

        ArrayList<String> s = intent.getStringArrayListExtra("status");

        Log.d(TAG, s.toString());

        /*
        Indices of S:
        0: postID
        1: userID
        2: title
        3: body
        4: date
        5: startTime
        6: hours
         */


        String postID = s.get(0);


        statusHeadText.setText(s.get(2).toString());
        statusBodyText.setText(s.get(3).toString());
        statusDate.setText(s.get(4).toString());
        statusStartTime.setText(s.get(5).toString());
        duration.setText(s.get(6).toString()+ " hours");




        database.getReference().child("activity")
                .addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count = 0;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String recordID = snapshot.getKey();

                            for (DataSnapshot record : snapshot.getChildren()) {

                                String key = record.getKey();
                                if (key.equals("postID")) {
                                    String val = (String) record.getValue();
                                    if (val.equals(postID)) {
                                        count++;
                                    }
                                }
                            }
                        }

                        statisticsBodyText.setText("Redemptions: " + String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




    }

    public void goToStatusMain(View view) {
        Intent intent = new Intent(this, StatusMain.class);
        startActivity(intent);
    }
}