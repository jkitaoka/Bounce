package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReviewResponse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_response);
    }

    public void goToReviewMain(View view) {
        Intent intent = new Intent(this, ReviewMainPage.class);
        startActivity(intent);
    }

    public void postResponse(View view) {
        goToReviewMain(view);
    }
}