package com.LBS.DreamHome;


import java.io.Serializable;

public class HomeData implements Serializable {
    public String Price;
    public String Agreement;
    public String BedRooms;
    public String BathRooms;
    public String Toilets;
    public String WardRobe;
    public String Beds;
    public String Fans;
    public String Lights;
    public String ModularKitchen;
    public String AC;
    public String Inverter;
    public String PhoneNo;
    public String url;
    public String RentTo;
    public String Owner;
    public String Date;
    public double lat;
    public double lng;
    public String UID;


    public HomeData() {
    }

    public HomeData(String Price,
                    String Agreement,
                    String BedRooms,
                    String BathRooms,
                    String Toilets,
                    String WardRobe,
                    String Beds,
                    String Fans,
                    String Lights,
                    String ModularKitchen,
                    String AC,
                    String Inverter,
                    String PhoneNo,
                    String url,
                    String RentTo,
                    String Owner,
                    String Date,
                    double lat,
                    double lng,
                    String UID) {
        this.Price = Price;
        this.Agreement = Agreement;
        this.BedRooms = BedRooms;
        this.BathRooms = BathRooms;
        this.Toilets = Toilets;
        this.WardRobe = WardRobe;
        this.Beds = Beds;
        this.Fans = Fans;
        this.Lights = Lights;
        this.ModularKitchen = ModularKitchen;
        this.AC = AC;
        this.Inverter = Inverter;
        this.PhoneNo = PhoneNo;
        this.url = url;
        this.RentTo = RentTo;
        this.Owner = Owner;
        this.Date = Date;
        this.lat = lat;
        this.lng = lng;
        this.UID = UID;
    }

    /** public String getPrice() {
     return Price;
     }

     public void setPrice(String price) {
     Price = price;
     }

     public String getAgreement() {
     return Agreement;
     }

     public void setAgreement(String agreement) {
     Agreement = agreement;
     }

     public String getBedRooms() {
     return BedRooms;
     }

     public void setBedRooms(String bedRooms) {
     BedRooms = bedRooms;
     }

     public String getBathRooms() {
     return BathRooms;
     }

     public void setBathRooms(String bathRooms) {
     BathRooms = bathRooms;
     }

     public String getToilets() {
     return Toilets;
     }

     public void setToilets(String toilets) {
     Toilets = toilets;
     }

     public String getWardRobe() {
     return WardRobe;
     }

     public void setWardRobe(String wardRobe) {
     WardRobe = wardRobe;
     }

     public String getBeds() {
     return Beds;
     }

     public void setBeds(String beds) {
     Beds = beds;
     }

     public String getFans() {
     return Fans;
     }

     public void setFans(String fans) {
     Fans = fans;
     }

     public String getLights() {
     return Lights;
     }

     public void setLights(String lights) {
     Lights = lights;
     }

     public String getModularKitchen() {
     return ModularKitchen;
     }

     public void setModularKitchen(String modularKitchen) {
     ModularKitchen = modularKitchen;
     }

     public String getAC() {
     return AC;
     }

     public void setAC(String AC) {
     this.AC = AC;
     }

     public String getInverter() {
     return Inverter;
     }

     public void setInverter(String inverter) {
     Inverter = inverter;
     }

     public String getPhoneNo() {
     return PhoneNo;
     }

     public void setPhoneNo(String phoneNo) {
     PhoneNo = phoneNo;
     }**/
}