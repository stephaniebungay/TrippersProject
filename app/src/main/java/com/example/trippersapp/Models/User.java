package com.example.trippersapp.Models;

public class User {

    public String fname, lname, contactno, emailadd, password;

    public User(){

    }

    public User(String fname, String lname, String contactno, String emailadd, String password){
        this.fname = fname;
        this.lname = lname;
        this.contactno = contactno;
        this.emailadd = emailadd;
        this.password = password;
    }

}
