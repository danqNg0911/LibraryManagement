package com.example.game;
import com.example.library.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class BlackMythWukongMenuController {

    public Button openButton;

    public Button goBackButton;

    @FXML
    public void initialize() {
        if (!Sound.isBackgroundMusicPlaying()) {
            Sound.playBackgroundMusic();
        }
        System.out.println("menu");
    }

    @FXML
    public void handleOpenButton() throws IOException {
        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukong.fxml", "stylesheet (css)/game.css",800, 800);
    }

    @FXML
    public void handleReturnToLib(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
        Sound.stopBackgroundMusic();
    }
}
