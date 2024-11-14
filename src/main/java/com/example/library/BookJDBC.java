package com.example.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookJDBC implements LinkJDBC {
    private Connection connection;

    protected String getDatabaseURL() {
        return databaseUserURL;
    }

    protected String getDatabaseUser() {
        return databaseUser;
    }

    protected String getDatabasePassword() {
        return databaseUserPassword;
    }

    public static Connection connectToDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseUserURL, databaseUser, databaseUserPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    protected void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra kết nối tới Database
    public boolean testDatabaseConnection() {
        try (Connection databaseConnect = connectToDatabase()) {
            if (databaseConnect != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error Database !");
            e.printStackTrace();
            return false;
        }
    }

    // Them sach vao database
    public static boolean addBookToDatabase(String username, String isbn, String title, String author, String category, String imageUrl, String description) {
        String query = "INSERT INTO `books` (username, isbn, title, author, category, imageUrl, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, isbn);
            sqlStatement.setString(3, title);
            sqlStatement.setString(4, author);
            sqlStatement.setString(5, category);
            sqlStatement.setString(6, imageUrl);
            sqlStatement.setString(7, description);

            int updateToDatabse = sqlStatement.executeUpdate();
            if (updateToDatabse > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkBook(String username, String title, String author) {
        String query = "SELECT * FROM books where username=? and title=? and author=?";

        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, title);
            sqlStatement.setString(3, author);

            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Book> getAllBooksFromDatabase(String username) throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE username=?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String category = resultSet.getString("category");
                    String imageUrl = resultSet.getString("imageUrl");
                    String description = resultSet.getString("description");
                    Timestamp addedDate = resultSet.getTimestamp("added_date"); // Lấy ngày tháng
                    Book book = new Book(title, author, category, imageUrl, description, addedDate); // Giả sử Book có constructor phù hợp
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Book> getAllBooksFromDatabase(String username, boolean isMostRecent, boolean isMostAdded, boolean isMostView) throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE username=?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String category = resultSet.getString("category");
                    String imageUrl = resultSet.getString("imageUrl");
                    String description = resultSet.getString("description");
                    Timestamp addedDate = resultSet.getTimestamp("added_date"); // Lấy ngày tháng
                    Book book = new Book(title, author, category, imageUrl, description, addedDate); // Giả sử Book có constructor phù hợp
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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


    public static boolean deleteBookFromDatabase(String username, String title, String author) {
        String query = "DELETE FROM books where username=? and title=? and author=?";

        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, title);
            sqlStatement.setString(3, author);

            int rowsAfterDeletion = sqlStatement.executeUpdate();
            return rowsAfterDeletion > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, Integer> getBooksByDay(String username) throws SQLException {
        Map<String, Integer> booksByDay = new LinkedHashMap<>();  // Dùng LinkedHashMap để giữ thứ tự theo ngày
        String query = "SELECT DATE(added_date) AS added_day, COUNT(*) FROM books WHERE username=? GROUP BY added_day ORDER BY added_day";

        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                while (resultSet.next()) {
                    String addedDay = resultSet.getString("added_day");  // Ngày dưới định dạng YYYY-MM-DD
                    int count = resultSet.getInt("COUNT(*)");
                    booksByDay.put(addedDay, count);  // Lưu vào Map với key là ngày, value là số lượng sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksByDay;
    }

}