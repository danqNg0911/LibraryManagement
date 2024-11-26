package com.example.library;
import com.example.game.Sound;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

//import javax.print.attribute.standard.Media;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class WindowManager {
    private static Stage stage;
    private static Scene scene;
    private static final Stack<Scene> sceneStack = new Stack<>();
    private static MediaPlayer mediaPlayer;

    public static void setStage(Stage newStage) {
        stage =  newStage;

        newStage.setResizable(false);
    }

    public static void setScene(Scene newScene) {
        scene = newScene;
    }

    // Navigate to a new scene and save the current scene in the stack
    public static void navigateTo(Scene newScene) {
        if (stage.getScene() != null) {
            sceneStack.push(stage.getScene());
        }
        stage.setScene(newScene);
    }

    public static void goBack() {
        if (!sceneStack.isEmpty()) {
            stage.setScene(sceneStack.pop());
        } else {
            System.out.println("No previous scene to go back to.");
        }
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

        WindowManager.navigateTo(scene);
        stage.setTitle("Ulib Library Manager");
        //stage.setScene(scene);
        stage.show();
    }

    // Truyền file FXML và CSS
    public static void addGameFxml(String fxmlFile, String cssFile, int width, int height) throws IOException {
        FXMLLoader Loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(Loader.load(), width, height);
        scene.getStylesheets().add(Objects.requireNonNull(WindowManager.class.getResource(cssFile)).toExternalForm());
        WindowManager.navigateTo(scene);
        stage.setTitle("Black Myth Wukong");
        //stage.setScene(scene);
        stage.show();
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

    //Chuyen den trang khac
    public static void handlemoveButton(String fxmlFile, String cssMainFile, String cssSubFile, int width, int height, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.setStage(stage);
        WindowManager.addFxmlCss(fxmlFile, cssMainFile, cssSubFile, width, height);
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

    public static void showPic(MouseEvent event, Button button, ImageView imageView) {
        // Khi chuột di chuyển vào nút
        button.setOnMouseEntered(mouseEvent -> imageView.setVisible(true));
    }

    public static void unshowPic(MouseEvent event, Button button, ImageView imageView) {
        // Khi chuột di chuyển ra ngoài nút
        button.setOnMouseExited(mouseEvent -> imageView.setVisible(false));
    }

    // Phương thức phát âm thanh
    public static void playButtonSound() {
        String soundFile = LinkSetting.SOUND_CLICK_MOUSE.getLink();
        Media sound = new Media(WindowManager.class.getResource(soundFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void playMusic(String filePath) {
        // Nếu đã có nhạc đang phát, dừng lại
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        // Tạo và phát nhạc từ file mới
        Media media = new Media(WindowManager.class.getResource(filePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp lại
        mediaPlayer.play();
    }

    public static void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    public static void resumeMusic() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = null;
    }
}
