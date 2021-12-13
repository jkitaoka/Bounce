package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class BarSideMain extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView activeStatusHeaders, totalDealsRedeemed, dailyDealsRedeemed;
    String TAG = "BarSideMain";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_side_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        /*
        update activeStatusHeaders with active statuses
        update totalDealsRedeemed with total redemptions
        update dailyDealsRedeemed with daily redemptions
         */

        activeStatusHeaders = (TextView) findViewById(R.id.activeStatusHeaders);
        totalDealsRedeemed = (TextView) findViewById(R.id.totalDealsRedeemed);


        /*
        get posts from this user from posts table
        get posts from this user from this day from posts table
        get active statuses from posts table
        get count of records with postID matching something from arraylists
        update total and daily redemptions, status headers

         */


        ArrayList<String> postIDs = new ArrayList<>();
        ArrayList<HashMap> statusObjs = new ArrayList<>();


        ArrayList<HashMap> activeStatus = new ArrayList<>();


        database.getReference().child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String UID = snapshot.getKey();
                            HashMap<String, String> v = (HashMap<String, String>) snapshot.getValue();

                            for (DataSnapshot record : snapshot.getChildren()) {

                                String key = record.getKey();
                                if (key.equals("userID")) {
                                    String val = (String) record.getValue();
                                    if (val.equals(userID)) {
                                        postIDs.add(UID);
                                        statusObjs.add(v);
                                    }
                                }
                            }
                        }

                        Log.d(TAG, postIDs.toString());

                        ArrayList<String> dateStr = new ArrayList<>();

                        for (HashMap<String, String> status : statusObjs) {
                            Log.d(TAG, status.get("title").toString());
                            Log.d(TAG, status.get("title").toString());
                            dateStr.add(status.get("date").toString() + " " + status.get("startTime").toString());
                        }


                        //for each status convert date, starttime and duration into start time and end times
                        Date startDate;
                        Date endDate;
                        int dur;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

                        Calendar cal = Calendar.getInstance();

                        Date currTime = new Date();

                        Log.d(TAG, dateStr.toString());
                        for (int i = 0; i < dateStr.size(); i++) {
                            try {

                                startDate = dateFormat.parse(dateStr.get(i));
                                dur = Integer.parseInt(statusObjs.get(i).get("hours").toString());
                                cal.setTime(startDate);
                                cal.add(Calendar.HOUR, dur);
                                endDate = cal.getTime();

                                if (!currTime.after(endDate) && !currTime.before(startDate)) {
                                    activeStatus.add(statusObjs.get(i));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.i(TAG, "Display active status");
                        if (activeStatus.size() < 1) {
                            activeStatusHeaders.setText("No Active Status!");
                        } else if (activeStatus.size() < 2) {
                            Log.i(TAG, "One status");
                            activeStatusHeaders.setText(activeStatus.get(0).get("title").toString());
                        } else {
                            Log.i(TAG, "Two statuses");
                            activeStatusHeaders.setText(activeStatus.get(0).get("title").toString() + "\n" + activeStatus.get(1).get("title").toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    public void updateRedemption() {

    }


    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
    }

    public void goToStatusMain(View view) {
        Intent intent = new Intent(this, StatusMain.class);
        startActivity(intent);
    }


    public void goToPostStatus(View view) {
        Intent intent = new Intent(this, PostStatus.class);
        startActivity(intent);

    }
}