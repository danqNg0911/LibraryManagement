package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;

public class IntroController {

    public Button newUpdateButton;
    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        // Đường dẫn video (đặt trong thư mục resources hoặc thư mục gốc dự án)
        String videoPath = getClass().getResource(LinkSetting.VIDEO_INTRO.getLink()).toExternalForm();

        // Tạo Media và MediaPlayer
        Media media = new Media(videoPath);
        mediaPlayer = new MediaPlayer(media);

        // Gắn MediaPlayer vào MediaView
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.play();
    }

    @FXML
    public void handleReturnToLib(ActionEvent actionEvent) throws IOException {
        mediaPlayer.stop();
        String fxmlFile = "fxml/UserDashboard.fxml";
        WindowManager.addFxmlCss(fxmlFile, "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800);
    }

    @FXML
    public void handleTryAiNow(ActionEvent actionEvent) throws IOException {
        mediaPlayer.stop();
        WindowManager.addFxmlCss("fxml/AI.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/AI.css", 640, 700);
    }
}
