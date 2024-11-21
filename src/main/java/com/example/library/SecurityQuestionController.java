package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SecurityQuestionController {

    private String name;
    private String username;
    private String password;
    private String phonenumber;
    private String email;
    private boolean isReader;
    private int avaID;
    private int score;

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();

    public void setUserData(String name, String username, String password, String phonenumber, String email, boolean isReader) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phonenumber = phonenumber;
        this.email = email;
        this.isReader = isReader;
    }

    @FXML
    public TextField Q1Field;

    @FXML
    public TextField Q2Field;

    @FXML
    public TextField Q3Field;

    @FXML
    public DatePicker birthdateField;

    @FXML
    public Button button;

    @FXML
    public Label questionLabel;

    @FXML
    public Label successfulLabel;

    public void handleRegisterButton(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        String birthdate = birthdateField.getValue().toString();
        String Q1 = Q1Field.getText();
        String Q2 = Q2Field.getText();
        String Q3 = Q3Field.getText();
        int avaID = -1;
        int score = 0;

        // Check thông tin bỏ trống
        if (birthdate.isEmpty() || Q1.isEmpty() || Q2.isEmpty() || Q3.isEmpty()) {
            WindowManager.RedWarningLabel(questionLabel, "Please don't leave any information blank !", 3);
        }

        // them vao User Database
        else if (this.isReader){
            userJDBC.addAccountToDatabase(this.name, this.username, this.password, this.phonenumber, this.email, birthdate, Q1, Q2, Q3, avaID, score);
            successfulLabel.setText("Congratulation! You have successfully registered");
            WindowManager.moveToAnotherScene(event, "fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 2,600, 500);
        }
        else {
            if (managerJDBC.checkManagerNameWithBirthdate(this.username, birthdate)) {
                managerJDBC.addAccountToDatabase(this.name, this.username, this.password, this.phonenumber, this.email, birthdate, Q1, Q2, Q3, avaID, score);
                successfulLabel.setText("Congratulation! You have successfully registered");
                WindowManager.moveToAnotherScene(event, "fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 2, 600, 500);
            }
        }
    }
}

