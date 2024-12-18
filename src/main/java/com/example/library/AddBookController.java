package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

import java.io.IOException;

public class AddBookController extends UserCollectionCltController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button updateBookButton;

    private boolean bookEdit = false;

    private int editedBookId = -1;

    public void initialize() {
        baseInitialize();
    }

    public void updateYourBook(ActionEvent actionEvent) throws IOException {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();
        String description = descriptionField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || description.isEmpty()) {
            WindowManager.alertWindow(Alert.AlertType.ERROR, "Alert", "Please complete all information to create your book", "stylesheet (css)/login_alert.css");
        } else {
            if (bookEdit) {
                if (editedBookId == -1) {
                    System.out.println("Book setting error!");
                    return;
                }
                BookJDBC.editBook(editedBookId, title, author, category, description);
                WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Announcement", "Your book has been successfully changed", "stylesheet (css)/login_alert.css");
            } else {
                BookJDBC.addBookToDatabase(user.getUsername(), "", title, author, category, null, description, "create");
                WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Announcement", "This book has been added to your library", "stylesheet (css)/login_alert.css");
            }
        }
    }

    public void setBookEditButton() {
        updateBookButton.setText("Edit Book");
    }

    public void setInfoForBookEditing(Book book) {
        bookEdit = true;
        editedBookId = book.getId();
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        categoryField.setText(book.getCategory());
        descriptionField.setText(book.getDescription());
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
}
