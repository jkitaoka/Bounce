package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class PatronOnboarding extends AppCompatActivity {
    DatePickerDialog picker;
    EditText editFirstName, editLastName, editAge, editEmail, editPassword;
    private FirebaseAuth mAuth;

    public void backClicked(View view) {
        Intent intent = new Intent (this, SignInPage.class);
        startActivity(intent);
    }

    public void createClicked(View view) {
        Log.i("click", "Create clicked in patron onboarding");
        if (calculateAge(editAge) < 21)  {
            // Sign in fails because the user is not > 21
            Toast.makeText(PatronOnboarding.this, "You must be over 21 use Bounce.",
                    Toast.LENGTH_SHORT).show();
        } else { // Store the user in the database

//            String firstName = editFirstName.getText().toString();
//            String lastName = editLastName.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            if (email.equals("") || password.equals("")) {
                Toast.makeText(PatronOnboarding.this, "Fields cannot be left blank.",
                        Toast.LENGTH_SHORT).show();
            }

//            User user = new User(firstName, lastName, email, password);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.i("update UI", "update UI method called");
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user
                                    Toast.makeText(PatronOnboarding.this, "Sign in failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        }

    private void updateUI(FirebaseUser user) { // This will send the patron to the main page
        Intent intent = new Intent(this, ContentMainPage.class);
        startActivity(intent);
    }



    public int calculateAge(EditText editAge) {
        String dateString = editAge.toString();


        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patron_onboarding);

        editAge = (EditText) findViewById(R.id.enterDOB); //Remember this is in the MM/DD/YYYY format
        editAge.setInputType(InputType.TYPE_NULL);
        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(PatronOnboarding.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editAge.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }
}