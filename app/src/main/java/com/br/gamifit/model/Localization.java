package com.br.gamifit.model;

import java.io.Serializable;

public class Localization implements Serializable{
    private double longitude;
    private double latitude;
    private String address;

    private final double EARTH_RADIUS = 6378.137;

    public Localization(){}

    public Localization(double latitude,double longitude,String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double calculateDistance(Localization otherLocalization){
        double thisLatitude = this.latitude * Math.PI/180;
        double thisLongitude = this.longitude * Math.PI/180;
        double otherLatitude = otherLocalization.getLatitude() * Math.PI/180;
        double otherLongitude = otherLocalization.getLongitude() * Math.PI/180;

        double lat1Cos = Math.cos(thisLatitude);
        double lat2Cos = Math.cos(otherLatitude);
        double lat1Sin = Math.sin(thisLatitude);
        double lat2Sin = Math.sin(otherLatitude);

        double lon2MinusLon1 = otherLongitude - thisLongitude;
        double lonCos = Math.cos(lon2MinusLon1);
        double distance = EARTH_RADIUS * Math.acos(lat1Cos*lat2Cos*lonCos + lat1Sin*lat2Sin);

        return distance;
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
