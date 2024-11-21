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

public class UserSettingController extends UserController {
    private boolean isPause = false;
    private boolean isPlay = false;
    private static final String mainSound1 = LinkSetting.MAIN_SOUND_1.getLink();
    private static final String mainSound2 = LinkSetting.MAIN_SOUND_2.getLink();
    private static final String mainSound3 = LinkSetting.MAIN_SOUND_3.getLink();
    private static final String mainSound4 = LinkSetting.MAIN_SOUND_4.getLink();
    private static final String mainSound5 = LinkSetting.MAIN_SOUND_5.getLink();

    private int selectedAvatarId = -1;

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

    protected void helpChangePasswordForUserAndManager(String newPassword) {
        userJDBC.passwordUpdate(user.getUsername(), newPassword);
    }

    protected void helpChangeAnswersForUserAndManager(String question1, String question2, String question3) {
        userJDBC.securityQuestionsUpdate(user.getUsername(), question1, question2, question3);
    }

    // Thay phone number
    public void handleChangePhone(ActionEvent event) {
        WindowManager.playButtonSound();
        String newPhonenum = newPhoneField.getText();
        String currentPassword = currentPass3.getText();

        boolean passwordCheck = false;
        // Cập nhật tên mới trong cơ sở dữ liệu (giả sử có phương thức updateName trong User)
        boolean success = user.phoneNumUpdate(user.getUsername(),newPhonenum); // Cập nhật tên trong DBif (newName.isEmpty()) {

        // check password hiện tại
        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning3, "Password is incorrect", 2);
        } else {
            passwordCheck = true;
        }

        if (success && passwordCheck) {
            // Nếu cập nhật thành công, hieenj ra thong bao và thay đổi tên trong giao diện ngược lại thong bao loi
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Phone Number", "Your phone number has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));

        } else {

            WindowManager.RedWarningLabel(newPhoneWarning, "This phone is invalid", 2);
        }
    }

    // Thay phone number
    public void handleChangeEmail(ActionEvent event) {
        WindowManager.playButtonSound();
        String newEmail = newEmailField.getText();
        String currentPassword = currentPass3.getText();

        boolean passwordCheck = false;
        // Cập nhật tên mới trong cơ sở dữ liệu (giả sử có phương thức updateName trong User)
        boolean success = user.emailUpdate(user.getUsername(),newEmail); // Cập nhật tên trong DBif (newName.isEmpty()) {

        // check password hiện tại
        if (!currentPassword.equals(user.getPassword(user.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning3, "Password is incorrect", 2);
        } else {
            passwordCheck = true;
        }

        if (success && passwordCheck) {
            // Nếu cập nhật thành công, hieenj ra thong bao và thay đổi tên trong giao diện ngược lại thong bao loi
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Email", "Your email has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));

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
        if (!isPause && isPlay) { // Nếu đang phát -> tạm dừng
            WindowManager.pauseMusic();
            buttonPause.setText("Resume");
            isPause = true;
        } else if (isPause && isPlay) { // Nếu đang tạm dừng -> tiếp tục phát
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

}