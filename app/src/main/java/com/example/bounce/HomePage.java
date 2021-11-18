package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {


    public void goToPatronOnboarding(View view) {
        Intent intent = new Intent(this, PatronOnboarding.class);
        startActivity(intent);
    }

    public void goToBarOnboarding(View view) {
        Intent intent = new Intent(this, BarOnboarding.class);
        startActivity(intent);
    }

    public void goToSignIn(View view) {
        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

    }
}