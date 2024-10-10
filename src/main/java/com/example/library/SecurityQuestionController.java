package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SecurityQuestionController {

    private String name;
    private String username;
    private String password;
    private boolean isReader;

    public void setUserData(String name, String username, String password, boolean isReader) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.isReader = isReader;
    }

    @FXML
    public TextField Q1Field;

    @FXML
    public TextField Q2Field;

    @FXML
    public TextField Q3Field;

    @FXML
    public DatePicker birthdateField;

    @FXML
    public Button button;

    @FXML
    public Label questionLabel;

    @FXML
    public Label successfulLabel;

    public void handleRegisterButton(ActionEvent event) throws IOException {
        String birthdate = birthdateField.getValue().toString();
        String Q1 = Q1Field.getText();
        String Q2 = Q2Field.getText();
        String Q3 = Q3Field.getText();

        // Check thông tin bỏ trống
        if (birthdate.isEmpty() || Q1.isEmpty() || Q2.isEmpty() || Q3.isEmpty()) {
            WindowManager.RedWarningLabel(questionLabel, "Please don't leave any information blank !", 3);
        }

        // them vao User Database
        else if (this.isReader){
            UserJDBC.addAndCheckUserAccount(this.name, this.username, this.password,birthdate, Q1, Q2, Q3);

            successfulLabel.setText("Congratulation! You have successfully registered");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    WindowManager.setStage(stage);
                    try {
                        WindowManager.addFxmlCss("fxml/SignIn.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 600, 500);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            pause.play();
        }
        else {

        }
    }
}

