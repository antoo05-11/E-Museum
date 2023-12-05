package com.example.e_museum.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Thing implements MuseumEntity {
    private int museumID;
    private int thingID;
    private final int images;
    private final int videos;
    private final String name;
    private final String description;
    private final String origin;
    private final String age;
    private final String size;
    private final int collectionID;
    private final int duration;

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
        this.collectionID = resultSet.getInt("collectionID");
        this.duration = resultSet.getInt("duration");
    }

    public int getDuration() {
        return duration;
    }

    public int getMuseumID() {
        return museumID;
    }

    public int getThingID() {
        return thingID;
    }

    public int getImages() {
        return images;
    }

    public int getVideos() {
        return videos;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getAge() {
        return age;
    }

    public String getSize() {
        return size;
    }

    public int getCollectionID() {
        return collectionID;
    }
}
