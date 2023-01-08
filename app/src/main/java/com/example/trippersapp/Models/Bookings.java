package com.example.trippersapp.Models;

public class Bookings {

    private String country;
    private String customer;
    private String date;
    private String destination;
    private String email;
    private String note;
    private String pax;
    private String payment;
    private String phone;
    private String process;
    private String region;

    public Bookings(String country, String customer, String date, String destination, String email, String note, String pax, String payment, String phone, String process, String region) {
        this.country = country;
        this.customer = customer;
        this.date = date;
        this.destination = destination;
        this.email = email;
        this.note = note;
        this.pax = pax;
        this.payment = payment;
        this.phone = phone;
        this.process = process;
        this.region = region;
    }
    public Bookings(){

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEmail() {
        return email;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }






}