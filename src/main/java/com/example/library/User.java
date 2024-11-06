package com.example.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class User extends BaseJDBC {

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

    UserJDBC userJDBC = new UserJDBC();

    public static String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        User.username = username;
    }

    /*public String getName() {
        return userJDBC.getName(username);
    }*/

    /*public static void setName(String username) {
        User.username = username;
    }*/

    /*public String getPassword() {
        return userJDBC.getPassword(username);
    }*/

    /*public static void setPassword(String username) {
        User.username = username;
    }*/

    public boolean nameUpdate(String username, String newName) {
        if (userJDBC.nameUpdate(username, newName)) {
            return true;
        }
        else {
            return false;
        }
    }

    // gán = null để log out
    public void clearUser() {
        username = null;
    }
}
