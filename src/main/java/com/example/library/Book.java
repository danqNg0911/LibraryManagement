package com.example.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String author;
    private String category;
    private String imageUrl;
    private String description;

    private String username;
    private String source;
    private Date date;
    private String status;
    private int id;

    public Book(String title, String author, String category, String imageUrl, String description, Date date) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.date = date;
    }

    public Book(String title, String author, String category, String imageUrl, String description) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Book(String title, String author, String category, String imageUrl, String description, String username, String source, Date date, int id) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.username = username;
        this.source = source;
        this.date = date;
        this.id = id;
    }

    public Book(String title, String author, String category, String imageUrl, String description, int id) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public String getSource() {
        return source;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
