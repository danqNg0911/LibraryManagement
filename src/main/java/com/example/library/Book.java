package com.example.library;

public class Book {
    private String title;
    private String author;
    private String category;
    private String imageUrl;

    public Book(String title, String author, String category, String imageUrl) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;
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
}
