package com.example.bounce;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PatronOnboarding extends AppCompatActivity {
    EditText editFirstName, editLastName, editEmail, editPassword;
    String firstName, lastName, email, password, userID;
    TextView editAge;

    FirebaseAuth mAuth;

    private static final String TAG = "PatronOnboarding";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public void goToHomePage(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void pickDate(View view) {
        Log.i("PatronOnboarding", "Pick date clicked!");
        editAge = (TextView) findViewById(R.id.enterDOB);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Log.i("PatronOnboarding", "Setting date..."); // Only enters on second click... why?
                editAge.setText(month + 1 + "/" + day + "/" + year);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                PatronOnboarding.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        Calendar max = Calendar.getInstance();
        max.set(year-21, month, day); //Today's date 21 years ago to ensure user is 21
        dialog.getDatePicker().setMaxDate(max.getTimeInMillis());
        Calendar min = Calendar.getInstance();
        min.set(year-120, month, day); // The oldest person alive is 118, so max age is 120 :)
        dialog.getDatePicker().setMinDate(min.getTimeInMillis());
        Log.i("PatronOnboarding", "Showing dialog");

        dialog.show();
    }

    public void createClicked(View view) {
        Log.i(TAG, "create clicked");

        firstName = editFirstName.getText().toString();
        lastName = editLastName.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with signed-in user's information
                            Log.i(TAG, "Creating Firebase User");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid();

                            writeNewUser(userID, firstName, lastName);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user
                            Toast.makeText(PatronOnboarding.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        // Get user from database
//        if (user.getEmail()) in database .isB
        Intent intent = new Intent(this, ContentMainPage.class);
        startActivity(intent);
    }

    public void writeNewUser(String userID, String firstName, String lastName) {
        User user = new User(firstName, lastName);

        Log.i(TAG, "Write user to database");

        //Write to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        Log.i(TAG, "Write user to database");
        reference.child(userID).setValue(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patron_onboarding);

        mAuth = FirebaseAuth.getInstance();

        editFirstName = (EditText) findViewById(R.id.enterFirstName);
        editLastName = (EditText) findViewById(R.id.enterLastName);
        editEmail = (EditText) findViewById(R.id.enterEmail);
        editPassword = (EditText) findViewById(R.id.enterPassword);
    }
}