package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BarInfo extends AppCompatActivity {

    DatabaseReference mbase;
    TextView barname;

    @Override
    public void onStart() {
        super.onStart();
        mbase = FirebaseDatabase.getInstance().getReference();
        Log.i("BarInfo", "Query");

        // I don't actually think you want to query here but idk, maybe you do?

//        Query query = mbase.child("bars");
//
//        query.addValueEventListener((new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot data: snapshot.getChildren()){
//
//                   Bar bar = data.getValue(Bar.class);
//                   // I don't think this is what was intended
//                    Log.i("BarInfo", bar.barName);
//                    Log.i("BarInfo", bar.barAddress);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        }));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("BarInfo", "Creating...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_info);
        barname = findViewById(R.id.barname);
    }

    public void goToMainPage(View view) {
        Intent intent = new Intent(this, ContentMainPage.class);
        startActivity(intent);
    }
}