package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BarOnboarding extends AppCompatActivity {

    class Bar {
        public String password;
        public String name;
        public String lastNameOrAddress;
        public boolean isBar;

        public Bar(String password, String name, String lastNameOrAddress, boolean isBar) {
            this.password = password;
            this.name = name;
            this.lastNameOrAddress = lastNameOrAddress;
            this.isBar = isBar;
        }
    }

    EditText editName, editAddress, editEmail, editPassword;
    String barName, barAddress, email, password;


    public void backClicked(View view) {
        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
    }

    public void createClicked(View view) {
        Intent intent = new Intent(this, BarSideMain.class);
        startActivity(intent);

        barName = editName.getText().toString();
        barAddress = editAddress.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        writeNewUser(email, password, barName, barAddress);
    }

    public void writeNewUser(String email, String password, String barName, String barAddress) {
        Bar bar = new Bar(password, barName, barAddress, true);
        Log.i("BarOnboarding", "Write message to database");

        //Write to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        // Need to figure out how to make the child the email rather than how you're doing it here
        reference.child("the double U").setValue(bar);

        // Read from database
        reference.child("the double U").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DataSnapshot> task) {
               Log.d("firebase", String.valueOf(task.getResult().getValue()));
           }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_onboarding);

        // Get user input
        editName = (EditText) findViewById(R.id.enterBarName);
        editAddress = (EditText) findViewById(R.id.enterBarAddress);
        editEmail = (EditText) findViewById(R.id.enterEmail);
        editPassword = (EditText) findViewById(R.id.enterPassword);
    }
}