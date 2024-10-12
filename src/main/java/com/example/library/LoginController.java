package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;

public class LoginController {

    @FXML
    private Label usernameWarning;

    @FXML
    private Label passwordWarning;

    @FXML
    private javafx.scene.control.TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleCreateNewAccountButton(ActionEvent event) throws IOException {
        // Tải file register.fxml khi người dùng nhấn nút "Create new account"
        // Lấy stage hiện tại từ event (khi bấm nút)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/CreateAccount.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
        //WindowManager.addFXML("fxml/CreateAccount.fxml", 600, 500);
    }

    public void handleForgotPassword(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/ResetPassword.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 600);
    }

    @FXML
    public void handleSignInButton(ActionEvent event) throws IOException {
        String username = nameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty()) {
            WindowManager.RedWarningLabel(usernameWarning, "Please enter your username !", 2);
        }
        else if (!UserJDBC.checkUserAccount(username)) {
            WindowManager.RedWarningLabel(usernameWarning, "This account has never existed", 2);

        } else {
            // Nếu nhập username đúng nhưng không có password (password để rỗng)
            if (password.isEmpty()) {
                WindowManager.RedWarningLabel(passwordWarning, "Please enter your password !", 2);
            }

            // Nếu nhập username đúng nhưng password sai
            else if (!UserJDBC.checkUserAccount(username, password)) {
                WindowManager.RedWarningLabel(passwordWarning, "Wrong Password !", 2);
            }

            // Nhập đúng toàn bộ thông tin
            else {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowManager.setStage(stage);
                WindowManager.addFxmlCss("fxml/UserDashboard.fxml", "stylesheet (css)/style.css", "stylesheet (css)/", 600, 500);
            }
        }
    }
}