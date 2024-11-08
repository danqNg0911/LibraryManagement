package com.example.game;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

abstract class Monster extends ImageView{
    protected ImageView monster;
    protected Player player;
    protected String bulletImagePath;  // Đường dẫn đến ảnh đạn của monster này
    protected double bulletSpeedX;
    protected double bulletSpeedY;
    private Pane bottomPane;
    protected int health;
    protected boolean isDead;
    protected int numbers;
    protected boolean isPause;


    public Monster() {

    }

    public Monster(ImageView monster, Player player, String bulletImagePath, double bulletSpeedX, double bulletSpeedY, Pane bottomPane) {
        this.monster = monster;
        this.player = player;
        this.bulletImagePath = bulletImagePath;
        this.bulletSpeedX = bulletSpeedX;
        this.bulletSpeedY = bulletSpeedY;
        this.bottomPane = bottomPane;
        this.isPause = false;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsPause(boolean isPause) {
        this.isPause = isPause;
    }

    protected abstract void shoot();


    public ImageView getMonsterImageView() {
        return this.monster;
    }

    protected abstract void movement(ActionEvent event, Player player, double originalX, double originalY);

    public void reduceHealth() {
        if (isPause) {
            return;
        }

        health--; // Giảm 1 máu khi bị đánh
        if (health <= 2 ) {
            almostDie();
        }

        if (health < 0) {
            die();
            //bottomPane.getChildren().remove(monster); // Xóa quái vật khỏi giao diện
        }
    }

    protected void die() {
        if (isPause) {
            return;
        }

        dieAnimation();
        // Tạo hiệu ứng di chuyển lên cao
        TranslateTransition riseUp = new TranslateTransition(Duration.seconds(0.5), monster);
        riseUp.setByY(-50); // Di chuyển lên cao 50px

        // Tạo hiệu ứng rơi xuống
        TranslateTransition fallDown = new TranslateTransition(Duration.seconds(1), monster);
        fallDown.setByY(600); // Di chuyển xuống phía dưới (giá trị tùy chỉnh theo chiều cao của Pane)

        // Kết hợp hai hiệu ứng
        SequentialTransition dieAnimation = new SequentialTransition(riseUp, fallDown);

        // Tạo EventHandler riêng để loại bỏ quái vật khi hiệu ứng hoàn tất
        EventHandler<ActionEvent> removeMonsterHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bottomPane.getChildren().remove(monster);
            }
        };

        // Đặt EventHandler cho sự kiện kết thúc
        dieAnimation.setOnFinished(removeMonsterHandler);

        // Bắt đầu hiệu ứng
        dieAnimation.play();
    }

    // Phương thức xóa quái vật khỏi bottomPane
    public void removeMonster() {
        bottomPane.getChildren().remove(monster);  // Loại bỏ quái vật khỏi giao diện
        monster = null;
    }

    protected abstract void dieAnimation();
    protected abstract void almostDie();
}
