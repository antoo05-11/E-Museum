package com.example.e_museum.data_fetching.models;

import android.annotation.SuppressLint;

import com.example.e_museum.data_fetching.SQLConnection;
import com.example.e_museum.entities.Collection;
import com.example.e_museum.entities.Thing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionModel {
    @SuppressLint("DefaultLocale")
    public List<Collection> getCollectionByMuseumID(int museumID) throws SQLException {
        final String query = String.format("select * from collections where museumID = %d", museumID);
        final ResultSet resultSet = SQLConnection.getSqlConnection().getDataQuery(query);
        if (resultSet == null || !resultSet.isBeforeFirst()) return null;

        List<Collection> collections = new ArrayList<>();
        while (resultSet.next()) {
            List<Thing> things = Model.getInstance().getThingController().
                    getThingsByCollectionID(resultSet.getInt("collectionID"));
            collections.add(new Collection(resultSet, things));
        }
        return collections;
    }
}
