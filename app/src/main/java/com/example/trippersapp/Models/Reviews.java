package com.example.trippersapp.Models;

import com.google.firebase.database.ServerValue;

public class Reviews {

    private String content;
    private float rating;
    private Object timestamp;
    private String user_email;
    private String user_pfp;
    private String username;

    public Reviews() {
    }

    public Reviews(String content, float rating, String user_email, String user_pfp, String username ) {
        this.content = content;
        this.rating = rating;
        this.timestamp = ServerValue.TIMESTAMP;
        this.user_email = user_email;
        this.user_pfp = user_pfp;
        this.username = username;

    }

    public Reviews(String content, float rating, Object timestamp, String user_email, String user_pfp, String username) {
        this.content = content;
        this.rating = rating;
        this.timestamp = timestamp;
        this.user_email = user_email;
        this.user_pfp = user_pfp;
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pfp() {
        return user_pfp;
    }

    public void setUser_pfp(String user_pfp) {
        this.user_pfp = user_pfp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}



