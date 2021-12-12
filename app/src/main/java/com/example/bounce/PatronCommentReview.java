package com.example.bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PatronCommentReview extends AppCompatActivity {

    EditText commentBodyText;
    String userID;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron_comment_review);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("reviews");


        commentBodyText = (EditText) findViewById(R.id.commentBodyText);
    }

    public void goToBarInfo(View view) {
        Intent intent = new Intent(this, BarInfo.class);
        startActivity(intent);
    }

    private void writeNewComment(String userID, String barID, String text, long timestamp){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("reviews");
        String reviewID = reference.push().getKey();

        Review review = new Review(reviewID, userID, barID, text, timestamp);
        reference.child(reviewID).setValue(review);
    }


    //need to test this....
    public void postComment(View view) {

        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        long timestamp = System.currentTimeMillis();
        String text = commentBodyText.getText().toString();
        //TODO: GET BAR ID FROM THIS PAGE
        //TODO: once recyclerview is done, send extra intent with barID from prev screen

        String barID = "bar001";

        writeNewComment(userID, barID,text,timestamp);

        //TODO: Error handle for null fields

        Toast.makeText(PatronCommentReview.this, "Comment Posted!",
                Toast.LENGTH_SHORT).show();

        goToBarInfo(view);

    }
}