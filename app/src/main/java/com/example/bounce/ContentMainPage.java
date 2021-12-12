package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;


public class ContentMainPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    barNameAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the Firebase Realtime Database

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
        /*Log.i("ContentMainPage", "HERE IS THE KEY");
        Log.i("ContentMainPage", );*/
        adapter = new barNameAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }
    public void goToBarInfo(View view) {
        Log.i("ContentMainPage", "Go to Bar Info");
        Intent intent = new Intent(this, BarInfo.class);
        Log.i("ContentMainPage", "Starting activity...");
        startActivity(intent);
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
