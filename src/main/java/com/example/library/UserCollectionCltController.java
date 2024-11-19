package com.example.library;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class UserCollectionCltController extends  UserCollectionController {

    @FXML
    public RadioButton radioButton1;

    @FXML
    public RadioButton radioButton2;

    @FXML
    public RadioButton radioButton3;

    @FXML
    public Button comfirmButton;

    @FXML
    public TitledPane filters;

    protected boolean isMostRecent;
    protected boolean isMostAdded;
    protected boolean isMostView;

    public void baseCltInitialize() throws IOException {
        baseInitialize();
        filters.setExpanded(false);
        showCollectionData(new ActionEvent());
    }

    @FXML
    protected void handleConfirmButton(ActionEvent actionEvent) throws IOException, SQLException {
        WindowManager.playButtonSound();

        isMostRecent = false;
        isMostAdded = false;
        isMostView = false;

        if (radioButton1.isSelected()) {
            isMostRecent = true;
        }
        if (radioButton2.isSelected()) {
            isMostAdded = true;
        }
        if (radioButton3.isSelected()) {
            isMostView = true;
        }
        showCollectionData(actionEvent);
    }

    @FXML
    protected void handleAllCollectionButton(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void showCollectionData(ActionEvent actionEvent) throws IOException {
        // Hiển thị loading indicator và mờ giao diện
        loadingIndicator.setVisible(true);
        mainSce.setEffect(new GaussianBlur(4));

        // Tạo Task để tải dữ liệu
        Task<List<Book>> loadBooksTask = new Task<>() {
            @Override
            protected List<Book> call() throws Exception {
                // Tải sách từ dịch vụ (ở đây thay vì gọi trực tiếp BookService, bạn có thể thêm logic khác nếu cần)
                return BookService.getBooksFromKeywords(getListNumber(), isMostRecent, isMostAdded, isMostView);
            }
        };

        // Khi task hoàn thành
        loadBooksTask.setOnSucceeded(e -> {
            // Lấy danh sách sách từ kết quả của Task
            books.clear();
            collectionBookContainer.getChildren().clear();
            books = loadBooksTask.getValue();

            // Sắp xếp sách theo tiêu đề
            Map<Character, List<Book>> BooksSortByTitle = sortByTitle(books);

            // Hiển thị các nhóm sách
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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            // Ẩn loading indicator và bỏ mờ giao diện
            loadingIndicator.setVisible(false);
            mainSce.setEffect(null);
        });

        // Khi task thất bại
        loadBooksTask.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
            mainSce.setEffect(null);
            WindowManager.alertWindow(Alert.AlertType.ERROR, "Error", "An error occurred while fetching books", "stylesheet (css)/login_alert.css");
        });

        // Chạy Task trong một thread nền
        new Thread(loadBooksTask).start();
    }

    protected int getListNumber() {
        // Override
        return 0;
    }

    protected Map<Character, List<Book>> sortByTitle(List<Book> books) {
        Map<Character, List<Book>> ListByTitle = new TreeMap<Character, List<Book>>();
        for (Book book : books) {
            char first = book.getTitle().toLowerCase().charAt(0);
            ListByTitle.computeIfAbsent(first, k -> new ArrayList<>()).add(book);
        }
        return ListByTitle;
    }
}
