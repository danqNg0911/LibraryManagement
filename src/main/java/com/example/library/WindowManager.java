package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class WindowManager {
    private static Stage stage;
    private static Scene scene;

    public static void setStage(Stage newStage) {
        stage =  newStage;

        newStage.setResizable(false);
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
    public static void RedWarningLabel (Label label, String warningText, int SecondToDisplay) {
        label.setText(warningText);

        // Ngắt hiển thị cảnh báo sau SecondToDisplay giây
        PauseTransition pause = new PauseTransition(Duration.seconds(SecondToDisplay));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                label.setText(""); // Xóa thông báo
            }
        });
        pause.play();
    }

    public static void moveToAnotherScene(ActionEvent event, String fxmlFile, String cssMainFile, String cssSubFile, int SecondToDisplay, int width, int height) {
        PauseTransition pause = new PauseTransition(Duration.seconds(SecondToDisplay));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowManager.setStage(stage);
                try {
                    WindowManager.addFxmlCss(fxmlFile, cssMainFile, cssSubFile, width, height);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        pause.play();
    }

    // Cửa sổ thông báo không có file CSS
    public static void alertWindow (Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.show();
    }

    // Cửa sổ thông báo có file CSS
    public static void alertWindow (Alert.AlertType alertType, String title, String message, String cssFile) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(title);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource(cssFile)).toExternalForm());
        alert.show();
    }


    //Chuyen den trang khac
    public static void handlemoveButton(String fxmlFile, String cssMainFile, String cssSubFile, int width, int height, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss(fxmlFile, cssMainFile, cssSubFile, width, height);
    }

    public static void showPic(MouseEvent event, Button button, ImageView imageView) {
        // Khi chuột di chuyển vào nút
        button.setOnMouseEntered(mouseEvent -> imageView.setVisible(true));
    }

    public static void unshowPic(MouseEvent event, Button button, ImageView imageView) {
        // Khi chuột di chuyển ra ngoài nút
        button.setOnMouseExited(mouseEvent -> imageView.setVisible(false));
    }

}
