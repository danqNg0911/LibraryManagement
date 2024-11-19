package com.example.library;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
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

public class ManagerSettingController extends ManagerController {


    @FXML
    private ImageView newAvatar;

    @FXML
    private Button uploadImageButton;

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    public void showAnimationStg(MouseEvent event) {
        return;
    }

    public void unshowAnimationStg(MouseEvent event) {
        return;
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        return;
    }


    protected void helpChangePasswordForUserAndManager(String newPassword) {
        managerJDBC.passwordUpdate(manager.getUsername(), newPassword);
    }

    protected void helpChangeAnswersForUserAndManager(String question1, String question2, String question3) {
        managerJDBC.securityQuestionsUpdate(manager.getUsername(), question1, question2, question3);
    }
}