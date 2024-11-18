package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

import java.io.IOException;

public class ViewUserManagerController extends ManagerController {

    private UserAccount selectedAccount;

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
    private Label currentBorrowedLabel;

    @FXML
    private Label currentOverdueLabel;

    @FXML
    private BarChart<?, ?> rollingYearChart;


    public void setUserDetails(UserAccount account) {
        selectedAccount = account;
        currentName1Label.setText(account.getName());
        currentUserameLabel.setText(account.getUsername());
        currentEmailLabel.setText(account.getEmail());
        currentPhoneLabel.setText(account.getPhonenum());
        if (account.getAvatar() > 0) {
            int avatarId = account.getAvatar();
            switch (avatarId) {
                case 1: {
                    Image ava1Img = new Image(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava1Img);
                    break;
                }
                case 2: {
                    Image ava2Img = new Image(getClass().getResource(LinkSetting.AVATAR_2.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava2Img);
                    break;
                }
                case 3: {
                    Image ava3Img = new Image(getClass().getResource(LinkSetting.AVATAR_3.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava3Img);
                    break;
                }
                case 4: {
                    Image ava4Img = new Image(getClass().getResource(LinkSetting.AVATAR_4.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava4Img);
                    break;
                }
                case 5: {
                    Image ava5Img = new Image(getClass().getResource(LinkSetting.AVATAR_5.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava5Img);
                    break;
                }
                case 6: {
                    Image ava6Img = new Image(getClass().getResource(LinkSetting.AVATAR_6.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava6Img);
                    break;
                }
                case 7: {
                    Image ava7Img = new Image(getClass().getResource(LinkSetting.AVATAR_7.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava7Img);
                    break;
                }
                case 8: {
                    Image ava8Img = new Image(getClass().getResource(LinkSetting.AVATAR_8.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava8Img);
                    break;
                }
                case 9: {
                    Image ava9Img = new Image(getClass().getResource(LinkSetting.AVATAR_9.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava9Img);
                    break;
                }
                case 0: {
                    Image ava0Img = new Image(getClass().getResource(LinkSetting.AVATAR_0.getLink()).toExternalForm());
                    currentAvatar1.setImage(ava0Img);
                    break;
                }
                default:
                    System.out.println("Unknown avatar id: " + avatarId);
            }
        }
    }

    public void backToPreviousStage(ActionEvent event) throws IOException {
        //WindowManager.addFxmlCss("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800);
        WindowManager.goBack();
    }

    public void initialize() {
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

}

