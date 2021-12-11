package com.example.bounce;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class barname {

    private String barName;

    public barname(){}

    public String getBarName(){
        return barName;
    }

    public void setBarName(String barName){
        this.barName = barName;
    }
}
