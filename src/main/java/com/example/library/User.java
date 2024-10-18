package com.example.library;

import java.sql.SQLException;

public class User {
    public static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getName() {
        return UserJDBC.getName(username);
    }

    /*public static void setName(String username) {
        User.username = username;
    }*/

    public static String getPassword() {
        return UserJDBC.getPassword(username);
    }

    /*public static void setPassword(String username) {
        User.username = username;
    }*/

    public static boolean nameUpdate(String username, String newName) {
        if (UserJDBC.nameUpdate(username, newName)) {
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
