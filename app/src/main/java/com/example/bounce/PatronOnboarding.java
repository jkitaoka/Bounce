package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PatronOnboarding extends AppCompatActivity {

    public void backClicked(View view) {
        Intent intent = new Intent (this, SignInPage.class);
        startActivity(intent);
    }
    public void createAccount(View view) {
        Intent intent = new Intent (this, ContentMainPage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patron_onboarding);
    }
}