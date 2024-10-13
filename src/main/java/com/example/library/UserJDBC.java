package com.example.library;

import java.sql.*;

public class UserJDBC {
    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/useraccount";
    private static final String databseUser = "root";
    private static final String databasePassword = "Haidang0911.";

    // Kết nối database user từ mysql workbench với project java
    private static Connection connect() {
        Connection databaseConnect = null;
        try {
            databaseConnect = DriverManager.getConnection(databaseURL, databseUser, databasePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseConnect;
    }


    // kiểm tra kết nối Database
    public static boolean testConnection() {
        try (Connection databaseConnect = connect()) {
            if (databaseConnect != null) {
                System.out.println("Successful Connection");
                return true;
            } else {
                System.out.println("Failed Connection");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error Database !");
            e.printStackTrace();
            return false;
        }
    }


    // Thêm tài khoản và kiểm tra sự tồn tại của tài khoản trong database
    public static boolean addAndCheckUserAccount(String name, String username, String password, String birthdate, String Q1, String Q2, String Q3) {
        String query = "INSERT INTO `users` (name, username, password, birthdate, Q1, Q2, Q3) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection databaseConnect = connect(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, name);
            sqlStatement.setString(2, username);
            sqlStatement.setString(3, password);
            sqlStatement.setString(4, birthdate);
            sqlStatement.setString(5, Q1);
            sqlStatement.setString(6, Q2);
            sqlStatement.setString(7, Q3);

            int updateToDatabse = sqlStatement.executeUpdate();
            if (updateToDatabse > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Kiểm tra sự tồn tại của tài khoản trong database
    public static boolean checkUserAccount(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection databaseConnect = connect(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username);
            ResultSet resultSet = sqlStatement.executeQuery();

            // Tài khoản đã tồn tại
            if (resultSet.next()) {
                System.out.println("Tài khoản đã tồn tại!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nếu không tìm thấy tài khoản
    }


    // Kiểm tra tài khoản (đã tồn tại) có được nhâp đúng password không
    public static boolean checkUserAccount(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection databaseConnect = connect(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username);
            sqlStatement.setString(2, password);
            ResultSet resultSet = sqlStatement.executeQuery();

            // Nhập đúng mật khẩu
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nhập sai mật khẩu
    }

    // Kiem tra cau hoi bao mat
    public static boolean securityQuestionCheck(String username, String Q1, String Q2, String Q3) {
        String query = "SELECT * FROM users WHERE username = ? AND Q1 = ? AND Q2 = ? AND Q3 = ?";
        try (Connection databaseConnect = connect(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, Q1);
            sqlStatement.setString(3, Q2);
            sqlStatement.setString(4, Q3);
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiem tra birthdate
    public static boolean birthdateCheck(String username, String birthdate) {
        String query = "SELECT * FROM users WHERE username = ? AND birthdate = ?";
        try (Connection databaseConnect = connect(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, birthdate);
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean passWordUpdate(String username, String password) {
        String query = "UPDATE `users` SET password = ? WHERE username = ?";
        try (Connection databaseConnect = connect(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, password);
            sqlStatement.setString(2, username);

            int rowsUpdated = sqlStatement.executeUpdate();

            if (rowsUpdated > 0) {
                return true; // Cập nhật thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
