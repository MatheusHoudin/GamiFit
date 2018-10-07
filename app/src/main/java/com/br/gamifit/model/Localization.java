package com.br.gamifit.model;

import java.io.Serializable;

public class Localization implements Serializable{
    private double longitude;
    private double latitude;
    private String address;

    public Localization(){}

    public Localization(double latitude,double longitude,String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
