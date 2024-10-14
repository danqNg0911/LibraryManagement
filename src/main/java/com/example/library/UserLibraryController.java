package com.example.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLibraryController {

    @FXML
    private MenuButton accountMenu;

    @FXML
    private Label accountName;

    @FXML
    private Button collectionButton;

    @FXML
    private ImageView collectionPic;

    @FXML
    private Button dashboardButton;

    @FXML
    private ImageView dashboardPic;

    @FXML
    private Button libraryButton;

    @FXML
    private ImageView libraryPic;

    @FXML
    private Label libraryTitle;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane mainSce;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingPic;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> bookList;

    @FXML
    private ImageView bookCoverPicture;

    @FXML
    private TextArea bookInformation;

    private List<JsonObject> bookResults = new ArrayList<>();

    public void onSearch(ActionEvent event) {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();

        try {
            String jsonResponse = API.searchBooks(title, author, category);
            parseAndDisplayResults(jsonResponse);
        } catch (Exception e) {
            bookInformation.setText("Error fetching data: " + e.getMessage());
        }
    }

    private void parseAndDisplayResults(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");

        if (items == null || items.size() == 0) {
            bookList.setItems(FXCollections.observableArrayList("No results found."));
            return;
        }

        bookResults.clear(); // Reset previous results
        List<String> titles = new ArrayList<>();

        for (var item : items) {
            JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
            String title = volumeInfo.get("title").getAsString();
            bookResults.add(item.getAsJsonObject());
            titles.add(title);
        }

        ObservableList<String> observableTitles = FXCollections.observableArrayList(titles);
        bookList.setItems(observableTitles);
    }

    public void onBookSelected() {
        int selectedIndex = bookList.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= bookResults.size()) {
            return;
        }

        JsonObject selectedBook = bookResults.get(selectedIndex).getAsJsonObject("volumeInfo");
        displayBookDetails(selectedBook);
    }

    private void displayBookDetails(JsonObject volumeInfo) {
        String title = volumeInfo.get("title").getAsString();

        String authors = "Unknown Author";
        if (volumeInfo.has("authors")) {
            JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
            List<String> authorList = new ArrayList<>();
            for (JsonElement authorElement : authorsArray) {
                authorList.add(authorElement.getAsString());
            }
            authors = String.join(", ", authorList);
        }

        String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
        String category = volumeInfo.has("categories") ? volumeInfo.get("categories").getAsString() : "No category available";

        bookInformation.setText("Title: " + title + "\nAuthors: " + authors + "\nCategory: " + category + "\nDescription: " + description);

        if (volumeInfo.has("imageLinks") && volumeInfo.getAsJsonObject("imageLinks").has("thumbnail")) {
            String imageUrl = volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
            bookCoverPicture.setImage(new Image(imageUrl));
        } else {
            bookCoverPicture.setImage(null);
        }
    }

    // Di chuột vào hiện hiệu ứng và ngược lại
    public void showAnimationDas(MouseEvent event) {
        WindowManager.showPic(event, dashboardButton, dashboardPic);
    }

    public void unshowAnimationDas(MouseEvent event) {
        WindowManager.unshowPic(event, dashboardButton, dashboardPic);
    }

    /*public void showAnimationLib(MouseEvent event) {
        WindowManager.showPic(event, libraryButton, libraryPic);
    }

    public void unshowAnimationLib(MouseEvent event) {
        WindowManager.unshowPic(event, libraryButton, libraryPic);
    }*/

    public void showAnimationClt(MouseEvent event) {
        WindowManager.showPic(event, collectionButton, collectionPic);
    }

    public void unshowAnimationClt(MouseEvent event) {
        WindowManager.unshowPic(event, collectionButton, collectionPic);
    }

    public void showAnimationStg(MouseEvent event) {
        WindowManager.showPic(event, settingButton, settingPic);
    }

    public void unshowAnimationStg(MouseEvent event) {
        WindowManager.unshowPic(event, settingButton, settingPic);
    }

    // Chuyen den trang khac
    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }
}