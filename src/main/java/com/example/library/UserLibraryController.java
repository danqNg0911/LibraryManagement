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
import org.controlsfx.control.textfield.TextFields;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserLibraryController extends UserController {

    @FXML
    public ImageView libImage;

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

    private List<String> categorySuggestions;
    private List<String> titleSuggestions;
    private List<String> authorSuggestions;

    private static final String CATEGORY_DATA_FILE_PATH = LinkSetting.CATEGORY_LIST_FILE_PATH.getLink();
    private static final String TITLE_DATA_FILE_PATH = LinkSetting.TITLE_LIST_FILE_PATH.getLink();
    private static final String AUTHOR_DATA_FILE_PATH = LinkSetting.AUTHOR_LIST_FILE_PATH.getLink();

    @FXML
    public void initialize() throws IOException {
        baseInitialize();
        libImage.setImage(new Image(getClass().getResource("/com/example/library/assets/lib (2).png").toExternalForm()));
        categorySuggestions = loadSuggestions(CATEGORY_DATA_FILE_PATH);
        titleSuggestions = loadSuggestions(TITLE_DATA_FILE_PATH);
        authorSuggestions = loadSuggestions(AUTHOR_DATA_FILE_PATH);
        TextFields.bindAutoCompletion(titleField, titleSuggestions);
        TextFields.bindAutoCompletion(authorField, authorSuggestions);
        TextFields.bindAutoCompletion(categoryField, categorySuggestions);
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
            Date date = new Date(Timestamp.from(Instant.now()).getTime());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/library/fxml/bookItem.fxml"));

            try {
                // tao Hbox cho tung doi tuong
                HBox bookItem = loader.load();

                BookItemController controller = loader.getController();
                controller.setBook(new Book(title, authors, categories, imageUrl, description, date));
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

    private List<String> loadSuggestions(String filePath) throws IOException {
        List<String> suggestionsList = new ArrayList<>();
        //InputStream inputStream = getClass().getResourceAsStream(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                suggestionsList.add(line.trim());
            }
        }
        return suggestionsList;
    }
}