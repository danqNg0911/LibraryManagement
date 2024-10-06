package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.swing.text.html.ImageView;

public class Controller {
    @FXML
    private Label result;
    private Label op;
    private long number1 = 0;
    private long number2 = 0;
    private String operator;
    private boolean start = true;
    private ImageView loginLogo;;

    public void processNumber(ActionEvent event) {
        if (start) {
            result.setText("");
            start = false;
        }
        String value = ((Button)event.getSource()).getText();
        result.setText(result.getText() + value);
    }

    public void processOperator(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        if (!value.equals("=")) {
            if (result.getText().isEmpty()) {
                result.setText("");
                start = false;
                return;
            }
            operator = value;
            number1 = Long.parseLong(result.getText());
            result.setText("");
        }
        else {
            if (result.getText().isEmpty()) {
                result.setText("");
                start = false;
                return;
            }
            number2 = Long.parseLong(result.getText());
            String output = String.valueOf(Modal.cacll(number1, number2, operator));
            result.setText(output);
            start = true;
        }
    }

}