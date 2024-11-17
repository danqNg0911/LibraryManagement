package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class AddBookController extends UserController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextArea descriptionField;

    public void addYourBook(ActionEvent event) {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();
        String description = descriptionField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || description.isEmpty()) {
            WindowManager.alertWindow(Alert.AlertType.ERROR, "Alert", "Please complete all information to create your book", "stylesheet (css)/login_alert.css");
        } else {
            BookJDBC.addBookToDatabase(user.getUsername(), "", title, author, category,null, description, "create");
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Announcement", "This book has been added to your library", "stylesheet (css)/login_alert.css");
        }
    }

    public void resetInfo(ActionEvent event) {
        titleField.clear();
        authorField.clear();
        categoryField.clear();
        descriptionField.clear();
    }

    public void backToPreviousStage(ActionEvent event) {
        WindowManager.goBack();
    }

    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

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
