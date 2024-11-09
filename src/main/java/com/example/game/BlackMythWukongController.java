package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.*;

public class BlackMythWukongController {

    public Label player_lives;
    public Label bat_num;
    public Label sprite_num;
    public Label robot_num;
    public Label troll_num;
    public Pane pauseOverlay;
    public Button resumeButton;
    public Button restartButton;
    public Button menuButton;
    public Pane gameOverOverlay;
    public Pane gameWinARoundOverlay;
    public Pane gameWinAllRoundOverlay;
    public Label answerLabel_A;
    public Label answerLabel_B;
    public Label answerLabel_C;
    public Label answerLabel_D;
    public Label questionLabel;
    public Label timeLabel;
    private Image playerLeftImage;
    private Image playerRightImage;
    private ImageView leftHit;
    private ImageView rightHit;
    private boolean isRight;

    private static final String QUESTION_FILE_PATH = "D:\\LibraryManagement\\LibraryManagement\\data\\QuestionInGame.txt";

    private Timeline gameTimer; // Biến để lưu timeline của bộ đếm
    private int gameTime = Base.TIME.getInfo();

    private boolean isPaused = false;
    private boolean isLose = false;
    private boolean isWin = false;

    private List<Question> questions;
    private Question currentQuestion;
    private String correctAnswerText; // Biến lưu đáp án đúng của câu hỏi hiện tại


    @FXML
    private Pane bottomPane;

    @FXML
    private ImageView playerView; // Hình ảnh của Wukong

    private Player player;

    @FXML
    private ImageView monsterA, monsterB, monsterC, monsterD; // Các quái vật

    @FXML
    private ImageView monsA, monsB, monsC, monsD;

    private final ImageView[] monsters;
    private final ImageView[] monsterInAnswers;
    private List<Monster> monsterList = new ArrayList<>();

    private Background bg;

    private final Random random = new Random();

    private static int playerLives = Base.PLAYER_LIVES.getInfo();

    private int aCnt = 0;
    private int bCnt = 0;
    private int cCnt = 0;
    private int dCnt = 0;

    private int aNums = 0;
    private int bNums = 0;
    private int cNums = 0;
    private int dNums = 0;


    public BlackMythWukongController() throws Exception {
        // Mảng chứa các quái vật
        monsters = new ImageView[4]; // Khởi tạo mảng với kích thước 4
        monsterInAnswers = new ImageView[4];
    }

