package com.example.trippersapp.Models;

public class User {

    public String fname;
    public String lname;
    public String contactno;
    public String emailadd;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmailadd() {
        return emailadd;
    }

    public void setEmailadd(String emailadd) {
        this.emailadd = emailadd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String password;

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
