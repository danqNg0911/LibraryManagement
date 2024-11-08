package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MonsterC extends Monster{

    private static final double STOP_DISTANCE = 70; // Khoảng cách tối thiểu để quái vật dừng lại
    private static final double SHOOT_COOLDOWN = 200; // Thời gian cooldown giữa các lần bắn (ms)
    private long lastShotTime; // Thời gian của lần bắn cuối
    private Pane bottomPane; // Pane để chứa viên đạn
    private CharacterAnimation aniA;
    private Timeline bulletTimeline; // Định nghĩa bulletTimeline ở đây
    private Timeline moveTimeline; // Timeline để quản lý di chuyển
    private boolean isShooting = false;
    private boolean isUp = false;
    private double moveSpeedY = -10; // Tốc độ di chuyển theo trục Y (có thể điều chỉnh)

    private static final int INITIAL_HEALTH = Base.MONSTER_C_LIVES.getInfo();

    public MonsterC(ImageView monster, Player player, String bulletImagePath, double bulletSpeedX, double bulletSpeedY, Pane bottomPane) {
        super(monster, player, bulletImagePath, bulletSpeedX, bulletSpeedY, bottomPane);

        this.bottomPane = bottomPane; // Khởi tạo bottomPane
        this.lastShotTime = System.currentTimeMillis(); // Khởi tạo thời gian bắn cuối
        this.health = INITIAL_HEALTH;
        this.isDead = false;

        // Khởi tạo Timeline để điều khiển di chuyển
        moveTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_C_BULLET_TIME.getInfo()), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                shootMultiple(); // Bắn sau mỗi 2 giây
//                for (int i = 0; i < 3; i++) {
//                    shoot();
//                }
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
        // Hiệu ứng nhấp nháy
        monster.setOpacity(0.5);
    }

    @Override
    protected void almostDie() {
        // Hiệu ứng nhấp nháy
        Timeline blinkEffect = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            private boolean isVisible = true;

            @Override
            public void handle(ActionEvent event) {
                isVisible = !isVisible; // Thay đổi trạng thái hiển thị
                monster.setOpacity(isVisible ? 0.8 : 0.3); // Đặt opacity để nhấp nháy
            }
        }));
        blinkEffect.setCycleCount(Timeline.INDEFINITE); // Số lần nhấp nháy
        return;
    }


    private void shootMultiple() {
        if (isPause) {
            return;
        }

        if (isDead) {
            return;
        }

        isShooting = true;
        for (int i = 0; i <= Base.MONSTER_C_NUMBER_OF_BULLET_PER_TURN.getInfo(); i++) {
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
        // Kiểm tra cooldown
//        if (currentTime - lastShotTime < SHOOT_COOLDOWN) {
//            return null; // Không bắn nếu còn trong thời gian cooldown
//        }

        // Cập nhật thời gian bắn
        lastShotTime = currentTime;

        // Khởi động hoạt ảnh tấn công của quái vật
//        aniA = new CharacterAnimation(monster, "monsters/A/left/03-Attack", 8, "A");
//        aniA.start();

        // Tạo viên đạn tại vị trí hiện tại của quái vật
        Bullet bullet = new Bullet(bulletImagePath, monster.getLayoutX(), monster.getLayoutY(), bulletSpeedX, bulletSpeedY, bottomPane, "C", player);
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

        if (monster == null) {
            return;
        }

        // Lấy chiều cao của Pane và vị trí hiện tại của monster
        double paneHeight = bottomPane.getHeight();
        double monsterY = monster.getLayoutY();
        double monsterX = monster.getLayoutX();
        double playerY = player.getLayoutY();
        double playerX = player.getLayoutX();

        // Kiểm tra nếu player ở ngay trên đầu hoặc dưới chân của monster và có cùng tọa độ X
        double monsterTop = monsterY;
        double monsterBottom = monsterY + monster.getFitHeight();
        double playerBottom = playerY + player.getPlayerImage().getFitHeight();
        double playerTop = playerY;

        // Kiểm tra nếu player cùng tọa độ X hoặc gần cùng tọa độ X (±5 pixels)
        boolean isSameX = Math.abs(playerX - monsterX) <= 15;
        boolean isBlockedAbove = isSameX && (playerBottom >= monsterTop && playerBottom <= monsterBottom);
        boolean isBlockedBelow = isSameX && (playerTop <= monsterBottom && playerTop >= monsterTop);

        // Nếu player ở ngay trên đầu hoặc dưới chân và cùng tọa độ X thì monster không di chuyển
        if (isBlockedAbove || isBlockedBelow) {
            return;
        }

        int move = Base.MONSTER_C_MOVE_SPEED.getInfo();
        // Kiểm tra nếu chạm mép trên hoặc mép dưới của Pane để thay đổi hướng
        if (monsterY <= move) {
            isUp = true;
        }
        if (monsterY + monster.getFitHeight() >= paneHeight - move) {
            isUp = false;
        }

        // Cập nhật hướng di chuyển
        moveSpeedY = isUp ? move : -move;

        // Cập nhật vị trí theo trục Y dựa trên tốc độ di chuyển đã cập nhật
        monster.setLayoutY(monsterY + moveSpeedY);
    }
}
