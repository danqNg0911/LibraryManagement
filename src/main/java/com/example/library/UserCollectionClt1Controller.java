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
    public void initialize() throws IOException {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
        filters.setExpanded(false);
        showCollectionData(new ActionEvent());

        gamePic.setVisible(false);

        // Sự kiện khi di chuột vào gameButton
        gameButton.setOnMouseEntered(event -> {

            // Hiển thị label khi chuột trỏ vào button
            gamePic.setVisible(true);
        });

        // Sự kiện khi chuột rời khỏi gameButton
        gameButton.setOnMouseExited(event -> {

            // Ẩn label khi chuột rời khỏi button
            gamePic.setVisible(false);
        });

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