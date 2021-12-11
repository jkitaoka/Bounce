package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarOnboarding extends AppCompatActivity {

    EditText editName, editAddress, editEmail, editPassword;
    String barName, barAddress, email, password, userID;
    FirebaseAuth mAuth;

    public void backClicked(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void createClicked(View view) {
        Log.i("BarOnboarding", "Create clicked");
        barName = editName.getText().toString();
        barAddress = editAddress.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        Log.i("BarOnboarding", "User written");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with signed-in user's information
                            Log.i("BarOnboarding", "Creating Firebase User");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid();
                            writeNewUser(userID, barName, barAddress);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user
                            Toast.makeText(BarOnboarding.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        Log.i("BarOnboarding", "Send to bar main");
        Intent intent = new Intent(this, BarSideMain.class);
        startActivity(intent);
    }

    public void writeNewUser(String userID, String barName, String barAddress) {
        Bar bar = new Bar(barName, barAddress);
        Log.i("BarOnboarding", "Write user to database");
        //Write to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("bars");
        reference.child(userID).setValue(bar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("BarOnboarding", "Creating...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_onboarding);

        mAuth = FirebaseAuth.getInstance();

        editName = (EditText) findViewById(R.id.enterBarName);
        editAddress = (EditText) findViewById(R.id.enterBarAddress);
        editEmail = (EditText) findViewById(R.id.enterEmail);
        editPassword = (EditText) findViewById(R.id.enterPassword);
    }
}