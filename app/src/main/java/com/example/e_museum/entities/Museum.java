package com.example.e_museum.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Museum implements MuseumEntity {
    private int museumID;
    private String name;
    private String website;
    private String address;
    private String phoneNumber;
    private String description;
    private float pos_longitude;
    private float pos_latitude;

    public Museum() {
        museumID = -1;
    }

    public Museum(ResultSet resultSet) throws SQLException {
        this.museumID = resultSet.getInt("museumID");
        this.name = resultSet.getString("name");
        this.website = resultSet.getString("website");
        this.address = resultSet.getString("address");
        this.phoneNumber = resultSet.getString("phoneNumber");
        this.description = resultSet.getString("description");
        this.pos_latitude = resultSet.getFloat("pos_latitude");
        this.pos_longitude = resultSet.getFloat("pos_longitude");
    }

    public int getMuseumID() {
        return museumID;
    }

    public void setMuseumID(int museumID) {
        this.museumID = museumID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPos_longitude() {
        return pos_longitude;
    }

    public void setPos_longitude(float pos_longitude) {
        this.pos_longitude = pos_longitude;
    }

    public float getPos_latitude() {
        return pos_latitude;
    }

    public void setPos_latitude(float pos_latitude) {
        this.pos_latitude = pos_latitude;
    }
}
