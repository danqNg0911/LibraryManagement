package com.example.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    // Các danh sách từ khóa
    private static final List<String> KEYWORDS_LIST_0 = List.of(
            "Java Programming",
            "Data Science",
            "Artificial Intelligence",
            "Machine Learning",
            "Deep Learning"
    );

    private static final List<String> KEYWORDS_LIST_1 = List.of(
            "To Kill a Mockingbird",
            "1984",
            "Pride and Prejudice",
            "The Great Gatsby",
            "Moby Dick"
    );

    private static final List<String> KEYWORDS_LIST_2 = List.of(
            "chrismas",
            "noel"
    );

    private static final List<String> KEYWORDS_LIST_3 = List.of(
            "flower",
            "firework"
    );

    private static final List<String> KEYWORDS_LIST_4 = List.of(
            "summer",
            "game"
    );

    private static final List<String> KEYWORDS_LIST_5 = List.of(
            "love",
            "autumn"
    );

    private static final List<String> KEYWORDS_LIST_6 = List.of(
            "football",
            "winter"
    );

    // Chọn danh sách từ khóa
    private static List<String> getKeywordsByListNumber(int listNumber) {
        switch (listNumber) {
            case 0:
                return KEYWORDS_LIST_0;
            case 1:
                return KEYWORDS_LIST_1;
            case 2:
                return KEYWORDS_LIST_2;
            case 3:
                return KEYWORDS_LIST_3;
            case 4:
                return KEYWORDS_LIST_4;
            case 5:
                return KEYWORDS_LIST_5;
            case 6:
                return KEYWORDS_LIST_6;
            default:
                throw new IllegalArgumentException("Invalid list number. Choose between 0 and 6.");
        }
    }

    // Hàm lấy thông tin sách từ từ khóa
    public static List<Book> getBooksFromKeywords(int listNumber, boolean isMostRecent, boolean isMostAdded, boolean isMostView) {
        List<String> keywords = getKeywordsByListNumber(listNumber); // Phần này giữ nguyên
        List<Book> books = new ArrayList<>();

        for (String keyword : keywords) {
            try {
                String jsonResponse = API.searchBooks(keyword, "", "", "");
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                if (jsonObject.has("items")) {
                    JsonArray items = jsonObject.getAsJsonArray("items");
                    for (int i = 0; i < items.size(); i++) {
                        JsonElement item = items.get(i);
                        JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");

                        String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown Title";
                        String authors = getAuthors(volumeInfo);
                        String categories = getCategories(volumeInfo);
                        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
                        String imageUrl = getImageUrl(volumeInfo);

                        // Tạo đối tượng Timestamp hiện tại
                        Timestamp addedDate = new Timestamp(System.currentTimeMillis());

                        // Tạo đối tượng Book đầy đủ
                        books.add(new Book(title, authors, categories, imageUrl, description, addedDate));
                    }
                }
            } catch (Exception e) {
                System.err.println("Không lấy được sách cho từ khóa: " + keyword);
            }
        }

        List<Book> filteredBooks = new ArrayList<>();

        for (int i = books.size() - 1; i >= 0; i--) {
            boolean shouldRemove = false;

            if (isMostRecent && i % 2 == 0) {
                shouldRemove = true;
            }
            else if (isMostAdded && (i == books.size() - 1 || i == books.size() - 2 || i == books.size() - 3 || i == books.size() - 4 || i == books.size() - 5)) {
                shouldRemove = true;
            }
            else if (isMostView && i % 2 != 0) {
                shouldRemove = true;
            }

            if (!shouldRemove) {
                filteredBooks.add(books.get(i)); // Thêm phần tử không bị xóa vào danh sách mới
            }
        }

        books = filteredBooks;

        return books;
    }

    private static String getAuthors(JsonObject volumeInfo) {
        if (volumeInfo.has("authors")) {
            JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
            List<String> authorList = new ArrayList<>();
            for (JsonElement authorElement : authorsArray) {
                authorList.add(authorElement.getAsString());
            }
            return String.join(", ", authorList);
        }
        return "Unknown Author";
    }

    private static String getCategories(JsonObject volumeInfo) {
        if (volumeInfo.has("categories")) {
            JsonArray categoriesArray = volumeInfo.getAsJsonArray("categories");
            List<String> categoryList = new ArrayList<>();
            for (JsonElement categoryElement : categoriesArray) {
                categoryList.add(categoryElement.getAsString());
            }
            return String.join(", ", categoryList);
        }
        return "Unknown Category";
    }

    private static String getImageUrl(JsonObject volumeInfo) {
        if (volumeInfo.has("imageLinks") && volumeInfo.getAsJsonObject("imageLinks").has("thumbnail")) {
            return volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
        }
        return null;
    }
}
