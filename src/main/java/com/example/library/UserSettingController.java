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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class UserSettingController extends UserController implements BaseSettingController {
    private boolean isPause = false;
    private boolean isPlay = false;
    private static final String mainSound1 = LinkSetting.MAIN_SOUND_1.getLink();
    private static final String mainSound2 = LinkSetting.MAIN_SOUND_2.getLink();
    private static final String mainSound3 = LinkSetting.MAIN_SOUND_3.getLink();
    private static final String mainSound4 = LinkSetting.MAIN_SOUND_4.getLink();
    private static final String mainSound5 = LinkSetting.MAIN_SOUND_5.getLink();

    private int selectedAvatarId = -1;

    @FXML
    protected TextField Q1Field;

    @FXML
    protected TextField Q2Field;

    @FXML
    protected TextField Q3Field;

    @FXML
    protected Label question1Warning;

    @FXML
    protected Label question2Warning;

    @FXML
    protected Label question3Warning;

    @FXML
    protected Label currentNameLabel;

    @FXML
    protected TextField newNameField;

    @FXML
    protected Button changeNameButton;

    @FXML
    protected Label changeNameSuccessedLabel;

    @FXML
    protected PasswordField currentPass;

    @FXML
    protected PasswordField currentPass1;

    @FXML
    protected PasswordField currentPass2;

    @FXML
    protected Label currentPassWarning;

    @FXML
    protected Label currentPassWarning1;

    @FXML
    protected Label currentPassWarning2;

    @FXML
    protected Button changePassButton;

    @FXML
    protected PasswordField confirmNewPass;

    @FXML
    protected Label confirmPassWarning;

    @FXML
    protected PasswordField newPass;

    @FXML
    protected Label newPassWarning;

    @FXML
    protected TitledPane nameTitledPane;

    @FXML
    protected TitledPane passTitledPane;

    @FXML
    protected TitledPane avatarTitledPane;

    @FXML
    protected Button changeAnswerButton;

    @FXML
    private Button changePhoneButton;

    @FXML
    private PasswordField currentPass3;

    @FXML
    private Label currentPassWarning3;

    @FXML
    private TextField newEmailField;

    @FXML
    private Button changeEmailButton;

    @FXML
    private Label newEmailWarning;

    @FXML
    private TextField newPhoneField;

    @FXML
    private Label newPhoneWarning;

    @FXML
    public Button buttonPause;

    @FXML
    private Button ava0;

    @FXML
    private Button ava1;

    @FXML
    private Button ava2;

    @FXML
    private Button ava3;

    @FXML
    private Button ava4;

    @FXML
    private Button ava5;

    @FXML
    private Button ava6;

    @FXML
    private Button ava7;

    @FXML
    private Button ava8;

    @FXML
    private Button ava9;

    @FXML
    private Button changeAvaButton;

    @FXML
    private Label score;

    @FXML
    private Label gift1;

    @FXML
    private Label gift2;

    @FXML
    private Button buttonMusic1;

    @FXML
    private Button buttonMusic2;

    @FXML
    public void initialize() throws IOException {
        baseInitialize();
        currentNameLabel.setText(user.getName(user.getUsername()));
        if (user.getScore(user.getUsername()) == 0) {
            score.setText(String.valueOf(0));
        } else {
            score.setText(String.valueOf(user.getScore(user.getUsername())));
            gift1.setVisible(true);
            buttonMusic1.setVisible(true);
        }
        if (user.getScore(user.getUsername()) >= 3) {
            gift2.setVisible(true);
            buttonMusic2.setVisible(true);
        }
    }


    public void showAnimationStg(MouseEvent event) {
        return;
    }

    public void unshowAnimationStg(MouseEvent event) {
        return;
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        return;
    }

    /**
     * Xử lý sự kiện thay đổi tên người dùng.
     */
    @FXML
    public void handleChangeName(ActionEvent event) {
        String newName = newNameField.getText();
        String currentPassword = currentPass1.getText();

        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning1, "Password is incorrect", 2);
            return;
        }

        boolean success = user.nameUpdate(user.getUsername(), newName);

        if (success) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Name", "Your name has been successfully changed", "stylesheet (css)/login_alert.css");
            accountName.setText(newName);
            currentNameLabel.setText(newName);
        } else {
            WindowManager.RedWarningLabel(changeNameSuccessedLabel, "This name is invalid", 2);
        }
    }

    /**
     * Xử lý sự kiện thay đổi mật khẩu.
     */
    @FXML
    public void handleChangePassword(ActionEvent event) {
        String currentPassword = currentPass.getText();
        String newPassword = newPass.getText();
        String confirmPassword = confirmNewPass.getText(); // Xác nhận mật khẩu mới

        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning, "Password is incorrect", 2);
            return;
        }

        if (newPassword.isEmpty()) {
            WindowManager.RedWarningLabel(newPassWarning, "This information is required", 2);
            return;
        } else if (newPassword.length() < 8) {
            WindowManager.RedWarningLabel(newPassWarning, "Password must be over 8 characters", 2);
            return;
        } else if (newPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(newPassWarning, "New password must be different from the current password", 2);
            return;
        }

        if (confirmPassword.isEmpty()) {
            WindowManager.RedWarningLabel(confirmPassWarning, "This information is required", 2);
            return;
        } else if (!confirmPassword.equals(newPassword)) {
            WindowManager.RedWarningLabel(confirmPassWarning, "Password must be the same", 2);
            return;
        }

        userJDBC.passwordUpdate(user.getUsername(), newPassword);
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Password Reset", "Your password has been successfully changed", "stylesheet (css)/login_alert.css");
    }

    /**
     * Xử lý sự kiện thay đổi câu hỏi bảo mật.
     */
    @FXML
    public void handleChangeAnswers(ActionEvent event) {
        WindowManager.playButtonSound();
        String question1 = Q1Field.getText();
        String question2 = Q2Field.getText();
        String question3 = Q3Field.getText();
        String currentPassword = currentPass2.getText();

        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning2, "Password is incorrect", 2);
            return;
        }

        if (question1.isEmpty()) {
            WindowManager.RedWarningLabel(question1Warning, "This information is required", 2);
            return;
        }

        if (question2.isEmpty()) {
            WindowManager.RedWarningLabel(question2Warning, "This information is required", 2);
            return;
        }

        if (question3.isEmpty()) {
            WindowManager.RedWarningLabel(question3Warning, "This information is required", 2);
            return;
        }

        userJDBC.securityQuestionsUpdate(user.getUsername(), question1, question2, question3);
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change security answers", "Your security answers have been successfully changed", "stylesheet (css)/login_alert.css");
    }

    public void handleChangePhone(ActionEvent event) {
        WindowManager.playButtonSound();
        String newPhonenum = newPhoneField.getText();
        String currentPassword = currentPass3.getText();

        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning3, "Password is incorrect", 2);
            return;
        }

        if (newPhonenum.isEmpty()) {
            WindowManager.RedWarningLabel(newPhoneWarning, "This information is required", 2);
            return;
        }

        if (newPhonenum.length() != 10) {
            WindowManager.RedWarningLabel(newPhoneWarning, "Phone number must be 10 characters", 2);
            return;
        }

        boolean success = user.phoneNumUpdate(user.getUsername(), newPhonenum);

        if (success) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Phone Number", "Your phone number has been successfully changed", "stylesheet (css)/login_alert.css");
        } else {
            WindowManager.RedWarningLabel(newPhoneWarning, "This phone is invalid", 2);
        }
    }

    // Thay phone number
    public void handleChangeEmail(ActionEvent event) {
        WindowManager.playButtonSound();
        String newEmail = newEmailField.getText();
        String currentPassword = currentPass3.getText();

        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning3, "Password is incorrect", 2);
            return;
        }

        if (newEmail.isEmpty()) {
            WindowManager.RedWarningLabel(newEmailWarning, "This information is required", 2);
            return;
        }

        if (!newEmail.contains("@")) {
            WindowManager.RedWarningLabel(newEmailWarning, "Your new email is invalid", 2);
            return;
        }

        boolean success = user.emailUpdate(user.getUsername(), newEmail); // Thực hiện cập nhật

        if (success) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Email", "Your email has been successfully changed", "stylesheet (css)/login_alert.css");
        } else {
            WindowManager.RedWarningLabel(newEmailWarning, "This email is invalid", 2);
        }
    }

    public void playMusic1(ActionEvent event) {
        WindowManager.playMusic(mainSound1);
        isPlay = true;
        isPause = false;
        buttonPause.setText("Pause");
    }

    public void playMusic2(ActionEvent event) {
        WindowManager.playMusic(mainSound2);
        isPlay = true;
        isPause = false;
        buttonPause.setText("Pause");
    }

    public void playMusic3(ActionEvent event) {
        WindowManager.playMusic(mainSound3);
        isPlay = true;
        isPause = false;
        buttonPause.setText("Pause");
    }

    public void playMusic4(ActionEvent event) {
        WindowManager.playMusic(mainSound4);
        isPlay = true;
        isPause = false;
        buttonPause.setText("Pause");
    }

    public void playMusic5(ActionEvent event) {
        WindowManager.playMusic(mainSound5);
        isPlay = true;
        isPause = false;
        buttonPause.setText("Pause");
    }

    public void pauseMusic(ActionEvent event) {
        if (!isPause && isPlay) {
            WindowManager.pauseMusic();
            buttonPause.setText("Resume");
            isPause = true;
        } else if (isPause && isPlay) {
            WindowManager.resumeMusic();
            buttonPause.setText("Pause");
            isPause = false;
        }
    }

    public void stopMusic(ActionEvent event) {
        WindowManager.stopMusic();
        isPlay = false;
        isPause = false;
        buttonPause.setText("Pause");
        pauseMusic(event);
    }

    public void updateAvatar(int avatarId) {
        user.avatarUpdate(user.getUsername(), avatarId);
        switch (avatarId) {
            case 1: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 2: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_2.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 3: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_3.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 4: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_4.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 5: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_5.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 6: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_6.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 7: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_7.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 8: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_8.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 9: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_9.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            case 0: {
                avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_0.getLink()).toExternalForm());
                currentAvatar.setImage(avaImg);
                break;
            }
            default:
                System.out.println("Unknown avatar id: " + avatarId);
        }
        System.out.println("Avatar updated to ID: " + avatarId);
    }

    @FXML
    public void handleAvatarClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        selectedAvatarId = Integer.parseInt(clickedButton.getText());

        System.out.println("Selected Avatar ID: " + selectedAvatarId);
    }

    public void handleChangeAvatar(ActionEvent event) {
        if (selectedAvatarId != -1) {
            String username = user.getUsername();
            updateAvatar(selectedAvatarId);
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Avatar Changed",
                    "Your avatar has been successfully updated!", "stylesheet (css)/login_alert.css");
        } else {
            WindowManager.alertWindow(Alert.AlertType.WARNING, "Avatar Not Selected",
                    "Please select an avatar first!", "stylesheet (css)/login_alert.css");
        }
    }

    public void freeUpHeapMemory() {
        currentAvatar = null;
        System.gc();
    }

}