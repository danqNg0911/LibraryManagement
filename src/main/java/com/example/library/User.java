package com.example.library;

public class User {
    public static String username;

    public String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public void clearUser() {
        username = null;
    }
}
