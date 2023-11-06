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

    public ResultSet getDataQuery(String query) throws SQLException {
        ResultSet resultSet = null;
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            Log.d("getDataQuery: ", query);
            throw e;
        }
        return resultSet;
    }


    public int updateQuery(String query) {
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
