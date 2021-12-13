package com.example.bounce;

import java.io.StringBufferInputStream;

public class StatusRedemption {


    public String postID;
    public String barID;
    public String userID;
    public String activityID;

    public StatusRedemption(){

    }

    public StatusRedemption(String activityID, String postID, String barID, String userID){
        this.activityID = activityID;
        this.postID = postID;
        this.barID = barID;
        this.userID=  userID;
    }

}
