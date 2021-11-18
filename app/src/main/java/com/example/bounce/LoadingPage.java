package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingPage extends AppCompatActivity {

    private void goToHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);

        //Loads activity for 1.5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                goToHomePage();
            }
        }, 3000);
    }
}