package com.example.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/SignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stylesheet("stylesheet (css)/style.css", scene);
        stylesheet("stylesheet (css)/login.css", scene);
        stage.setTitle("Ulib Library Management");
        stage.setScene(scene);
        stage.show();
    }

    public void stylesheet(String filepath, Scene scene) {
        scene.getStylesheets().add(getClass().getResource(filepath).toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

