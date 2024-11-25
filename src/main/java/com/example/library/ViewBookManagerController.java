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
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

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
                if (book.getSource() != null && book.getSource().equals("create")) {
                    Image defaultImage = new Image(getClass().getResource(LinkSetting.DEFAULT_COVER_IMAGE.getLink()).toExternalForm());
                    coverImage.setImage(defaultImage);
                } else {
                    Image nullImage = new Image(getClass().getResource(LinkSetting.IMAGE_NULL.getLink()).toExternalForm());
                    coverImage.setImage(nullImage);
                }
            }

            username.setText("Username: " + selectedBook.getUsername());

            if (selectedBook.getSource() != null && !selectedBook.getSource().equals("borrowed")) {
                borrowedDate.setText("Created date: " + selectedBook.getDate());
            } else {
                borrowersNumber.setText("Number of Borrowers: " + BookJDBC.getNumberOfBorrowers(selectedBook.getTitle(), selectedBook.getAuthor()));
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

    public void viewUserAccount(ActionEvent event) throws SQLException {
        UserAccount selectedUser = user.getAccountByUsername(book.getUsername());
        if (selectedUser != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/ViewUserManager.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ViewUserManagerController controller = loader.getController();

            try {
                controller.setUserDetails(selectedUser);
            } catch (SQLException e) {
                System.out.println("loi BookJDBC.getTotalBorrowedBooks(...)");
            }
            //controller.showBarChart(selectedUser);

            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet (css)/managerStyles.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/library/stylesheet (css)/managerUserStyle.css")).toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowManager.navigateTo(scene);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void backToPreviousStage(ActionEvent event) throws IOException {
        WindowManager.goBack();
    }

    public void deleteBook(ActionEvent event) throws SQLException {
        BookJDBC.deleteBookFromDatabase(book.getUsername(), book.getTitle(), book.getAuthor(), book.getId() );
        if (book.getSource().equals("borrowed")) {
            BookJDBC.returnBorrowedBooks(book.getUsername(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getDate());
        }
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Announcement", "This book is successfully removed", "stylesheet (css)/login_alert.css");
    }

    public void initialize() throws SQLException {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

}
