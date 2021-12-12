package com.example.bounce;

public class Review {
    String reviewID,userID,barID,text;
    long timestamp;


    public Review(String reviewID, String userID, String barID, String text, long timestamp) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.barID = barID;
        this.text = text;
        this.timestamp = timestamp;
    }
}
