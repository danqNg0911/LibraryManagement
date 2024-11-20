package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.SQLException;

public class ViewBookManagerController extends ManagerController {
    @FXML
    private Label bookTitle;

    @FXML
    private Label authors;

    @FXML
    private Label category;

    @FXML
    private Label borrowersNumber;

    @FXML
    private Label description;

    @FXML
    private ImageView coverImage;

    @FXML
    private Label username;

    @FXML
    private Label borrowedDate;

    @FXML
    private Label statusText;

    @FXML
    private Label status;

    @FXML
    private Button backButton;

    private Book book;

    public void setBookDetails(Book selectedBook) throws SQLException {
        book = selectedBook;
        if (selectedBook != null) {
            bookTitle.setText(selectedBook.getTitle());
            authors.setText("Authors: " + selectedBook.getAuthor());
            category.setText("Category: " + selectedBook.getCategory());
            description.setText(selectedBook.getDescription());

            if (selectedBook.getImageUrl() != null) {
                coverImage.setImage(new Image(selectedBook.getImageUrl()));
            } else {
                Image nullImage = new Image(LinkSetting.IMAGE_NULL.getLink());
                coverImage.setImage(nullImage);
            }

            username.setText("Username: " + selectedBook.getUsername());

            if (selectedBook.getSource() != null && !selectedBook.getSource().equals("borrowed")) {
                borrowedDate.setText("Created date: " + selectedBook.getDate());
            } else {
                borrowersNumber.setText("Number of Borrowers: " + BookJDBC.getNumberOfBorrowers(selectedBook.getUsername(), selectedBook.getTitle(), selectedBook.getAuthor()));
                borrowedDate.setText("Borrowed date: " + selectedBook.getDate());
                statusText.setVisible(true);
                status.setText(selectedBook.getStatus());
                if (status.getText().equals("Overdue")) {
                    status.setStyle("-fx-text-fill: #ff5757;");
                } else if (status.getText().equals("Available")) {
                    status.setStyle("-fx-text-fill: #00CD00;");
                }
            }
        }
    }

    public void backToPreviousStage(ActionEvent event) throws IOException {
        WindowManager.goBack();
    }

    public void deleteBook(ActionEvent event) {
        BookJDBC.deleteBookFromDatabase(book.getUsername(), book.getTitle(), book.getAuthor(), book.getId() );
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Announcement", "This book is successfully removed", "stylesheet (css)/login_alert.css");
    }

    public void initialize() throws SQLException {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

}
