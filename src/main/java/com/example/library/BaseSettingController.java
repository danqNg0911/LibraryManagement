package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public interface BaseSettingController {

    @FXML
    void handleChangeName(ActionEvent event);

    @FXML
    void handleChangePassword(ActionEvent event);

}
