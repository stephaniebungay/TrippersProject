package com.example.trippersapp.Models;

public class Bookings {

    private String customer;
    private String date;
    private String destination;
    private String email;
    private String note;
    private String pax;
    private String phone;
    private String payment;

    public Bookings(String customer, String date, String destination, String email, String note, String pax, String phone, String payment) {
        this.customer = customer;
        this.date = date;
        this.destination = destination;
        this.email = email;
        this.note = note;
        this.pax = pax;
        this.phone = phone;
        this.payment = payment;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = pax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }




}
