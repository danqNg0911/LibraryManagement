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

public class UserUpgradeController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    User user = new User();

    @FXML
    private Button accSetButton;

    @FXML
    private VBox accVBox;

    @FXML
    private Button accountButton;

    @FXML
    private Label accountName;

    @FXML
    private Button collectionButton;

    @FXML
    private ImageView collectionPic;

    @FXML
    private ImageView currentAvatar;

    @FXML
    private Button dashboardButton;

    @FXML
    private ImageView dashboardPic;

    @FXML
    private ImageView dashboardPic11;

    @FXML
    private Label dashboardTitle;

    @FXML
    private Button donateButton;

    @FXML
    private Button helpsButton;

    @FXML
    private Pane keyFeature;

    @FXML
    private Button libraryButton;

    @FXML
    private ImageView libraryPic;

    @FXML
    private ImageView libraryPic11;

    @FXML
    private ImageView logo;

    @FXML
    private Button logoutButton;

    @FXML
    private Pane mainPane;

    @FXML
    private AnchorPane mainSce;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingPic;

    @FXML
    private ToggleGroup term;

    @FXML
    private Button upgradeButton;

    // Di chuột vào hiện hiệu ứng và ngược lại
    public void showAnimationDas(MouseEvent event) {
        WindowManager.showPic(event, dashboardButton, dashboardPic);
    }

    public void unshowAnimationDas(MouseEvent event) {
        WindowManager.unshowPic(event, dashboardButton, dashboardPic);
    }

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

    public void showAnimationHelps(MouseEvent event) {
        WindowManager.showPic(event, helpsButton, dashboardPic11);
    }

    public void unshowAnimationHelps(MouseEvent event) {
        WindowManager.unshowPic(event, helpsButton, dashboardPic11);
    }

    /*public void showAnimationUpg(MouseEvent event) {
        WindowManager.showPic(event, upgradeButton, libraryPic11);
    }

    public void unshowAnimationUpg(MouseEvent event) {
        WindowManager.unshowPic(event, upgradeButton, libraryPic11);
    }*/
    // Chuyen den trang khac
    public void moveToLibrary(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, actionEvent);
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToHelps(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserHelps.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userHelpsStyle.css", 1200, 800, actionEvent);
    }

    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
    }

    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    public void showOptionAccount(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        accVBox.setVisible(!accVBox.isVisible());
    }

    //log out
    public void logOut(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
        user.closeConnection();
        pause.play();
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    public void showThanks(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Thanks support", "Thanks for your suppport!!\nCheck your email!!", "stylesheet (css)/login_alert.css");
    }
}