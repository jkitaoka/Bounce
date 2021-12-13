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

public class StatusMain extends AppCompatActivity {

    TextView activeStatusHeader, activeStatusHeader2, scheduledStatusHeader1, scheduledStatusHeader2;
    FirebaseAuth mAuth;
    String TAG = "StatusMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        ArrayList<String> postIDs = new ArrayList<>();
        ArrayList<HashMap> statusObjs = new ArrayList<>();


        ArrayList<HashMap> activeStatus = new ArrayList<>();
        ArrayList<HashMap> scheduledStatus = new ArrayList<>();
        ArrayList<HashMap> pastStatus = new ArrayList<>();


        activeStatusHeader = (TextView) findViewById(R.id.activeStatusHeader);
        activeStatusHeader2 = (TextView) findViewById(R.id.activeStatusHeader2);
        scheduledStatusHeader1 = (TextView) findViewById(R.id.scheduledStatusHeader1);
        scheduledStatusHeader2 = (TextView) findViewById(R.id.scheduledStatusHeader2);

        // Iterate through bars to determine if bar posts a status
        database.getReference().child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String UID = snapshot.getKey();
                            HashMap<String,String> v = (HashMap<String,String>) snapshot.getValue();

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


                        for(HashMap<String,String> status:statusObjs){
                            Log.d(TAG, status.get("title").toString());
                            Log.d(TAG, status.get("title").toString());
                            dateStr.add(status.get("date").toString() + " "  + status.get("startTime").toString());
                        }

                        //for each status convert date, starttime and duration into start time and end times
                        Date startDate;
                        Date endDate;
                        int dur;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

                        Calendar cal = Calendar.getInstance();

                        Date currTime=new Date();

                        Log.d(TAG, dateStr.toString());
                        for(int i = 0;i<dateStr.size();i++){
                            try {

                                startDate = dateFormat.parse(dateStr.get(i));
                                dur = Integer.parseInt(statusObjs.get(i).get("hours").toString());
                                cal.setTime(startDate);
                                cal.add(Calendar.HOUR, dur);
                                endDate = cal.getTime();

                                if(currTime.after(endDate)){
                                    pastStatus.add(statusObjs.get(i));
                                }

                                else if(currTime.before(startDate)){
                                    scheduledStatus.add(statusObjs.get(i));
                                }

                                else{
                                    activeStatus.add(statusObjs.get(i));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        //display active status
                        if(activeStatus.size() < 1){
                            activeStatusHeader.setText("No Active Status");
                            activeStatusHeader.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) { }});
                        } else {
                            if (activeStatus.size() < 2) {
                                String header = activeStatus.get(0).get("title").toString();
                                activeStatusHeader.setText(header);
                                activeStatusHeader.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //go to status indiv page
                                        goToStatusIndividual(view, activeStatus.get(0));

                                    }
                                });
                            } else { // There are two active statuses
                                String header1 = activeStatus.get(0).get("title").toString();
                                activeStatusHeader.setText(header1);
                                activeStatusHeader.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //go to status indiv page
                                        goToStatusIndividual(view, activeStatus.get(0));

                                    }
                                });

                                String header2 = activeStatus.get(1).get("title").toString();
                                activeStatusHeader2.setText(header2);
                                activeStatusHeader2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //go to status indiv page
                                        goToStatusIndividual(view, activeStatus.get(1));
                                    }
                                });

                            }
                        }



                        //display scheduled statuses

                        if(scheduledStatus.size() < 2){
                            if(scheduledStatus.size() < 1){
                                scheduledStatusHeader1.setText("No Scheduled Status");
                                scheduledStatusHeader1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {} });
                                scheduledStatusHeader2.setText("No Scheduled Status");
                                scheduledStatusHeader2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {} });
                            } else{
                                String header = scheduledStatus.get(0).get("title").toString();
                                scheduledStatusHeader1.setText(header);
                                scheduledStatusHeader1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //go to status indiv page
                                        goToStatusIndividual(view, scheduledStatus.get(0));
                                    }
                                });
                                scheduledStatusHeader2.setText("No Scheduled Status");
                                scheduledStatusHeader2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {} });
                            }


                        } else{
                            String header = scheduledStatus.get(0).get("title").toString();
                            scheduledStatusHeader1.setText(header);
                            scheduledStatusHeader1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //go to status indiv page
                                    goToStatusIndividual(view, scheduledStatus.get(0));
                                }
                            });

                            String header1 = scheduledStatus.get(1).get("title").toString();
                            scheduledStatusHeader2.setText(header1);
                            scheduledStatusHeader2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //go to status indiv page
                                    goToStatusIndividual(view, scheduledStatus.get(1));
                                }
                            });
                        }

//                        //display past statuses
//
//                        if(pastStatus.size() < 1){
//                            pastStatusHeader1.setText("No Past Statuses");
//                            pastStatusHeader1.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                }
//                            });
//                        } else{
//                            String header = pastStatus.get(0).get("title").toString();
//                            pastStatusHeader1.setText(header);
//                            pastStatusHeader1.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    //go to status indiv page
//                                    goToStatusIndividual(view, pastStatus.get(0));
//
//                                }
//                            });
//                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



    }

    public void goToPostStatus(View view) {
        Intent intent = new Intent(this, PostStatus.class);
        startActivity(intent);
    }

    public void goToBarMain(View view) {
        Intent intent = new Intent(this, BarSideMain.class);
        startActivity(intent);
    }


    public void goToStatusIndividual(View view, HashMap<String, String> status) {
        if(status == null||status.isEmpty()){
            Intent intent = new Intent(this, StatusIndividual.class);
            startActivity(intent);
            return;
        }



        Intent intent = new Intent(this, StatusIndividual.class);


        ArrayList<String> statusList = new ArrayList<>();
        statusList.add(status.get("postID"));
        statusList.add(status.get("userID"));
        statusList.add(status.get("title"));
        statusList.add(status.get("body"));
        statusList.add(status.get("date"));
        statusList.add(status.get("startTime"));
        statusList.add(status.get("hours"));


        intent.putStringArrayListExtra("status",statusList);

        startActivity(intent);


    }



}