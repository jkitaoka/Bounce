package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostStatus extends AppCompatActivity {

    EditText statusHeadText, statusBodyText, statusDate, startTime,duration;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String userID, postID, email, password;
    FirebaseAuth mAuth;
    Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_status);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("posts");

        statusHeadText = (EditText) findViewById(R.id.statusHeadText);
        statusBodyText = (EditText) findViewById(R.id.statusBodyText);
        statusDate = (EditText) findViewById(R.id.statusDate);
        startTime = (EditText) findViewById(R.id.startTime);
        duration = (EditText) findViewById(R.id.duration);

        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PostStatus.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



    }

    public void goToStatusMain(View view) {
        Intent intent = new Intent(this, StatusMain.class);
        startActivity(intent);
    }



    private void writeNewPost(String userID, String title, String body, String date, String startTime, int hours) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("posts");

        //generate post ID from number of previous posts
        database.getReference().child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        count = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Status status = snapshot.getValue(Status.class);
                            if(status.userID.equals(userID)){
                                count++;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        postID = userID.concat(String.valueOf(count));

        Status status = new Status(userID, postID, title, body, date, startTime, hours);
        reference.child(postID).setValue(status);
    }

    public void pickDate(View view) {
        statusDate = (EditText) findViewById(R.id.statusDate);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                PostStatus.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));


        Calendar max = Calendar.getInstance();
        max.set(year+1, month, day); // Furthest event to schedule is 1 year out
        dialog.getDatePicker().setMaxDate(max.getTimeInMillis());
        Calendar min = Calendar.getInstance();
        min.set(year, month, day); // Soonest event scheduled can be today
        dialog.getDatePicker().setMinDate(min.getTimeInMillis());
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                statusDate.setText(month+1+"/"+day+"/"+year);
            }
        };
    }



    public void postStatus(View view) {

        FirebaseUser user = mAuth.getCurrentUser();

        userID = user.getUid();

        //TODO: Error handle for null fields


        String title = statusHeadText.getText().toString();
        String body = statusBodyText.getText().toString();
        String date = statusDate.getText().toString();
        String time = startTime.getText().toString();
        int dur = Integer.parseInt(duration.getText().toString());

        writeNewPost(userID,title,body,date,time,dur);
        goToStatusMain(view);
    }



}