package com.example.bounce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        Log.i("Redeem", "Creating redeem page");
        slider = (SlideToActView) findViewById(R.id.slider);

        slider.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                int dealNum = getIntent().getIntExtra("deal", 0);
                Log.i("Redeem", "Redeem swiped");

                if (dealNum == 1) {
                    Intent intent = new Intent(Redeem.this, BarInfo.class);
                    intent.putExtra("dealNum", 1);
                    startActivity(intent);
                }
                if (dealNum == 2) {
                    Intent intent = new Intent(Redeem.this, BarInfo.class);
                    intent.putExtra("dealNum", 2);
                    startActivity(intent);
                }
            }
        });
    }
}