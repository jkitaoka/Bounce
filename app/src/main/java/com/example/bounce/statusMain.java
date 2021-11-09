package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class statusMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_main);
    }

    public void goToPostStatus(View view) {
        Intent intent = new Intent(this, PostStatus.class);
        startActivity(intent);
    }


    public void goToStatusIndividual(View view) {
        Intent intent = new Intent(this, StatusIndividual.class);
        intent.putExtra("header", "Status Header");
        intent.putExtra("body", "Status Body");
        //need to pass through rest of status info to status individual activity
        //and need to lookup on db
        startActivity(intent);
    }



}