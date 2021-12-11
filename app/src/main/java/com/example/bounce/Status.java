package com.example.bounce;


import java.util.Date;

public class Status {

    public String userID;
    public String postID;
    public String title;
    public String body;
    public String date;
    public String startTime;
    public int hours;


    public Status(String userID, String postID, String title, String body, String date, String startTime, int hours) {
        this.userID = userID;
        this.postID = postID;
        this.title = title;
        this.body = body;
        this.date = date;
        this.startTime = startTime;
        this.hours = hours;


    }

}
