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

    public void initialize() {
        baseCltInitialize();
    }

    // Tải dữ liệu và cập nhật UI trong một thread riêng biệt
    @Override
    public void showCollectionData(ActionEvent actionEvent) throws IOException {
        loadingIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS); // Chỉ định trạng thái quay không xác định
        loadingIndicator.setVisible(true);
        mainSce.setEffect(new GaussianBlur(4)); // Mờ giao diện khi đang tải dữ liệu

        // Tạo một task để tải dữ liệu từ cơ sở dữ liệu mà không làm gián đoạn giao diện
        Task<Void> loadDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                books.clear();
                books = BookJDBC.getAllBooksFromDatabase(user.getUsername(), isMostRecent, isMostAdded, isMostView); // Lấy sách từ cơ sở dữ liệu
                return null;
            }
        };

        loadDataTask.setOnSucceeded(event -> {
            // Cập nhật giao diện sau khi dữ liệu được tải về thành công
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

            // Ẩn loading indicator và hủy hiệu ứng mờ
            loadingIndicator.setVisible(false);
            mainSce.setEffect(null);
        });

        loadDataTask.setOnFailed(event -> {
            // Xử lý nếu có lỗi khi tải dữ liệu
            loadingIndicator.setVisible(false);
            mainSce.setEffect(null);
            // Hiển thị thông báo lỗi hoặc thông báo người dùng
            System.out.println("Error loading data");
        });

        // Chạy task trong một thread riêng
        new Thread(loadDataTask).start();
    }
}