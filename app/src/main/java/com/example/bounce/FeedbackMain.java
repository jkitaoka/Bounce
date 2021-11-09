package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FeedbackMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_main);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, BarSideMain.class);
        startActivity(intent);
    }

    //addExtra, etc
    public void goToReviewResponse(View view) {
        Intent intent = new Intent(this, ReviewResponse.class);
        startActivity(intent);
    }



}