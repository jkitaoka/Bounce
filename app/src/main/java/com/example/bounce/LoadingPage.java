package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private void goToHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) { // User is signed in
//            // Determine if user is a bar or customer
//            // Go to appropriate page; this will be some attribute in the database
//
//        } else {
//            Log.i("loading page", "go to home page");
//            goToHomePage(); // Go to signing page if user is not already signed in
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);

//        mAuth = FirebaseAuth.getInstance();

        //Loads activity for 1.5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                goToHomePage();
            }
        }, 3000);
    }
}