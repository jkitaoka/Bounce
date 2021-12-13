package com.example.bounce;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PostStatus extends AppCompatActivity {

    EditText statusHeadText, statusBodyText, statusDate, startTime,duration;
    String userID;
    FirebaseAuth mAuth;

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

        statusDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PostStatus.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        statusDate.setText(month+1+"/"+day+"/"+year);
                    }
                },year,month,day);

                dialog.setTitle("Select Date");
                dialog.show();
            }
        });

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
                        String min = "";
                        String hour = "";
                        if(selectedMinute<10){
                            min = "0" + selectedMinute;
                        } else{
                            min = String.valueOf(selectedMinute);
                        }
                        if(selectedHour<10){
                            hour = "0" + selectedHour;
                        } else{
                            hour = String.valueOf(selectedHour);
                        }


                        startTime.setText( hour + ":" + min);
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



    private void writeNewPost(String userID, String title, String body, String date, String startTime, String hours) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("posts");

        //METHOD FOR GETTING UNIQUE ID's
        String postID = reference.push().getKey();

        Status status = new Status(userID, postID, title, body, date, startTime, hours);
        reference.child(postID).setValue(status);
    }



    public void postStatus(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        String title = statusHeadText.getText().toString();
        String body = statusBodyText.getText().toString();
        String date = statusDate.getText().toString();
        String time = startTime.getText().toString();
        String dur = duration.getText().toString();
        int numRedemptions = 0;

        if (title.equals("") || body.equals("") || date.equals("")
                || time.equals("") || dur.equals("")) {
            Toast.makeText(PostStatus.this, "Fields cannot be left blank.",
                    Toast.LENGTH_SHORT).show();
        } else {
            writeNewPost(userID, title, body, date, time, dur);
            Toast.makeText(PostStatus.this, "Status Posted!",
                    Toast.LENGTH_SHORT).show();
            goToStatusMain(view);
        }
    }
}