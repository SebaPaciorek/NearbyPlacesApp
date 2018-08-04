package com.example.nearbyplacesapp.model.places.bill;

public class BillModel {


    private double Price;
    private String Date;
    private String Adress;
    private String PlaceName;
    private double GeoLong;
    private double GeoLat;

    public BillModel() {
    }

    public BillModel(double price, String date, String adress, String placeName, double geoLong, double geoLat) {
        Price = price;
        Date = date;
        Adress = adress;
        PlaceName = placeName;
        GeoLong = geoLong;
        GeoLat = geoLat;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public double getGeoLong() {
        return GeoLong;
    }

    public void setGeoLong(double geoLong) {
        GeoLong = geoLong;
    }

    public double getGeoLat() {
        return GeoLat;
    }

    public void setGeoLat(double geoLat) {
        GeoLat = geoLat;
    }
}
