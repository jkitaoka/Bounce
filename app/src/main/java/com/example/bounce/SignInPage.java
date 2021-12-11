package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        Log.d("SignInPage", "updating UI");
        Intent intent = new Intent(this, BarSideMain.class);
        startActivity(intent);

//        Intent intent = new Intent(this, ContentMainPage.class);
//        startActivity(intent);
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

    public void goToBarHome(View view) {
        Intent intent = new Intent(this, BarSideMain.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("SignInPage", "onStart");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Log.i("SignInPage", "current user not null");
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
    }
}
