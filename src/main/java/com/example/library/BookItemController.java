package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BookItemController {
    User user = new User();

    @FXML
    private ImageView bookCover;

    @FXML
    private Label bookTitle;

    @FXML
    private Label bookInfo;

    @FXML
    private Button backButton;

    private Book book;

    public void setBook(Book book) {
        this.book = book;
        bookTitle.setText(book.getTitle());
        bookInfo.setText("Authors: " + book.getAuthor() + "\nCategory: " + book.getCategory() + "\nDescription: " + book.getDescription());

        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            Image image = new Image(book.getImageUrl());
            bookCover.setImage(image);
        } else {
            Image nullImage = new Image(getClass().getResource(LinkSetting.IMAGE_NULL.getLink()).toExternalForm());
            bookCover.setImage(nullImage);
        }
    }

    public void viewBook(ActionEvent event) throws SQLException {
        // Load ViewItem.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/ViewItem.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lấy controller của ViewItem và truyền thông tin sách
        ViewItemController viewItemController = loader.getController();
        viewItemController.setBookDetails(book);
        if (book.getId() == -1) {
            viewItemController.setDeleteButton();
        }

        //WindowManager.handlemoveButton("/com/example/library/fxml/ViewItem.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, event);

        Scene scene = new Scene(root, 1200, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.navigateTo(scene);
        stage.setScene(scene);
        stage.show();
    }

    public void addBook(ActionEvent event) {
        String title = book.getTitle();
        String author = book.getAuthor();
        String category = book.getCategory();
        String description = book.getDescription();
        String imageUrl = book.getImageUrl();
        String username = user.getUsername();
        int id = book.getId();
        Date date = new Date(Timestamp.from(Instant.now()).getTime());

        if (!BookJDBC.checkBook(username, title, author)) {
            BookJDBC.addBookToDatabase(username, "", title, author, category, imageUrl, description, "borrowed");
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Announcement", "You have added a book", "stylesheet (css)/login_alert.css");
        } else {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Alert", "This book had already been added to your library", "stylesheet (css)/login_alert.css");
        }
    }
}
