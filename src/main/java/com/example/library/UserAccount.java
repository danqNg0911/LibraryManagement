package com.example.library;

public class UserAccount {
    private String name;
    private String username;
    private String phonenum;
    private String email;
    private int avatar;

    // Constructor
    public UserAccount(String name, String username, String phonenum, String email) {
        this.name = name;
        this.username = username;
        this.phonenum = phonenum;
        this.email = email;
    }

    // Constructor
    public UserAccount(String name, String username, String phonenum, String email, int avatar) {
        this.name = name;
        this.username = username;
        this.phonenum = phonenum;
        this.email = email;
        this.avatar = avatar;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhonenum() { return phonenum; }
    public void setPhonenum(String phonenum) { this.phonenum = phonenum; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAvatar() {
        return avatar;
    }
    public void setAvatar(int avaID) {
        this.avatar = avaID;
    }
}