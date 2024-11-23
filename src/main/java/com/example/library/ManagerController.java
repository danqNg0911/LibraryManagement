package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

abstract class ManagerController extends BaseController {

    @FXML
    protected ImageView MBPic;

    @FXML
    protected ImageView MUPic;

    @FXML
    protected Button accSetButton;

    @FXML
    protected VBox accVBox;

    @FXML
    protected Button accountButton;

    @FXML
    protected Label accountName;

    @FXML
    protected ImageView currentAvatar;

    @FXML
    protected ImageView dashboardPic11;

    @FXML
    protected Label dashboardTitle;

    @FXML
    protected Button helpsButton;

    @FXML
    protected ImageView logo;

    @FXML
    protected Button logoutButton;

    @FXML
    protected AnchorPane mainSce;

    @FXML
    protected Button manageBooksButton;

    @FXML
    protected Button manageUsersButton;

    @FXML
    protected Button settingButton;

    @FXML
    protected ImageView settingPic;

    @FXML
    protected Button loanManagementButton;

    @FXML
    protected ImageView loanManagementPic;

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

    public void showAnimationHelps(MouseEvent event) {
        WindowManager.showPic(event, helpsButton, dashboardPic11);
    }

    public void unshowAnimationHelps(MouseEvent event) {
        WindowManager.unshowPic(event, helpsButton, dashboardPic11);
    }

    public void showAnimationStg(MouseEvent event) {
        WindowManager.showPic(event, settingButton, settingPic);
    }

    public void unshowAnimationStg(MouseEvent event) {
        WindowManager.unshowPic(event, settingButton, settingPic);
    }

    public void showAnimationLoan(MouseEvent event) {
        WindowManager.showPic(event, loanManagementButton, loanManagementPic);
    }

    public void unshowAnimationLoan(MouseEvent event) {
        WindowManager.unshowPic(event, loanManagementButton, loanManagementPic);
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

    public void moveToHelps(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerHelps.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerHelpsStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerSetting.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerStgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToLoanManagement(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/LoanManagement.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerStgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerSetting.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerStgStyle.css", 1200, 800, actionEvent);
    }

    public void showOptionAccount(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        accVBox.setVisible(!accVBox.isVisible());
    }
}
