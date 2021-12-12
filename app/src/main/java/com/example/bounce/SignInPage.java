package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignInPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText editEmail, editPassword;
    String email, password;

    public void loginClicked(View view) {
        Log.d("SignInPage", "login clicked, signing in");

        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("SignInPage", "task complete");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignInPage", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignInPage", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        // You will eventually have to change this method depending
        // whether a bar or user is signing in
        Log.i("SignInPage", "updating UI");
        // here you have to determine if bar or user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        String userID = user.getUid();
        Log.i("SignInPage", userID);

        // Iterate through bars to determine if user is a bar
        database.getReference().child("bars")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.i("SignInPage","inside for loop");
                            String UID = snapshot.getKey();
                            Log.i("SignInPage", UID);
                            if (UID.equals(userID)) {
                                // they are a bar, send to bar page
                                Log.i("SignInPage","send to bar main");
                                Intent intent = new Intent(SignInPage.this, BarSideMain.class);
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
                            Log.i("SignInPage", UID);
                            if (UID.equals(userID)) {
                                // they are a bar, send to bar page
                                Log.i("SignInPage","send to patron main");

                                editEmail = (EditText) findViewById(R.id.email);
                                editPassword = (EditText) findViewById(R.id.password);

//                                String usernameKey = "username";
//                                String passwordKey = "password";
//
//                                String user  = editEmail.getText().toString();
//                                String password = editPassword.getText().toString();
//                                SharedPreferences sharedPreferences = getSharedPreferences("com.example.bounce", Context.MODE_PRIVATE);
//
//
//                                sharedPreferences.edit().putString(usernameKey, user).apply();
//                                sharedPreferences.edit().putString(passwordKey, password).apply();
//
//                                Toast.makeText(SignInPage.this, "Saved Login Info",
//                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignInPage.this, ContentMainPage.class);
                                startActivity(intent);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void reload() {

    }


    public void goToPatronOnboarding(View view) {
        Intent intent = new Intent(this, PatronOnboarding.class);
        startActivity(intent);
    }
    public void goToHomePage(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void goToBarOnboarding(View view) {
        Intent intent = new Intent(this, BarOnboarding.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("SignInPage", "onStart");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        Log.d("SignInPage", "Creating mAuth");
        mAuth = FirebaseAuth.getInstance();
        Log.d("SignInPage", "Getting email + password");
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
//        String usernameKey = "username";
//        String passwordKey = "password";
//
//        SharedPreferences sharedPreferences = getSharedPreferences("com.example.bounce", Context.MODE_PRIVATE);
//        if(!sharedPreferences.getString(usernameKey, "").equals("")){
//            String username = sharedPreferences.getString(usernameKey, "");
//            String password = sharedPreferences.getString(passwordKey, "");
//
//            editEmail.setText(username);
//            editPassword.setText(password);
//
//            Toast.makeText(SignInPage.this, "Loaded Login Info",
//                    Toast.LENGTH_SHORT).show();
//
//        }
//        else{
//            //Toast.makeText(SignInPage.this, "No login info",Toast.LENGTH_SHORT).show();
//        }
    }
}
