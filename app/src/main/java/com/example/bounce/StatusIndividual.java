package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StatusIndividual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_individual);
    }

    public void goToStatusMain(View view) {
        Intent intent = new Intent(this, statusMain.class);
        startActivity(intent);
    }
}