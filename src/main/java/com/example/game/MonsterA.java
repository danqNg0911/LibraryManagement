package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MonsterA extends Monster {

    private static final double STOP_DISTANCE = 70; // Khoảng cách tối thiểu để quái vật dừng lại
    private static final double SHOOT_COOLDOWN = 200; // Thời gian cooldown giữa các lần bắn (ms)
    private long lastShotTime; // Thời gian của lần bắn cuối
    private Pane bottomPane; // Pane để chứa viên đạn
    private CharacterAnimation aniA;
    private Timeline bulletTimeline; // Định nghĩa bulletTimeline ở đây
    private Timeline moveTimeline; // Timeline để quản lý di chuyển
    private boolean isShooting = false;


    private static final int INITIAL_HEALTH = NumSetting.MONSTER_A_LIVES.getNum();

    public MonsterA(ImageView monster, Player player, String bulletImagePath, double bulletSpeedX, double bulletSpeedY, Pane bottomPane) {
        super(monster, player, bulletImagePath, bulletSpeedX, bulletSpeedY, bottomPane);
        this.bottomPane = bottomPane; // Khởi tạo bottomPane
        this.lastShotTime = System.currentTimeMillis(); // Khởi tạo thời gian bắn cuối
        this.health = INITIAL_HEALTH;
        this.isDead = false;


            // Khởi tạo Timeline để điều khiển di chuyển
            moveTimeline = new Timeline(new KeyFrame(Duration.seconds(NumSetting.MONSTER_A_BULLET_TIME.getNum()), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (bottomPane.getChildren().contains(monster)) {
                        //Sound.playMonsterSound("A");
                        shootMultiple(); // Bắn sau mỗi 2 giây
                    }
                }
            }));
            moveTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn
            moveTimeline.play(); // Bắt đầu Timeline
    }


    private void shootMultiple() {

        if (monster == null || isPause || isDead) {
            return;
        }

        isShooting = true;
        for (int i = 0; i <= NumSetting.MONSTER_A_NUMBER_OF_BULLET_PER_TURN.getNum(); i++) {

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
        if (monster == null) {
            return; // Avoid accessing monster if it's null
        }

        if (!bulletImagePath.isEmpty()) {
            // Tạo viên đạn tại vị trí hiện tại của quái vật
            Bullet bullet = new Bullet(bulletImagePath, monster.getLayoutX(), monster.getLayoutY(), bulletSpeedX, bulletSpeedY, bottomPane, "A", player);
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
    }

    @Override
    public void movement(ActionEvent event, Player player, double originalX, double originalY) {
        if (isPause) {
            return;
        }

        double playerX = player.getLayoutX();
        double playerY = player.getLayoutY();
        //System.out.println(playerX + "  " + playerY);

        if (monster == null) {
            return;
        }

        // Tính toán khoảng cách giữa quái vật và người chơi
        double distanceX = playerX - monster.getLayoutX();
        double distanceY = playerY - monster.getLayoutY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Kiểm tra cooldown và bắn nhiều viên đạn sau mỗi 2 giây
        if (distance <= STOP_DISTANCE || isShooting) {
            return; // Nếu quái vật đã gần người chơi, dừng lại
        } else if (monster.getLayoutX() + monster.getFitWidth() <= 8) {
            // Xóa quái vật khỏi bottomPane
            bottomPane.getChildren().remove(monster);
            // Giải phóng bộ nhớ cho quái vật
            //monster = null; // Giải phóng tham chiếu
            return; // Kết thúc hàm
        } else {
            // Tiếp tục di chuyển
            if (aniA == null || !aniA.isRunning()) { // Chỉ khởi động hoạt ảnh nếu chưa chạy
                aniA = new CharacterAnimation(monster, "monsters/A/left/02-Fly", 8, "A");
                aniA.start();
            }
            monster.setLayoutX(monster.getLayoutX() - NumSetting.MONSTER_A_MOVE_SPEED.getNum()); // Di chuyển quái vật
        }
    }

    @Override
    protected  void dieAnimation() {
        isDead = true;
        CharacterAnimation die = new CharacterAnimation(monster, "monsters/A/left/05-Die", 8, "A", true);
        die.start();
    }

    @Override
    protected void almostDie() {
        CharacterAnimation die = new CharacterAnimation(monster, "monsters/A/left/04-Hurt/FadeFX", 8, "A");
        die.start();
    }
}
