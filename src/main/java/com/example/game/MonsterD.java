package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MonsterD extends Monster {

    private static final double STOP_DISTANCE = 70; // Khoảng cách tối thiểu để quái vật dừng lại
    private static final double SHOOT_COOLDOWN = 200; // Thời gian cooldown giữa các lần bắn (ms)
    private long lastShotTime; // Thời gian của lần bắn cuối
    private Pane bottomPane; // Pane để chứa viên đạn
    private CharacterAnimation aniA;
    private Timeline bulletTimeline; // Định nghĩa bulletTimeline ở đây
    private Timeline moveTimeline; // Timeline để quản lý di chuyển
    private Timeline attackTimeline; // Timeline cho việc tấn công tạm thời
    private boolean isShooting = false;
    private boolean isTemporarilyAttacking = false; // Đang tấn công tạm thời


    private static final int INITIAL_HEALTH = Base.MONSTER_D_LIVES.getInfo();

    public MonsterD(ImageView monster, Player player, String bulletImagePath, double bulletSpeedX, double bulletSpeedY, Pane bottomPane) {
        super(monster, player, bulletImagePath, bulletSpeedX, bulletSpeedY, bottomPane);
        this.bottomPane = bottomPane; // Khởi tạo bottomPane
        this.lastShotTime = System.currentTimeMillis(); // Khởi tạo thời gian bắn cuối
        this.health = INITIAL_HEALTH;
        this.isDead = false;

        // Khởi tạo Timeline để điều khiển hành vi di chuyển tạm thời và trở lại
        moveTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_D_TELEPORT_TIME.getInfo()), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startTemporaryAttack(); // Bắt đầu tấn công tạm thời mỗi ... giây

            }
        }));
        moveTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn
        moveTimeline.play(); // Bắt đầu Timeline
    }

    @Override
    protected void dieAnimation() {
        isDead = true;
        // Xoay quái vật nằm ngang
        monster.setRotate(90); // Đặt góc xoay 90 độ để quái vật nằm ngang

        // Hiệu ứng nhấp nháy và mờ dần
        Timeline dieEffect = new Timeline(
                new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                    private boolean isVisible = true;

                    @Override
                    public void handle(ActionEvent event) {
                        isVisible = !isVisible;
                        monster.setOpacity(isVisible ? 1.0 : 0.5); // Nhấp nháy dần
                    }
                }),
                new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        monster.setOpacity(0); // Dần mờ sau khi nhấp nháy
                    }
                })
        );

        dieEffect.setCycleCount(5); // Số lần nhấp nháy trước khi biến mất

        // Sử dụng EventHandler thay vì lambda cho setOnFinished
        dieEffect.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bottomPane.getChildren().remove(monster); // Xóa khỏi giao diện sau khi hoàn tất
            }
        });

        dieEffect.play();
    }


    @Override
    protected void almostDie() {

        // Hiệu ứng nhấp nháy
        Timeline blinkEffect = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            private boolean isVisible = true;

            @Override
            public void handle(ActionEvent event) {
                isVisible = !isVisible; // Thay đổi trạng thái hiển thị
                monster.setOpacity(isVisible ? 1.0 : 0.5); // Đặt opacity để nhấp nháy
            }
        }));
        blinkEffect.setCycleCount(Timeline.INDEFINITE); // Số lần nhấp nháy
        return;
    }

    private void startTemporaryAttack() {

        if (monster == null || isPause || isDead) {
            return;
        }

        if (isTemporarilyAttacking) return; // Tránh kích hoạt lại nếu đang tấn công tạm thời

        isTemporarilyAttacking = true;

        // Lưu vị trí ban đầu của quái vật để trở lại sau
        final double originalX = monster.getLayoutX();
        final double originalY = monster.getLayoutY();

        // Di chuyển quái vật tới gần người chơi (tức thời)
        double playerX = player.getLayoutX();
        double targetX = playerX + player.getPlayerImage().getFitWidth() + 5; // Vị trí bên phải người chơi
        monster.setLayoutX(targetX);
        monster.setLayoutY(player.getLayoutY()); // Căn chỉnh trục Y với người chơif

        // Khởi động lại trạng thái tấn công
        Timeline attackTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_D_NUMBER_OF_HIT_PER_TURN.getInfo()), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (monster == null) {
                    return;
                }

                if (monster.getLayoutY() == player.getLayoutY()) {
                    player.decreaseHealth(Base.MONSTER_D_DAMAGE.getInfo());
                }
                monster.setLayoutX(originalX); // Quay lại vị trí ban đầu
                monster.setLayoutY(originalY);
                monster.setImage(new Image("/com/example/game/assets/monsters/D/left/troll_1.png"));
                isTemporarilyAttacking = false; // Kết thúc trạng thái tấn công
            }
        }));
        attackTimeline.setCycleCount(1); // Chỉ chạy một lần
        attackTimeline.play(); // Bắt đầu Timeline

        animation();

    }

    private void shootMultiple() {
//        if (isShooting) return; // Tránh bắn nhiều lần đồng thời
//        isShooting = true;
//
//        for (int i = 0; i < 4; i++) {
//            // Tạo một Timeline cho từng viên đạn
//            Timeline shootTimeline = new Timeline(new KeyFrame(Duration.millis(i * SHOOT_COOLDOWN), new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    shoot(); // Bắn viên đạn
//                }
//            }));
//            shootTimeline.setCycleCount(1); // Chỉ bắn một lần
//            shootTimeline.play();
//        }
//
//        isShooting = false;
    }

    @Override
    protected void shoot() {
//        long currentTime = System.currentTimeMillis();
//        if (currentTime - lastShotTime < SHOOT_COOLDOWN) {
//            return; // Kiểm tra cooldown trước khi bắn
//        }
//
//        lastShotTime = currentTime;
//
//        if (bulletImagePath.isEmpty()) {
//            // Tạo viên đạn tại vị trí hiện tại của quái vật
//            Bullet bullet = new Bullet(bulletImagePath, monster.getLayoutX(), monster.getLayoutY(), bulletSpeedX, bulletSpeedY, bottomPane, "D", player);
//            bullet.moveBullet(monster);
//
//            // Thêm viên đạn vào Pane
//            bottomPane.getChildren().add(bullet.getBulletImage());
//
//            // Tạo Timeline để cập nhật vị trí của viên đạn
//            bulletTimeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    bullet.updatePosition(); // Cập nhật vị trí viên đạn
//
//                    // Kiểm tra xem viên đạn có vượt ra ngoài màn hình không
//                    if (bullet.isOutOfBounds()) {
//                        bottomPane.getChildren().remove(bullet.getBulletImage()); // Xóa viên đạn khỏi Pane
//                        bulletTimeline.stop(); // Dừng Timeline
//                    }
//                }
//            }));
//
//            bulletTimeline.setCycleCount(Timeline.INDEFINITE); // Lặp lại vô hạn
//            bulletTimeline.play(); // Bắt đầu di chuyển viên đạn
//
//        }
    }

    @Override
    public void movement(ActionEvent event, Player player, double originalX, double originalY) {
        if (monster == null) {
            return; // Nếu quái vật không tồn tại
        }

        if (isPause) {
            return;
        }

        double playerX = player.getLayoutX();
        double playerY = player.getLayoutY();

        double distanceX = playerX - monster.getLayoutX();
        double distanceY = playerY - monster.getLayoutY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Kiểm tra cooldown và bắn nhiều viên đạn sau mỗi 2 giây
        if (distance <= STOP_DISTANCE || isShooting) {
            return; // Nếu quái vật đã gần người chơi, dừng lại
        }

        if (isTemporarilyAttacking) {
            // Khi đang tấn công, không di chuyển bình thường
            animation();
            return;
        }

        if (monster.getLayoutX() + monster.getFitWidth() <= 8) {
            // Xóa quái vật khỏi bottomPane
            bottomPane.getChildren().remove(monster);
            return; // Kết thúc hàm
        } else {
            // Di chuyển quái vật từ phải sang trái
            monster.setLayoutX(monster.getLayoutX() - Base.MONSTER_D_MOVE_SPEED.getInfo()); // Di chuyển quái vật
            if (aniA != null && aniA.isRunning()) { // Chỉ khởi động hoạt ảnh nếu chưa chạy
                aniA.stop();
            }
            //animation(); // Khởi động hoạt ảnh di chuyển
        }
    }

    private void animation() {
        if (aniA == null || !aniA.isRunning()) { // Chỉ khởi động hoạt ảnh nếu chưa chạy
            aniA = new CharacterAnimation(monster, "monsters/D/left/", 4, "D");
            aniA.start();
        }
    }
}
