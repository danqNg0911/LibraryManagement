package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class ViewItemController extends UserController {
    private Book selectedBook;

    @FXML
    private Label Authors;

    @FXML
    private Label BookTitle;

    @FXML
    private Label Category;

    @FXML
    private Label borrowersNumber;

    @FXML
    private Label Description;

    @FXML
    private Button backButton;

    @FXML
    private ImageView coverImage;

    @FXML
    private ImageView currentAvatar;

    @FXML
    private Button dashboardButton;

    @FXML
    private Label libraryTitle;

    @FXML
    private Button addBookButton;


    public void setBookDetails(Book book) throws SQLException {
        selectedBook = book;
        BookTitle.setText(book.getTitle());
        Authors.setText("Authors: " + book.getAuthor());
        Category.setText("Category: " + book.getCategory());
        Description.setText(book.getDescription());
        borrowersNumber.setText("Number of Borrowers: " + BookJDBC.getNumberOfBorrowers(selectedBook.getTitle(), selectedBook.getAuthor()));
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            Image image = new Image(book.getImageUrl());
            coverImage.setImage(image);
        } else {
            if (book.getSource() != null && book.getSource().equals("create")) {
                Image defaultImage = new Image(getClass().getResource(LinkSetting.DEFAULT_COVER_IMAGE.getLink()).toExternalForm());
                coverImage.setImage(defaultImage);
            } else {
                Image nullImage = new Image(getClass().getResource(LinkSetting.IMAGE_NULL.getLink()).toExternalForm());
                coverImage.setImage(nullImage);
            }

        }
    }

    public void deleteBook(ActionEvent event) throws SQLException {
        if (BookJDBC.checkBook(user.getUsername(), selectedBook.getTitle(), selectedBook.getAuthor())) {
            BookJDBC.deleteBookFromDatabase(user.getUsername(), selectedBook.getTitle(), selectedBook.getAuthor(), selectedBook.getId());
            if (selectedBook.getSource().equals("borrowed")) {
                BookJDBC.returnBorrowedBooks(user.getUsername(), selectedBook.getTitle(), selectedBook.getAuthor(), selectedBook.getCategory(), selectedBook.getDate());
            }
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Announcement", "This book is successfully removed", "stylesheet (css)/login_alert.css");
        } else {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Alert", "This book hasn't been added to your library", "stylesheet (css)/login_alert.css");
        }
    }

    //back to previous stage
    public void backToPreviousStage(ActionEvent event) throws IOException {
        //WindowManager.addFxmlCss("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800);
        WindowManager.goBack();
    }

    @FXML
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