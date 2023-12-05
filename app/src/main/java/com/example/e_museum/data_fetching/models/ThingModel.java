package com.example.e_museum.data_fetching.models;

import android.annotation.SuppressLint;

import com.example.e_museum.entities.Thing;
import com.example.e_museum.data_fetching.SQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("DefaultLocale")
public class ThingModel {

    public Thing getThingByID(int thingID) throws SQLException {
        final String query = String.format("select * from things where thingID = %d", thingID);
        ResultSet resultSet = SQLConnection.getSqlConnection().getDataQuery(query);
        if (resultSet == null || !resultSet.isBeforeFirst()) {
            return null;
        }
        resultSet.next();
        return new Thing(resultSet);
    }

    public List<Thing> getThingsByCollectionID(int collectionID) throws SQLException {
        final List<Thing> things = new ArrayList<>();
        final String query = String.format("select * from things where collectionID = %d", collectionID);
        final ResultSet resultSet = SQLConnection.getSqlConnection().getDataQuery(query);
        if (resultSet == null || !resultSet.isBeforeFirst()) {
            return null;
        }
        while (resultSet.next()) {
            things.add(new Thing(resultSet));
        }
        return things;
    }
}
