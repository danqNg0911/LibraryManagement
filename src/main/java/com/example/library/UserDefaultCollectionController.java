package com.example.library;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserDefaultCollectionController extends UserCollectionController {
    public Button clt1Button;
    public ImageView clt1Image;
    public Button clt2Button;
    public ImageView clt2Image;
    public Button clt3Button;
    public ImageView clt3Image;
    public Button clt4Button;
    public ImageView clt4Image;
    public Button clt5Button;
    public ImageView clt5Image;
    public Button clt6Button;
    public ImageView clt6Image;

    public UserCollectionController userCollectionController;

    protected Map<Character, List<Book>> sortByTitle(List<Book> books) {
        Map<Character, List<Book>> ListByTitle = new TreeMap<Character, List<Book>>();
        for (Book book : books) {
            char first = book.getTitle().toLowerCase().charAt(0);
            ListByTitle.computeIfAbsent(first, k -> new ArrayList<>()).add(book);
        }
        return ListByTitle;
    }

    public void handleClt1Button(ActionEvent actionEvent) throws SQLException, IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollectionClt1.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void handleClt2Button(ActionEvent actionEvent) throws SQLException, IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollectionClt2.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void handleClt3Button(ActionEvent actionEvent) throws SQLException, IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollectionClt3.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void handleClt4Button(ActionEvent actionEvent) throws SQLException, IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollectionClt4.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void handleClt5Button(ActionEvent actionEvent) throws SQLException, IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollectionClt5.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void handleClt6Button(ActionEvent actionEvent) throws SQLException, IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollectionClt6.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }


}
