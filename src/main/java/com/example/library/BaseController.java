package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public abstract class BaseController  {

    protected UserJDBC userJDBC = new UserJDBC();
    protected ManagerJDBC managerJDBC = new ManagerJDBC();
    protected Manager manager = new Manager();
    protected User user = new User();


    @FXML
    protected Label accountName;

    @FXML
    protected Button accountButton;

    @FXML
    protected VBox accVBox;

    @FXML
    protected Button accSetButton;

    @FXML
    protected Button accHelpsButton;

    @FXML
    protected Button helpsButton;

    @FXML
    protected AnchorPane mainSce;

    @FXML
    protected Button logoutButton;

    @FXML
    protected Button settingButton;

    @FXML
    protected ImageView settingPic;

    @FXML
    protected ImageView currentAvatar;

    @FXML
    protected ImageView logo;

    /**
     * Xử lý sự kiện đăng xuất.
     */
    public void logOut(ActionEvent event) throws IOException {
        WindowManager.playButtonSound(); // Phát âm thanh khi nhấn nút
        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500); // Chuyển về giao diện đăng nhập
        manager.closeConnection(); // Đóng kết nối cơ sở dữ liệu
    }

    /**
     * Giải phóng bộ nhớ heap (được ghi đè ở lớp con nếu cần).
     */
    public void freeUpHeapMemory() {
        // Override ở lớp con nếu cần
    }
}
