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

public class ManagerHelpsController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    Manager manager = new Manager();

    @FXML
    private ImageView MBPic;

    @FXML
    private ImageView MUPic;

    @FXML
    private Button accSetButton;

    @FXML
    private VBox accVBox;

    @FXML
    private Button accountButton;

    @FXML
    private Label accountName;

    @FXML
    private ImageView currentAvatar;

    @FXML
    private ImageView dashboardPic11;

    @FXML
    private Label dashboardTitle;

    @FXML
    private Button helpsButton;

    @FXML
    private ImageView logo;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane mainSce;

    @FXML
    private Button manageBooksButton;

    @FXML
    private Button manageUsersButton;

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

    public void showAnimationMU(MouseEvent event) {
        WindowManager.showPic(event, manageUsersButton, MUPic);
    }

    public void unshowAnimationMU(MouseEvent event) {
        WindowManager.unshowPic(event, manageUsersButton, MUPic);
    }

    public void showAnimationMB(MouseEvent event) {
        WindowManager.showPic(event, manageBooksButton, MBPic);
    }

    public void unshowAnimationMB(MouseEvent event) {
        WindowManager.unshowPic(event, manageBooksButton, MBPic);
    }

    public void showAnimationStg(MouseEvent event) {
        WindowManager.showPic(event, settingButton, settingPic);
    }

    public void unshowAnimationStg(MouseEvent event) {
        WindowManager.unshowPic(event, settingButton, settingPic);
    }

    // Chuyen den trang khac
    public void moveToManageUsers(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerUser.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerUserStyle.css", 1200, 800, actionEvent);
    }

    public void moveToManageBooks(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerBook.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerBookStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerSetting.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerStgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerSetting.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerStgStyle.css", 1200, 800, actionEvent);
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
        manager.closeConnection();
        pause.play();
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }
}