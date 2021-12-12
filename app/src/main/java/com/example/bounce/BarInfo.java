package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BarInfo extends AppCompatActivity {

    DatabaseReference mbase;
    TextView barname;
    Button redeem1, redeem2;

    @Override
    public void onStart() {
        super.onStart();
        mbase = FirebaseDatabase.getInstance().getReference();
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_info);
        barname = findViewById(R.id.barname);
        redeem1 = findViewById(R.id.deal1);
        redeem2 = findViewById(R.id.deal2);
    }


    public void redeem(View view) {
        // In here you'll have to pass the deal as an intent extra maybe?
        Intent intent = new Intent(this, Redeem.class);


        startActivity(intent);
    }
}