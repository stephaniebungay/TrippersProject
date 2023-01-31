package com.example.trippersapp.Models;

import java.util.List;

public class Packages {

    public Integer days;
    public Integer nights;
    public String package_attraction;
    public String package_availability;
    public String package_country;
    public String package_description;
    public String package_id;
    public List<String> package_images;
    public double package_latitude;
    public double package_longitude;
    public String package_name;
    public String package_poster;
    public Integer package_price;
    public String package_region;
    public String package_video;
    public String tourStatus;



    public Packages(Integer days, Integer nights, String package_attraction, String package_availability, String package_country, String package_description, String package_id, List<String> package_images, double package_latitude, double package_longitude, String package_name, String package_poster, Integer package_price, String package_region, String package_video, String tourStatus) {
        this.days = days;
        this.nights = nights;
        this.package_attraction = package_attraction;
        this.package_availability = package_availability;
        this.package_country = package_country;
        this.package_description = package_description;
        this.package_id = package_id;
        this.package_images = package_images;
        this.package_latitude = package_latitude;
        this.package_longitude = package_longitude;
        this.package_name = package_name;
        this.package_poster = package_poster;
        this.package_price = package_price;
        this.package_region = package_region;
        this.package_video = package_video;
        this.tourStatus = tourStatus;
    }

    public Packages(){}


    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }

    public String getPackage_attraction() {
        return package_attraction;
    }

    public void setPackage_attraction(String package_attraction) {
        this.package_attraction = package_attraction;
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

    public List<String> getPackage_images() {
        return package_images;
    }

    public void setPackage_images(List<String> package_images) {
        this.package_images = package_images;
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



    public String getPackage_poster() {
        return package_poster;
    }

    public void setPackage_poster(String package_poster) {
        this.package_poster = package_poster;
    }

    public Integer getPackage_price() {
        return package_price;
    }

    public void setPackage_price(Integer package_price) {
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

    public String getTourStatus() {
        return tourStatus;
    }

    public void setTourStatus(String tourStatus) {
        this.tourStatus = tourStatus;
    }
}
