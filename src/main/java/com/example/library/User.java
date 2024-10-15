package com.example.library;

public class User {
    //private static User instance = new User();
    //private User currentLoggedinUser;
    //private String username;
    //private User() {};

    public static String username;

    /*public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }*/

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
