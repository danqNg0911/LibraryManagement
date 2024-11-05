package com.example.library;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class ManagerSettingController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    Manager manager = new Manager();

    @FXML
    private ImageView MBPic;

    @FXML
    private ImageView MUPic;

    @FXML
    private TextField Q1Field;

    @FXML
    private TextField Q2Field;

    @FXML
    private TextField Q3Field;

    @FXML
    private Button accSetButton;

    @FXML
    private VBox accVBox;

    @FXML
    private Button accountButton;

    @FXML
    private Label accountName;

    @FXML
    private TitledPane avatarTitledPane;

    @FXML
    private Button changeAnswerButton;

    @FXML
    private Button changeAvatarButton;

    @FXML
    private Button changeNameButton;

    @FXML
    private Label changeNameSuccessedLabel;

    @FXML
    private Button changePassButton;

    @FXML
    private PasswordField confirmNewPass;

    @FXML
    private Label confirmPassWarning;

    @FXML
    private ImageView currentAvatar;

    @FXML
    private Label currentNameLabel;

    @FXML
    private PasswordField currentPass;

    @FXML
    private PasswordField currentPass1;

    @FXML
    private PasswordField currentPass2;

    @FXML
    private Label currentPassWarning;

    @FXML
    private Label currentPassWarning1;

    @FXML
    private Label currentPassWarning2;

    @FXML
    private ImageView dashboardPic11;

    @FXML
    private Label dashboardTitle;

    @FXML
    private Button helpsButton;

    @FXML
    private ImageView logo;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane mainSce;

    @FXML
    private Button manageBooksButton;

    @FXML
    private Button manageUsersButton;

    @FXML
    private TitledPane nameTitledPane;

    @FXML
    private ImageView newAvatar;

    @FXML
    private TextField newNameField;

    @FXML
    private PasswordField newPass;

    @FXML
    private Label newPassWarning;

    @FXML
    private TitledPane passTitledPane;

    @FXML
    private Label question1Warning;

    @FXML
    private Label question2Warning;

    @FXML
    private Label question3Warning;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingPic;

    @FXML
    private Button uploadImageButton;

    // Di chuột vào hiện hiệu ứng và ngược lại
    /*public void showAnimationDsb(MouseEvent event) {
        WindowManager.showPic(event, dashboardButton, dashboardPic);
    }

    public void unshowAnimationDsb(MouseEvent event) {
        WindowManager.unshowPic(event, dashboardButton, dashboardPic);
    }*/

    public void showAnimationMU(MouseEvent event) {
        WindowManager.showPic(event, manageUsersButton, MUPic);
    }

    public void unshowAnimationMU(MouseEvent event) {
        WindowManager.unshowPic(event, manageUsersButton, MUPic);
    }

    public void showAnimationMB(MouseEvent event) {
        WindowManager.showPic(event, manageBooksButton, MBPic);
    }

    public void unshowAnimationMB(MouseEvent event) {
        WindowManager.unshowPic(event, manageBooksButton, MBPic);
    }

    public void showAnimationHelps(MouseEvent event) {
        WindowManager.showPic(event, helpsButton, dashboardPic11);
    }

    public void unshowAnimationHelps(MouseEvent event) {
        WindowManager.unshowPic(event, helpsButton, dashboardPic11);
    }

    // Chuyen den trang khac
    public void moveToManageUsers(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerUser.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerUserStyle.css", 1200, 800, actionEvent);
    }

    public void moveToManageBooks(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerBook.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerBookStyle.css", 1200, 800, actionEvent);
    }

    public void moveToHelps(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerHelps.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerHelpsStyle.css", 1200, 800, actionEvent);
    }

    public void moveToaccSetting(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        WindowManager.handlemoveButton("fxml/ManagerSetting.fxml", "stylesheet (css)/managerStyles.css", "stylesheet (css)/managerStgStyle.css", 1200, 800, actionEvent);
    }

    public void showOptionAccount(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        accVBox.setVisible(!accVBox.isVisible());
    }

    //log out
    public void logOut(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
        manager.closeConnection();
        pause.play();
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    @FXML
    // Thay tên
    public void handleChangeName(ActionEvent event) {
        String newName = newNameField.getText();
        String currentPassword = currentPass1.getText();

        boolean passwordCheck = false;
        // Cập nhật tên mới trong cơ sở dữ liệu (giả sử có phương thức updateName trong User)
        boolean success = manager.nameUpdate(manager.getUsername(),newName); // Cập nhật tên trong DBif (newName.isEmpty()) {

        // check password hiện tại
        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning1, "Password is incorrect", 2);
        } else {
            passwordCheck = true;
        }

        if (success && passwordCheck) {
            // Nếu cập nhật thành công, hieenj ra thong bao và thay đổi tên trong giao diện ngược lại thong bao loi
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Name", "Your name has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));

            accountName.setText(newName);
            currentNameLabel.setText(newName);
        } else {

            WindowManager.RedWarningLabel(changeNameSuccessedLabel, "This name is invalid", 2);
        }
    }

    @FXML
    // Thay password
    public void handleChangePassword(ActionEvent event) {
        String currentPassword = currentPass.getText();
        String newPassword = newPass.getText();
        String confirmPassword = confirmNewPass.getText();

        boolean passwordCheck = false;
        boolean newPasswordCheck = false;
        boolean confirmPasswordCheck = false;

        // check password hiện tại
        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning, "Password is incorrect", 2);
        } else {
            passwordCheck = true;
        }

        // check new password
        if (passwordCheck && newPassword.isEmpty()) {
            WindowManager.RedWarningLabel(newPassWarning, "This information is required", 2);
        } else if (passwordCheck && newPassword.length() < 8) {
            WindowManager.RedWarningLabel(newPassWarning, "Password must be over 8 characters", 2);
        } else if (passwordCheck && newPassword.equals(manager.getPassword(manager.getUsername()))) {
            WindowManager.RedWarningLabel(newPassWarning, "New password must be different the current password ", 2);
        } else {
            newPasswordCheck = true;
        }

        // check confirm password
        if (passwordCheck && confirmPassword.isEmpty()) {
            WindowManager.RedWarningLabel(confirmPassWarning, "This information is required", 2);
        } else if (passwordCheck && newPasswordCheck && !confirmPassword.equals(newPassword)){
            WindowManager.RedWarningLabel(confirmPassWarning, "Password must be the same", 2);
        } else {
            confirmPasswordCheck = true;
        }

        // Nếu thay password thành công
        if (passwordCheck && newPasswordCheck && confirmPasswordCheck) {
            userJDBC.passwordUpdate(manager.getUsername(), newPassword);
            // Thông bao doi mat khau thanh cong
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Password Reset", "Your password has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
        }
    }

    @FXML
    public void handleChangeAnswers(ActionEvent event) {
        String question1 = Q1Field.getText();
        String question2 = Q2Field.getText();
        String question3 = Q3Field.getText();
        String currentPassword = currentPass2.getText();

        boolean passwordCheck = false;
        boolean question1Check = false;
        boolean question2Check = false;
        boolean question3Check = false;

        // check xem nhập đúng mkhau chưa
        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning2, "Password is incorrect", 2);
        } else {
            passwordCheck = true;
        }

        // security question khong hợp lệ
        if (passwordCheck && question1.isEmpty()) {
            WindowManager.RedWarningLabel(question1Warning, "This information is required", 2);
        } else {
            question1Check = true;
        }

        if (passwordCheck && question2.isEmpty()) {
            WindowManager.RedWarningLabel(question2Warning, "This information is required", 2);
        } else {
            question2Check = true;
        }

        if (passwordCheck && question3.isEmpty()) {
            WindowManager.RedWarningLabel(question3Warning, "This information is required", 2);
        } else {
            question3Check = true;
        }

        // khi nhập các câu trả lời hợp lệ
        if (passwordCheck && question1Check && question2Check && question3Check) {
            userJDBC.securityQuestionsUpdate(manager.getUsername(), question1, question2, question3);
            // Thông bao doi mat khau thanh cong
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change security answers", "Your security answers has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
        }
    }
}