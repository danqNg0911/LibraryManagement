package com.example.library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerJDBC extends BaseJDBC {
    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/useraccount";
    private static final String databaseUser = "root";
    private static final String databasePassword = "Haidang0911.";
    private static final String MANAGER_DATA_FILE_PATH = "F:\\OOP\\LibraryManagement_Ulib\\LibraryManagement\\data\\ListOfManagers.txt";

    public final List<String> listOfManager = new ArrayList<>();

    {
        try (BufferedReader br = new BufferedReader(new FileReader(MANAGER_DATA_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                listOfManager.add(line.trim());  // Thêm từng dòng vào listOfManager
            }
        } catch (IOException e) {
            System.out.println("loi file manager");
            e.printStackTrace();
        }
    }

    @Override
    protected String getDatabaseURL() {
        return databaseURL;
    }

    @Override
    protected String getDatabaseUser() {
        return databaseUser;
    }

    @Override
    protected String getDatabasePassword() {
        return databasePassword;
    }


    @Override
    protected Connection connectToDatabase() {
        Connection databaseConnect = null;
        try {
            databaseConnect = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseConnect;
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
