package com.example.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Manager extends BaseJDBC implements LinkJDBC{

    private static final String MANAGER_DATA_FILE_PATH = LinkSetting.MANAGER_LIST_FILE_PATH.getLink();

    @Override
    protected String getDatabaseURL() {
        return databaseManagerURL;
    }

    @Override
    protected String getDatabaseUser() {
        return databaseManager;
    }

    @Override
    protected String getDatabasePassword() {
        return databaseManagerPassword;
    }

    @Override
    protected Connection connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseManagerURL, databaseManager, databaseManagerPassword);
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

    ManagerJDBC managerJDBC = new ManagerJDBC();

    public static String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Manager.username = username;
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
        if (managerJDBC.nameUpdate(username, newName)) {
            return true;
        }
        else {
            return false;
        }
    }

}
