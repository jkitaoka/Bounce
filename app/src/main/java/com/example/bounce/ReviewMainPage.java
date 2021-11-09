package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReviewMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main_page);
    }

    public void goToFeedbackMain(View view) {
        Intent intent = new Intent(this, FeedbackMain.class);
        startActivity(intent);
    }

    //add putExtra, etc
    public void goToReviewIndividual(View view) {
        Intent intent = new Intent(this, ReviewResponse.class);
        startActivity(intent);
    }

}