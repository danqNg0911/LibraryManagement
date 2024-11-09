package com.example.library;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    UserJDBC userJDBC = new UserJDBC();
    ManagerJDBC managerJDBC = new ManagerJDBC();

    @Override
    public void start(Stage stage) throws IOException {
        WindowManager.setStage(stage);

        WindowManager.addFxmlCss("fxml/BeginScene.fxml", "stylesheet (css)/style.css", "stylesheet (css)/login.css", 400, 300);

        if (userJDBC.testDatabaseConnection()) {
            System.out.println("Successful UserDatabase Connection");
        }

        if (managerJDBC.testDatabaseConnection()) {
            System.out.println("Successful ManagerDatabase Connection");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

