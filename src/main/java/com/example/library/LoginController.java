package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LoginController {

    @FXML
    public void handleCreateNewAccount(ActionEvent event) throws IOException {
        // Tải file register.fxml khi người dùng nhấn nút "Create new account"
        // Lấy stage hiện tại từ event (khi bấm nút)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/CreateAccount.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
        //WindowManager.addFXML("fxml/CreateAccount.fxml", 600, 500);
    }
}