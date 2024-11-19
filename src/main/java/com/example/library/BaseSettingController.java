package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public abstract class BaseSettingController {

    @FXML
    protected TextField Q1Field;

    @FXML
    protected TextField Q2Field;

    @FXML
    protected TextField Q3Field;

    @FXML
    protected Label question1Warning;

    @FXML
    protected Label question2Warning;

    @FXML
    protected Label question3Warning;

    @FXML
    protected Label currentNameLabel;

    @FXML
    protected TextField newNameField;

    @FXML
    protected Button changeNameButton;

    @FXML
    protected Label changeNameSuccessedLabel;

    @FXML
    protected PasswordField currentPass;

    @FXML
    protected PasswordField currentPass1;

    @FXML
    protected PasswordField currentPass2;

    @FXML
    protected Label currentPassWarning;

    @FXML
    protected Label currentPassWarning1;

    @FXML
    protected Label currentPassWarning2;

    @FXML
    protected Button changePassButton;

    @FXML
    protected PasswordField confirmNewPass;

    @FXML
    protected Label confirmPassWarning;

    @FXML
    protected PasswordField newPass;

    @FXML
    protected Label newPassWarning;

    @FXML
    protected TitledPane nameTitledPane;

    @FXML
    protected TitledPane passTitledPane;

    @FXML
    protected TitledPane avatarTitledPane;

    @FXML
    protected Button changeAnswerButton;

    @FXML
    protected abstract void handleChangeName(ActionEvent event);

    @FXML
    protected abstract void handleChangePassword(ActionEvent event);

}
