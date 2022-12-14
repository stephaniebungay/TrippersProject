package com.example.trippersapp.Models;

import java.util.HashMap;

public class Packages {

    public String package_attractions;
    public String package_availability;
    public String package_country;
    public String package_description;
    public String package_id;
    public double package_latitude;
    public double package_longitude;
    public String package_name;
    public HashMap<String, Object> package_photos;
    public String package_poster;
    public String package_price;
    public String package_region;
    public String package_video;

    public Packages(){}

    public Packages(String package_attractions, String package_availability, String package_country, String package_description, String package_id, double package_latitude, double package_longitude, String package_name, HashMap<String, Object> package_photos, String package_poster, String package_price, String package_region, String package_video) {
        this.package_attractions = package_attractions;
        this.package_availability = package_availability;
        this.package_country = package_country;
        this.package_description = package_description;
        this.package_id = package_id;
        this.package_latitude = package_latitude;
        this.package_longitude = package_longitude;
        this.package_name = package_name;
        this.package_photos = package_photos;
        this.package_poster = package_poster;
        this.package_price = package_price;
        this.package_region = package_region;
        this.package_video = package_video;
    }


    public String getPackage_attractions() {
        return package_attractions;
    }

    public void setPackage_attractions(String package_attractions) {
        this.package_attractions = package_attractions;
    }

    public String getPackage_availability() {
        return package_availability;
    }

    public void setPackage_availability(String package_availability) {
        this.package_availability = package_availability;
    }

    public String getPackage_country() {
        return package_country;
    }

    public void setPackage_country(String package_country) {
        this.package_country = package_country;
    }

    public String getPackage_description() {
        return package_description;
    }

    public void setPackage_description(String package_description) {
        this.package_description = package_description;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }


    public double getPackage_latitude() {
        return package_latitude;
    }

    public void setPackage_latitude(double package_latitude) {
        this.package_latitude = package_latitude;
    }

    public double getPackage_longitude() {
        return package_longitude;
    }

    public void setPackage_longitude(double package_longitude) {
        this.package_longitude = package_longitude;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public HashMap<String, Object> getPackage_photos() {
        return package_photos;
    }

    public void setPackage_photos(HashMap<String, Object> package_photos) {
        this.package_photos = package_photos;
    }

    public String getPackage_poster() {
        return package_poster;
    }

    public void setPackage_poster(String package_poster) {
        this.package_poster = package_poster;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }


    public String getPackage_region() {
        return package_region;
    }

    public void setPackage_region(String package_region) {
        this.package_region = package_region;
    }




    public String getPackage_video() {
        return package_video;
    }

    public void setPackage_video(String package_video) {
        this.package_video = package_video;
    }
}
