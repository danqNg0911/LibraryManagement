package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MonsterB extends Monster {

    private static final double STOP_DISTANCE = 70; // Khoảng cách tối thiểu để quái vật dừng lại
    private static final double SHOOT_COOLDOWN = 200; // Thời gian cooldown giữa các lần bắn (ms)
    private long lastShotTime; // Thời gian của lần bắn cuối
    private Pane bottomPane; // Pane để chứa viên đạn
    private CharacterAnimation aniA;
    private Timeline bulletTimeline; // Định nghĩa bulletTimeline ở đây
    private Timeline moveTimeline; // Timeline để quản lý di chuyển
    private boolean isShooting = false;

    private int zigzagCount = 0; // Đếm số bước trong chu kỳ ziczac
    private boolean isMovingRight = false; // Di chuyển từ phải sang trái
    private boolean isMovingDown = true; // Trạng thái di chuyển dọc (xuống)
    private static final int MAX_ZIGZAG_STEPS = 5; // Số bước ziczac

    private static final int INITIAL_HEALTH = NumSetting.MONSTER_B_LIVES.getNum();


    public MonsterB(ImageView monster, Player player, String bulletImagePath, double bulletSpeedX, double bulletSpeedY, Pane bottomPane) {
        super(monster, player, bulletImagePath, bulletSpeedX, bulletSpeedY, bottomPane);
        if (monster == null) {
            System.out.println("Monster B cannot be null");
        }

        this.bottomPane = bottomPane; // Khởi tạo bottomPane
        this.lastShotTime = System.currentTimeMillis(); // Khởi tạo thời gian bắn cuối
        this.health = INITIAL_HEALTH;
        this.isDead = false;

        // Khởi tạo Timeline để điều khiển di chuyển
        moveTimeline = new Timeline(new KeyFrame(Duration.seconds(NumSetting.MONSTER_B_BULLET_TIME.getNum()), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shootMultiple(); // Bắn sau mỗi ... giây
            }
        }));
        moveTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn
        moveTimeline.play(); // Bắt đầu Timeline
    }


    private void shootMultiple() {
        if (isPause) {
            return;
        }

        if (isDead) {
            return;
        }

        isShooting = true;
        for (int i = 0; i <= NumSetting.MONSTER_B_NUMBER_OF_BULLET_PER_TURN.getNum(); i++) {
            // Tạo một Timeline cho từng viên đạn
            Timeline shootTimeline = new Timeline(new KeyFrame(Duration.millis(i * SHOOT_COOLDOWN), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    shoot(); // Bắn viên đạn
                }
            }));
            shootTimeline.setCycleCount(1); // Chỉ bắn một lần
            shootTimeline.play();
        }
        isShooting = false;
    }

    @Override
    protected void shoot() {
        long currentTime = System.currentTimeMillis();
        lastShotTime = currentTime;

        // Tạo viên đạn tại vị trí hiện tại của quái vật
        Bullet bullet = new Bullet(bulletImagePath, monster.getLayoutX(), monster.getLayoutY(), bulletSpeedX, bulletSpeedY, bottomPane, "B", player);
        bullet.moveBullet(monster);

        // Thêm viên đạn vào Pane
        bottomPane.getChildren().add(bullet.getBulletImage());

        // Tạo Timeline để cập nhật vị trí của viên đạn
        bulletTimeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bullet.updatePosition(); // Cập nhật vị trí viên đạn

                // Kiểm tra xem viên đạn có vượt ra ngoài màn hình không
                if (bullet.isOutOfBounds()) {
                    bottomPane.getChildren().remove(bullet.getBulletImage()); // Xóa viên đạn khỏi Pane
                    bulletTimeline.stop(); // Dừng Timeline
                }
            }
        }));

        bulletTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn
        bulletTimeline.play(); // Bắt đầu di chuyển viên đạn

    }

    @Override
    public void movement(ActionEvent event, Player player, double originalX, double originalY) {
        if (isPause) {
            return;
        }


        double playerX = player.getLayoutX();
        double playerY = player.getLayoutY();

        if (monster == null) {
            return;
        }

        double distanceX = playerX - monster.getLayoutX();
        double distanceY = playerY - monster.getLayoutY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Kiểm tra cooldown và bắn nhiều viên đạn sau mỗi 2 giây
        if (distance <= STOP_DISTANCE || isShooting) {
            return; // Nếu quái vật đã gần người chơi, dừng lại
        }

        int move = NumSetting.MONSTER_B_MOVE_SPEED.getNum();

        // Kiểm tra hướng di chuyển ngang
        if (isMovingRight) {
            monster.setLayoutX(monster.getLayoutX() + move); // Di chuyển sang phải
            animation();
        } else {
            monster.setLayoutX(monster.getLayoutX() - move); // Di chuyển sang trái
            animation();
        }

        // Kiểm tra hướng di chuyển dọc
        if (isMovingDown) {
            monster.setLayoutY(monster.getLayoutY() + move); // Di chuyển xuống
            animation();
        } else {
            monster.setLayoutY(monster.getLayoutY() - move); // Di chuyển lên
            animation();
        }

        // Tăng biến đếm ziczac
        zigzagCount++;

        // Sau mỗi 5 bước, đổi hướng dọc và đặt lại biến đếm
        if (zigzagCount >= MAX_ZIGZAG_STEPS) {
            isMovingDown = !isMovingDown; // Đổi hướng lên/xuống
            zigzagCount = 0; // Đặt lại biến đếm
        }

        else if (monster.getLayoutX() + monster.getFitWidth() <= 8) {
            // Xóa quái vật khỏi bottomPane
            bottomPane.getChildren().remove(monster);
            // Giải phóng bộ nhớ cho quái vật
            //monster = null; // Giải phóng tham chiếu
            return; // Kết thúc hàm
        }
    }

//    @Override
//    protected void die() {
//        dieAnimation();
//        bottomPane.getChildren().remove(monster);
//    }

    @Override
    protected  void dieAnimation() {
        isDead = true;
        // Xoay quái vật nằm ngang
        monster.setRotate(90); // Đặt góc xoay 90 độ để quái vật nằm ngang

        // Hiệu ứng nhấp nháy
        monster.setOpacity(0.5);
        CharacterAnimation die = new CharacterAnimation(monster, "monsters/B/left/round ghost dead", 5, "B", true);
        die.start();
    }

    @Override
    protected void almostDie() {
        CharacterAnimation die = new CharacterAnimation(monster, "monsters/B/left/round ghost taking damage", 4, "B");
        die.start();
    }

    private void animation() {
        if (aniA == null || !aniA.isRunning()) { // Chỉ khởi động hoạt ảnh nếu chưa chạy
            aniA = new CharacterAnimation(monster, "monsters/B/left/round ghost walk", 6, "B");
            aniA.start();
        }
    }
}
