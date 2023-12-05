package com.example.e_museum.data_fetching.models;

import com.example.e_museum.data_fetching.SQLConnection;

public final class Model {
    private static Model instance;
    private final SQLConnection sqlConnection;
    private final ThingModel thingController = new ThingModel();
    private final MuseumModel museumModel = new MuseumModel();
    private final MapModel mapModel = new MapModel();
    private final NotificationModel notificationModel = new NotificationModel();
    private final CollectionModel collectionModel = new CollectionModel();

    private Model() {
        sqlConnection = SQLConnection.getSqlConnection();
        final String url = "jdbc:mysql://b8fu1r5tflhnrnqjztht-mysql.services.clever-cloud.com:3306/b8fu1r5tflhnrnqjztht";
        final String username = "unxmdvotjktefgp8";
        final String password = "4XxtC2Ky5Dzz2AEEoC60";
        sqlConnection.connectServer(url, username, password);
    }

    public synchronized static Model getInstance() {
        if (instance == null) {
            synchronized (Model.class) {
                instance = new Model();
            }
        }
        return instance;
    }

    public SQLConnection getSqlConnection() {
        return sqlConnection;
    }

    public ThingModel getThingController() {
        return thingController;
    }

    public MuseumModel getMuseumModel() {
        return museumModel;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public NotificationModel getNotificationModel() {
        return notificationModel;
    }

    public CollectionModel getCollectionModel() {
        return collectionModel;
    }
}
