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

public class UserLibraryController extends UserController {
    @FXML
    private TextField authorField;

    @FXML
    private VBox booksContainer;

    @FXML
    private TextField categoryField;

    @FXML
    private VBox cltOptionVBox;

    @FXML
    private Label libraryTitle;

    @FXML
    private Button searchButton;

    @FXML
    private Button selectCltButton;

    @FXML
    private TextField titleField;

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

    public void showAnimationLib(MouseEvent event) {
       return;
    }

    public void unshowAnimationLib(MouseEvent event) {
        return;
    }

    public void moveToLibrary(ActionEvent actionEvent) throws IOException {
        return;
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

        int avatarId = user.getAvatar(user.getUsername());
        switch (avatarId) {
            case 1: {
                Image ava1Img = new Image(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
                currentAvatar.setImage(ava1Img);
                break;
            }
            case 2: {
                Image ava2Img = new Image(getClass().getResource(LinkSetting.AVATAR_2.getLink()).toExternalForm());
                currentAvatar.setImage(ava2Img);
                break;
            }
            case 3: {
                Image ava3Img = new Image(getClass().getResource(LinkSetting.AVATAR_3.getLink()).toExternalForm());
                currentAvatar.setImage(ava3Img);
                break;
            }
            case 4: {
                Image ava4Img = new Image(getClass().getResource(LinkSetting.AVATAR_4.getLink()).toExternalForm());
                currentAvatar.setImage(ava4Img);
                break;
            }
            case 5: {
                Image ava5Img = new Image(getClass().getResource(LinkSetting.AVATAR_5.getLink()).toExternalForm());
                currentAvatar.setImage(ava5Img);
                break;
            }
            case 6: {
                Image ava6Img = new Image(getClass().getResource(LinkSetting.AVATAR_6.getLink()).toExternalForm());
                currentAvatar.setImage(ava6Img);
                break;
            }
            case 7: {
                Image ava7Img = new Image(getClass().getResource(LinkSetting.AVATAR_7.getLink()).toExternalForm());
                currentAvatar.setImage(ava7Img);
                break;
            }
            case 8: {
                Image ava8Img = new Image(getClass().getResource(LinkSetting.AVATAR_8.getLink()).toExternalForm());
                currentAvatar.setImage(ava8Img);
                break;
            }
            case 9: {
                Image ava9Img = new Image(getClass().getResource(LinkSetting.AVATAR_9.getLink()).toExternalForm());
                currentAvatar.setImage(ava9Img);
                break;
            }
            case 0: {
                Image ava0Img = new Image(getClass().getResource(LinkSetting.AVATAR_0.getLink()).toExternalForm());
                currentAvatar.setImage(ava0Img);
                break;
            }
            default:
                System.out.println("Unknown avatar id: " + avatarId);
        }
        System.out.println("Avatar updated to ID: " + avatarId);
    }
}