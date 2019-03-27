package com.example.quakeapp;

public class Earthquake {

    private  String location;
    private String magnitude;
    private  String date;

    Earthquake(String location,String magnitude,String date){

        this.location = location;
        this.magnitude = magnitude;
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


}
