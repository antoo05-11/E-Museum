package com.example.e_museum.entities;

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
    private String open_time;
    private String adult_ticket;
    private String student_ticket;
    private String child_over_6_ticket;
    private String child_under_6_ticket;
    private String the_disabled_ticket;

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
        this.open_time = resultSet.getString("open_time");
        this.adult_ticket = resultSet.getString("adult_ticket");
        this.student_ticket = resultSet.getString("student_ticket");
        this.child_over_6_ticket = resultSet.getString("child_over_6_ticket");
        this.child_under_6_ticket = resultSet.getString("child_under_6_ticket");
        this.the_disabled_ticket = resultSet.getString("the_disabled_ticket");
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

    public String getOpen_time() {
        return open_time;
    }

    public String getAdult_ticket() {
        return adult_ticket;
    }

    public String getStudent_ticket() {
        return student_ticket;
    }

    public String getChild_over_6_ticket() {
        return child_over_6_ticket;
    }

    public String getChild_under_6_ticket() {
        return child_under_6_ticket;
    }

    public String getThe_disabled_ticket() {
        return the_disabled_ticket;
    }
}
