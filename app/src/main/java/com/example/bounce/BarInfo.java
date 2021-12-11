package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_info);

        mbase = FirebaseDatabase.getInstance().getReference();
        Query query = mbase.child("bars").orderByChild("barname");

        query.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
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