package com.example.game;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.event.EventHandler;

public class Bullet {
    private ImageView bulletImage;
    private double speedX;
    private double speedY;
    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 400;
    private Timeline timeline; // Thêm biến để lưu Timeline
    private Pane bottomPane; // Pane để chứa viên đạn
    private double damage;
    private Player player;
    private boolean isPause;
    private String answer;

    public Bullet(String imagePath, double startX, double startY, double speedX, double speedY, Pane bottomPane, String answer,Player player) {
        bulletImage = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        this.isPause = false;
        this.answer = answer;

        double bulletWidth = 0;
        double bulletHeight = 0;
        if (this.answer.equals("A")) {
            bulletWidth = NumSetting.MONSTER_A_BULLET_WIDTH.getNum();
            bulletHeight = NumSetting.MONSTER_A_BULLET_HEIGHT.getNum();
            damage = NumSetting.MONSTER_A_DAMAGE.getNum();
        }
        else if (this.answer.equals("B")) {
            bulletWidth = NumSetting.MONSTER_B_BULLET_WIDTH.getNum();
            bulletHeight = NumSetting.MONSTER_B_BULLET_HEIGHT.getNum();
            damage = NumSetting.MONSTER_B_DAMAGE.getNum();
        }
        else if (this.answer.equals("C")) {
            bulletWidth = NumSetting.MONSTER_C_BULLET_WIDTH.getNum();
            bulletHeight = NumSetting.MONSTER_C_BULLET_HEIGHT.getNum();
            damage = NumSetting.MONSTER_C_DAMAGE.getNum();
        }
        else if (this.answer.equals("D")) {
            bulletWidth = 0;
            bulletHeight = 0;
            damage = 0;
        }
        bulletImage.setFitWidth(bulletWidth);
        bulletImage.setFitHeight(bulletHeight);
        bulletImage.setLayoutX(startX);
        bulletImage.setLayoutY(startY);
        this.bottomPane = bottomPane;
        this.speedX = speedX;
        this.speedY = speedY;
        this.player = player;
    }

    public boolean isCollidingWith() {
        // Lấy vị trí của viên đạn và người chơi
        double bulletX = bulletImage.getLayoutX();
        double bulletY = bulletImage.getLayoutY();
        double playerX = player.getLayoutX();
        double playerY = player.getLayoutY();

        // Giả sử kích thước của viên đạn và người chơi là 50x50
        double bulletWidth = bulletImage.getBoundsInLocal().getWidth();
        double bulletHeight = bulletImage.getBoundsInLocal().getHeight();
        double playerWidth = player.getBoundsInLocal().getWidth() - 10;
        double playerHeight = player.getBoundsInLocal().getHeight() - 10;

        // Kiểm tra va chạm
        if (bulletX < playerX + playerWidth && bulletX + bulletWidth > playerX && bulletY < playerY + playerHeight && bulletY + bulletHeight > playerY) {
            return true;
        }

        return false;
    }

    public void moveBullet(ImageView monster) {
        if (isPause) {
            return;
        }

        if (player == null) {
            System.out.println("Player is null, cannot move bullet.");
            return;
        }

        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isPause || monster == null) {
                    removeBullet();
                    return;
                }
                // Cập nhật vị trí viên đạn theo vận tốc
                updatePosition();

                // Kiểm tra va chạm với người chơi
                if (isCollidingWith()) {
                    if (bulletImage != null) { // Kiểm tra xem bulletImage có null không trước khi gọi setVisible
                        bulletImage.setVisible(false); // Ẩn viên đạn
                        Sound.playIsHitSound();
                    }
                    timeline.stop(); // Dừng hoạt ảnh
                    player.decreaseHealth((int) damage); // Giảm máu người chơi
                    removeBullet(); // Loại bỏ viên đạn khỏi Pane sau khi va chạm
                }

                // Kiểm tra xem viên đạn có ra ngoài màn hình không
                if (isOutOfBounds()) {
                    if (bulletImage != null) { // Kiểm tra bulletImage trước khi gọi setVisible
                        bulletImage.setVisible(false); // Ẩn viên đạn khi ra ngoài
                        bottomPane.getChildren().remove(bulletImage);
                    }
                    timeline.stop(); // Dừng hoạt ảnh
                }
            }
        });

        // Tạo Timeline với KeyFrame để chạy liên tục
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Thiết lập lặp vô hạn
        timeline.play(); // Bắt đầu di chuyển viên đạn
    }

    public void removeBullet() {
        if (bulletImage != null) {
            bottomPane.getChildren().remove(bulletImage);
            //bulletImage = null;
        }
    }

    // Phương thức kiểm tra xem viên đạn có vượt ra ngoài màn hình không
    public boolean isOutOfBounds() {
        if (bulletImage == null) { // Kiểm tra xem bulletImage có null không
            return true; // Nếu đã là null, coi như nó đã ra ngoài màn hình
        }

        double x = bulletImage.getLayoutX(); // Lấy vị trí X của viên đạn
        double y = bulletImage.getLayoutY(); // Lấy vị trí Y của viên đạn
        // Kiểm tra nếu viên đạn ra ngoài các cạnh của màn hình
        return x < 0 || x > SCREEN_WIDTH || y < 0 || y > SCREEN_HEIGHT;
    }

    public ImageView getBulletImage() {
        return bulletImage;
    }

    // Phương thức cập nhật vị trí viên đạn (có thể sử dụng cho mục đích khác)
    public void updatePosition() {
        if (bulletImage != null) { // Kiểm tra xem bulletImage có null không trước khi cập nhật vị trí
            bulletImage.setLayoutX(bulletImage.getLayoutX() + speedX);
            bulletImage.setLayoutY(bulletImage.getLayoutY() + speedY);
        }
    }

    public void setIsPause(boolean isPause) {
        this.isPause = isPause;
    }
}
