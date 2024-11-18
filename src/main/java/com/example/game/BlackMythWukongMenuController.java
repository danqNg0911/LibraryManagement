package com.example.game;
import com.example.library.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class BlackMythWukongMenuController {

    public Button openButton;

    @FXML
    public void initialize() {
        if (!Sound.isBackgroundMusicPlaying()) {
            Sound.playBackgroundMusic();
        }
        System.out.println("menu");
    }

    @FXML
    public void handleOpenButton() throws IOException {
        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukong.fxml", 800, 800);
    }
}
