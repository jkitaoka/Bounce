package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContentMainPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    barNameAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the Firebase Realtime Database
    int position;
    int index;
    Bar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_page);

        mbase = FirebaseDatabase.getInstance().getReference("bars");

        recyclerView = findViewById(R.id.recycler1);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<barname> options = new FirebaseRecyclerOptions.Builder<barname>()
                .setQuery(mbase, barname.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new barNameAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }


    public void goToBarInfo(View view) {
        Log.i("ContentMainPage", "in bar info");
        RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(view);
        position = viewHolder.getAdapterPosition();
        Log.i("ContentMainPage", String.valueOf(position));
        index = 0;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("bars")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (index == position) { // You have reached the desired bar
                                bar = snapshot.getValue(Bar.class); // Get the bar
                                Log.i("ContentMainPage", bar.barName);
                                Intent intent = new Intent(ContentMainPage.this, BarInfo.class);
                                intent.putExtra("barName", bar.barName);
                                startActivity(intent);
                                return;
                            } else {
                                index++;
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

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}
