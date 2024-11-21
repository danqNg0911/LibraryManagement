package com.example.library;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

public class UserDashboardController extends UserController {

    private final List<Image> images = new ArrayList<>();
    private int currentIndex = 0;
    SequentialTransition sequentialTransition;

    @FXML
    public Label introLabel;

    @FXML
    public Button introButton;

    @FXML
    public ImageView intro;

    @FXML
    private Label currentEmailLabel;

    @FXML
    private Label currentName1Label;

    @FXML
    private Label currentPhoneLabel;

    @FXML
    private Label currentUserameLabel;

    @FXML
    private BarChart<String, Number> rollingYearChart;

    @FXML
    public void initialize() {
        baseInitialize();

        introLabel.setVisible(false);

        // Sự kiện khi di chuột vào introButton
        introButton.setOnMouseEntered(event -> {

            // Hiển thị label khi chuột trỏ vào button
            introLabel.setVisible(true);
        });

        // Sự kiện khi chuột rời khỏi introButton
        introButton.setOnMouseExited(event -> {

            // Ẩn label khi chuột rời khỏi button
            introLabel.setVisible(false);
        });

        currentName1Label.setText(user.getName(user.getUsername()));
        currentUserameLabel.setText(user.getUsername());
        currentPhoneLabel.setText(user.getPhone(user.getUsername()));
        currentEmailLabel.setText(user.getEmail(user.getUsername()));
        showBarChart();

        // Thêm các đường dẫn ảnh vào danh sách
        images.add(new Image(getClass().getResource("/com/example/library/assets/intro_ulib.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/com/example/library/assets/intro_new_update.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/com/example/library/assets/intro_bmw.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/com/example/library/assets/intro_coin.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/com/example/library/assets/intro_upgrade.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/com/example/library/assets/intro_ai.png").toExternalForm()));

        // Bắt đầu vòng lặp hiển thị ảnh
        startImageLoop();

    }


    // Di chuột vào hiện hiệu ứng và ngược lại
    public void showAnimationDas(MouseEvent event) {
        return;
    }

    public void unshowAnimationDas(MouseEvent event) {
        return;
    }

    public void moveToDashboard(ActionEvent actionEvent) throws IOException {
        return;
    }

    public void handleIntroButton(ActionEvent actionEvent) throws IOException {
        WindowManager.handlemoveButton("fxml/Intro.fxml", "stylesheet (css)/userStyles.css", "stylesheet (css)/intro.css", 640, 500, actionEvent);
    }

    private void startImageLoop() {
        if (images.isEmpty()) return;

        // Tạo Transition mờ dần ảnh cũ
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), intro);
        fadeOut.setFromValue(1.0); // Ảnh rõ nét
        fadeOut.setToValue(0.6);   // Ảnh mờ hoàn toàn

        // Tạo Transition rõ dần ảnh mới
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), intro);
        fadeIn.setFromValue(0.6);  // Ảnh mờ hoàn toàn
        fadeIn.setToValue(1.0);    // Ảnh rõ nét

        // Thay đổi ảnh trong fadeOut và fadeIn
        fadeOut.setOnFinished(event -> {
            // Chuyển sang ảnh tiếp theo
            currentIndex = (currentIndex + 1) % images.size();
            intro.setImage(images.get(currentIndex));
        });

        // Tạo khoảng chờ giữa hai lần chuyển ảnh
        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        // Kết hợp fadeOut -> fadeIn -> pause thành một chuỗi
        sequentialTransition = new SequentialTransition(
                fadeOut,
                fadeIn,
                pause
        );

        // Lặp lại mãi mãi
        sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);
        sequentialTransition.play();
    }





    public void showBarChart() {
        // Xóa dữ liệu cũ nếu có
        rollingYearChart.getData().clear();

        // Tạo series dữ liệu
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Number of books added by days");

        try {
            // Lấy dữ liệu từ cơ sở dữ liệu
            Map<String, Integer> books = BookJDBC.getBooksByDay(user.getUsername());

            Map<String, Number> fullData = new HashMap<>(books);

            // Sắp xếp dữ liệu theo ngày
            List<Map.Entry<String, Number>> sortedData = new ArrayList<>(fullData.entrySet());
            sortedData.sort(Map.Entry.comparingByKey());

            // Thêm dữ liệu vào series
            for (Map.Entry<String, Number> entry : sortedData) {
                String day = entry.getKey();
                Number count = entry.getValue();

                // Tạo đối tượng Data với giá trị Y ban đầu là 0
                XYChart.Data<String, Number> data = new XYChart.Data<>(day, 0);
                dataSeries.getData().add(data);

                // Tạo animation tăng giá trị từ 0 đến giá trị thực
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

        // Thêm series vào biểu đồ
        rollingYearChart.getData().add(dataSeries);

        // Đảm bảo trục Y tự động điều chỉnh theo dữ liệu
        rollingYearChart.getYAxis().setAutoRanging(true);

        // Đảm bảo trục X hiển thị đầy đủ các ngày
        rollingYearChart.getXAxis().setAutoRanging(true);
    }
}