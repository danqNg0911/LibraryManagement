package com.example.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLibraryController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    User user = new User();

    @FXML
    private Button accSetButton;

    @FXML
    private VBox accVBox;

    @FXML
    private Button accountButton;

    @FXML
    private Label accountName;

    @FXML
    private TextField authorField;

    @FXML
    private VBox booksContainer;

    @FXML
    private TextField categoryField;

    @FXML
    private VBox cltOptionVBox;

    @FXML
    private Button collectionButton;

    @FXML
    private ImageView collectionPic;

    @FXML
    private ImageView currentAvatar;

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
    private Button searchButton;

    @FXML
    private Button selectCltButton;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingPic;

    @FXML
    private TextField titleField;

    @FXML
    private TextField isbnField;

    public void onSearch(ActionEvent event) {
        String isbn = isbnField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();

        if (isbn.isEmpty() && title.isEmpty() && author.isEmpty() && category.isEmpty()) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Alert", "You must filled at least one field to search", "stylesheet (css)/login_alert.css");
        } else {
            try {
                String jsonResponse = API.searchBooks(title, author, category, isbn);
                if (jsonResponse.isEmpty()) {
                    WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Alert", "No books match your search in current library", "stylesheet (css)/login_alert.css");
                } else {
                    updateBook(jsonResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateBook(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();

        if (!jsonObject.has("items")) {
            System.out.println("No items found");
            return;
        }

        JsonArray items = jsonObject.getAsJsonArray("items");
        booksContainer.getChildren().clear();  // Xóa kết quả cũ

        if (items.size() == 0) {
            System.out.println("No books found");
            return;
        }


        for (JsonElement item : items) {
            JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");

            String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown Title";
            String authors = getAuthors(volumeInfo);
            String categories = getCategories(volumeInfo);
            String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
            String imageUrl = getImageUrl(volumeInfo);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/library/fxml/bookItem.fxml"));

            try {
                // tao Hbox cho tung doi tuong
                HBox bookItem = loader.load();

                BookItemController controller = loader.getController();
                controller.setBook(new Book(title, authors, categories, imageUrl, description));
                booksContainer.getChildren().add(bookItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*private String getISBN(JsonObject volumeInfo) {
        if (volumeInfo.has("industryIdentifiers")) {
            JsonArray industryIdentifiers = volumeInfo.getAsJsonArray("industryIdentifiers");

            String isbn10 = null;
            String isbn13 = null;

            for (JsonElement identifier : industryIdentifiers) {
                JsonObject id = identifier.getAsJsonObject();
                String type = id.get("type").getAsString();

                if (type.equals("ISBN_13")) {
                    isbn13 = id.get("identifier").getAsString();
                }

                if (type.equals("ISBN_10")) {
                    isbn10 = id.get("identifier").getAsString();
                }
            }

            if (isbn10 != null && isbn13 != null) {
                return "ISBN13: " + isbn13 + "    " + "ISBN10: " + isbn10;
            } else if (isbn10 != null) {
                return "ISBN13: " + isbn13;
            } else if (isbn13 != null) {
                return "ISBN10: " + isbn10;
            }
        }

        return "ISBN does not exist";
    }*/


    private String getAuthors(JsonObject volumeInfo) {
        if (volumeInfo.has("authors")) {
            JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
            List<String> authorList = new ArrayList<>();
            for (JsonElement authorElement : authorsArray) {
                authorList.add(authorElement.getAsString());
            }
            return String.join(", ", authorList);
        }
        return "Unknown Author";
    }

    private String getCategories(JsonObject volumeInfo) {
        if (volumeInfo.has("categories")) {
            JsonArray categoriesArray = volumeInfo.getAsJsonArray("categories");
            List<String> categoryList = new ArrayList<>();
            for (JsonElement categoryElement : categoriesArray) {
                categoryList.add(categoryElement.getAsString());
            }
            return String.join(", ", categoryList);
        }
        return "Unknown Category";
    }

    private String getImageUrl(JsonObject volumeInfo) {
        if (volumeInfo.has("imageLinks") && volumeInfo.getAsJsonObject("imageLinks").has("thumbnail")) {
            return volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
        }
        return null;
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
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    public void showOptionAccount(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        accVBox.setVisible(!accVBox.isVisible());
    }

    public void showCltOption(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        cltOptionVBox.setVisible(!cltOptionVBox.isVisible());
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }
}