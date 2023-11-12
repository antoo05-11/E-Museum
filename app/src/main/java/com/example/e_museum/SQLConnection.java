package com.example.e_museum;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
    public Connection connection;
    private boolean reconnecting;
    private static SQLConnection sqlConnection;

    String url, username, password;

    public static SQLConnection getSqlConnection() {
        if (sqlConnection == null) {
            sqlConnection = new SQLConnection();
        }
        return sqlConnection;
    }

    public boolean isReconnecting() {
        return reconnecting;
    }

    public SQLConnection() {
        reconnecting = true;
    }

    public void connectServer(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        connectServer();
    }

    public ResultSet getDataQuery(String query) {
        connectServer();
        ResultSet resultSet = null;
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {

            if (e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException
                    || e instanceof com.mysql.jdbc.exceptions.jdbc4.CommunicationsException) {
                connectServer();
                return getDataQuery(query);
            }
            Log.d("getDataQuery: ", e.getClass().toString());
        }
        return resultSet;
    }

    private void connectServer() {
        while (connection == null) {
            try {
                Log.i("Database", "Connecting");
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                reconnecting = false;
            } catch (SQLException e) {
                Log.i("Database", "Connection failed");
                reconnecting = true;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public int updateQuery(String query) {
        connectServer();
        int rowsAffected = 0;
        try (Statement statement = connection.createStatement()) {
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {

            System.out.println(query);
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }

    public void addClosingWork() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }
}
