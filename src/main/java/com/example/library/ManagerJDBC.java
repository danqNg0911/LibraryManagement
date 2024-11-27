package com.example.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerJDBC extends BaseJDBC implements LinkJDBC {
    private static final String MANAGER_DATA_FILE_PATH = LinkSetting.MANAGER_LIST_FILE_PATH.getLink();

    public final List<String> listOfManager = new ArrayList<>();

    {
        try (BufferedReader br = new BufferedReader(new FileReader(MANAGER_DATA_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                listOfManager.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("loi file manager");
            e.printStackTrace();
        }
    }

    @Override
    protected String getDatabaseURL() {
        return databaseManagerURL;
    }

    @Override
    protected String getDatabaseUser() {
        return databaseManager;
    }

    @Override
    protected String getDatabasePassword() {
        return databaseManagerPassword;
    }


    @Override
    protected Connection connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseManagerURL, databaseManager, databaseManagerPassword);
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

    public boolean checkMemberOfManager (String username) {
        for (String members : listOfManager) {
            if (members.contains("// " + username + " //")) {
                return true;
            }
        }
        return false;
    }

    // Lấy ra tên của 1 Manager từ listOfManager
    public String getManagerName(String str) {
        if (!str.contains("//")) {
            return "";
        }

        StringBuilder name = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            name.append(str.charAt(i));
            if (str.charAt(i + 1) == '/') {
                break;
            }
        }
        return name.toString().trim();
    }


    // Lấy ra ngày sinh của 1 Manager từ listOfManager
    public String getManagerBirthdate(String str) {
        if (!str.contains("//")) {
            return "";
        }

        StringBuilder birthdate = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            birthdate.append(str.charAt(i));
            if (str.charAt(i - 1) == '/') {
                break;
            }
        }
        return birthdate.reverse().toString().trim();
    }


    // Lấy ra Username của 1 Manager từ listOfManager
    public String getManagerUsername(String str) {

        if (!str.contains("//")) {
            return "";
        }
        int l = 0, r = str.length() - 1;

        while (str.charAt(l) != '/' && l < r) {
            l++;
        }

        while (str.charAt(r) != '/' && l < r) {
            r--;
        }

        return str.substring(l + 1, r).trim();
    }



    // Kiểm tra xem username và name của Manager có thuộc bộ phận Manager không
    public boolean checkManagerNameWithUsername(String username, String name) {
        int i = 0;
        for (i = 0; i < listOfManager.size(); i++) {
            if (listOfManager.get(i).contains(username)) {
                break;
            }
        }
        if (i == listOfManager.size()) return false;
        //System.out.println(i + "  " + username + "  " + getManagerName(listOfManager.get(i)) + "  " + name);
        return name.equals(getManagerName(listOfManager.get(i)));
    }

    // Kiểm tra họ tên và ngày sinh của Manager có thuộc bộ phận Manager không
    public boolean checkManagerNameWithBirthdate(String username, String birthdate) {
        int i = 0;
        for (i = 0; i < listOfManager.size(); i++) {
            if (listOfManager.get(i).contains(username)) {
                break;
            }
        }
        if (i == listOfManager.size()) return false;
        System.out.println(i + "  " + username + "  " + getManagerBirthdate(listOfManager.get(i)) + "  " + birthdate);
        return birthdate.equals(getManagerBirthdate(listOfManager.get(i)));
    }

}
