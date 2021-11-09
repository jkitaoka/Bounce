package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignInPage extends AppCompatActivity {

    public void loginClicked(View view) {
        //Get email from user input
        EditText emailEntered = (EditText) findViewById(R.id.email);
        String email = emailEntered.getText().toString();
        //Save email in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("bounce", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("email", email).apply();
        //Go to Main page (get from Hannah/John)
        goToBarHome(view);//test - John
    }


    public void goToPatronOnboarding(View view) {
        Intent intent = new Intent(this, PatronOnboarding.class);
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

        String emailKey = "email";
        SharedPreferences sharedPreferences = getSharedPreferences("bounce", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(emailKey, "");

        if (!email.equals("")) { //User has logged in
            //Go to main page (get from Hannah/John)


        } else { //User hasn't logged in yet
            setContentView(R.layout.sign_in_page);
        }
    }
}