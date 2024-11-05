package com.example.library;

import java.sql.*;

public class UserJDBC extends BaseJDBC{
    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/useraccount";
    private static final String databaseUser = "root";
    private static final String databasePassword = "Haidang0911.";

    @Override
    protected String getDatabaseURL() {
        return databaseURL;
    }

    @Override
    protected String getDatabaseUser() {
        return databaseUser;
    }

    @Override
    protected String getDatabasePassword() {
        return databasePassword;
    }

    @Override
    protected Connection connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    protected void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
