package com.example.e_museum.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notification implements MuseumEntity {
    private int museumID;
    private final int notificationID;
    private String content;
    private Date dateStart;
    private Date dateEnd;
    private String condition;
    private String name;
    private String shortText;

    public Notification() {
        notificationID = -1;
    }

    public Notification(ResultSet resultSet) throws SQLException {
        this.museumID = resultSet.getInt("museumID");
        this.notificationID = resultSet.getInt("notificationID");
        this.content = resultSet.getString("content");
        this.dateStart = resultSet.getDate("dateStart");
        this.dateEnd = resultSet.getDate("dateEnd");
        this.condition = resultSet.getString("condition");
        this.shortText = resultSet.getString("shortText");
        this.name = resultSet.getString("name");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortText() {
        return shortText;
    }

    public int getMuseumID() {
        return museumID;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public String getContent() {
        return content;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getCondition() {
        return condition;
    }
}
