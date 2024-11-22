package com.example.game;
import com.example.library.User;
import com.example.library.UserJDBC;
import com.example.library.WindowManager;

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

import java.io.IOException;
import com.example.game.*;
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
    public Label roundLabel;
    private Image playerLeftImage;
    private Image playerRightImage;
    private ImageView leftHit;
    private ImageView rightHit;
    private boolean isRight;
    private boolean deathSound;
    private static int roundCount;

    private static final String QUESTION_FILE_PATH = LinkSetting.QUESTION_PATH.getLink();

    private Timeline gameTimer; // Biến để lưu timeline của bộ đếm
    private int gameTime;

    private boolean isPaused = false;
    private boolean isLose = false;
    private boolean isWin = false;

    private List<Question> questions;
    private Question currentQuestion;
    private String correctAnswerText; // Biến lưu đáp án đúng của câu hỏi hiện tại

    private Timeline updateLabelsTimeline;
    private Timeline spawnATimeline;
    private Timeline spawnBTimeline;
    private Timeline spawnCTimeline;
    private Timeline spawnDTimeline;

    private UserJDBC userJDBC = new UserJDBC();
    private User user = new User();

    private static int score = 0;

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

    private static int playerLives = NumSetting.PLAYER_LIVES.getNum();

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

        isPaused = false; // Đảm bảo game bắt đầu ở trạng thái không pause
        isLose = false;
        isWin = false;
        deathSound = false;

        pauseOverlay.setVisible(false);
        gameOverOverlay.setVisible(false);
        gameWinARoundOverlay.setVisible(false);

        roundCount = 1;

        try {
            // Đọc file bmw_round và cập nhật các giá trị trong enum
            Map<String, Integer> config = GameRound.loadConfig(roundCount);
            NumSetting.updateSettingsFromConfig(config);  // Cập nhật enum từ file bmw_round
        } catch (Exception e) {
            System.out.println("Không thể tải file bmw_round");
        }

        bg = new Background(bottomPane);
        bg.start(1);

        gameTime = NumSetting.TIME.getNum();

        startGameTimer();

        //Sound.playBackgroundMusic();

        // Gán các quái vật vào mảng
        monsters[0] = monsterA;
        monsters[1] = monsterB;
        monsters[2] = monsterC;
        monsters[3] = monsterD;

        monsterInAnswers[0] = monsA;
        monsterInAnswers[1] = monsB;
        monsterInAnswers[2] = monsC;
        monsterInAnswers[3] = monsD;


        aCnt = NumSetting.MONSTER_A_SPAWN_NUMBER.getNum();
        bCnt = NumSetting.MONSTER_B_SPAWN_NUMBER.getNum();
        cCnt = NumSetting.MONSTER_C_SPAWN_NUMBER.getNum();
        dCnt = NumSetting.MONSTER_D_SPAWN_NUMBER.getNum();

        aNums = NumSetting.MONSTER_A_SPAWN_NUMBER.getNum();
        bNums = NumSetting.MONSTER_B_SPAWN_NUMBER.getNum();
        cNums = NumSetting.MONSTER_C_SPAWN_NUMBER.getNum();
        dNums = NumSetting.MONSTER_D_SPAWN_NUMBER.getNum();

        player = new Player(bottomPane, playerView);


        try {
            playerRightImage = new Image(getClass().getResource(LinkSetting.PLAYER_IMAGE_RIGHT.getLink()).toExternalForm());
            playerLeftImage = new Image(getClass().getResource(LinkSetting.PLAYER_IMAGE_LEFT.getLink()).toExternalForm());
        } catch (Exception e) {
            System.out.println("Loi class BMW. Line = 150");
        }

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
                            if ((isPaused || isLose || isWin) && event.getCode() == KeyCode.M) {
                                try {
                                    monsterA = null;
                                    monsterB = null;
                                    monsterC = null;
                                    monsterD = null;
                                    Sound.stopBackgroundMusic();
                                    Sound.restartBackgroundMusic();
                                    WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukongMenu.fxml", "stylesheet (css)/game.css", 1100, 600);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                return;
                            }
                            if (event.getCode() == KeyCode.R) { // Phím R để restart game
                                restartGame(); // Gọi phương thức restartGame()
                                event.consume(); // Ngừng sự kiện không lan truyền ra ngoài
                            }
                            if (isWin && event.getCode() == KeyCode.N) {
                                roundCount++;
                                restartGame();
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
        spawnATimeline = new Timeline(new KeyFrame(Duration.seconds(NumSetting.MONSTER_A_SPAWN_TIME.getNum()), new SpawnMonsterAHandler()));
        spawnBTimeline = new Timeline(new KeyFrame(Duration.seconds(NumSetting.MONSTER_B_SPAWN_TIME.getNum()), new SpawnMonsterBHandler()));
        spawnCTimeline = new Timeline(new KeyFrame(Duration.seconds(NumSetting.MONSTER_C_SPAWN_TIME.getNum()), new SpawnMonsterCHandler()));
        spawnDTimeline = new Timeline(new KeyFrame(Duration.seconds(NumSetting.MONSTER_D_SPAWN_TIME.getNum()), new SpawnMonsterDHandler()));

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
        updateLabelsTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (roundCount == 6) {
                    updateLabelsTimeline.stop();
                    stopGame();
                    if (user != null) {
                        int newscore = user.getScore(user.getUsername()) + 1;
                        user.scoreUpdate(user.getUsername(), newscore);
                    } else {
                        System.out.println("Ko xac dinh dc ng dung");
                    }
                    try {
                        WindowManager.addGameFxml("/com/example/game/fxml/BlackMythWukongVictory.fxml", "stylesheet (css)/game.css", 1100, 600);
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                player_lives.setText("Lives: " + player.getHealth());
                roundLabel.setText("Round: " + roundCount);
                bat_num.setText("Bat: " + aNums);
                sprite_num.setText("Sprite: " + bNums);
                robot_num.setText("Robot: " + cNums);
                troll_num.setText("Troll: " + dNums);

                /*if (!deathSound && player.getHealth() <= 0) {
                    Sound.playPlayerDeathSound();
                    deathSound = true;
                }*/

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

            if (isWin) {
                questions.remove(currentQuestion);
            }

            // Lấy ngẫu nhiên câu hỏi và xóa khỏi danh sách
            currentQuestion = Question.getRandomQuestion(questions);

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

//
//        if (!isWin) {
//            Sound.restartBackgroundMusic();
//        }

        //questions = Question.loadQuestionsFromFile(QUESTION_FILE_PATH); // Đường dẫn file
        loadNextQuestion();


        // Dừng và khởi tạo lại đối tượng bg với hình nền mới

        try {
            // Đọc file bmw_round và cập nhật các giá trị trong enum
            Map<String, Integer> config = GameRound.loadConfig(roundCount);
            System.out.println(roundCount);
            NumSetting.updateSettingsFromConfig(config);  // Cập nhật enum từ file bmw_round
        } catch (Exception e) {
            System.out.println("Không thể tải file bmw_round");
        }

        // Khôi phục lại trạng thái pause, ẩn overlay pause
        isPaused = false;
        isLose = false;
        isWin = false;
        pauseOverlay.setVisible(false);
        gameOverOverlay.setVisible(false);
        gameWinARoundOverlay.setVisible(false);
        gameTime = NumSetting.TIME.getNum();

        bg = new Background(bottomPane);
        System.out.println(NumSetting.BACKGROUND_IMAGE.getNum());
        bg.start(1);

        // Reset các quái vật
        for (int i = 0; i < monsterList.size(); i++) {
            //System.out.println(monsterList.get(i).getLayoutX());
            monsterList.get(i).removeMonster();
        }

        monsterList.clear(); // Xóa tất cả các quái vật hiện tại trong danh sách

        // Khôi phục số lượng mạng sống của người chơi
        player.setHealth(NumSetting.PLAYER_LIVES.getNum()); // Giả sử phương thức setHealth được định nghĩa trong lớp Player
        // Khôi phục số lượng quái vật
        aCnt = NumSetting.MONSTER_A_SPAWN_NUMBER.getNum();
        bCnt = NumSetting.MONSTER_B_SPAWN_NUMBER.getNum();
        cCnt = NumSetting.MONSTER_C_SPAWN_NUMBER.getNum();
        dCnt = NumSetting.MONSTER_D_SPAWN_NUMBER.getNum();

        aNums = NumSetting.MONSTER_A_SPAWN_NUMBER.getNum();
        bNums = NumSetting.MONSTER_B_SPAWN_NUMBER.getNum();
        cNums = NumSetting.MONSTER_C_SPAWN_NUMBER.getNum();
        dNums = NumSetting.MONSTER_D_SPAWN_NUMBER.getNum();

        // Khởi tạo lại các quái vật, người chơi, vv.
        playerView.setLayoutX(100); // Vị trí ban đầu của người chơi
        playerView.setLayoutY(200);

        // Khởi tạo lại các quái vật mới
        monsterAnswers(); // Hàm này sẽ spawn lại các quái vật

        // Reset lại các Timeline spawn quái vật với thời gian spawn quy định
        spawnATimeline.stop();
        spawnBTimeline.stop();
        spawnCTimeline.stop();
        spawnDTimeline.stop();

        // Khởi tạo các Timeline riêng cho từng loại quái vật
        spawnATimeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(NumSetting.MONSTER_A_SPAWN_TIME.getNum()), new SpawnMonsterAHandler()));
        spawnBTimeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(NumSetting.MONSTER_B_SPAWN_TIME.getNum()), new SpawnMonsterBHandler()));
        spawnCTimeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(NumSetting.MONSTER_C_SPAWN_TIME.getNum()), new SpawnMonsterCHandler()));
        spawnDTimeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(NumSetting.MONSTER_D_SPAWN_TIME.getNum()), new SpawnMonsterDHandler()));

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
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(NumSetting.PLAYER_SET_PAUSE_TIME.getNum())); // Chỉ chờ ... giây, có thể điều chỉnh
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
            //Sound.pauseBackgroundMusic();
        }

        else {
            pause = false;
            //Sound.resumeBackgroundMusic();
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
            throw new Exception("Loi class BMW. Line = 506");
        }
        return new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
    }


    // Di chuyển nhân vật Wukong dựa trên các phím W, A, S, D
    public void handleKeyPress(KeyEvent event) {
        // Kiểm tra nếu tạm dừng thì không xử lý di chuyển
        if (isPaused) {
            return;
        }

        try {
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
                    break;
                case S:
                    // Kiểm tra để không cho phép playerView đi xuống dưới viền màn hình
                    if (currentY + moving + playerHeight <= screenHeight - borderBottomSize) {
                        playerView.setLayoutY(currentY + moving);
                    }
                    break;
                case A:
                    isRight = false;
                    playerView.setImage(playerLeftImage);
                    // Kiểm tra để không cho phép playerView đi sang trái viền màn hình
                    if (currentX - moving >= borderLeftSize) {
                        playerView.setLayoutX(currentX - moving);
                    }
                    break;
                case D:
                    isRight = true;
                    playerView.setImage(playerRightImage);
                    // Kiểm tra để không cho phép playerView đi sang phải viền màn hình
                    if (currentX + moving + playerWidth <= screenWidth - borderRightSize) {
                        playerView.setLayoutX(currentX + moving);
                    }
                    break;
                case SPACE:
                    // Tạo và thêm hitImageView tương ứng với hướng của playerView
                    ImageView hitImageView = new ImageView();
                    hitImageView.setFitHeight(50);
                    hitImageView.setFitWidth(50);
                    Sound.playHitSound();
                    if (isRight) {
                        hitImageView.setImage(new Image(getClass().getResource(LinkSetting.PLAYER_IMAGE_HIT_RIGHT.getLink()).toExternalForm()));
                        hitImageView.setLayoutX(currentX + playerWidth / 2 + 1); // Vị trí bên phải
                    } else {
                        hitImageView.setImage(new Image(getClass().getResource(LinkSetting.PLAYER_IMAGE_HIT_LEFT.getLink()).toExternalForm()));
                        hitImageView.setLayoutX(currentX - 5 - hitImageView.getFitWidth() / 2); // Vị trí bên trái
                    }
                    hitImageView.setLayoutY(currentY + 4); // Đặt Y để nằm ngang với playerView
                    bottomPane.getChildren().add(hitImageView); // Thêm vào giao diện

                    // Thiết lập Timeline để xóa hitImageView sau ms
                    Timeline hitTimeline = new Timeline(new KeyFrame(Duration.millis(NumSetting.PLAYER_HIT_TIME.getNum()), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            bottomPane.getChildren().remove(hitImageView); // Xóa hitImageView
                        }
                    }));

                    List<Monster> monstersToRemove = new ArrayList<>();
                    // Kiểm tra va chạm với các monster
                    for (Monster monster : monsterList) {
                        if (monster != null && IsHit(playerView, monster.getMonsterImageView())) {
                            //System.out.println("Hit !!!");
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
                    break;
                default:
                    break; // Bỏ qua các phím khác
            }
        } catch (Exception e) {
            System.out.println("Lỗi trong quá trình xử lý phím. Line = 618");
            //e.printStackTrace(); // In ra thông tin chi tiết của lỗi
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
            return LinkSetting.MONSTER_A_BULLET.getLink();
        } else if (answer.equals("B")) {
            return LinkSetting.MONSTER_B_BULLET.getLink();
        } else if (answer.equals("C")) {
            return LinkSetting.MONSTER_C_BULLET.getLink();
        } else if (answer.equals("D")) {
            //return "/com/example/game/assets/monsters/D";
            return LinkSetting.MONSTER_D_BULLET.getLink();
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

        try {
            if (answer.equals("A")) {
                bulletPath = getBulletPath("A");
                monsterImage = new Image(getClass().getResource(LinkSetting.MONSTER_ANIMATION.getLink() + "monsters/A/left/01-Idle/Bat_0.png").toExternalForm());
                layoutX = 805;
                layoutY = 15 + Math.random() * (270 - 15);
                bulletSpeedX = -1 * NumSetting.MONSTER_A_BULLET_SPEED.getNum();
                bulletSpeedY = 0;
                fitWidth = NumSetting.MONSTER_A_WIDTH.getNum();
                fitHeight = NumSetting.MONSTER_A_HEIGHT.getNum();
            } else if (answer.equals("B")) {
                bulletPath = getBulletPath("B");
                monsterImage = new Image(getClass().getResource( LinkSetting.MONSTER_ANIMATION.getLink() + "monsters/B/left/round ghost walk/sprite_0.png").toExternalForm());
                layoutX = 805;
                layoutY = 20 + Math.random() * (270 - 20);
                bulletSpeedX = -1 * NumSetting.MONSTER_B_BULLET_SPEED.getNum();
                bulletSpeedY = 0;
                fitWidth = NumSetting.MONSTER_B_WIDTH.getNum();
                fitHeight = NumSetting.MONSTER_B_HEIGHT.getNum();
            } else if (answer.equals("C")) {
                bulletPath = getBulletPath("C");
                monsterImage = new Image(getClass().getResource(LinkSetting.MONSTER_ANIMATION.getLink() + "monsters/C/robot_left.png").toExternalForm());
                layoutX = 550 + Math.random() * (700 - 550);
                layoutY = 320;
                bulletSpeedX = -1 * NumSetting.MONSTER_C_BULLET_SPEED.getNum();
                bulletSpeedY = 0;
                fitWidth = NumSetting.MONSTER_C_WIDTH.getNum();
                fitHeight = NumSetting.MONSTER_C_HEIGHT.getNum();
            } else if (answer.equals("D")) {
                bulletPath = getBulletPath("D");
                monsterImage = new Image(getClass().getResource(LinkSetting.MONSTER_ANIMATION.getLink() + "monsters/D/left/troll_1.png").toExternalForm());
                layoutX = 805;
                layoutY = 270 + Math.random() * (320 - 290);
                bulletSpeedX = -1 * NumSetting.MONSTER_D_BULLET_SPEED.getNum();
                bulletSpeedY = 0;
                fitWidth = NumSetting.MONSTER_D_WIDTH.getNum();
                fitHeight = NumSetting.MONSTER_D_HEIGHT.getNum();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Không thể tải hình ảnh quái vật. Line = 744");
        }

        if (monsterImage == null) {
            System.out.println("Không thể tải hình ảnh quái vật.");
            return;  // Nếu hình ảnh không tải được, thoát khỏi phương thức
        }

        final ImageView monsterImageView = new ImageView();
        monsterImageView.setFitWidth(fitWidth);
        monsterImageView.setFitHeight(fitHeight);
        monsterImageView.setImage(monsterImage);
        monsterImageView.setLayoutX(layoutX);
        monsterImageView.setLayoutY(layoutY);
        bottomPane.getChildren().add(monsterImageView);

        final Monster m;  // Make the variable `final`
        if (answer.equals("A")) {
            m = new MonsterA(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        } else if (answer.equals("B")) {
            m = new MonsterB(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        } else if (answer.equals("C")) {
            m = new MonsterC(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        } else if (answer.equals("D")) {
            m = new MonsterD(monsterImageView, player, bulletPath, bulletSpeedX, bulletSpeedY, bottomPane);
        } else {
            return;
        }

        monsterList.add(m);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                m.movement(event, player, 800, 800);
                if (monsterImageView.getLayoutX() + monsterImageView.getFitWidth() <= 8) {
                    bottomPane.getChildren().remove(monsterImageView);
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopGame() {
        // Dừng tất cả Timeline
        if (gameTimer != null) gameTimer.stop();
        if (updateLabelsTimeline != null) updateLabelsTimeline.stop();
        if (spawnATimeline != null) spawnATimeline.stop();
        if (spawnBTimeline != null) spawnBTimeline.stop();
        if (spawnCTimeline != null) spawnCTimeline.stop();
        if (spawnDTimeline != null) spawnDTimeline.stop();

        // Dừng tất cả quái vật
        for (Monster monster : monsterList) {
            if (monster != null) monster.setIsPause(true);
        }

        // Dừng background và player
        if (bg != null) bg.setIsPause(true);
        if (player != null) player.setIsPause(true);

        // Dừng âm thanh
        Sound.stopAllSounds(); // Nếu bạn đã thêm phương thức này trong lớp Sound
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

}