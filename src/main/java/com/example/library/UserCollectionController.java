package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserCollectionController extends UserController {
    @FXML
    private VBox sortBox;

    @FXML
    private Button addNewClt;

    @FXML
    private Button addedDateSortButton;

    @FXML
    private VBox cltOptionVBox;

    @FXML
    private Label collectionTitle;

    @FXML
    private Button selectCltButton;

    @FXML
    private Button sortButton;

    @FXML
    private VBox sortOptionVBox;

    @FXML
    private Button titleSortButton;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private VBox collectionBookContainer;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    private List<Book> books = new ArrayList<>();

    private boolean TitleSorting = true;


    private Map<Character, List<Book>> sortBooks(List<Book> books) {
        Map<Character, List<Book>> ListByTitle = new TreeMap<Character, List<Book>>();
        if (TitleSorting) {
            for (Book book : books) {
                char first = book.getTitle().toLowerCase().charAt(0);
                ListByTitle.computeIfAbsent(first, k -> new ArrayList<>()).add(book);
            }
        } else {
            for (Book book : books) {
                char first = book.getAuthor().toLowerCase().charAt(0);
                ListByTitle.computeIfAbsent(first, k -> new ArrayList<>()).add(book);
            }
        }
        return ListByTitle;
    }

    public void sortByTitle(MouseEvent mouseEvent) {
        this.TitleSorting = true;
        try {
            showData();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortByAuthor(MouseEvent mouseEvent) {
        this.TitleSorting = false;
        try {
            showData();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void showData(ActionEvent actionEvent) throws IOException, SQLException {
        showData();
    }

    public void showData() throws IOException, SQLException {
        //loadingIndicator.setVisible(true);
        //mainSce.setEffect(new GaussianBlur(4));
        books.clear();
        collectionBookContainer.getChildren().clear();

        String titleSearch = titleField.getText().trim();
        String authorSearch = authorField.getText().trim();
        String categorySearch = categoryField.getText().trim();

        if (titleSearch.isEmpty() && authorSearch.isEmpty() && categorySearch.isEmpty()) {
            books = BookJDBC.getAllBooksFromDatabase(user.getUsername());
        } else {
            books = BookJDBC.searchBooksFromDatabase(user.getUsername(), titleSearch, authorSearch, categorySearch);
        }

        if (books.isEmpty()) {
            WindowManager.alertWindow(Alert.AlertType.ERROR, "Announcement", "There's no thing to show from your library", "stylesheet (css)/login_alert.css");
        } else {
        Map<Character, List<Book>> BooksSortByTitle = sortBooks(books);
        for (Map.Entry<Character, List<Book>> entry : BooksSortByTitle.entrySet()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/Group.fxml"));
                Node collectionGroupNode = loader.load();
                CollectionGroupController collectionGroupController = loader.getController();
                collectionGroupController.addGroupCharacter(entry.getKey());
                for (Book book : entry.getValue()) {
                    FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/GroupItem.fxml"));
                    Node bookItemNode = itemLoader.load();
                    GroupItemController bookItemController = itemLoader.getController();
                    bookItemController.setGroupItem(book);
                    collectionGroupController.addBookItem(bookItemNode);
                }
                collectionBookContainer.getChildren().add(collectionGroupNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } }

        //loadingIndicator.setVisible(false);
        //mainSce.setEffect(null);
    }

    public void moveToAddBook(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/AddBook.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userHelpsStyle.css", 1200, 800, actionEvent);
    }

    public void showSortBox(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        sortBox.setVisible(!sortBox.isVisible());
    }

    public void showAnimationClt(MouseEvent event) {
        return;
    }

    public void unshowAnimationClt(MouseEvent event) {
        return;
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        return;
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }
}