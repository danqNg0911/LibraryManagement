package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.concurrent.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserCollectionClt1Controller extends UserCollectionCltController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    public void initialize() {
        baseCltInitialize();
    }

    @Override
    public void showCollectionData(ActionEvent actionEvent) throws IOException {
        loadingIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        loadingIndicator.setVisible(true);
        mainSce.setEffect(new GaussianBlur(4));

        Task<Void> loadDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                books.clear();

                String titleSearch = titleField.getText().trim();
                String authorSearch = authorField.getText().trim();
                String categorySearch = categoryField.getText().trim();

                if (titleSearch.isEmpty() && authorSearch.isEmpty() && categorySearch.isEmpty()) {
                    books = BookJDBC.getAllBooksFromDatabase(user.getUsername(), isMostRecent, isMostAdded, isMostView); // Lấy sách từ cơ sở dữ liệu
                } else {
                    books = BookJDBC.searchBooksFromDatabase(user.getUsername(), titleSearch, authorSearch, categorySearch, isMostRecent, isMostAdded, isMostView);
                }
                return null;
            }
        };

        loadDataTask.setOnSucceeded(event -> {
            Map<Character, List<Book>> BooksSortByTitle = sortByTitle(books);
            collectionBookContainer.getChildren().clear();
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
            }

            loadingIndicator.setVisible(false);
            mainSce.setEffect(null);
        });

        loadDataTask.setOnFailed(event -> {
            loadingIndicator.setVisible(false);
            mainSce.setEffect(null);
            System.out.println("Error loading data");
        });

        new Thread(loadDataTask).start();
    }

    public void moveToAddBook(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/AddBook.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userHelpsStyle.css", 1200, 800, actionEvent);
    }
}