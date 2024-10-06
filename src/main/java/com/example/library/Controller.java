package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import javax.swing.text.html.ImageView;

public class Controller {

    @FXML
    public void handleCreateNewAccount(ActionEvent event) throws IOException {
        // Tải file register.fxml khi người dùng nhấn nút "Create new account"
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/CreateAccount.fxml"));
        Scene registerScene = new Scene(fxmlLoader.load(), 600, 500);
        registerScene.getStylesheets().add(getClass().getResource("stylesheet (css)/style.css").toExternalForm());
        registerScene.getStylesheets().add(getClass().getResource("stylesheet (css)/login.css").toExternalForm());
        // Lấy stage hiện tại từ event (khi bấm nút)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(registerScene);
        stage.show();
    }
}