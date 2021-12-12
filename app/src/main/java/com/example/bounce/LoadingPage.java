package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "LoadingPage";
    public FirebaseUser user;

    private void goToHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void directUser() {
        user = mAuth.getCurrentUser();
        if (user != null) { // User is signed in
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String userID = user.getUid();
            Log.i(TAG, userID);

            // Iterate through bars to determine if user is a bar
            database.getReference().child("bars")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.i(TAG,"inside for loop");
                                String UID = snapshot.getKey();
                                Log.i(TAG, UID);
                                if (UID.equals(userID)) {
                                    // they are a bar, send to bar page
                                    Log.i(TAG,"send to bar main");
                                    Intent intent = new Intent(LoadingPage.this, BarSideMain.class);
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

            // Iterate through users to determine if user is a patron
            database.getReference().child("users")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String UID = snapshot.getKey();
                                Log.i(TAG, UID);
                                if (UID.equals(userID)) {
                                    // they are a bar, send to bar page
                                    Log.i(TAG,"send to patron main");
                                    Intent intent = new Intent(LoadingPage.this, ContentMainPage.class);
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

        } else {
            Log.i(TAG, "go to home page");
            goToHomePage(); // Go to signing page if user is not already signed in
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        mAuth = FirebaseAuth.getInstance();

        Log.i(TAG, "Load for 1.5 seconds...");
        //Loads activity for 1.5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                directUser();
            }
        }, 1500);
    }
}