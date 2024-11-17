package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.List;

public class ManagerUserController extends ManagerController {
    @FXML
    private TableView<UserAccount> userTable;

    @FXML
    private TableColumn<UserAccount, String> nameColumn;

    @FXML
    private TableColumn<UserAccount, String> usernameColumn;

    @FXML
    private TableColumn<UserAccount, String> emailColumn;

    @FXML
    private TableColumn<UserAccount, String> phonenumColumn;


    public void showAnimationMU(MouseEvent event) {
        return;
    }

    public void unshowAnimationMU(MouseEvent event) {
        return;
    }

    public void moveToManageUsers(ActionEvent actionEvent) throws IOException {
        return;
    }

    public void loadUserAccounts() {
        User userDB = new User(); // Tạo đối tượng User để truy cập cơ sở dữ liệu user
        List<UserAccount> userAccounts = userDB.getAllUserAccounts(); // Lấy tất cả user

        // Chuyển đổi List sang ObservableList và đưa vào TableView
        ObservableList<UserAccount> observableUserList = FXCollections.observableArrayList(userAccounts);
        userTable.setItems(observableUserList);
    }


    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        phonenumColumn.setCellValueFactory(new PropertyValueFactory<>("phonenum"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Load dữ liệu
        loadUserAccounts();
    }
}