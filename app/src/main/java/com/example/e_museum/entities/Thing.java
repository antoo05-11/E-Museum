package com.example.e_museum.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Thing {
    int museumID;
    int thingID;
    int images;
    int videos;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String description;
    String shortText;

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public Thing(int museumID, int thingID, int images, int videos, String description) {
        this.museumID = museumID;
        this.thingID = thingID;
        this.images = images;
        this.videos = videos;
        this.description = description;
    }

    public Thing(ResultSet resultSet) throws SQLException {
        this.museumID = resultSet.getInt("museumID");
        this.thingID = resultSet.getInt("thingID");
        this.images = resultSet.getInt("images");
        this.videos = resultSet.getInt("videos");
        this.description = resultSet.getString("description");
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
}
