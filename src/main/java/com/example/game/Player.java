package com.example.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.geometry.Bounds;

public class Player {
    private static int health = NumSetting.PLAYER_LIVES.getNum();
    private ImageView player; // ImageView để hiển thị người chơi
    private Pane bottomPane;
    private boolean isPause;

    public Player(Pane bottomPane, ImageView player) {

        this.player = player;
        this.bottomPane = bottomPane;
        this.isPause = false;

        show();
    }

    public void show() {
        try {
            player.setImage(new Image(getClass().getResource(LinkSetting.PLAYER_IMAGE_RIGHT.getLink()).toExternalForm()));
        } catch (Exception e) {
            System.out.println("loi show(). Line = 29");
        }
        player.setFitWidth(NumSetting.PLAYER_WIDTH.getNum());
        player.setFitHeight(NumSetting.PLAYER_HEIGHT.getNum());
        player.setLayoutX(100);
        player.setLayoutY(100);

        if (!bottomPane.getChildren().contains(player)) {
            bottomPane.getChildren().add(player);
            player.toFront();
        }

    }
    // Phương thức để giảm máu
    public void decreaseHealth(int amount) {
        if (isPause) {
            return;
        }
        System.out.println("-" + amount);

        if (health <= 0) {
            // Xử lý khi người chơi hết máu, ví dụ: kết thúc trò chơi hoặc thông báo thua cuộc
            //this.bottomPane.getChildren().remove(player);
            health = 0;
            Sound.playPlayerDeathSound();
            System.out.println("Player is defeated!");
        }
        else {
            if (health - amount < 0) {
                health = 0;
            }
            else {
                health -= amount;
            }
        }
    }

    public void setHealth(int rhealth) {
        health = rhealth;
    }
    public int getHealth() {
        return health;
    }

    public void setIsPause(boolean isPause) {
        this.isPause = isPause;
    }

    public ImageView getPlayerImage() {
        return player;
    }

    public double getLayoutX() {
        return getPlayerImage().getLayoutX();
    }

    public double getLayoutY() {
        return getPlayerImage().getLayoutY();
    }

    public Bounds getBoundsInLocal() {
        return player.getBoundsInLocal();
    }
}
