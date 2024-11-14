package com.example.library;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserLibraryController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    User user = new User();

    @FXML
    private Button accHelpsButton;

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
    private ImageView dashboardPic11;

    @FXML
    private Button helpsButton;

    @FXML
    private Button libraryButton;

    @FXML
    private ImageView libraryPic;

    @FXML
    private ImageView libraryPic11;

    @FXML
    private Label libraryTitle;

    @FXML
    private ImageView logo;

    @FXML
    private Button logoutButton;

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
    private Button upgradeButton;

    @FXML
    private TextField isbnField;

    @FXML
    private ProgressIndicator loadingSearching;

    public void onSearch(ActionEvent event) {
        String isbn = isbnField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();

        if (isbn.isEmpty() && title.isEmpty() && author.isEmpty() && category.isEmpty()) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Alert", "You must filled at least one field to search", "stylesheet (css)/login_alert.css");
        } else {
            loadingSearching.setVisible(true);
            // làm mờ khi tìm kiếm
            mainSce.setEffect(new GaussianBlur(4));

            // Sử dụng Task để tìm kiếm sách trên luồng nền
            Task<String> searchTask = new Task<>() {
                @Override
                protected String call() throws Exception {
                    return API.searchBooks(title, author, category, isbn);  // Gọi API trên nền
                }
            };

            // Khi task hoàn thành (onSucceeded), cập nhật UI
            searchTask.setOnSucceeded(e -> {
                String jsonResponse = searchTask.getValue();
                if (jsonResponse.isEmpty()) {
                    WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Alert", "No books match your search in the current library", "stylesheet (css)/login_alert.css");
                } else {
                    updateBook(jsonResponse);  // Cập nhật sách vào giao diện
                }
                loadingSearching.setVisible(false);  // Ẩn biểu tượng loading sau khi hoàn thành
                mainSce.setEffect(null);
            });

            // Nếu task gặp lỗi (onFailed), xử lý lỗi và tắt biểu tượng loading
            searchTask.setOnFailed(e -> {
                loadingSearching.setVisible(false);
                mainSce.setEffect(null);
                WindowManager.alertWindow(Alert.AlertType.ERROR, "Error", "An error occurred during the search", "stylesheet (css)/login_alert.css");
            });

            // Chạy task trên luồng nền
            new Thread(searchTask).start();
        }
    }

    public void updateBook(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();

        if (!jsonObject.has("items")) {
            System.out.println("No items found");
            loadingSearching.setVisible(false);
            return;
        }

        JsonArray items = jsonObject.getAsJsonArray("items");
        booksContainer.getChildren().clear();  // Xóa kết quả cũ

        if (items.size() == 0) {
            System.out.println("No books found");
            loadingSearching.setVisible(false);
            return;
        }


        for (JsonElement item : items) {
            JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");

            String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown Title";
            String authors = getAuthors(volumeInfo);
            String categories = getCategories(volumeInfo);
            String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description available";
            String imageUrl = getImageUrl(volumeInfo);

            // Cung cấp giá trị cho timestamp (ví dụ như thời gian hiện tại)
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/library/fxml/bookItem.fxml"));

            try {
                // tao Hbox cho tung doi tuong
                HBox bookItem = loader.load();

                BookItemController controller = loader.getController();
                controller.setBook(new Book(title, authors, categories, imageUrl, description, timestamp));
                booksContainer.getChildren().add(bookItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadingSearching.setVisible(false);
    }

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

    public void showAnimationHelps(MouseEvent event) {
        WindowManager.showPic(event, helpsButton, dashboardPic11);
    }

    public void unshowAnimationHelps(MouseEvent event) {
        WindowManager.unshowPic(event, helpsButton, dashboardPic11);
    }

    public void showAnimationUpg(MouseEvent event) {
        WindowManager.showPic(event, upgradeButton, libraryPic11);
    }

    public void unshowAnimationUpg(MouseEvent event) {
        WindowManager.unshowPic(event, upgradeButton, libraryPic11);
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

    public void moveToHelps(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserHelps.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userHelpsStyle.css", 1200, 800, actionEvent);
    }

    public void moveToUpgrade(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserUpgrade.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userUpgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserSetting.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userStgStyle.css", 1200, 800, actionEvent);
    }

    public void moveToAccHelps(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserHelps.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userHelpsStyle.css", 1200, 800, actionEvent);
    }

    public void showOptionAccount(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        accVBox.setVisible(!accVBox.isVisible());
    }

    public void showCltOption(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        cltOptionVBox.setVisible(!cltOptionVBox.isVisible());
    }

    //log out
    public void logOut(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
        user.closeConnection();
        pause.play();
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }
}