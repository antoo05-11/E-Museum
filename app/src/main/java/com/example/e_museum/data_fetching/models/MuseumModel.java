package com.example.e_museum.data_fetching.models;

import com.example.e_museum.entities.Museum;
import com.example.e_museum.data_fetching.SQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MuseumModel {
    public List<Museum> getAllMuseums() throws SQLException {
        final String query = "select * from museums";
        ResultSet resultSet = SQLConnection.getSqlConnection().getDataQuery(query);
        if (resultSet == null || !resultSet.isBeforeFirst()) return null;

        List<Museum> museums = new ArrayList<>();
        while (resultSet.next()) {
            museums.add(new Museum(resultSet));
        }

        return museums;
    }
}
