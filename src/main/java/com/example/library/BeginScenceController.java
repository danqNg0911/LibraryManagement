package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class BeginScenceController {
    @FXML
    private Pane body_left_login;

    @FXML
    private Button start;

    @FXML
    void moveToLogin(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
    }
}
