package com.example.francesco.mytravel.utils;

public class DestItem {

    public String name;
    public String country;
    public String id;
    public String trip_id;
    public String latitude;
    public String longitude;

    public DestItem(String n, String c, String i, String t, String lat, String lon) {
        name = n;
        country = c;
        id = i;
        trip_id = t;
        latitude = lat;
        longitude = lon;
    }
}
