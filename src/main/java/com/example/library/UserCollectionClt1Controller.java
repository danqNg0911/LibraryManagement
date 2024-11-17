package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.concurrent.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserCollectionClt1Controller {

    public RadioButton radioButton2;
    public RadioButton radioButton3;
    public RadioButton radioButton1;
    public Button comfirmButton;
    public TitledPane filters;
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
    public void handleConfirmButton(ActionEvent actionEvent) throws IOException, SQLException {
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
        showMyCollectionData(actionEvent);
    }

    // Tải dữ liệu và cập nhật UI trong một thread riêng biệt
    public void showMyCollectionData(ActionEvent actionEvent) throws IOException, SQLException {
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

    protected Map<Character, List<Book>> sortByTitle(List<Book> books) {
        Map<Character, List<Book>> ListByTitle = new TreeMap<Character, List<Book>>();
        for (Book book : books) {
            char first = book.getTitle().toLowerCase().charAt(0);
            ListByTitle.computeIfAbsent(first, k -> new ArrayList<>()).add(book);
        }
        return ListByTitle;
    }

    // Di chuột vào hiện hiệu ứng và ngược lại
    public void showAnimationDas(MouseEvent event) {
        WindowManager.showPic(event, dashboardButton, dashboardPic);
    }

    public void unshowAnimationDas(MouseEvent event) {
        WindowManager.unshowPic(event, dashboardButton, dashboardPic);
    }

    public void showAnimationLib(MouseEvent event) {
        WindowManager.showPic(event, libraryButton, libraryPic);
    }

    public void unshowAnimationLib(MouseEvent event) {
        WindowManager.unshowPic(event, libraryButton, libraryPic);
    }

//    public void showAnimationClt(MouseEvent event) {
//        WindowManager.showPic(event, collectionButton, collectionPic);
//    }
//
//    public void unshowAnimationClt(MouseEvent event) {
//        WindowManager.unshowPic(event, collectionButton, collectionPic);
//    }

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
    public void moveToLibrary(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, actionEvent);
    }

    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
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

    public void showSortOption(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        sortOptionVBox.setVisible(!sortOptionVBox.isVisible());
    }

    public void handleAllCollectionButton(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
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
    public void initialize() throws IOException, SQLException {
        // Hiển thị username
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
        filters.setExpanded(false);
        showMyCollectionData(new ActionEvent());
    }
}