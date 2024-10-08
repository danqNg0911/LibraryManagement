package com.example.library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WindowManager {
    private static Stage stage;
    private static Scene scene;

    public static void setStage(Stage newStage) {
        stage =  newStage;
    }

    public static void setScene(Scene newScene) {
        scene = newScene;
    }

    public static void addFXML(String fxmlFile, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Ulib Library Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void addFxmlCss(String fxmlFile, String cssMainFile, String cssSubFile, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource(cssMainFile)).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource(cssSubFile)).toExternalForm());

        stage.setTitle("Ulib Library Manager");
        stage.setScene(scene);
        stage.show();
    }

}
