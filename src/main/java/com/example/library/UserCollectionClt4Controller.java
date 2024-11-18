package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.concurrent.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserCollectionClt4Controller extends UserController implements UserCollectionCltController {

    public Button allCollectionButton;
    public RadioButton radioButton2;
    public RadioButton radioButton3;
    public RadioButton radioButton1;
    public Button comfirmButton;
    public TitledPane filters;
    public Button gameButton;
    public ImageView gamePic;
    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    User user = new User();

    @FXML
    protected Button accHelpsButton;

    @FXML
    protected Button accSetButton;

    @FXML
    protected VBox accVBox;

    @FXML
    protected Button accountButton;

    @FXML
    public Label accountName;

    @FXML
    protected Button addNewClt;

    @FXML
    protected Button addedDateSortButton;

    @FXML
    protected VBox cltOptionVBox;

    @FXML
    protected Button collectionButton;

    @FXML
    protected ImageView collectionPic;

    @FXML
    protected Label collectionTitle;

    @FXML
    protected ImageView currentAvatar;

    @FXML
    protected Button dashboardButton;

    @FXML
    protected ImageView dashboardPic;

    @FXML
    protected ImageView dashboardPic11;

    @FXML
    protected Button helpsButton;

    @FXML
    protected Button libraryButton;

    @FXML
    protected ImageView libraryPic;

    @FXML
    protected ImageView libraryPic11;

    @FXML
    protected ImageView logo;

    @FXML
    protected Button logoutButton;

    @FXML
    protected AnchorPane mainSce;

    @FXML
    protected Button selectCltButton;

    @FXML
    protected Button settingButton;

    @FXML
    protected ImageView settingPic;

    @FXML
    protected Button sortButton;

    @FXML
    protected VBox sortOptionVBox;

    @FXML
    protected Button titleSortButton;

    @FXML
    protected Button upgradeButton;

    @FXML
    protected ProgressIndicator loadingIndicator;

    @FXML
    protected VBox collectionBookContainer;


    private boolean isMostRecent;
    private boolean isMostAdded;
    private boolean isMostView;

    protected List<Book> books = new ArrayList<>();

    @FXML
    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
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

    public void showCollectionData(ActionEvent actionEvent) throws IOException {
        // Hiển thị loading indicator và mờ giao diện
        loadingIndicator.setVisible(true);
        mainSce.setEffect(new GaussianBlur(4));

        // Tạo Task để tải dữ liệu
        Task<List<Book>> loadBooksTask = new Task<>() {
            @Override
            protected List<Book> call() throws Exception {
                // Tải sách từ dịch vụ (ở đây thay vì gọi trực tiếp BookService, bạn có thể thêm logic khác nếu cần)
                return BookService.getBooksFromKeywords(4, isMostRecent, isMostAdded, isMostView);
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


    public Map<Character, List<Book>> sortByTitle(List<Book> books) {
        Map<Character, List<Book>> ListByTitle = new TreeMap<Character, List<Book>>();
        for (Book book : books) {
            char first = book.getTitle().toLowerCase().charAt(0);
            ListByTitle.computeIfAbsent(first, k -> new ArrayList<>()).add(book);
        }
        return ListByTitle;
    }

    public void showDefaultCollectionData() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/UserDefaultCollection.fxml"));
        collectionBookContainer.getChildren().add((Node) loader.load());
    }

    public void handleAllCollectionButton(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void initialize() throws IOException, SQLException {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
        showCollectionData(new ActionEvent());
        filters.setExpanded(false);
        isMostRecent = false;
        isMostAdded = false;
        isMostView = false;

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

    public void handleGameButton(ActionEvent event) throws IOException {
        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukongMenu.fxml", 800, 800);
    }
}