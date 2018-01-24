package com.example.jisungkim.app;

public class MarkerItem {

    double lat;
    double lon;
    String mark;

    public MarkerItem(double lat, double lon, String mark) {
        this.lat = lat;
        this.lon = lon;
        this.mark = mark;
    }

    //getter , setter 설정

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }


}
