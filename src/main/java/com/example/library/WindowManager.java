package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    // Truyền file FXML
    public static void addFxml(String fxmlFile, int width, int height) throws IOException {
        FXMLLoader Loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(Loader.load(), width, height);
        stage.setTitle("Ulib Library Manager");
        stage.setScene(scene);
        stage.show();
    }


    // Truyền file FXML và CSS
    public static void addFxmlCss(String fxmlFile, String cssMainFile, String cssSubFile, int width, int height) throws IOException {
        FXMLLoader Loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(Loader.load(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource(cssMainFile)).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource(cssSubFile)).toExternalForm());

        stage.setTitle("Ulib Library Manager");
        stage.setScene(scene);
        stage.show();
    }

    // Hiển thị dòng cảnh báo đỏ
    public static void RedWarningLabel (Label label, String warningText, int displayedSecond) {
        label.setText(warningText);

        // Ngắt hiển thị cảnh báo sau displayedSecond giây
        PauseTransition pause = new PauseTransition(Duration.seconds(displayedSecond));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                label.setText(""); // Xóa thông báo
            }
        });
        pause.play();
    }
}
