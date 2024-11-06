package com.example.library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
//import java.lang.classfile.Label;
import java.util.List;

public class CollectionGroupController {
    @FXML
    private HBox BookContainer;

    @FXML
    private Label groupCharacter;

    public void addBookItem(Node bookItemNode) {
        BookContainer.getChildren().add(bookItemNode);
    }

    public void addGroupCharacter(Character character) {
        groupCharacter.setText(character.toString().toUpperCase());
    }
}
