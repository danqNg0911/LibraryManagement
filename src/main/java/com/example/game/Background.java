package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Background {
    private static final double SCREEN_WIDTH = 800; // Độ rộng màn hình
    private static final double SCREEN_HEIGHT = 400; // Độ dài màn hình
    private static final double BACKGROUND_SPEED = NumSetting.BACKGROUND_SPEED.getNum(); // Tốc độ di chuyển
    private ImageView bg1;
    private ImageView bg2;
    private boolean isPause = false;
    private Timeline timeline;
    private Pane bottomPane;

    public Background(Pane bottomPane) {
        this.bottomPane = bottomPane;
    }

    public void start() {

        // Tạo hai ImageView cho nền
        ImageView backgroundImageView = null;
        try {
            Image backgroundImage = new Image(getClass().getResource(LinkSetting.BACKGROUND_IMAGE.getLink()).toExternalForm()); // Đường dẫn đến hình nền
            backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setLayoutY(0);
            backgroundImageView.setFitHeight(400);
            backgroundImageView.setFitWidth(800);

            bg1 = new ImageView(backgroundImage);
            bg2 = new ImageView(backgroundImage);

            // Đặt kích thước và vị trí cho nền
            bg1.setFitHeight(SCREEN_HEIGHT);
            bg1.setFitWidth(SCREEN_WIDTH);
            bg2.setFitHeight(SCREEN_HEIGHT);
            bg2.setFitWidth(SCREEN_WIDTH);

            // Đặt bg2 ở bên phải bg1
            bg1.setLayoutX(0);
            bg2.setLayoutX(SCREEN_WIDTH);
        } catch (Exception e) {
            System.out.println("Loi file background....");
        }

        if (bg1 != null && bg2 != null) {
            if (!bottomPane.getChildren().contains(bg1)) {
                bottomPane.getChildren().add(bg1);
                bg1.toBack();
            }
            if (!bottomPane.getChildren().contains(bg2)) {
                bottomPane.getChildren().add(bg2);
                bg2.toBack();
            }
        } else {
            System.out.println("bg1 or bg2 is null!");
        }


        // Timeline để di chuyển nền
        timeline = new Timeline(new KeyFrame(Duration.millis(10), new BackgroundHandler()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Chạy timeline nếu không phải đang ở trạng thái tạm dừng
        if (!isPause) {
            timeline.play();
        }
    }

    // Lớp xử lý riêng thay cho lambda
    private class BackgroundHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            // Di chuyển nền sang trái
            bg1.setLayoutX(bg1.getLayoutX() - BACKGROUND_SPEED);
            bg2.setLayoutX(bg2.getLayoutX() - BACKGROUND_SPEED);

            // Nếu bg1 ra khỏi màn hình, đặt nó sang bên phải của bg2
            if (bg1.getLayoutX() <= -SCREEN_WIDTH) {
                bg1.setLayoutX(bg2.getLayoutX() + SCREEN_WIDTH);
            }

            // Nếu bg2 ra khỏi màn hình, đặt nó sang bên phải của bg1
            if (bg2.getLayoutX() <= -SCREEN_WIDTH) {
                bg2.setLayoutX(bg1.getLayoutX() + SCREEN_WIDTH);
            }
        }
    }

    public void setIsPause(boolean isPause) {
        this.isPause = isPause;
        if (isPause) {
            timeline.pause(); // Tạm dừng Timeline khi Pause
        } else {
            timeline.play(); // Tiếp tục Timeline khi Resume
        }
    }
}
