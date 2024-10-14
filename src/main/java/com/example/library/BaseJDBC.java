package com.example.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class BaseJDBC {
    protected abstract String getDatabaseURL();
    protected abstract String getDatabaseUser();
    protected abstract String getDatabasePassword();


    // Kết nối Project đến Database
    protected abstract Connection connectToDatabase();

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

    // Thêm tài khoản vào Database
    public boolean addAccountToDatabase(String name, String username, String password, String birthdate, String Q1, String Q2, String Q3) {
        String query = "INSERT INTO `accounts` (name, username, password, birthdate, Q1, Q2, Q3) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, name);
            sqlStatement.setString(2, username);
            sqlStatement.setString(3, password);
            sqlStatement.setString(4, birthdate);
            sqlStatement.setString(5, Q1);
            sqlStatement.setString(6, Q2);
            sqlStatement.setString(7, Q3);

            int updateToDatabse = sqlStatement.executeUpdate();
            if (updateToDatabse > 0) {
                return true; // Tài khoản đã tồn tại và không thể thêm
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Tài khoản chưa tồn tại và có thể thêm
    }

    // Kiểm tra sự tồn tại của tài khoản trong Database
    public boolean checkAccountIsExisted(String username) {
        String query = "SELECT * FROM `accounts` WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username);
            ResultSet resultSet = sqlStatement.executeQuery();


            if (resultSet.next()) {
                System.out.println("Tài khoản User đã tồn tại!");
                return true; // Tài khoản đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Tài khoản chưa tồn tạ
    }

    // Kiểm tra tài khoản (đã tồn tại) có được nhâp đúng mật khẩu không ?
    public boolean checkUsernameWithPassword(String username, String password) {
        String query = "SELECT * FROM `accounts` WHERE username = ? AND password = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username);
            sqlStatement.setString(2, password);
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                return true; // Nhập đúng mật khẩu
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Nhập sai mật khẩu
    }

    // Kiem tra cau hoi bao mat
    public boolean checkUsernameWithSecurityQuestion(String username, String Q1, String Q2, String Q3) {
        String query = "SELECT * FROM `accounts` WHERE username = ? AND Q1 = ? AND Q2 = ? AND Q3 = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, Q1);
            sqlStatement.setString(3, Q2);
            sqlStatement.setString(4, Q3);
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                return true; // Thông tin được xác thực đúng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Thông tin được xác thực sai
    }

    // Cập nhật mật khẩu
    public boolean passwordUpdate(String username, String password) {
        String query = "UPDATE `accounts` SET password = ? WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
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

    // Kiem tra birthdate
    public boolean checkUsernameWithBirthdate(String username, String birthdate) {
        String query = "SELECT * FROM `accounts` WHERE username = ? AND birthdate = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, username);
            sqlStatement.setString(2, birthdate);

            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                return true; // Ngày sinh đúng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Ngày sinh sai
    }
}
