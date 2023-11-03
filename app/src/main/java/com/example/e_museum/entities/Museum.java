package com.example.e_museum.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Museum {
    int museumID;
    String name;
    String website;
    String address;
    String phoneNumber;
    String description;

    public Museum(int museumID, String name, String website, String address, String phoneNumber, String description) {
        this.museumID = museumID;
        this.name = name;
        this.website = website;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public Museum(ResultSet resultSet) throws SQLException {
        this.museumID = resultSet.getInt("museumID");
        this.name = resultSet.getString("name");
        this.website = resultSet.getString("website");
        this.address = resultSet.getString("address");
        this.phoneNumber = resultSet.getString("phoneNumber");
        this.description = resultSet.getString("description");
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
}
