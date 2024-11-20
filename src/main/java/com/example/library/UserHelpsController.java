package com.example.library;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UserHelpsController extends UserController {

    @FXML
    private Button sendButton;

    @FXML
    public void initialize() throws IOException {
        baseInitialize();
    }

    public void showAnimationHelps(MouseEvent event) {
        return;
    }

    public void unshowAnimationHelps(MouseEvent event) {
        return;
    }

    public void moveToAccHelps(ActionEvent actionEvent) throws IOException {
        return;
    }


    public void openYouTubeChannel(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        String url = "https://www.youtube.com/@phamhuy2195";

        // Mở trình duyệt mặc định
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop không hỗ trợ chức năng này.");
        }
    }

    public void sendOpinion(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Thanks support", "Thanks for your suppport!!\nCheck your email!!", "stylesheet (css)/login_alert.css");
    }

}