package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class CharacterAnimation {
    private ImageView imageView;
    private List<Image> images = new ArrayList<>();
    private int currentIndex = 0;
    private Timeline animationTimeline;
    private boolean isRunning = false; // Biến trạng thái

    public CharacterAnimation(ImageView imageView, String path, int numberOfImages, String answer) {
        this.imageView = imageView;
        loadImages(path, numberOfImages, answer);
        initAnimation(true, 400);
    }

    public CharacterAnimation(ImageView imageView, String path, int numberOfImages, String answer, boolean die) {
        if (die) {
            this.imageView = imageView;
            loadImages(path, numberOfImages, answer);

            double second = 400;
            if (answer.equals("D")) {
                second = 3000;
            }
            initAnimation(false, second);
        }
    }

    private void loadImages(String path, int numberOfImages, String answer) {
        for (int i = 0; i < numberOfImages; i++) {
            String imagePath = LinkSetting.MONSTER_ANIMATION.getLink(); // Tên ảnh: "_1.png", "_2.png", ...
            if (answer.equals("A")) {
                imagePath = imagePath + path + "/Bat_" + i + ".png";
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                images.add(image);
            }
            else if (answer.equals("B")) {
                imagePath = imagePath + path + "/sprite_" + i + ".png";
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                images.add(image);
            }
            else if (answer.equals("C")) {
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                images.add(image);
            }
            else if (answer.equals("D")){
                imagePath = imagePath + path + "/troll_" + (i + 1) + ".png";
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                images.add(image);
            }
        }
    }

    private void initAnimation(boolean loop, double second) {
        if (images.isEmpty()) {
            System.out.println("khong co anh monster de animation");
            return;
        }

        animationTimeline = new Timeline(new KeyFrame(Duration.millis(second), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                imageView.setImage(images.get(currentIndex));
                currentIndex++;
                if (currentIndex >= images.size()) {
                    if (loop) {
                        currentIndex = 0;
                    } else {
                        animationTimeline.stop(); // Dừng tại frame cuối nếu không lặp lại
                    }
                }
            }
        }));

        animationTimeline.setCycleCount(loop ? Timeline.INDEFINITE : images.size());
    }

    public void start() {
        if (!isRunning) { // Kiểm tra trạng thái trước khi bắt đầu
            animationTimeline.play();
            isRunning = true; // Cập nhật trạng thái
        }
    }

    public void stop() {
        if (isRunning) { // Kiểm tra trạng thái trước khi dừng
            animationTimeline.stop();
            isRunning = false; // Cập nhật trạng thái
        }
    }

    // Phương thức kiểm tra xem animation có đang chạy hay không
    public boolean isRunning() {
        return isRunning;
    }
}
