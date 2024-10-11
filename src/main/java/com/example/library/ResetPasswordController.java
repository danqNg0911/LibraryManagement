package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ResetPasswordController {
    @FXML
    public PasswordField newpasswordField;

    @FXML
    public PasswordField confirmpasswordField;

    @FXML
    private TextField usernamecheckField;

    @FXML
    private TextField Q1_Field;

    @FXML
    private TextField Q2_Field;

    @FXML
    private TextField Q3_Field;

    @FXML
    private Label usernameWarning;

    @FXML
    private Label newPasswordWarning;

    @FXML
    private Label confirmPasswordWarning;

    @FXML
    private Label question1Warning;

    @FXML
    private Label question2Warning;

    @FXML
    private Label question3Warning;


    public void handleResetButton(ActionEvent event) throws IOException {
        String username = usernamecheckField.getText();
        String newPassword = newpasswordField.getText();
        String confirmPassword = confirmpasswordField.getText();
        String question1 = Q1_Field.getText();
        String question2 = Q2_Field.getText();
        String question3 = Q3_Field.getText();
        boolean success = true;
        boolean userConfirm = false;

        // usernameField bị trống
        if (username.isEmpty()) {
            WindowManager.RedWarningLabel(usernameWarning, "This information is required", 2);
            success = false;
        } else {
            success = true;
        }

        // Tài khoản (username) đã tồn tại trên Database
        if (!UserJDBC.checkUserAccount(username)) {
            WindowManager.RedWarningLabel(usernameWarning, "This account has existed", 2);
            success = false;
        } else {
            success = true;
        }

        // newpasswordField bị trống
        if (newPassword.isEmpty()) {
            WindowManager.RedWarningLabel(newPasswordWarning, "This information is required", 2);
            success = false;
        } else {
            success = true;
        }

        // confirmpasswordField bị trống
        if (confirmPassword.isEmpty()) {
            WindowManager.RedWarningLabel(confirmPasswordWarning, "This information is required", 2);
            success = false;
        } else {
            success = true;
        }

        // newPassword dưới 8 kí tự
        if (newPassword.length() < 8) {
            WindowManager.RedWarningLabel(newPasswordWarning, "Password must be over 8 characters", 2);
            success = false;
        } else {
            success = true;
        }

        // Password và confirmPassword khác nhau
        if (!confirmPassword.equals(newPassword)) {
            WindowManager.RedWarningLabel(confirmPasswordWarning, "Password must be the same", 2);
            success= false;
        } else {
            success = true;
        }

        if (question1.isEmpty()) {
            WindowManager.RedWarningLabel(question1Warning, "This information is required", 2);
            success = false;
        } else {
            success = true;
        }

        if (question2.isEmpty()) {
            WindowManager.RedWarningLabel(question2Warning, "This information is required", 2);
            success = false;
        } else {
            success = true;
        }

        if (question3.isEmpty()) {
            WindowManager.RedWarningLabel(question3Warning, "This information is required", 2);
            success = false;
        } else {
            success = true;
        }

        if (success) {
            if (UserJDBC.securityQuestionCheck(username, question1, question2, question3)) {
                userConfirm = UserJDBC.passWordUpdate(username, newPassword);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You got wrong in one or more security questions");
                alert.show();
            }

            if(userConfirm) {
                // Thông bao doi mat khau thanh cong
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Reset");
                alert.setHeaderText(null);
                alert.setContentText("Your password has been successfully changed.");
                alert.show();

                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
                delay.setOnFinished(e -> {
                    try {
                        // Chuyển đến màn hình đăng nhập
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        WindowManager.setStage(stage);
                        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                delay.play();
            }
        }
    }
}
