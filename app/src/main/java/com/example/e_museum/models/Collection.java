package com.example.e_museum.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Collection implements MuseumEntity {
    private final int collectionID;
    private int museumID;
    private String name;
    private List<Thing> thingsList;

    public Collection() {
        collectionID = -1;
    }

    public Collection(ResultSet resultSet, List<Thing> thingsList) throws SQLException {
        collectionID = resultSet.getInt("collectionID");
        museumID = resultSet.getInt("museumID");
        name = resultSet.getString("name");
        this.thingsList = thingsList;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public int getMuseumID() {
        return museumID;
    }

    public String getName() {
        return name;
    }

    public List<Thing> getThingsList() {
        return thingsList;
    }

    public int getThingsNum() {
        if (thingsList != null)
            return thingsList.size();
        return 0;
    }
}
