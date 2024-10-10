package com.example.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet/style.css", "stylesheet/login.css", 600, 500);
        UserJDBC.testConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

