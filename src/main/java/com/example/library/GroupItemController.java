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
import java.sql.SQLException;

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

    private Book selectedBook;

    public void setGroupItem(Book book) {
        this.selectedBook = book;
        Title.setText(book.getTitle());
        Author.setText(book.getAuthor());
        if (book.getImageUrl() != null) {
            Cover.setImage(new Image(book.getImageUrl()));
        } else {
            // C:/YEAR 2/OOP/JavaFX/Bai tap lon _ Thu VIen/src/main/resources/com/example/library/assets/Picture_is_not_available.png
            if (book.getSource() != null && book.getSource().equals("create")) {
                Image defaultImage = new Image(getClass().getResource(LinkSetting.DEFAULT_COVER_IMAGE.getLink()).toExternalForm());
                Cover.setImage(defaultImage);
            } else {
                Image nullImage = new Image(getClass().getResource(LinkSetting.IMAGE_NULL.getLink()).toExternalForm());
                Cover.setImage(nullImage);
            }
        }
    }

    public void viewBook(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/viewItem.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lấy controller của ViewItem và truyền thông tin sách
        ViewItemController viewItemController = loader.getController();
        viewItemController.setBookDetails(selectedBook);

        Scene scene = new Scene(root, 1200, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.navigateTo(scene);
        stage.setScene(scene);
        stage.show();
    }
}