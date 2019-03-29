package com.example.quakeapp;

public class Earthquake {


    private String distance;
    private  String location;
    private String magnitude;
    private  String date;

    Earthquake(String magnitude,String distance,String location,String date){


        this.magnitude = magnitude;
        this.distance = distance;
        this.location = location;
        this.date = date;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }



}
