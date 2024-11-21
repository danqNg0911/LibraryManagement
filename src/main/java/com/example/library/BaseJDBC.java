package com.example.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class BaseJDBC {
    protected Connection connection;
    protected abstract String getDatabaseURL();
    protected abstract String getDatabaseUser();
    protected abstract String getDatabasePassword();


    // Kết nối Project đến Database
    protected abstract Connection connectToDatabase();

    // Ngắt kết nối Database
    protected abstract void closeConnection();

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
    public boolean addAccountToDatabase(String name, String username, String password, String phonenum, String email, String birthdate, String Q1, String Q2, String Q3, int avaID, int score) {
        String query = "INSERT INTO accounts (name, username, password, birthdate, Q1, Q2, Q3, phonenum, email, avatar, gamescore) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, name);
            sqlStatement.setString(2, username);
            sqlStatement.setString(3, password);
            sqlStatement.setString(8, phonenum);
            sqlStatement.setString(9, email);
            sqlStatement.setString(4, birthdate);
            sqlStatement.setString(5, Q1);
            sqlStatement.setString(6, Q2);
            sqlStatement.setString(7, Q3);
            sqlStatement.setInt(10, avaID);
            sqlStatement.setInt(11, score);

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
        String query = "SELECT * FROM accounts WHERE username = ?";
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
        String query = "SELECT * FROM accounts WHERE username = ? AND password = ?";
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
        String query = "SELECT * FROM accounts WHERE username = ? AND Q1 = ? AND Q2 = ? AND Q3 = ?";
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
        if (password == null || password.trim().isEmpty() || password.trim().length() < 8) {
            //System.out.println("Tên không hợp lệ!");
            return false;
        }
        String query = "UPDATE accounts SET password = ? WHERE username = ?";
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
        String query = "SELECT * FROM accounts WHERE username = ? AND birthdate = ?";
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

    // Cap nhat ten
    public boolean nameUpdate(String username, String name) {
        if (name == null || name.trim().isEmpty()) {
            //System.out.println("Tên không hợp lệ!");
            return false;
        }
        String query = "UPDATE accounts SET name = ? WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, name);
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

    // Cap nhat cau hoi bao mat
    public boolean securityQuestionsUpdate(String username, String ques1, String ques2, String ques3) {
        String query = "UPDATE accounts SET ques1 = ?, ques2 = ?, ques3 = ? WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, ques1);
            sqlStatement.setString(2, ques2);
            sqlStatement.setString(3, ques3);
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

    //cap nhat phone number
    public boolean phoneNumUpdate(String username, String phonenum) {
        if (phonenum == null || phonenum.trim().isEmpty() || phonenum.trim().length() != 10) {
            //System.out.println("Tên không hợp lệ!");
            return false;
        }
        String query = "UPDATE accounts SET phonenum = ? WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, phonenum);
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

    //cap nhat phone number
    public boolean emailUpdate(String username, String email) {
        if (email == null || email.trim().isEmpty() || !email.trim().contains("@")) {
            //System.out.println("Tên không hợp lệ!");
            return false;
        }
        String query = "UPDATE accounts SET email = ? WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setString(1, email);
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

    public boolean avatarUpdate(String username, int avaID) {
        if (avaID < 0 || avaID > 10) {
            //System.out.println("Tên không hợp lệ!");
            return false;
        }
        String query = "UPDATE accounts SET avatar = ? WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setInt(1, avaID);
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

    public boolean scoreUpdate(String username, int newScore) {
        String query = "UPDATE accounts SET gamescore = ? WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {
            sqlStatement.setInt(1, newScore);
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

    // Lấy tên của user
    public String getName(String username) {
        String query = "SELECT name FROM accounts WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username); // Đặt giá trị của tham số vào câu SQL
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                return name;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy tài khoản
    }

    // Lấy mật khẩu của user
    public String getPassword(String username) {
        String query = "SELECT password FROM accounts WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username); // Đặt giá trị của tham số vào câu SQL
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                return password;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy tài khoản
    }


    // Lấy phone của user
    public String getPhone(String username) {
        String query = "SELECT phonenum FROM accounts WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username); // Đặt giá trị của tham số vào câu SQL
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                String phonenum = resultSet.getString("phonenum");
                return phonenum;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy tài khoản
    }

    // lấy email của user
    public String getEmail(String username) {
        String query = "SELECT email FROM accounts WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username); // Đặt giá trị của tham số vào câu SQL
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("email");
                return email;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy tài khoản
    }

    public int getAvatar(String username) {
        String query = "SELECT avatar FROM accounts WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username); // Đặt giá trị của tham số vào câu SQL
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                int avaId = resultSet.getInt("avatar");
                return avaId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Nếu không tìm thấy tài khoản
    }

    public int getScore(String username) {
        String query = "SELECT gamescore FROM accounts WHERE username = ?";
        try (Connection databaseConnect = connectToDatabase(); PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username); // Đặt giá trị của tham số vào câu SQL
            ResultSet resultSet = sqlStatement.executeQuery();

            if (resultSet.next()) {
                int score = resultSet.getInt("gamescore");
                return score;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Nếu không tìm thấy tài khoản
    }
}