package com.example.bounce;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ncorti.slidetoact.SlideToActView;

public class Redeem extends AppCompatActivity {
    SlideToActView slider;
    Button redeem1, redeem2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem);

        slider = findViewById(R.id.slider);
        redeem1 = findViewById(R.id.deal1);
        redeem2 = findViewById(R.id.deal2);

        slider.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {

            }
        });
    }
}