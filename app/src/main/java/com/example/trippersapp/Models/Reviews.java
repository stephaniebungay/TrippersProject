package com.example.trippersapp.Models;

import com.google.firebase.database.ServerValue;

public class Reviews {

    private String content;
    private String user_email;
    private String  user_pfp;
    private String username;
    private String rating;
    private Object timestamp;

    public Reviews(){
    }

    public Reviews(String content, String user_email, String user_pfp, String username, String rating) {
        this.content = content;
        this.user_email = user_email;
        this.user_pfp = user_pfp;
        this.username = username;
        this.rating = rating;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Reviews(String content, String user_email, String user_pfp, String username, String rating, Object timestamp) {
        this.content = content;
        this.user_email = user_email;
        this.user_pfp = user_pfp;
        this.username = username;
        this.rating = rating;
        this.timestamp = timestamp;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
