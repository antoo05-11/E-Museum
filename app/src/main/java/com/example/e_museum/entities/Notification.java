package com.example.e_museum.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notification {
    int museumID;
    int notificationID;
    String content;
    Date dateStart;
    Date dateEnd;
    String condition;
    String name;
    String shortText;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public int getMuseumID() {
        return museumID;
    }

    public void setMuseumID(int museumID) {
        this.museumID = museumID;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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
}
