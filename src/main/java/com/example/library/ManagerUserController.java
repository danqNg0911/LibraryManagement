package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @FXML
    private TextField nameField;


    public void showAnimationMU(MouseEvent event) {
        return;
    }

    public void unshowAnimationMU(MouseEvent event) {
        return;
    }

    public void moveToManageUsers(ActionEvent actionEvent) throws IOException {
        return;
    }

    public void loadUserAccounts() throws SQLException {
        User userDB = new User();
        String name = nameField.getText().trim();
        List<UserAccount> userAccounts = new ArrayList<>();
        if (name.isEmpty()) {
            userAccounts = userDB.getAllUserAccounts();
        } else {
            userAccounts = userDB.searchUserAccounts(name);// Lấy tất cả user
        }

        ObservableList<UserAccount> observableUserList = FXCollections.observableArrayList(userAccounts);
        userTable.setItems(observableUserList);
    }

    public void searchUser(ActionEvent actionEvent) throws SQLException {
        loadUserAccounts();
    }


    @FXML
    public void initialize() throws SQLException {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        phonenumColumn.setCellValueFactory(new PropertyValueFactory<>("phonenum"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        userTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                UserAccount selectedUser = userTable.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/ViewUserManager.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ViewUserManagerController controller = loader.getController();

                    try {
                        controller.setUserDetails(selectedUser);
                    } catch (SQLException e) {
                        System.out.println("loi BookJDBC.getTotalBorrowedBooks(...)");
                    }

                    Scene scene = new Scene(root, 1200, 800);
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet (css)/managerStyles.css")).toExternalForm());
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/library/stylesheet (css)/managerUserStyle.css")).toExternalForm());

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    WindowManager.navigateTo(scene);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        });

        loadUserAccounts();
    }
}