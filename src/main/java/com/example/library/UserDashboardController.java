package com.example.library;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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

public class UserDashboardController extends UserController {
    @FXML
    private ImageView currentAvatar1;

    @FXML
    private Label currentEmailLabel;

    @FXML
    private Label currentName1Label;

    @FXML
    private Label currentPhoneLabel;

    @FXML
    private Label currentUserameLabel;

    @FXML
    private Label dashboardTitle;

    @FXML
    private BarChart<?, ?> rollingYearChart;

    public void showAnimationDas(MouseEvent event) {
        return;
    }

    public void unshowAnimationDas(MouseEvent event) {
        return;
    }

    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        return;
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
        currentName1Label.setText(user.getName(user.getUsername()));
        currentUserameLabel.setText(user.getUsername());
        currentPhoneLabel.setText(user.getPhone(user.getUsername()));
        currentEmailLabel.setText(user.getEmail(user.getUsername()));
    }

    public void handleGameButton(ActionEvent event) throws IOException {
        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukongMenu.fxml", 800, 800);
    }
}