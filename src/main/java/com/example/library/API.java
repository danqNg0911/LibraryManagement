package com.example.library;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class API {
    private static final String API_KEY = "AIzaSyCZ9Ee9DuenXY5gvW6IcKh_NhTMnB5U4qM";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // Hàm lấy dữ liệu sách từ Google Books API
    public static String searchBooks(String title, String author, String category, String isbn) throws Exception {
        OkHttpClient client = new OkHttpClient();
        StringBuilder query = new StringBuilder();


        if (!isbn.isEmpty()) {
            query.append("isbn:").append(isbn);
        }
        if (!title.isEmpty()) {
            query.append("intitle:").append(title).append("+");
        }
        if (!author.isEmpty()) {
                query.append("inauthor:").append(author).append("+");
        }
        if (!category.isEmpty()) {
            query.append("subject:").append(category).append("+");
        }

        String url = BASE_URL + query.toString() + "&key=" + API_KEY;

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Unexpected code: " + response);
            }
            return response.body().string();
        }
    }
}

