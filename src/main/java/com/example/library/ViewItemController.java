package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewItemController {

    @FXML
    private MenuButton accountMenu;

    @FXML
    private Button collectionButton;

    @FXML
    private ImageView collectionPic;

    @FXML
    private Button dashboardButton;

    @FXML
    private ImageView dashboardPic;

    @FXML
    private Button libraryButton;

    @FXML
    private ImageView libraryPic;

    @FXML
    private Label libraryTitle;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane mainSce;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingPic;

    private Book selectedBook;

    @FXML
    private ImageView coverImage;

    @FXML
    private Label BookTitle;

    @FXML
    private Label Authors;

    @FXML
    private Label Category;

    @FXML
    private Label CopiesNumber;

    @FXML
    private Label Description;

    public void setBookDetails(Book book) {
        selectedBook = book;
        BookTitle.setText(book.getTitle());
        Authors.setText("Authors: " + book.getAuthor());
        Category.setText("Category: " + book.getCategory());
        Description.setText(book.getDescription());
        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            Image image = new Image(book.getImageUrl());
            coverImage.setImage(image);
        } else {
            coverImage.setImage(null);
        }
    }

    /*public void back(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.getBack(stage);
    }*/

    // Di chuột vào hiện hiệu ứng và ngược lại
    public void showAnimationDas(MouseEvent event) {
        WindowManager.showPic(event, dashboardButton, dashboardPic);
    }

    public void unshowAnimationDas(MouseEvent event) {
        WindowManager.unshowPic(event, dashboardButton, dashboardPic);
    }

    /*public void showAnimationLib(MouseEvent event) {
        WindowManager.showPic(event, libraryButton, libraryPic);
    }

    public void unshowAnimationLib(MouseEvent event) {
        WindowManager.unshowPic(event, libraryButton, libraryPic);
    }*/

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
    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }
}