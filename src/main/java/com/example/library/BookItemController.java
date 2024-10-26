package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class BookItemController {
    @FXML
    private ImageView bookCover;

    @FXML
    private Label bookTitle;

    @FXML
    private Label bookInfo;

    @FXML
    private Button viewItemButton;

    private Book book;

    @FXML
    private Button backButton;

    public void setBook(Book book) {
        this.book = book;
        bookTitle.setText(book.getTitle());
        bookInfo.setText("Authors: " + book.getAuthor() + "\nCategory: " + book.getCategory() + "\nDescription: " + book.getDescription());

        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
            Image image = new Image(book.getImageUrl());
            bookCover.setImage(image);
        } else {
            bookCover.setImage(null);
        }
    }

    public void viewBook(ActionEvent event) {
        //luu view hien tai vao viewstack de quay lai
        //Parent lastView = ((Node) event.getSource()).getScene().getRoot();
        //WindowManager.addView(lastView);

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


        //WindowManager.handlemoveButton("/com/example/library/fxml/ViewItem.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, event);

        Scene scene = new Scene(root, 1200, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
