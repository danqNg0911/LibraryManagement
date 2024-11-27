package com.example.library;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewUserManagerController extends ManagerController {

    private UserAccount selectedAccount;

    @FXML
    private ImageView currentAvatar1;

    @FXML
    private Label currentEmailLabel;

    @FXML
    private Label currentName1Label;

    @FXML
    private Label currentPhoneLabel;

    @FXML
    private Label currentUserameLabel;

    @FXML
    private Label currentBorrowedLabel;

    @FXML
    private Label currentOverdueLabel;

    @FXML
    private BarChart<String, Number> rollingYearChart;

    @FXML
    private Label managerEmail;

    @FXML
    private Label userEmail;

    public void setUserDetails(UserAccount account) throws SQLException {
        userEmail.setText(account.getEmail());
        selectedAccount = account;
        currentName1Label.setText(account.getName());
        currentUserameLabel.setText(account.getUsername());
        currentEmailLabel.setText(account.getEmail());
        currentPhoneLabel.setText(account.getPhonenum());
        currentBorrowedLabel.setText(Integer.toString(BookJDBC.getTotalBorrowedBooks(account.getUsername())));
        showBarChart(account);
        if (account.getAvatar() > 0) {
            int avatarId = account.getAvatar();
            switch (avatarId) {
                case 1: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_1.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 2: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_2.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 3: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_3.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 4: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_4.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 5: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_5.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 6: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_6.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 7: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_7.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 8: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_8.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 9: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_9.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                case 0: {
                    avaImg = ImageCache.getImage(getClass().getResource(LinkSetting.AVATAR_0.getLink()).toExternalForm());
                    currentAvatar1.setImage(avaImg);
                    break;
                }
                default:
                    System.out.println("Unknown avatar id: " + avatarId);
            }
            System.out.println("Avatar updated to ID: " + avatarId);
        }
    }

    public void openMail(ActionEvent actionEvent) throws IOException {
        WindowManager.playButtonSound();
        String url = "https://mail.google.com/mail/u/0/#inbox?compose=new";


        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop không hỗ trợ chức năng này.");
        }
    }

    public void backToPreviousStage(ActionEvent event) throws IOException {
        WindowManager.goBack();
    }

    public void showBarChart(UserAccount account) {
        System.out.println(account.getUsername());

        rollingYearChart.getData().clear();

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Number of books added by days");

        try {
            Map<String, Integer> books = BookJDBC.getBooksByDay(account.getUsername());

            Map<String, Number> fullData = new HashMap<>(books);

            List<Map.Entry<String, Number>> sortedData = new ArrayList<>(fullData.entrySet());
            sortedData.sort(Map.Entry.comparingByKey());

            for (Map.Entry<String, Number> entry : sortedData) {
                String day = entry.getKey();
                Number count = entry.getValue();

                XYChart.Data<String, Number> data = new XYChart.Data<>(day, 0);
                dataSeries.getData().add(data);

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(data.YValueProperty(), count);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setCycleCount(1);
                timeline.play();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rollingYearChart.getData().add(dataSeries);

        rollingYearChart.getYAxis().setAutoRanging(true);

        rollingYearChart.getXAxis().setAutoRanging(true);
    }

    public void initialize() {
        managerEmail.setText((manager.getEmail(manager.getUsername())));
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }
}

