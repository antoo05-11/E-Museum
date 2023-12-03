package com.example.e_museum.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapGuide {
    private final String title;
    private final String content;
    private final String imageURL;

    private final int posX;
    private final int posY;

    public MapGuide(ResultSet resultSet) throws SQLException {
        this.title = resultSet.getString("title");
        this.content = resultSet.getString("content");
        this.imageURL = resultSet.getString("url");
        this.posX = resultSet.getInt("posX");
        this.posY = resultSet.getInt("posY");
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
