package com.example.e_museum.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Museum implements MuseumEntity {
    private final int museumID;
    private String name;
    private String website;
    private String address;
    private String phoneNumber;
    private String description;
    private float pos_longitude;
    private float pos_latitude;
    private int imagesNum;

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
        this.imagesNum = resultSet.getInt("imagesNum");
    }

    public int getMuseumID() {
        return museumID;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public float getPos_longitude() {
        return pos_longitude;
    }

    public float getPos_latitude() {
        return pos_latitude;
    }

    public int getImagesNum() {
        return imagesNum;
    }
}
