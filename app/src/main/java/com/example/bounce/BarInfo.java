package com.example.bounce;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BarInfo extends AppCompatActivity {


    private Button button;
    DatabaseReference mbase;
    TextView barName = (TextView) findViewById(R.id.BarName);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("BarInfo", "Creating...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_info);

        Log.i("BarInfo", "Get firebase reference");

        mbase = FirebaseDatabase.getInstance().getReference();
        Log.i("BarInfo", "Query");
        Query query = mbase.child("bars").orderByChild("barname");

        query.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("BarInfo", "In data change");
                for(DataSnapshot data: snapshot.getChildren()){
                    Log.i("BarInfo", "In for loop");
                    String barNames = data.child("barname").getValue().toString();
                    barName.setText(barNames);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));

    }
}