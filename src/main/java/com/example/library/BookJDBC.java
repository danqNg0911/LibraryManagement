package com.example.library;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookJDBC {
    private Connection connection;
    private static final String databaseURL = "jdbc:mysql://localhost:3307/useraccount";
    private static final String databaseUser = "root";
    private static final String databasePassword = "bongbibo9";

    protected String getDatabaseURL() {
        return databaseURL;
    }

    protected String getDatabaseUser() {
        return databaseUser;
    }

    protected String getDatabasePassword() {
        return databasePassword;
    }

    public static Connection connectToDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
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
                    Book book = new Book(title, author, category, imageUrl, description);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}