package com.example.library;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIConnection {
    private static final String API_KEY = "AIzaSyCZ9Ee9DuenXY5gvW6IcKh_NhTMnB5U4qM"; // Thay bằng API Key của bạn
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // Hàm lấy dữ liệu sách từ Google Books API
    public String searchBooks(String searchQuery) throws Exception {
        OkHttpClient client = new OkHttpClient();

        // Tạo URL yêu cầu với từ khóa tìm kiếm
        String url = BASE_URL + searchQuery + "&key=" + API_KEY;

        // Tạo yêu cầu HTTP
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Thực hiện yêu cầu và nhận phản hồi
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Unexpected code: " + response);
            }

            // Chuyển đổi phản hồi thành chuỗi JSON
            return response.body().string();
        }
    }

    // Phân tích dữ liệu JSON và in thông tin sách
    public void parseBookData(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        // Lấy mảng chứa các thông tin sách
        for (var item : jsonObject.getAsJsonArray("items")) {
            JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
            String title = volumeInfo.get("title").getAsString();
            String authors = volumeInfo.get("authors").toString();
            System.out.println("Title: " + title + ", Authors: " + authors);
        }
    }
}
