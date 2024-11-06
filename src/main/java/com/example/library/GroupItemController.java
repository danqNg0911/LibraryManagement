package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GroupItemController {
    User user = new User();

    @FXML
    private ImageView Cover;

    @FXML
    private Label Title;

    @FXML
    private Label Author;

    @FXML
    private Button View;

    public void setGroupItem(Book book) {
        Title.setText(book.getTitle());
        Author.setText(book.getAuthor());

        if (book.getImageUrl() != null) {
            Cover.setImage(new Image(book.getImageUrl()));
        } else {
            Image nullImage = new Image("file:/D:/LibraryManagement/LibraryManagement/src/main/resources/com/example/library/assets/Picture_is_not_available.png");
            Cover.setImage(nullImage);
        }
    }

}
