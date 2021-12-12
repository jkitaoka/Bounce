package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarSideMain extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_side_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        // Iterate through bars to determine if user is a bar
        database.getReference().child("bars")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String UID = snapshot.getKey();
                            if (UID.equals(userID)) {
                                Bar bar = snapshot.getValue(Bar.class);
                                TextView tv = (TextView) findViewById(R.id.welcomeText);
                                tv.setText(bar.barName);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
    }

    public void goToStatusMain(View view) {
        Intent intent = new Intent(this, PostStatus.class);
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