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
    private boolean isPause;
    private Timeline timeline;
    private Pane bottomPane;

    public Background(Pane bottomPane) {
        this.bottomPane = bottomPane;
        this.isPause = false;
        timeline = new Timeline(new KeyFrame(Duration.millis(10), new BackgroundHandler()));
        bg1 = new ImageView();
        bg2 = new ImageView();
    }

    public void start(int roundCount) {
        this.isPause = false;
        //System.out.println(isPause);

        // Tạo hai ImageView cho nền
        ImageView backgroundImageView = null;
        try {
            System.out.println("roundCount in BG: " + roundCount);
            Image backgroundImage = new Image(getClass().getResource(LinkSetting.BACKGROUND_IMAGE.getLink() + roundCount + ".png").toExternalForm()); // Đường dẫn đến hình nền
            backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setLayoutY(0);
            backgroundImageView.setFitHeight(400);
            backgroundImageView.setFitWidth(800);

            bg1.setImage(backgroundImage);
            bg2.setImage(backgroundImage);

            // Đặt kích thước và vị trí cho nền
            bg1.setFitHeight(SCREEN_HEIGHT);
            bg1.setFitWidth(SCREEN_WIDTH);
            bg2.setFitHeight(SCREEN_HEIGHT);
            bg2.setFitWidth(SCREEN_WIDTH);

            // Đặt bg2 ở bên phải bg1
            bg1.setLayoutX(0);
            bg2.setLayoutX(SCREEN_WIDTH);
        } catch (Exception e) {
            System.out.println("Loi file background...." + LinkSetting.BACKGROUND_IMAGE.getLink() + 1 + ".png");
        }

        if (bg1 != null && bg2 != null) {
            if (true) {
                System.out.println("hihi");
                bottomPane.getChildren().remove(bg1);
                bottomPane.getChildren().add(bg1);
                bg1.toBack();
            }
            if (true) {
                bottomPane.getChildren().remove(bg2);
                bottomPane.getChildren().add(bg2);
                bg2.toBack();
            }
        } else {
            System.out.println("bg1 or bg2 is null!");
        }


        // Timeline để di chuyển nền
        timeline.setCycleCount(Timeline.INDEFINITE);

        setIsPause(false);
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
        if (this.isPause) {
            timeline.play(); // Tạm dừng Timeline khi Pause
        } else {
            timeline.play(); // Tiếp tục Timeline khi Resume
        }
    }
}
