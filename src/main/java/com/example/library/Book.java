package com.example.library;

import java.sql.Timestamp;

public class Book {
    private String title;
    private String author;
    private String category;
    private String imageUrl;
    private String description;
    private Timestamp addedDate; // Thêm trường ngày tháng

    public Book(String title, String author, String category, String imageUrl, String description, Timestamp addedDate) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.addedDate = addedDate;
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

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }
}
