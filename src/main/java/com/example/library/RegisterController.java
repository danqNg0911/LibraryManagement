package com.example.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RegisterController {
    @FXML
    private RadioButton adminRadioButton;

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
    private RadioButton userRadioButton;

    @FXML
    private TextField usernameTextField;
}
