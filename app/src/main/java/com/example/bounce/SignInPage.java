package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    public void loginClicked(View view) {
        editEmail = (EditText) findViewById(R.id.enterEmail);
        editPassword = (EditText) findViewById(R.id.enterPassword);

        String email = editEmail.toString();
        String password = editPassword.toString();
        if (email != null && password != null) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(SignInPage.this, "Login failed.",
                                        Toast.LENGTH_SHORT);
                            }
                        }
                    });
            goToBarHome(view);
        } else {
            Toast.makeText(SignInPage.this, "Fields cannot be left blank.",
                    Toast.LENGTH_SHORT);
        }
    }

    private void updateUI(FirebaseUser user) {
        // Edit this method
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

        mAuth = FirebaseAuth.getInstance();
    }
}
