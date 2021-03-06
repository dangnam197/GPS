package com.example.dangn.gps.Model;

public class CityWeather {
    private int id;
    private String name;
    private String country;
    private double lon;
    private double lat;

    public CityWeather(int id, String name, String country, double lon, double lat) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lon = lon;
        this.lat = lat;
    }

    public CityWeather(int id, String name, double lon, double lat) {

        this.id = id;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
