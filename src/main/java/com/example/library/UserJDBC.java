package com.example.library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserJDBC {
    private static final String databaseURL = "jdbc:mysql://localhost:3307/useraccount";
    private static final String databseUser = "root";
    private static final String databasePassword = "bongbibo9";

    // Kết nối database user từ mysql workbench với project java
    private static Connection connect() {
        Connection cn = null;
        try{
            cn = DriverManager.getConnection(databaseURL, databseUser, databasePassword);
            System.out.println("Successful Connection");
        }   catch (SQLException e) {
            System.out.println("Failed Connection");
            e.printStackTrace();
        }
        return cn;
    }

    // kiểm tra kết nối
    public static boolean testConnection() {
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("Successful Connection");
                return true;
            } else {
                System.out.println("Failed Connection");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error Database !s");
            e.printStackTrace();
            return false;
        }
    }
}
