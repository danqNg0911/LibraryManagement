package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private Pane body_left_login;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ImageView logo;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField setPasswordField;

    @FXML
    private TextField usernameTextField;


    @FXML
    private RadioButton managerRadioButton;

    @FXML
    private RadioButton readerRadioButton;

    @FXML
    private ToggleGroup selectUserType;

    public void handleSecurityQuestion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/SecurityQuestion.fxml"));
        Scene registerScene = new Scene(fxmlLoader.load(), 600, 500);
        registerScene.getStylesheets().add(getClass().getResource("stylesheet (css)/style.css").toExternalForm());
        registerScene.getStylesheets().add(getClass().getResource("stylesheet (css)/login.css").toExternalForm());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(registerScene);
        stage.show();
    }
}
