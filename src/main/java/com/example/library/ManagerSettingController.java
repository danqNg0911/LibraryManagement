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

public class ManagerSettingController extends ManagerController implements BaseSettingController {


    @FXML
    public Label currentName2Label;

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
    private Label accountName;

    @FXML
    private Button answerButton;

    @FXML
    private VBox answersVbox;

    @FXML
    private Button changeEmailButton;

    @FXML
    private Label changeNameSuccessedLabel1;

    @FXML
    private Label changeNameSuccessedLabel11;

    @FXML
    private Label changeNameSuccessedLabel111;

    @FXML
    private Button changePhoneButton;

    @FXML
    private ImageView currentAvatar;

    @FXML
    private ImageView currentAvatar1;

    @FXML
    private Label currentEmailLabel;

    @FXML
    private Label currentName1Label;

    @FXML
    private PasswordField currentPass3;

    @FXML
    private Label currentPassWarning3;

    @FXML
    private Label currentPhoneLabel;

    @FXML
    private Label currentUserameLabel;

    @FXML
    private Button nameButton;

    @FXML
    private VBox nameVbox;

    @FXML
    private TextField newEmailField;

    @FXML
    private Label newEmailWarning;

    @FXML
    private TextField newPhoneField;

    @FXML
    private Label newPhoneWarning;

    @FXML
    private Button passButton;

    @FXML
    private VBox passVbox;

    @FXML
    private Button phonemailButton;

    @FXML
    private VBox phonemailVbox;


    public void showAnimationStg(MouseEvent event) {
        return;
    }

    public void unshowAnimationStg(MouseEvent event) {
        return;
    }

    public void moveToSetting(ActionEvent actionEvent) throws IOException {
        return;
    }

    @FXML
    public void initialize() {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
        currentName1Label.setText(manager.getName(manager.getUsername()));
        currentNameLabel.setText(manager.getName(manager.getUsername()));
        currentUserameLabel.setText(manager.getUsername());
        currentPhoneLabel.setText(manager.getPhone(manager.getUsername()));
        currentEmailLabel.setText(manager.getEmail(manager.getUsername()));
    }

    //thay ten
    public void handleChangeName(ActionEvent event) {
        String newName = newNameField.getText();
        String currentPassword = currentPass1.getText();

        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning1, "Password is incorrect", 2);
            return;
        }

        boolean success = manager.nameUpdate(manager.getUsername(),newName);

        if (success) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Name", "Your name has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));

            accountName.setText(newName);
            currentNameLabel.setText(newName);
            currentName1Label.setText(newName);
        } else {
            WindowManager.RedWarningLabel(changeNameSuccessedLabel, "This name is invalid", 2);
        }
    }

    // Thay password
    public void handleChangePassword(ActionEvent event) {
        String currentPassword = currentPass.getText();
        String newPassword = newPass.getText();
        String confirmPassword = confirmNewPass.getText();


        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
            WindowManager.RedWarningLabel(currentPassWarning, "Password is incorrect", 2);
            return;
        }

        if (newPassword.isEmpty()) {
            WindowManager.RedWarningLabel(newPassWarning, "This information is required", 2);
            return;
        } else if (newPassword.length() < 8) {
            WindowManager.RedWarningLabel(newPassWarning, "Password must be over 8 characters", 2);
            return;
        } else if (newPassword.equals(manager.getPassword(manager.getUsername()))) {
            WindowManager.RedWarningLabel(newPassWarning, "New password must be different the current password ", 2);
            return;
        }

        if (confirmPassword.isEmpty()) {
            WindowManager.RedWarningLabel(confirmPassWarning, "This information is required", 2);
            return;
        } else if (!confirmPassword.equals(newPassword)){
            WindowManager.RedWarningLabel(confirmPassWarning, "Password must be the same", 2);
            return;
        }

        managerJDBC.passwordUpdate(manager.getUsername(), newPassword);
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Password Reset", "Your password has been successfully changed", "stylesheet (css)/login_alert.css");
        PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
    }

    @FXML
    //thay cau hoi bao mat
    public void handleChangeAnswers(ActionEvent event) {
        String question1 = Q1Field.getText();
        String question2 = Q2Field.getText();
        String question3 = Q3Field.getText();
        String currentPassword = currentPass2.getText();

        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
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

        managerJDBC.securityQuestionsUpdate(manager.getUsername(), question1, question2, question3);
        WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change security answers", "Your security answers has been successfully changed", "stylesheet (css)/login_alert.css");
        PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
    }

    // Thay phone number
    public void handleChangePhone(ActionEvent event) {
        WindowManager.playButtonSound();
        String newPhonenum = newPhoneField.getText();
        String currentPassword = currentPass3.getText();

        // check password hiện tại
        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
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

        boolean success = manager.phoneNumUpdate(manager.getUsername(), newPhonenum);

        if (success) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Phone Number", "Your phone number has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
            currentPhoneLabel.setText(newPhonenum);
        } else {
            WindowManager.RedWarningLabel(newPhoneWarning, "This phone is invalid", 2);
        }
    }

    // Thay phone number
    public void handleChangeEmail(ActionEvent event) {
        WindowManager.playButtonSound();
        String newEmail = newEmailField.getText();
        String currentPassword = currentPass3.getText();

        // check password hiện tại
        if (!currentPassword.equals(manager.getPassword(manager.getUsername()))) {
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

        boolean success = manager.emailUpdate(manager.getUsername(), newEmail); // Thực hiện cập nhật

        if (success) {
            WindowManager.alertWindow(Alert.AlertType.INFORMATION, "Change Email", "Your email has been successfully changed", "stylesheet (css)/login_alert.css");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(2));
            currentEmailLabel.setText(newEmail);
        } else {
            WindowManager.RedWarningLabel(newEmailWarning, "This email is invalid", 2);
        }
    }

    public void handleName(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        nameVbox.setVisible(!nameVbox.isVisible());
        passVbox.setVisible(false);
        answersVbox.setVisible(false);
        phonemailVbox.setVisible(false);
    }

    public void handlePass(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        nameVbox.setVisible(false);
        passVbox.setVisible(!passVbox.isVisible());
        answersVbox.setVisible(false);
        phonemailVbox.setVisible(false);
    }

    public void handleAnswer(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        nameVbox.setVisible(false);
        passVbox.setVisible(false);
        answersVbox.setVisible(!answersVbox.isVisible());
        phonemailVbox.setVisible(false);
    }

    public void handlePhonemail(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        nameVbox.setVisible(false);
        passVbox.setVisible(false);
        answersVbox.setVisible(false);
        phonemailVbox.setVisible(!phonemailVbox.isVisible());
    }
}