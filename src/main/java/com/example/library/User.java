package com.example.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class User extends BaseJDBC implements LinkJDBC {

    @Override
    protected String getDatabaseURL() {
        return databaseUserURL;
    }

    @Override
    protected String getDatabaseUser() {
        return databaseUser;
    }

    @Override
    protected String getDatabasePassword() {
        return databaseUserPassword;
    }

    @Override
    protected Connection connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseUserURL, databaseUser, databaseUserPassword);
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

}
