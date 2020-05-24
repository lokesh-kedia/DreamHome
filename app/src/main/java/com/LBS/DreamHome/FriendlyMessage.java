package com.LBS.DreamHome;

public class FriendlyMessage {

    private String Price;
    private String BedRooms;
    private String url;
    private String PhoneNo;
    private String Area;
    private String Owner;
    private double lat;
    private double lng;
    private String Avail;
    private String Date;


    public FriendlyMessage() {
    }


    public FriendlyMessage(String Price, String BedRooms, String url, String PhoneNo, String Area, String Owner, double lat, double lng, String Avail, String Date) {
        this.Price = Price;
        this.BedRooms = BedRooms;
        this.url = url;
        this.PhoneNo = PhoneNo;
        this.Area = Area;
        this.Owner = Owner;
        this.lat = lat;
        this.lng = lng;
        this.Avail = Avail;
        this.Date = Date;

    }


    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getBedRooms() {
        return BedRooms;
    }

    public void setBedRooms(String BedRooms) {
        this.BedRooms = BedRooms;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public void setOwner(String Owner) {
        this.Owner = Owner;
    }

    public String getOwner() {
        return Owner;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAvail() {
        return Avail;
    }

    public void setAvail(String avail) {
        this.Avail = avail;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }
}