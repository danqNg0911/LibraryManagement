package com.example.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User extends BaseJDBC implements LinkJDBC {

    @Override
    protected String getDatabaseURL() {
        return databaseUserURL;
    }

    @Override
    protected String getDatabaseUser() {
        return databaseUser;
    }

    @Override
    protected String getDatabasePassword() {
        return databaseUserPassword;
    }

    @Override
    protected Connection connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseUserURL, databaseUser, databaseUserPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
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

    UserJDBC userJDBC = new UserJDBC();

    public static String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        User.username = username;
    }

    /*public String getName() {
        return userJDBC.getName(username);
    }*/

    /*public static void setName(String username) {
        User.username = username;
    }*/

    /*public String getPassword() {
        return userJDBC.getPassword(username);
    }*/

    /*public static void setPassword(String username) {
        User.username = username;
    }*/

    public boolean nameUpdate(String username, String newName) {
        if (userJDBC.nameUpdate(username, newName)) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<UserAccount> getAllUserAccounts() {
        List<UserAccount> userList = new ArrayList<>();
        String query = "SELECT name, username, phonenum, email, avatar FROM accounts";

        try (Connection databaseConnect = connectToDatabase();
             PreparedStatement sqlStatement = databaseConnect.prepareStatement(query);
             ResultSet resultSet = sqlStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String phonenum = resultSet.getString("phonenum");
                String email = resultSet.getString("email");
                int avatar = resultSet.getInt("avatar");

                // Tạo đối tượng UserAccount từ kết quả và thêm vào danh sách
                UserAccount user = new UserAccount(name, username, phonenum, email, avatar);
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<UserAccount> searchUserAccounts(String username) throws SQLException {
        List<UserAccount> userList = new ArrayList<>();
        String query = "SELECT name, username, phonenum, email, avatar FROM accounts WHERE username LIKE ?";

        try (Connection databaseConnect = connectToDatabase();
             PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, "%" + username + "%");

            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                while (resultSet.next()) {
                    userList.add(new UserAccount(
                            resultSet.getString("name"),
                            resultSet.getString("username"),
                            resultSet.getString("phonenum"),
                            resultSet.getString("email"),
                            resultSet.getInt("avatar")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public UserAccount getAccountByUsername(String username) throws SQLException {
        String query = "SELECT * FROM accounts WHERE username = ?";

        try (Connection databaseConnect = connectToDatabase();
             PreparedStatement sqlStatement = databaseConnect.prepareStatement(query)) {

            sqlStatement.setString(1, username);

            try (ResultSet resultSet = sqlStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserAccount(
                            resultSet.getString("name"),
                            resultSet.getString("username"),
                            resultSet.getString("phonenum"),
                            resultSet.getString("email"),
                            resultSet.getInt("avatar")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
