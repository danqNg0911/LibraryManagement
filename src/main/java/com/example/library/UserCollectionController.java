package com.example.library;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserCollectionController extends UserController {

    @FXML
    protected ProgressIndicator loadingIndicator;

    @FXML
    protected VBox collectionBookContainer;

    protected List<Book> books = new ArrayList<>();

    @FXML
    public void initialize()  {
        baseInitialize();
        System.out.println(user.getName(user.getUsername()));
        try {
            showDefaultCollectionData();
        } catch (IOException e) {
            System.out.println("class userCollectionController, line = 39");
        }
    }

    // Di chuột vào hiện hiệu ứng và ngược lại
    public void showAnimationClt(MouseEvent event) {
        return;
    }

    public void unshowAnimationClt(MouseEvent event) {
        return;
    }

    public void moveToCollection(ActionEvent actionEvent) throws IOException {
        return;
    }

    public void showDefaultCollectionData() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/UserDefaultCollection.fxml"));
        collectionBookContainer.getChildren().add((Node) loader.load());
    }
}