package com.example.library;

import java.sql.*;

public class UserJDBC extends BaseJDBC{
    private static final String databaseURL = "jdbc:mysql://localhost:3306/librarymanagement";
    private static final String databaseUser = "root";
    private static final String databasePassword = "Hieu@123456";

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
        Connection databaseConnect = null;
        try {
            databaseConnect = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseConnect;
    }
}
