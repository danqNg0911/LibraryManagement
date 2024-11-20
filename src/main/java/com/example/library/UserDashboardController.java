package com.example.library;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

public class UserDashboardController extends UserController{

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
    public void initialize()  {
        baseInitialize();
        currentName1Label.setText(user.getName(user.getUsername()));
        currentUserameLabel.setText(user.getUsername());
        currentPhoneLabel.setText(user.getPhone(user.getUsername()));
        currentEmailLabel.setText(user.getEmail(user.getUsername()));
        showBarChart();
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