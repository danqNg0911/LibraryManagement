package com.example.library;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class UserSettingController {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();
    User user = new User();

    @FXML
    private TextField Q1Field;

    @FXML
    private TextField Q2Field;

    @FXML
    private TextField Q3Field;

    @FXML
    private MenuButton accountMenu;

    @FXML
    private Label accountName;

    @FXML
    private AnchorPane accountStgPane;

    @FXML
    private TitledPane accountStgTitledPane;

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
    private Button collectionButton;

    @FXML
    private ImageView collectionPic;

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
    private Button dashboardButton;

    @FXML
    private ImageView dashboardPic;

    @FXML
    private Button libraryButton;

    @FXML
    private ImageView libraryPic;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane mainSce;

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
    private AnchorPane personalities;

    @FXML
    private TitledPane personalitiesTitledPane;

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
    private Label settingTitle;

    @FXML
    private Button uploadImageButton;

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

    public void showAnimationClt(MouseEvent event) {
        WindowManager.showPic(event, collectionButton, collectionPic);
    }

    public void unshowAnimationClt(MouseEvent event) {
        WindowManager.unshowPic(event, collectionButton, collectionPic);
    }

    /*public void showAnimationStg(MouseEvent event) {
        WindowManager.showPic(event, settingButton, settingPic);
    }

    public void unshowAnimationStg(MouseEvent event) {
        WindowManager.unshowPic(event, settingButton, settingPic);
    }*/

    // Chuyen den trang khac
    public void moveToLibrary(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserLibrary.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userLibStyle.css", 1200, 800, actionEvent);
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserCollection.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userCltStyle.css", 1200, 800, actionEvent);
    }

    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/UserDashboard.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800, actionEvent);
    }

    @FXML
    public void initialize() {
        // Hiển thị tên hiện tại của user
        accountName.setText(user.getName(user.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
        currentNameLabel.setText(user.getName(user.getUsername()));
    }

    @FXML
    // Thay tên
    public void handleChangeName(ActionEvent event) {
        String newName = newNameField.getText();
        String currentPassword = currentPass1.getText();

        boolean passwordCheck = false;
        // Cập nhật tên mới trong cơ sở dữ liệu (giả sử có phương thức updateName trong User)
        boolean success = user.nameUpdate(user.getUsername(),newName); // Cập nhật tên trong DBif (newName.isEmpty()) {

        // check password hiện tại
        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
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
        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning, "Password is incorrect", 2);
        } else {
            passwordCheck = true;
        }

        // check new password
        if (passwordCheck && newPassword.isEmpty()) {
            WindowManager.RedWarningLabel(newPassWarning, "This information is required", 2);
        } else if (passwordCheck && newPassword.length() < 8) {
            WindowManager.RedWarningLabel(newPassWarning, "Password must be over 8 characters", 2);
        } else if (passwordCheck && newPassword.equals(user.getPassword(user.getUsername()))) {
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
            userJDBC.passwordUpdate(user.getUsername(), newPassword);
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
        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
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
            userJDBC.securityQuestionsUpdate(user.getUsername(), question1, question2, question3);
            // Thông bao doi mat khau thanh cong
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change security answers", "Your security answers has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
        }
    }

}