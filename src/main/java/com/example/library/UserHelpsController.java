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
    public ImageView gamePic;
    public Button gameButton;
    @FXML
    private Label dashboardTitle;

    @FXML
    private Button sendButton;

    public void showAnimationHelps(MouseEvent event) {
        return;
    }

    public void unshowAnimationHelps(MouseEvent event) {
        return;
    }

    public void moveToAccHelps(ActionEvent actionEvent) throws IOException {
        return;
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

        gamePic.setVisible(false);

        // Sự kiện khi di chuột vào gameButton
        gameButton.setOnMouseEntered(event -> {

            // Hiển thị label khi chuột trỏ vào button
            gamePic.setVisible(true);
        });

        // Sự kiện khi chuột rời khỏi gameButton
        gameButton.setOnMouseExited(event -> {

            // Ẩn label khi chuột rời khỏi button
            gamePic.setVisible(false);
        });

        int avatarId = user.getAvatar(user.getUsername());
        switch (avatarId) {
            case 1: {
                javafx.scene.image.Image ava1Img = new Image(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
                currentAvatar.setImage(ava1Img);
                break;
            }
            case 2: {
                javafx.scene.image.Image ava2Img = new Image(getClass().getResource(LinkSetting.AVATAR_2.getLink()).toExternalForm());
                currentAvatar.setImage(ava2Img);
                break;
            }
            case 3: {
                javafx.scene.image.Image ava3Img = new Image(getClass().getResource(LinkSetting.AVATAR_3.getLink()).toExternalForm());
                currentAvatar.setImage(ava3Img);
                break;
            }
            case 4: {
                javafx.scene.image.Image ava4Img = new Image(getClass().getResource(LinkSetting.AVATAR_4.getLink()).toExternalForm());
                currentAvatar.setImage(ava4Img);
                break;
            }
            case 5: {
                javafx.scene.image.Image ava5Img = new Image(getClass().getResource(LinkSetting.AVATAR_5.getLink()).toExternalForm());
                currentAvatar.setImage(ava5Img);
                break;
            }
            case 6: {
                javafx.scene.image.Image ava6Img = new Image(getClass().getResource(LinkSetting.AVATAR_6.getLink()).toExternalForm());
                currentAvatar.setImage(ava6Img);
                break;
            }
            case 7: {
                javafx.scene.image.Image ava7Img = new Image(getClass().getResource(LinkSetting.AVATAR_7.getLink()).toExternalForm());
                currentAvatar.setImage(ava7Img);
                break;
            }
            case 8: {
                javafx.scene.image.Image ava8Img = new Image(getClass().getResource(LinkSetting.AVATAR_8.getLink()).toExternalForm());
                currentAvatar.setImage(ava8Img);
                break;
            }
            case 9: {
                javafx.scene.image.Image ava9Img = new Image(getClass().getResource(LinkSetting.AVATAR_9.getLink()).toExternalForm());
                currentAvatar.setImage(ava9Img);
                break;
            }
            case 0: {
                javafx.scene.image.Image ava0Img = new Image(getClass().getResource(LinkSetting.AVATAR_0.getLink()).toExternalForm());
                currentAvatar.setImage(ava0Img);
                break;
            }
            default:
                System.out.println("UserHelpsController, Line = 104");
        }
        System.out.println("Avatar updated to ID: " + avatarId);
    }

    public void openYouTubeChannel(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        String url = "https://www.youtube.com/@phamhuy2195";

        // Mở trình duyệt mặc định
        if(Desktop.isDesktopSupported()) {
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

    public void handleGameButton(ActionEvent event) throws IOException {
        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukongMenu.fxml", 800, 800);
    }
}