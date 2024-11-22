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

public abstract class BaseController extends BaseSettingController {

    protected UserJDBC userJDBC = new UserJDBC();
    protected ManagerJDBC managerJDBC = new ManagerJDBC();
    protected Manager manager = new Manager();
    protected User user = new User();


    @FXML
    protected Label accountName;



    // Thay tên
    @FXML
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
            helpChangePasswordForUserAndManager(newPassword);
            // Thông bao doi mat khau thanh cong
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Password Reset", "Your password has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
        }
    }

    protected void helpChangePasswordForUserAndManager(String newPassword) {
        // Override
    }

    @FXML
    public void handleChangeAnswers(ActionEvent event) {
        WindowManager.playButtonSound();
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
           helpChangeAnswersForUserAndManager(question1, question2, question3);
            // Thông bao doi mat khau thanh cong
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change security answers", "Your security answers has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
        }
    }

    protected void helpChangeAnswersForUserAndManager(String question1, String question2, String question3) {
        // Override
    }


    //log out
    public void logOut(ActionEvent event) throws IOException {
        WindowManager.playButtonSound();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
        manager.closeConnection();
        pause.play();
    }

    public void freeUpHeapMemory() {
        // Override
    }
}
