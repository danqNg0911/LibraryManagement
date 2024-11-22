package com.example.library;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

abstract class UserController extends BaseController {

    Image avaImg;

    @FXML
    protected Button accHelpsButton;

    @FXML
    protected Button accSetButton;

    @FXML
    protected VBox accVBox;

    @FXML
    protected Button accountButton;

    @FXML
    protected Button collectionButton;

    @FXML
    protected ImageView collectionPic;

    @FXML
    protected ImageView currentAvatar;

    @FXML
    protected Button dashboardButton;

    @FXML
    protected ImageView dashboardPic;

    @FXML
    protected ImageView dashboardPic11;

    @FXML
    protected Button helpsButton;

    @FXML
    protected Button libraryButton;

    @FXML
    protected ImageView libraryPic;

    @FXML
    protected ImageView libraryPic11;

    @FXML
    protected ImageView logo;

    @FXML
    protected Button logoutButton;

    @FXML
    protected AnchorPane mainSce;

    @FXML
    protected Button settingButton;

    @FXML
    protected ImageView settingPic;

    @FXML
    protected Button upgradeButton;

    @FXML
    protected Button gameButton;

    @FXML
    protected ImageView gamePic;

    protected void baseInitialize()  {
        avaImg = null;
        System.gc();

        avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
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
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 2: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_2.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 3: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_3.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 4: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_4.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 5: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_5.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 6: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_6.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 7: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_7.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 8: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_8.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 9: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_9.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 0: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_0.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            default:
                System.out.println("Unknown avatar id: " + avatarId);
        }
        System.out.println("Avatar updated to ID: " + avatarId);
    }

    // Di chuột vào hiện hiệu ứng và ngược lại
    @FXML
    public void showAnimationDas(MouseEvent event) {
        WindowManager.showPic(event, dashboardButton, dashboardPic);
    }

    @FXML
    public void unshowAnimationDas(MouseEvent event) {
        WindowManager.unshowPic(event, dashboardButton, dashboardPic);
    }

    @FXML
    public void showAnimationLib(MouseEvent event) {
        WindowManager.showPic(event, libraryButton, libraryPic);
    }

    @FXML
    public void unshowAnimationLib(MouseEvent event) {
        WindowManager.unshowPic(event, libraryButton, libraryPic);
    }

    @FXML
    public void showAnimationClt(MouseEvent event) {
        WindowManager.showPic(event, collectionButton, collectionPic);
    }

    @FXML
    public void unshowAnimationClt(MouseEvent event) {
        WindowManager.unshowPic(event, collectionButton, collectionPic);
    }

    @FXML
    public void showAnimationStg(MouseEvent event) {
        WindowManager.showPic(event, settingButton, settingPic);
    }

    @FXML
    public void unshowAnimationStg(MouseEvent event) {
        WindowManager.unshowPic(event, settingButton, settingPic);
    }

    @FXML
    public void showAnimationHelps(MouseEvent event) {
        WindowManager.showPic(event, helpsButton, dashboardPic11);
    }

    @FXML
    public void unshowAnimationHelps(MouseEvent event) {
        WindowManager.unshowPic(event, helpsButton, dashboardPic11);
    }

    @FXML
    public void showAnimationUpg(MouseEvent event) {
        WindowManager.showPic(event, upgradeButton, libraryPic11);
    }

    @FXML
    public void unshowAnimationUpg(MouseEvent event) {
        WindowManager.unshowPic(event, upgradeButton, libraryPic11);
    }
    //

    // Chuyen den trang khac
    @FXML
    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void moveToLibrary(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void moveToHelps(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserHelps.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userHelpsStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void moveToUpgrade(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserUpgrade.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userUpgStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void moveToAccHelps(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserHelps.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userHelpsStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void showOptionAccount(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        WindowManager.playButtonSound();
        accVBox.setVisible(!accVBox.isVisible());
    }

    @FXML
    protected void handleGameButton(ActionEvent event) throws IOException {
        freeUpHeapMemory();
        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukongMenu.fxml", "stylesheet (css)/game.css", 1100, 600);
    }

}