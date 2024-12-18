package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LoginController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    User user = new User();
    Manager manager = new Manager();

    @FXML
    private Pane body_left_login;

    @FXML
    private Button buttonCreateNewAccount;

    @FXML
    private Button buttonSignIn;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordWarning;

    @FXML
    private Label usernameWarning;

    @FXML
    public void handleCreateNewAccountButton(ActionEvent event) throws IOException {
        // Tải file register.fxml khi người dùng nhấn nút "Create new account"
        // Lấy stage hiện tại từ event (khi bấm nút)
        WindowManager.playButtonSound();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/CreateAccount.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
        //WindowManager.addFXML("fxml/CreateAccount.fxml", 600, 500);
    }

    public void handleForgotPassword(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/ResetPassword.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 530, 580);
        //WindowManager.addFXML("fxml/CreateAccount.fxml", 600, 500);
    }

    @FXML
    public void handleSignInButton(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();

        String username = nameField.getText();
        String password = passwordField.getText();
        boolean isReader = true;

        // Kiểm tra đối tượng là User hay Manager
        if (!username.isEmpty() && (managerJDBC.checkMemberOfManager(username))) {
            isReader = false;
            System.out.println("This is Manager account");
        }

        if (username.isEmpty()) {
            WindowManager.RedWarningLabel(usernameWarning, "Please enter your username !", 2);
        }

        else if (isReader && !userJDBC.checkAccountIsExisted(username)) {
            WindowManager.RedWarningLabel(usernameWarning, "This account does not exist", 2);
        }

        else {
            // Nếu nhập username đúng nhưng không có password (password để rỗng)
            if (password.isEmpty()) {
                WindowManager.RedWarningLabel(passwordWarning, "Please enter your password !", 2);
            }

            // Nếu nhập username đúng nhưng password sai
            else if (!userJDBC.checkUsernameWithPassword(username, password) && !managerJDBC.checkUsernameWithPassword(username, password)) {
                WindowManager.RedWarningLabel(passwordWarning, "Wrong Password !", 2);
            }

            // Nhập đúng toàn bộ thông tin
            else {
                if (managerJDBC.checkMemberOfManager(username)) {
                    manager.setUsername(username);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    WindowManager.setStage(stage);

                    String fxmlFile = "fxml/ManagerUser.fxml";
                    /*if (!isReader) {
                        fxmlFile = "";
                    }*/

                    WindowManager.addFxmlCss(fxmlFile, "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerUserStyle.css", 1200, 800);
                } else {
                    user.setUsername(username);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    WindowManager.setStage(stage);

                    String fxmlFile = "fxml/UserDashboard.fxml";
                    if (!isReader) {
                        fxmlFile = "";
                    }

                    WindowManager.addFxmlCss(fxmlFile, "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800);
                }
            }
        }
    }

    @FXML
    public void handleForgotPasswordLink(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/ResetPassword.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 800, 800);
        //WindowManager.addFxml("fxml/ResetPassword.fxml", 600, 600);
    }

}