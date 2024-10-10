package com.example.library;

import java.sql.*;

public class UserJDBC {
    private static final String databaseURL = "jdbc:mysql://localhost:3307/useraccount";
    private static final String databseUser = "root";
    private static final String databasePassword = "bongbibo9";

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
        String query = "INSERT INTO `user` (name, username, password, birthdate, Q1, Q2, Q3) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
        String query = "SELECT * FROM user WHERE username = ?";
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
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
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
}
