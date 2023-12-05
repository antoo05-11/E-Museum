package com.example.e_museum.data_fetching.models;

import android.annotation.SuppressLint;

import com.example.e_museum.data_fetching.SQLConnection;
import com.example.e_museum.entities.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("DefaultLocale")
public class NotificationModel {
    public Notification getNotificationByID(int notificationID) throws SQLException {
        final String query = String.format("select * from notifications where notificationID = %d", notificationID);
        ResultSet resultSet = SQLConnection.getSqlConnection().getDataQuery(query);
        if (resultSet == null || !resultSet.isBeforeFirst()) {
            return null;
        }
        resultSet.next();
        return new Notification(resultSet);
    }

    public List<Notification> getAllNotifications() throws SQLException {
        final String query = "select * from notifications";
        final List<Notification> notifications = new ArrayList<>();
        ResultSet resultSet = SQLConnection.getSqlConnection().getDataQuery(query);
        if (resultSet == null || !resultSet.isBeforeFirst()) {
            return null;
        }
        while (resultSet.next()) {
            notifications.add(new Notification(resultSet));
        }
        return notifications;
    }
}
