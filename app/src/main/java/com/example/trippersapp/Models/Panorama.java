package com.example.trippersapp.Models;

public class Panorama {
    private String destination;
    private String image;
    private String name;

    public Panorama(String destination, String image, String name) {
        this.destination = destination;
        this.image = image;
        this.name = name;
    }
    public Panorama(){

    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
