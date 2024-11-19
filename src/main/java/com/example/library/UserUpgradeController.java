package com.example.library;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
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

import java.io.IOException;

public class UserUpgradeController extends UserController {

    @FXML
    private Label dashboardTitle;

    @FXML
    private Button donateButton;

    @FXML
    private Pane keyFeature;

    @FXML
    private Pane mainPane;

    @FXML
    private ToggleGroup term;

    @FXML
    public void initialize() {
        baseInitialize();
    }

    public void showAnimationUpg(MouseEvent event) {
        return;
    }

    public void unshowAnimationUpg(MouseEvent event) {
        return;
    }

    public void moveToUpgrade(ActionEvent actionEvent) throws IOException {
        return;
    }

    public void showThanks(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Thanks support", "Thanks for your suppport!!\nCheck your email!!", "stylesheet (css)/login_alert.css");
    }

}