    @FXML
    public void initialize() throws Exception {
        questions = Question.loadQuestionsFromFile(QUESTION_FILE_PATH); // Đường dẫn file
        loadNextQuestion();
        
        bg = new Background();
        bg.start(bottomPane);

        startGameTimer();
        Sound.playBackgroundMusic();

        // Gán các quái vật vào mảng
        monsters[0] = monsterA;
        monsters[1] = monsterB;
        monsters[2] = monsterC;
        monsters[3] = monsterD;

        monsterInAnswers[0] = monsA;
        monsterInAnswers[1] = monsB;
        monsterInAnswers[2] = monsC;
        monsterInAnswers[3] = monsD;

        isPaused = false; // Đảm bảo game bắt đầu ở trạng thái không pause
        isLose = false;
        isWin = false;

        pauseOverlay.setVisible(false);
        gameOverOverlay.setVisible(false);
        gameWinARoundOverlay.setVisible(false);


        aCnt = Base.MONSTER_A_SPAWN_NUMBER.getInfo();
        bCnt = Base.MONSTER_B_SPAWN_NUMBER.getInfo();
        cCnt = Base.MONSTER_C_SPAWN_NUMBER.getInfo();
        dCnt = Base.MONSTER_D_SPAWN_NUMBER.getInfo();

        aNums = Base.MONSTER_A_SPAWN_NUMBER.getInfo();
        bNums = Base.MONSTER_B_SPAWN_NUMBER.getInfo();
        cNums = Base.MONSTER_C_SPAWN_NUMBER.getInfo();
        dNums = Base.MONSTER_D_SPAWN_NUMBER.getInfo();

        player = new Player(bottomPane, playerView);


        playerLeftImage = getImagePath("/com/example/game/assets/wukong/left/wukong_left.png");
        playerRightImage = getImagePath("/com/example/game/assets/wukong/right/wukong_right.png");

        // Gán sự kiện khi nhấn phím để điều khiển Wukong và tạm dừng
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (playerView.getScene() != null) {
                    playerView.getScene().getRoot().requestFocus();
                    playerView.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (!isWin && !isLose && event.getCode() == KeyCode.P) { // Phím P để tạm dừng
                                togglePause();
                                event.consume(); // Ngừng sự kiện không lan truyền ra ngoài
                            }
                            if (event.getCode() == KeyCode.R) { // Phím R để restart game
                                restartGame(); // Gọi phương thức restartGame()
                                event.consume(); // Ngừng sự kiện không lan truyền ra ngoài
                            }
                            if (!isPaused && !isLose) {
                                handleKeyPress(event);
                            }
                        }
                    });
                }
            }
        });

        isRight = true;
        // Khởi động di chuyển ngẫu nhiên cho các quái vật
        monsterAnswers();

        // Gọi monsterMovements() để bắt đầu di chuyển Monster
        //monsterMovements();

        // Khởi tạo các Timeline riêng cho từng loại quái vật
        Timeline spawnATimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_A_SPAWN_TIME.getInfo()), new SpawnMonsterAHandler()));
        Timeline spawnBTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_B_SPAWN_TIME.getInfo()), new SpawnMonsterBHandler()));
        Timeline spawnCTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_C_SPAWN_TIME.getInfo()), new SpawnMonsterCHandler()));
        Timeline spawnDTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_D_SPAWN_TIME.getInfo()), new SpawnMonsterDHandler()));

        // Thiết lập chu kỳ vô hạn cho mỗi Timeline
        spawnATimeline.setCycleCount(Timeline.INDEFINITE);
        spawnBTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnCTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnDTimeline.setCycleCount(Timeline.INDEFINITE);

        // Bắt đầu tất cả Timeline
        spawnATimeline.play();
        spawnBTimeline.play();
        spawnCTimeline.play();
        spawnDTimeline.play();

        // Khởi tạo Timeline để cập nhật số lượng lives và số lượng quái vật
        Timeline updateLabelsTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player_lives.setText("Lives: " + player.getHealth());
                bat_num.setText("Bat: " + aNums);
                sprite_num.setText("Sprite: " + bNums);
                robot_num.setText("Robot: " + cNums);
                troll_num.setText("Troll: " + dNums);
                if (!isLose && !isWin && (player.getHealth() == 0 || conditionToLose())) {
                    isLose = true;
                    setGameOverOverlay();
                }

                else if (!isLose && !isWin && player.getHealth() > 0 && conditionToWin()) {
                    isWin = true;
                    setGameWinARoundOverlay();
                }
            }
        }));

        updateLabelsTimeline.setCycleCount(Timeline.INDEFINITE);
        updateLabelsTimeline.play();

    }

    private void startGameTimer() {
        // Tạo một KeyFrame với thời gian 1 giây và xử lý sự kiện bằng một lớp EventHandler riêng
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new GameTimerHandler());

        gameTimer = new Timeline(keyFrame);
        gameTimer.setCycleCount(Timeline.INDEFINITE); // Chạy đến khi hết thời gian
        gameTimer.play(); // Bắt đầu bộ đếm
    }

    // Lớp EventHandler để xử lý sự kiện cho bộ đếm thời gian
    private class GameTimerHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (isPaused || isLose || isWin) {
                return;
            }

            gameTime--; // Giảm thời gian mỗi giây
            timeLabel.setText("Time left: " + gameTime + "s"); // Cập nhật label thời gian

            // Kiểm tra nếu hết thời gian
            if (gameTime <= 0) {
                //gameTimer.stop(); // Dừng bộ đếm
                isLose = true;
                isWin = false;
                setGameOverOverlay();
            }
        }
    }



    private void loadNextQuestion() {
        if (!questions.isEmpty()) {
            // Lấy ngẫu nhiên câu hỏi và xóa khỏi danh sách
            currentQuestion = Question.getRandomQuestion(questions);
            questions.remove(currentQuestion);

            questionLabel.setText(currentQuestion.getQuestion());

            // Lấy các đáp án và xáo trộn thứ tự
            List<String> answers = new ArrayList<>(List.of(currentQuestion.getAnswers()));
            //Collections.shuffle(answers);

            answerLabel_A.setText(answers.get(0));
            answerLabel_B.setText(answers.get(1));
            answerLabel_C.setText(answers.get(2));
            answerLabel_D.setText(answers.get(3));

            correctAnswerText = currentQuestion.getCorrectAnswer();

        }
    }

    public boolean conditionToLose() {
        if (gameTime <= 0) {
            return true;
        }
        if (aNums == 0 && !correctAnswerText.equals("A")) {
            return true;
        }
        if (bNums == 0 && !correctAnswerText.equals("B")) {
            return true;
        }
        if (cNums == 0 && !correctAnswerText.equals("C")) {
            return true;
        }
        if (dNums == 0 && !correctAnswerText.equals("D")) {
            return true;
        }
        return false;
    }

    public boolean conditionToWin() {
        if (aNums == 0 && correctAnswerText.equals("A")) {
            return true;
        }
        if (bNums == 0 && correctAnswerText.equals("B")) {
            return true;
        }
        if (cNums == 0 && correctAnswerText.equals("C")) {
            return true;
        }
        if (dNums == 0 && correctAnswerText.equals("D")) {
            return true;
        }
        return false;
    }

    // Phương thức khi bấm nút Resume
    private void resumeGame(ActionEvent event) {
        isPaused = false;
        pauseOverlay.setVisible(false); // Ẩn cửa sổ Pause
        // Tiếp tục game ở đây
    }

    public void restartGame() {

        // Kiểm tra nếu game chưa tạm dừng thì không cho phép restart
        if (!isPaused && !isLose && !isWin) {
            // Hiển thị thông báo hoặc làm gì đó nếu người chơi chưa tạm dừng game
            System.out.println("Vui lòng tạm dừng game trước khi restart!");
            return; // Không thực hiện restart nếu game chưa tạm dừng
        }

        Sound.restartBackgroundMusic();

        questions = Question.loadQuestionsFromFile(QUESTION_FILE_PATH); // Đường dẫn file
        loadNextQuestion();

        // Khôi phục lại trạng thái pause, ẩn overlay pause
        isPaused = false;
        isLose = false;
        isWin = false;
        pauseOverlay.setVisible(false);
        gameOverOverlay.setVisible(false);
        gameWinARoundOverlay.setVisible(false);
        gameTime = Base.TIME.getInfo();

        // Reset các quái vật
        for (int i = 0; i < monsterList.size(); i++) {
            //System.out.println(monsterList.get(i).getLayoutX());
            monsterList.get(i).removeMonster();
        }

        monsterList.clear(); // Xóa tất cả các quái vật hiện tại trong danh sách

        // Khôi phục số lượng mạng sống của người chơi
        player.setHealth(Base.PLAYER_LIVES.getInfo()); // Giả sử phương thức setHealth được định nghĩa trong lớp Player
        // Khôi phục số lượng quái vật
        aCnt = Base.MONSTER_A_SPAWN_NUMBER.getInfo();
        bCnt = Base.MONSTER_B_SPAWN_NUMBER.getInfo();
        cCnt = Base.MONSTER_C_SPAWN_NUMBER.getInfo();
        dCnt = Base.MONSTER_D_SPAWN_NUMBER.getInfo();

        aNums = Base.MONSTER_A_SPAWN_NUMBER.getInfo();
        bNums = Base.MONSTER_B_SPAWN_NUMBER.getInfo();
        cNums = Base.MONSTER_C_SPAWN_NUMBER.getInfo();
        dNums = Base.MONSTER_D_SPAWN_NUMBER.getInfo();

        // Khởi tạo lại các quái vật, người chơi, vv.
        playerView.setLayoutX(100); // Vị trí ban đầu của người chơi
        playerView.setLayoutY(200);

        // Khởi tạo lại các quái vật mới
        monsterAnswers(); // Hàm này sẽ spawn lại các quái vật

        // Khởi tạo các Timeline riêng cho từng loại quái vật
        Timeline spawnATimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_A_SPAWN_TIME.getInfo()), new SpawnMonsterAHandler()));
        Timeline spawnBTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_B_SPAWN_TIME.getInfo()), new SpawnMonsterBHandler()));
        Timeline spawnCTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_C_SPAWN_TIME.getInfo()), new SpawnMonsterCHandler()));
        Timeline spawnDTimeline = new Timeline(new KeyFrame(Duration.seconds(Base.MONSTER_D_SPAWN_TIME.getInfo()), new SpawnMonsterDHandler()));

        // Thiết lập chu kỳ vô hạn cho mỗi Timeline
        spawnATimeline.setCycleCount(Timeline.INDEFINITE);
        spawnBTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnCTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnDTimeline.setCycleCount(Timeline.INDEFINITE);

        // Bắt đầu tất cả Timeline
        spawnATimeline.play();
        spawnBTimeline.play();
        spawnCTimeline.play();
        spawnDTimeline.play();

        // Tạo một PauseTransition chỉ để chờ đợi để người chơi tiếp tục
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(Base.PLAYER_SET_PAUSE_TIME.getInfo())); // Chỉ chờ 1 giây, có thể điều chỉnh
        pauseTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Khi PauseTransition kết thúc, thiết lập lại trạng thái pause của player
                player.setIsPause(false); // Để game tiếp tục
            }
        });
        pauseTransition.play();

    }

    public void setGameWinARoundOverlay() {
        gameWinARoundOverlay.setVisible(true);
        for (Monster monster : monsterList) {
            monster.setIsPause(true);
        }
        bg.setIsPause(true);
        player.setIsPause(true);
    }

    public void setGameOverOverlay() {
        gameOverOverlay.setVisible(true);
        for (Monster monster : monsterList) {
            monster.setIsPause(true);
        }
        bg.setIsPause(true);
        player.setIsPause(true);
    }

    public void openMenu(ActionEvent event) {
    }

    // EventHandler cho mỗi loại quái vật
    private class SpawnMonsterAHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (!isPaused && aCnt > 0) {
                try {
                    spawnMonsters("A");
                    aCnt--;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class SpawnMonsterBHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (!isPaused && bCnt > 0) {
                try {
                    spawnMonsters("B");
                    bCnt--;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class SpawnMonsterCHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (!isPaused && cCnt > 0) {
                try {
                    spawnMonsters("C");
                    cCnt--;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class SpawnMonsterDHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (!isPaused && dCnt > 0) {
                try {
                    spawnMonsters("D");
                    dCnt--;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Phương thức bật/tắt tạm dừng
    private void togglePause() {
        isPaused = !isPaused;

        boolean pause;
        if (isPaused) {
            pause = true;
            Sound.pauseBackgroundMusic();
        } else {
            pause = false;
            Sound.resumeBackgroundMusic();
        }

        pauseOverlay.setVisible(pause); // Hiển thị/ẩn cửa sổ Pause khi tạm dừng
        for (Monster monster : monsterList) {
            monster.setIsPause(pause);
        }
        bg.setIsPause(pause);
        player.setIsPause(pause);
    }


    private Image getImagePath(String path) throws Exception {
        if (path == null) {
            throw new Exception("Hinh anh khong hop le");
        }
        return new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
    }


    // Di chuyển nhân vật Wukong dựa trên các phím W, A, S, D
    public void handleKeyPress(KeyEvent event) {
        // Kiểm tra nếu tạm dừng thì không xử lý di chuyển
        if (isPaused) {
            return;
        }

        // Lấy tọa độ hiện tại của playerView và kích thước màn hình
        double currentX = playerView.getLayoutX();
        double currentY = playerView.getLayoutY();
        double playerWidth = playerView.getFitWidth();
        double playerHeight = playerView.getFitHeight();
        double screenWidth = 800; // Đặt chiều rộng của màn hình
        double screenHeight = 400; // Đặt chiều cao của màn hình
        double borderTopSize = 9;
        double borderBottomSize = 9;
        double borderLeftSize = 8;
        double borderRightSize = 8;
        double moving = 10;


        // Xử lý các phím di chuyển
        switch (event.getCode()) {
            case W:
                // Kiểm tra để không cho phép playerView đi lên trên viền màn hình
                if (currentY - moving >= borderTopSize) {
                    playerView.setLayoutY(currentY - moving);
                }
                //System.out.println("W");
                break;
            case S:
                // Kiểm tra để không cho phép playerView đi xuống dưới viền màn hình
                if (currentY + moving + playerHeight <= screenHeight - borderBottomSize) {
                    playerView.setLayoutY(currentY + moving);
                    //System.out.println(playerView.getLayoutY());
                }
                //System.out.println("S");
                break;
            case A:
                isRight = false;
                playerView.setImage(playerLeftImage);
                // Kiểm tra để không cho phép playerView đi sang trái viền màn hình
                if (currentX - moving >= borderLeftSize) {
                    playerView.setLayoutX(currentX - moving);
                }
                //System.out.println("A");
                break;
            case D:
                isRight = true;
                playerView.setImage(playerRightImage);
                // Kiểm tra để không cho phép playerView đi sang phải viền màn hình
                if (currentX + moving + playerWidth <= screenWidth - borderRightSize) {
                    playerView.setLayoutX(currentX + moving);
                }
                //System.out.println("D");
                break;
            case SPACE:
                // Tạo và thêm hitImageView tương ứng với hướng của playerView
                ImageView hitImageView = new ImageView();
                hitImageView.setFitHeight(50);
                hitImageView.setFitWidth(50);
                Sound.playHitSound();
                if (isRight) {
                    hitImageView.setImage(new Image("com/example/game/assets/wukong/right/hit1.png"));
                    hitImageView.setLayoutX(currentX + playerWidth / 2 + 1); // Vị trí bên phải
                } else {
                    hitImageView.setImage(new Image("com/example/game/assets/wukong/left/hit1.png"));
                    hitImageView.setLayoutX(currentX - 5 - hitImageView.getFitWidth() / 2); // Vị trí bên trái
                }
                hitImageView.setLayoutY(currentY + 4); // Đặt Y để nằm ngang với playerView
                bottomPane.getChildren().add(hitImageView); // Thêm vào giao diện

                // Thiết lập Timeline để xóa hitImageView sau ms
                Timeline hitTimeline = new Timeline(new KeyFrame(Duration.millis(Base.PlAYER_HIT_TIME.getInfo()), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        bottomPane.getChildren().remove(hitImageView); // Xóa hitImageView
                    }
                }));

                List<Monster> monstersToRemove = new ArrayList<>();
                // Kiểm tra va chạm với các monster
                for (Monster monster : monsterList) {
                    if (monster != null && IsHit(playerView, monster.getMonsterImageView())) {
                        System.out.println("Hit !!!");
                        (monster).reduceHealth();
                        if (aNums >= 0 && monster.isDead() && monster instanceof MonsterA) {
                            aNums--;
                            monstersToRemove.add(monster);
                        }
                        if (bNums >= 0 && monster.isDead() && monster instanceof MonsterB) {
                            bNums--;
                            monstersToRemove.add(monster);
                        }
                        if (cNums >= 0 && monster.isDead() && monster instanceof MonsterC) {
                            cNums--;
                            monstersToRemove.add(monster);
                        }
                        if (dNums >= 0 && monster.isDead() && monster instanceof MonsterD) {
                            dNums--;
                            monstersToRemove.add(monster);
                        }
                    }
                }
                monsterList.removeAll(monstersToRemove);
                hitTimeline.play(); // Bắt đầu Timeline
                //System.out.println("SPACE");
                //System.out.println("Player :  " + playerView.getLayoutX() + "  " + playerView.getLayoutY());
                break;
            default:
                break; // Bỏ qua các phím khác
        }
    }

    private boolean IsHit(ImageView player, ImageView monster) {
        // Đặt khoảng cách cho phép (khoảng cách rất nhỏ)
        final double margin = 2.0;

        // Kiểm tra xem playerView và monster có va chạm hay không với khoảng cách cho phép
        if (player.getLayoutX() + player.getFitWidth() > monster.getLayoutX() - margin
                && player.getLayoutX() < monster.getLayoutX() + monster.getFitWidth() + margin
                && player.getLayoutY() + player.getFitHeight() > monster.getLayoutY() - margin
                && player.getLayoutY() < monster.getLayoutY() + monster.getFitHeight() + margin) {
            return true;
        }

        return false;
    }


    // Bắt đầu di chuyển ngẫu nhiên các quái vật trong đáp án
    private void monsterAnswers() {
        double[] originalX = {50, 450, 50, 450}; // Tọa độ X gốc A, B, C, D
        double[] originalY = {170, 170, 270, 270}; // Tọa độ Y gốc A, B, C, D

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < monsterInAnswers.length; i++) {
                    ImageView monster = monsterInAnswers[i];
                    // Kiểm tra xem quái vật có phải là null không
                    if (monster != null) {

                        double initialX = originalX[i];
                        double initialY = originalY[i];
                        // Di chuyển ngẫu nhiên các quái vật
                        double randomX = (random.nextDouble() * 10) - 8; // Tọa độ X ngẫu nhiên
                        double randomY = (random.nextDouble() * 10) - 8; // Tọa độ Y ngẫu nhiên
                        double moveX = monster.getLayoutX() + randomX;
                        double moveY = monster.getLayoutY() + randomY;
                        if (Math.abs(moveX - initialX) <= 8 && Math.abs(moveY - initialY) <= 8) {
                            monster.setLayoutX(moveX);
                            monster.setLayoutY(moveY);
                        }
                        //System.out.println(monster.getLayoutX() + "  " + monster.getLayoutY());
                    }
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Đặt số lần chạy vô hạn
        timeline.play(); // Bắt đầu timeline

        CharacterAnimation aniA = new CharacterAnimation(monsA, "monsters/A/right/02-Fly", 8, "A");
        aniA.start();
    }

    private String getBulletPath(String answer) {
        if (answer.equals("A")) {
            return "/com/example/game/assets/monsters/A/left/bullet_left.png";
        } else if (answer.equals("B")) {
            return "/com/example/game/assets/monsters/B/bullet.png";
        } else if (answer.equals("C")) {
            return "/com/example/game/assets/monsters/C/bullet_left.png";
        } else if (answer.equals("D")) {
            //return "/com/example/game/assets/monsters/D";
            return "/com/example/game/assets/monsters/C/bullet_left.png";
            //return "";
        }
        return "";
    }

    private void spawnMonsters(String answer) throws Exception {
        String bulletPath = "";
        Image monsterImage = null;
        double layoutX = 0;
        double layoutY = 0;
        double bulletSpeedX = 0;
        double bulletSpeedY = 0;
        double fitWidth = 0;
        double fitHeight = 0;

        //System.out.println("hihi ????");
        if (answer.equals("A")) {
            bulletPath = getBulletPath("A");
            //System.out.println("hihi ????");
            monsterImage = new Image("com/example/game/assets/monsters/A/left/01-Idle/Bat_0.png");
            layoutX = 805;
            layoutY = 15 + Math.random() * (270 - 15);
            bulletSpeedX = -1 * Base.MONSTER_A_BULLET_SPEED.getInfo();
            bulletSpeedY = 0;
            fitWidth = Base.MONSTER_A_WIDTH.getInfo();
            fitHeight = Base.MONSTER_A_HEIGHT.getInfo();
        } else if (answer.equals("B")) {
            bulletPath = getBulletPath("B");
            monsterImage = new Image("com/example/game/assets/monsters/B/left/round ghost walk/sprite_0.png");
            layoutX = 805;
            layoutY = 20 + Math.random() * (270 - 20);
            bulletSpeedX = -1 * Base.MONSTER_B_BULLET_SPEED.getInfo();
            bulletSpeedY = 0;
            fitWidth = Base.MONSTER_B_WIDTH.getInfo();
            fitHeight = Base.MONSTER_B_HEIGHT.getInfo();
        } else if (answer.equals("C")) {
            bulletPath = getBulletPath("C");
            monsterImage = new Image("com/example/game/assets/monsters/C/robot_left.png");
            layoutX = 550 + Math.random() * (700 - 550);
            layoutY = 320;
            bulletSpeedX = -1 * Base.MONSTER_C_BULLET_SPEED.getInfo();
            bulletSpeedY = 0;
            fitWidth = Base.MONSTER_C_WIDTH.getInfo();
            fitHeight = Base.MONSTER_C_HEIGHT.getInfo();
        } else if (answer.equals("D")) {
            bulletPath = getBulletPath("D");
            monsterImage = new Image("com/example/game/assets/monsters/D/left/troll_1.png");
            layoutX = 805;
            layoutY = 270 + Math.random() * (320 - 290);
            bulletSpeedX = -1 * Base.MONSTER_D_BULLET_SPEED.getInfo();
            bulletSpeedY = 0;
            fitWidth = Base.MONSTER_D_WIDTH.getInfo();
            fitHeight = Base.MONSTER_D_HEIGHT.getInfo();
        }

        //System.out.println("Spawning Monster...");

        final ImageView monsterImageView = new ImageView();
        monsterImageView.setFitWidth(fitWidth);
        monsterImageView.setFitHeight(fitHeight);

        monsterImageView.setImage(monsterImage);
        monsterImageView.setLayoutX(layoutX); // Vị trí X
        monsterImageView.setLayoutY(layoutY); // Vị trí Y

        bottomPane.getChildren().add(monsterImageView);

        Monster m = null;
        if (answer.equals("A")) {
            m = new MonsterA(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        } else if (answer.equals("B")) {
            m = new MonsterB(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        } else if (answer.equals("C")) {
            m = new MonsterC(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        } else if (answer.equals("D")) {
            m = new MonsterD(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        }
        //System.out.println("Monster spawned at position: (" + monsterImageView.getLayoutX() + ", " + monsterImageView.getLayoutY() + ")");


        Monster finalM = m;
        monsterList.add(finalM);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                finalM.movement(event, player, 800, 800);
                if (monsterImageView.getLayoutX() + monsterImageView.getFitWidth() <= 8) {
                    bottomPane.getChildren().remove(monsterImageView);
                }

                //System.out.println("Monster spawned at position: (" + monsterImageView.getLayoutX() + ", " + monsterImageView.getLayoutY() + ")");
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Lặp vô hạn
        timeline.play(); // Bắt đầu Timeline
    }

    // Phương thức bắn đạn từ vị trí của monster về phía playerView
    private void fireBulletTowardsPlayer(String bulletPath, double startX, double startY, double playerX, double playerY) {
        // Tính khoảng cách giữa monster và playerView
        double deltaX = playerX - startX;
        double deltaY = playerY - startY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Tính tốc độ của đạn trên mỗi trục (X, Y) theo hướng playerView
        double speed = 5; // Tốc độ đạn
        double speedX = (deltaX / distance) * speed;
        double speedY = (deltaY / distance) * speed;

        // Khởi tạo viên đạn và thêm nó vào màn hình
        //Bullet bullet = new Bullet(bulletPath, startX, startY, speedX, speedY, bottomPane);
        //bottomPane.getChildren().add(bullet.getBulletImage()); // bottomPane là bố cục chứa tất cả các đối tượng trong game
    }

}

