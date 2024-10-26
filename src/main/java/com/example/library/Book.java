package com.example.library;

public class Book {
    private String title;
    private String author;
    private String category;
    private String imageUrl;
    private String description;

    public Book(String title, String author, String category, String imageUrl, String description) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
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
}
