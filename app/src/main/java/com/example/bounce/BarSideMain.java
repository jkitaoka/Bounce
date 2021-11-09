package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BarSideMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_side_main);
    }

    public void goToSettings(View view) {
//        Intent intent = new Intent(this, asdfasdf.class);
//        startActivity(intent);
    }

    public void goToLogOut(View view) {
        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
    }



    public void goToStatusMain(View view) {
        Intent intent = new Intent(this, StatusMain.class);
        startActivity(intent);
    }

    //fill out more
    public void goToStatusIndividual(View view) {
        Intent intent = new Intent(this, StatusIndividual.class);
        //intent put extra
        startActivity(intent);
    }

    public void goToFeedbackMain(View view) {
        Intent intent = new Intent(this, FeedbackMain.class);
        startActivity(intent);
    }

}