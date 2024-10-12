package com.example.library;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

import java.io.IOException;

public class UserDashboardController {

    @FXML
    private MenuButton accountMenu;

    @FXML
    private Label accountName;

    @FXML
    private Button collectionButton;

    @FXML
    private ImageView collectionPic;

    @FXML
    private Button dashboardButton;

    @FXML
    private ImageView dashboardPic;

    @FXML
    private Label dashboardTitle;

    @FXML
    private Button libraryButton;

    @FXML
    private ImageView libraryPic;

    @FXML
    private ImageView logo;

    @FXML
    private Pane mainSce;

    @FXML
    private BarChart<?, ?> rollingYearChart;

    @FXML
    private Label selectCollectionLabel;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingPic;

    // Di chuột vào hiện hiệu ứng và ngược lại
    /*public void showAnimationDsb(MouseEvent event) {
        WindowManager.showPic(event, dashboardButton, dashboardPic);
    }

    public void unshowAnimationDsb(MouseEvent event) {
        WindowManager.unshowPic(event, dashboardButton, dashboardPic);
    }*/

    public void showAnimationLib(MouseEvent event) {
        WindowManager.showPic(event, libraryButton, libraryPic);
    }

    public void unshowAnimationLib(MouseEvent event) {
        WindowManager.unshowPic(event, libraryButton, libraryPic);
    }

    public void showAnimationClt(MouseEvent event) {
        WindowManager.showPic(event, collectionButton, collectionPic);
    }

    public void unshowAnimationClt(MouseEvent event) {
        WindowManager.unshowPic(event, collectionButton, collectionPic);
    }

    public void showAnimationStg(MouseEvent event) {
        WindowManager.showPic(event, settingButton, settingPic);
    }

    public void unshowAnimationStg(MouseEvent event) {
        WindowManager.unshowPic(event, settingButton, settingPic);
    }

    // Chuyen den trang khac
    public void moveToLibrary(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, actionEvent);
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

}