package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserCollectionController extends  UserController {

    public Button gameButton;
    public ImageView gamePic;
    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    User user = new User();

    @FXML
    protected Button accHelpsButton;

    @FXML
    protected Button accSetButton;

    @FXML
    protected VBox accVBox;

    @FXML
    protected Button accountButton;

    @FXML
    public Label accountName;

    @FXML
    protected Button addNewClt;

    @FXML
    protected Button addedDateSortButton;

    @FXML
    protected VBox cltOptionVBox;

    @FXML
    protected Button collectionButton;

    @FXML
    protected ImageView collectionPic;

    @FXML
    protected Label collectionTitle;

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
    protected Button selectCltButton;

    @FXML
    protected Button settingButton;

    @FXML
    protected ImageView settingPic;

    @FXML
    protected Button sortButton;

    @FXML
    protected VBox sortOptionVBox;

    @FXML
    protected Button titleSortButton;

    @FXML
    protected Button upgradeButton;

    @FXML
    protected ProgressIndicator loadingIndicator;

    @FXML
    protected VBox collectionBookContainer;

    protected List<Book> books = new ArrayList<>();

    public void showDefaultCollectionData() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/UserDefaultCollection.fxml"));
        collectionBookContainer.getChildren().add((Node) loader.load());
    }

    public void handleGameButton(ActionEvent event) throws IOException {
        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukongMenu.fxml", 800, 800);
    }

    @FXML
    public void initialize() throws IOException {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
        showDefaultCollectionData();

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
                Image ava1Img = new Image(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
                currentAvatar.setImage(ava1Img);
                break;
            }
            case 2: {
                Image ava2Img = new Image(getClass().getResource(LinkSetting.AVATAR_2.getLink()).toExternalForm());
                currentAvatar.setImage(ava2Img);
                break;
            }
            case 3: {
                Image ava3Img = new Image(getClass().getResource(LinkSetting.AVATAR_3.getLink()).toExternalForm());
                currentAvatar.setImage(ava3Img);
                break;
            }
            case 4: {
                Image ava4Img = new Image(getClass().getResource(LinkSetting.AVATAR_4.getLink()).toExternalForm());
                currentAvatar.setImage(ava4Img);
                break;
            }
            case 5: {
                Image ava5Img = new Image(getClass().getResource(LinkSetting.AVATAR_5.getLink()).toExternalForm());
                currentAvatar.setImage(ava5Img);
                break;
            }
            case 6: {
                Image ava6Img = new Image(getClass().getResource(LinkSetting.AVATAR_6.getLink()).toExternalForm());
                currentAvatar.setImage(ava6Img);
                break;
            }
            case 7: {
                Image ava7Img = new Image(getClass().getResource(LinkSetting.AVATAR_7.getLink()).toExternalForm());
                currentAvatar.setImage(ava7Img);
                break;
            }
            case 8: {
                Image ava8Img = new Image(getClass().getResource(LinkSetting.AVATAR_8.getLink()).toExternalForm());
                currentAvatar.setImage(ava8Img);
                break;
            }
            case 9: {
                Image ava9Img = new Image(getClass().getResource(LinkSetting.AVATAR_9.getLink()).toExternalForm());
                currentAvatar.setImage(ava9Img);
                break;
            }
            case 0: {
                Image ava0Img = new Image(getClass().getResource(LinkSetting.AVATAR_0.getLink()).toExternalForm());
                currentAvatar.setImage(ava0Img);
                break;
            }
            default:
                System.out.println("Unknown avatar id: " + avatarId);
        }
        System.out.println("Avatar updated to ID: " + avatarId);
    }
}