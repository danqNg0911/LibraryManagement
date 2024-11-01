package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserCollectionController {

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
    private Button addNewClt;

    @FXML
    private Button addedDateSortButton;

    @FXML
    private VBox cltOptionVBox;

    @FXML
    private Button collectionButton;

    @FXML
    private ImageView collectionPic;

    @FXML
    private Label collectionTitle;

    @FXML
    private ImageView currentAvatar;

    @FXML
    private Button dashboardButton;

    @FXML
    private ImageView dashboardPic;

    @FXML
    private Button libraryButton;

    @FXML
    private ImageView libraryPic;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane mainSce;

    @FXML
    private Button selectCltButton;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingPic;

    @FXML
    private Button sortButton;

    @FXML
    private VBox sortOptionVBox;

    @FXML
    private Button titleSortButton;


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

    /*public void showAnimationClt(MouseEvent event) {
        WindowManager.showPic(event, collectionButton, collectionPic);
    }

    public void unshowAnimationClt(MouseEvent event) {
        WindowManager.unshowPic(event, collectionButton, collectionPic);
    }*/

    public void showAnimationStg(MouseEvent event) {
        WindowManager.showPic(event, settingButton, settingPic);
    }

    public void unshowAnimationStg(MouseEvent event) {
        WindowManager.unshowPic(event, settingButton, settingPic);
    }

    // Chuyen den trang khac
    public void moveToLibrary(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, actionEvent);
    }

    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    public void showOptionAccount(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        accVBox.setVisible(!accVBox.isVisible());
    }

    public void showCltOption(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        cltOptionVBox.setVisible(!cltOptionVBox.isVisible());
    }

    public void showSortOption(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        sortOptionVBox.setVisible(!sortOptionVBox.isVisible());
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }
}