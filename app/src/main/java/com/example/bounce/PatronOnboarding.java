package com.example.bounce;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class PatronOnboarding extends AppCompatActivity {
    EditText editFirstName, editLastName, editAge, editEmail, editPassword;
    String firstName, lastName, email, password;

    private static final String TAG = "PatronOnboarding";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public void backClicked(View view) {
        Intent intent = new Intent (this, SignInPage.class);
        startActivity(intent);
    }

    public void pickDate(View view) {
        editAge = (EditText) findViewById(R.id.enterDOB);

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

        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editAge.setText(month+1+"/"+day+"/"+year);
            }
        };
    }

    public void createClicked(View view) {
        Log.i(TAG, "create clicked");
        Intent intent = new Intent(this, ContentMainPage.class);
        startActivity(intent);

        firstName = editFirstName.getText().toString();
        lastName = editLastName.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

        writeNewUser(email, password, firstName, lastName);
    }

    public void writeNewUser(String email, String password, String firstName, String lastName) { // Test that this works
        User user = new User(password, firstName, lastName, false);

        Log.i("PatronOnboarding", "Write user to database");

        //Write to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
//        // Need to figure out how to make the child the email rather than how you're doing it here
        reference.child("daddydevito@gmail.com").setValue(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patron_onboarding);

        editFirstName = (EditText) findViewById(R.id.enterFirstName);
        editLastName = (EditText) findViewById(R.id.enterLastName);
        editEmail = (EditText) findViewById(R.id.enterEmail);
        editPassword = (EditText) findViewById(R.id.enterPassword);
    }
}