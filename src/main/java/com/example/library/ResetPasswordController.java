package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
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

    @FXML
    private DatePicker birthdateField;

    @FXML
    private Label birthdateWarning;


    public void handleResetButton(ActionEvent event) throws IOException {
        String username = usernamecheckField.getText();
        String newPassword = newpasswordField.getText();
        String confirmPassword = confirmpasswordField.getText();
        String birthdate;
        String question1 = Q1_Field.getText();
        String question2 = Q2_Field.getText();
        String question3 = Q3_Field.getText();

        boolean usernameCheck = false;
        boolean passwordCheck = false;
        boolean confirmPasswordCheck = false;
        boolean birthdateCheck = false;
        boolean question1Check = false;
        boolean question2Check = false;
        boolean question3Check = false;

        // usernameField bị trống hoac khong hop le
        if (username.isEmpty()) {
            WindowManager.RedWarningLabel(usernameWarning, "This information is required", 2);
        } else if (!UserJDBC.checkUserAccount(username)) {
            WindowManager.RedWarningLabel(usernameWarning, "This account does not exist", 2);
        } else {
            usernameCheck = true;
        }

        // newpassword khong hop le
        if ((username.isEmpty() || usernameCheck) && newPassword.isEmpty()) {
            WindowManager.RedWarningLabel(newPasswordWarning, "This information is required", 2);
        } else if (usernameCheck && newPassword.length() < 8) {
            WindowManager.RedWarningLabel(newPasswordWarning, "Password must be over 8 characters", 2);
        } else {
            passwordCheck = true;
        }

        // confirmpasswordField bị trống
        if ((username.isEmpty() || usernameCheck) && confirmPassword.isEmpty()) {
            WindowManager.RedWarningLabel(confirmPasswordWarning, "This information is required", 2);
        } else if (usernameCheck && passwordCheck && !confirmPassword.equals(newPassword)){
            WindowManager.RedWarningLabel(confirmPasswordWarning, "Password must be the same", 2);
        } else {
            confirmPasswordCheck = true;
        }

        // Kiem tra birthdate
        if (birthdateField.getValue() == null) {
            if (username.isEmpty() || usernameCheck) WindowManager.RedWarningLabel(birthdateWarning, "This information is required", 2);
        } else {
            birthdate = birthdateField.getValue().toString();
            System.out.println(birthdate);
            if (usernameCheck && !UserJDBC.birthdateCheck(username, birthdate)) {
                WindowManager.RedWarningLabel(birthdateWarning, "Your birthdate is not correct", 2);
            } else {
                birthdateCheck = true;
            }
        }

        // security question bi trong
        if ((username.isEmpty() || usernameCheck) && question1.isEmpty()) {
            WindowManager.RedWarningLabel(question1Warning, "This information is required", 2);
        } else {
            question1Check = true;
        }

        if ((username.isEmpty() || usernameCheck) && question2.isEmpty()) {
            WindowManager.RedWarningLabel(question2Warning, "This information is required", 2);
        } else {
            question2Check = true;
        }

        if ((username.isEmpty() || usernameCheck) && question3.isEmpty()) {
            WindowManager.RedWarningLabel(question3Warning, "This information is required", 2);
        } else {
            question3Check = true;
        }


        // xu li reset password
        if (usernameCheck && passwordCheck && confirmPasswordCheck && birthdateCheck && question1Check && question2Check && question3Check) {
            if (UserJDBC.securityQuestionCheck(username, question1, question2, question3)) {
                UserJDBC.passWordUpdate(username, newPassword);

                // Thông bao doi mat khau thanh cong
                WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Password Reset", "Your password has been successfully changed", "stylesheet (css)/login_alert.css");

                PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
                delay.setOnFinished(e -> {
                    try {
                        // Chuyển đến màn hình đăng nhập
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        WindowManager.setStage(stage);
                        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css) (css)/style.css", "stylesheet (css) (css)/login.css", 600, 500);
                    } catch (IOException ex) {
                        System.out.println("Loi khi chuyen man hinh");
                    }
                });
                delay.play();

            } else {
                WindowManager.alertWindow(Alert.AlertType.ERROR, "Warning", "You got wrong in one or more security questions", "stylesheet (css)/login_alert.css");
            }
        }
    }
}