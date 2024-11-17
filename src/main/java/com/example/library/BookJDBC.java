package com.example.library;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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
    public static boolean addBookToDatabase(String username, String isbn, String title, String author, String category, String imageUrl, String description, String source) {
        String query = "INSERT INTO `books` (username, isbn, title, author, category, imageUrl, description, source, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, isbn);
            sqlStatement.setString(3, title);
            sqlStatement.setString(4, author);
            sqlStatement.setString(5, category);
            sqlStatement.setString(6, imageUrl);
            sqlStatement.setString(7, description);
            sqlStatement.setString(8, source);

            LocalDate date = LocalDate.now();
            sqlStatement.setDate(9, java.sql.Date.valueOf(date));

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
        String query = "SELECT * FROM books where username=?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String category = resultSet.getString("category");
                    String imageUrl = resultSet.getString("imageUrl");
                    String description = resultSet.getString("description");
                    int id = resultSet.getInt("id");
                    Book book = new Book(title, author, category, imageUrl, description, id);
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
                    Timestamp addedDate = resultSet.getTimestamp("date"); // Lấy ngày tháng
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


    public static List<Book> getAllBooksFromAllUser() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String category = resultSet.getString("category");
                    String imageUrl = resultSet.getString("imageUrl");
                    String description = resultSet.getString("description");
                    String username = resultSet.getString("username");
                    String source = resultSet.getString("source");
                    Date date = resultSet.getDate("date");
                    int id = resultSet.getInt("id");
                    Book book = new Book(title, author, category, imageUrl, description, username, source, date, id);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static boolean deleteBookFromDatabase(String username, String title, String author, int id) {
        String query = "DELETE FROM books where username=? and title=? and author=? and id=?";

        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, title);
            sqlStatement.setString(3, author);
            sqlStatement.setInt(4, id);

            int rowsAfterDeletion = sqlStatement.executeUpdate();
            return rowsAfterDeletion > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Book> searchBooksFromDatabase(String username, String title, String author, String category) throws SQLException {
        List<Book> books = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM books WHERE username = ?");

        if (title != null && !title.isEmpty()) {
            queryBuilder.append(" AND title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            queryBuilder.append(" AND author LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            queryBuilder.append(" AND category LIKE ?");
        }

        String query = queryBuilder.toString();

        try (Connection databaseConnect = connectToDatabase();
             PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username);

            int index = 2; // Bắt đầu đặt các tham số khác từ vị trí 2
            if (title != null && !title.isEmpty()) {
                sqlStatement.setString(index++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                sqlStatement.setString(index++, "%" + author + "%");
            }
            if (category != null && !category.isEmpty()) {
                sqlStatement.setString(index, "%" + category + "%");
            }

            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                while (resultSet.next()) {
                    String resultTitle = resultSet.getString("title");
                    String resultAuthor = resultSet.getString("author");
                    String resultCategory = resultSet.getString("category");
                    String imageUrl = resultSet.getString("imageUrl");
                    String description = resultSet.getString("description");
                    int id = resultSet.getInt("id");

                    Book book = new Book(resultTitle, resultAuthor, resultCategory, imageUrl, description, id);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
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