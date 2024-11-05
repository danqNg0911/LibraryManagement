package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();

    @FXML
    private Button backButton;

    @FXML
    private Pane body_left_login;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView logo;

    @FXML
    private RadioButton managerRadioButton;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private Button nextButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField phoneNumField;

    @FXML
    private Label phoneNumLabel;

    @FXML
    private RadioButton readerRadioButton;

    @FXML
    private ToggleGroup selectUserType;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    public void backToLogin(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.goBack();
    }

    public void handleNextButton(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phonenumber = phoneNumField.getText();
        String email = emailField.getText();
        boolean isReader = true;
        boolean isSuccesful = true;

        // nameField bị trống
        if (name.isEmpty()) {
            WindowManager.RedWarningLabel(nameLabel, "This information is required", 2);
            isSuccesful = false;
        }

        /*else {
            for (int i = 0; i < name.length(); i++) {
                if (!Character.isAlphabetic(name.charAt(i))) {
                    WindowManager.RedWarningLabel(nameLabel, "Name cannot contain special characters !", 2);
                    isSuccesful = false;
                }
            }
        }*/

        // Kiểm tra đối tượng là User hay Manager
        if (!username.isEmpty() && managerJDBC.checkMemberOfManager(username)) {
            System.out.println("This is Manager account");
            isReader = false;
        }

        // usernameField bị trống
        if (username.isEmpty()) {
            WindowManager.RedWarningLabel(usernameLabel, "This information is required", 2);
            isSuccesful = false;
        }

        // Tài khoản (username) đã tồn tại trên Database
        else if ((isReader && (userJDBC.checkAccountIsExisted(username))) || (!isReader && (!managerJDBC.checkManagerNameWithUsername(username, name)) || managerJDBC.checkAccountIsExisted(username))) {
            WindowManager.RedWarningLabel(usernameLabel, "This account has existed", 2);
            isSuccesful = false;
        }

        // passwordField bị trống
        if (password.isEmpty()) {
            WindowManager.RedWarningLabel(passwordLabel, "This information is required", 2);
            isSuccesful = false;
        }

        // Password dưới 8 kí tự
        else if (password.length() < 8) {
            WindowManager.RedWarningLabel(passwordLabel, "Password must be over 8 characters", 2);
            isSuccesful = false;
        }

        // Password và confirmPassword khác nhau
        else if (!confirmPassword.equals(password)) {
            WindowManager.RedWarningLabel(confirmPasswordLabel, "Password must be the same", 2);
            isSuccesful = false;
        }

        // phone number bị trống
        if (phonenumber.isEmpty()) {
            WindowManager.RedWarningLabel(phoneNumLabel, "This information is required", 2);
            isSuccesful = false;
        }
        // phone number ko đúng
        else {
            if (phonenumber.length() != 10) {
                WindowManager.RedWarningLabel(phoneNumLabel, "Phone number is false", 2);
                isSuccesful = false;
            }
        }

        // phone number bị trống
        if (email.isEmpty()) {
            WindowManager.RedWarningLabel(emailLabel, "This information is required", 2);
            isSuccesful = false;
        }
        // email ko đúng
        else if (!email.contains("@")) {
            WindowManager.RedWarningLabel(emailLabel, "This email is false", 2);
            isSuccesful = false;
        }

        // cả 2 radioButton đều không được chọn
        if (!managerRadioButton.isSelected() && !readerRadioButton.isSelected()) {
            isSuccesful = false;
        }

        if (readerRadioButton.isSelected() && !isReader) {
            isSuccesful = false;
        }

        if (managerRadioButton.isSelected() && isReader) {
            isSuccesful = false;
        }

        // Nhập thông tin thành công
        if (isSuccesful) {
            String reader = "reader";
            if (!isReader) {
                reader = "manager";
            }
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Welcome", "Your " + reader + " account has been almost registered. Just one more step !", "stylesheet (css)/login_alert.css");
            try {
                // Tải file FXML
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/SecurityQuestion.fxml"));

                // Tải FXML và nhận đối tượng controller
                AnchorPane root = loader.load();
                SecurityQuestionController securityQuestionController = loader.getController();

                // Truyền thông tin sang controller
                securityQuestionController.setUserData(name, username, password, phonenumber, email, isReader);


                Scene scene = new Scene(root, 600, 500);
                scene.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource("stylesheet (css)/style.css")).toExternalForm());
                scene.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource("stylesheet (css)/login.css")).toExternalForm());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Ulib Library Manager");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("loi file fxml");
            }
        }
    }
}
