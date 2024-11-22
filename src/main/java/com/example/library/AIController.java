package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

public class AIController extends UserController {

    @FXML
    private WebView webView;

    @FXML
    public void initialize() {
        // Tạo WebEngine để tải trang web
        WebEngine webEngine = webView.getEngine();
        // URL của trang AI
        String url = "https://gemini.google.com/?hl=vi";
        //String url = "https://copilot.microsoft.com/?msockid=210fd84e398e67e51320cd70384e663a";
        // Load trang web
        webEngine.load(url);
    }

    @FXML
    public void handleReturnToLib(ActionEvent actionEvent) throws IOException {
        freeUpHeapMemory();
        String fxmlFile = "fxml/UserDashboard.fxml";
        WindowManager.addFxmlCss(fxmlFile, "stylesheet (css)/userStyles.css", "stylesheet (css)/userDashStyle.css", 1200, 800);
    }

    @Override
    public void freeUpHeapMemory() {
        webView = null;
        System.gc();
    }
}
