package com.example.e_museum.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Thing implements Serializable {
    private int museumID;
    private int thingID;
    private int images;
    private int videos;
    private String name;
    private String description;
    private String origin;
    private String age;
    private String size;

    public Thing(int museumID, int thingID, int images, int videos, String name, String description, String origin, String age, String size) {
        this.museumID = museumID;
        this.thingID = thingID;
        this.images = images;
        this.videos = videos;
        this.name = name;
        this.description = description;
        this.origin = origin;
        this.age = age;
        this.size = size;
    }

    public Thing(ResultSet resultSet) throws SQLException {
        this.museumID = resultSet.getInt("museumID");
        this.name = resultSet.getString("name");
        this.thingID = resultSet.getInt("thingID");
        this.images = resultSet.getInt("images");
        this.videos = resultSet.getInt("videos");
        this.description = resultSet.getString("description");
        this.origin = resultSet.getString("origin");
        this.age = resultSet.getString("age");
        this.size = resultSet.getString("size");
    }

    public int getMuseumID() {
        return museumID;
    }

    public void setMuseumID(int museumID) {
        this.museumID = museumID;
    }

    public int getThingID() {
        return thingID;
    }

    public void setThingID(int thingID) {
        this.thingID = thingID;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
