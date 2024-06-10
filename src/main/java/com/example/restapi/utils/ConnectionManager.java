package com.example.restapi.utils;

import com.example.restapi.exceptions.DatabaseNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private ConnectionManager() {
    }
    private static final ConnectionManager INSTANCE = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException, DatabaseNotFoundException {
        try {
            String dbPath = "C:\\Users\\springboot\\IdeaProjects\\RestAPI\\identifier.sqlite";
            // change variable dbPath to your absolute path for sqlite db.

            return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException();
        }
    }

}
