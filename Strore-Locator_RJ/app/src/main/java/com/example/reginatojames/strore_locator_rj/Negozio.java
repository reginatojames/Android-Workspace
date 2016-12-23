package com.example.reginatojames.strore_locator_rj;

/**
 * Created by Reginato James on 18/04/2016.
 */
public class Negozio {

    String name, phone, id, address, lat, longi;

    public Negozio(String name, String phone, String id, String address, String lat, String longi) {
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.longi = longi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }
}
