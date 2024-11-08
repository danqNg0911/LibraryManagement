package com.example.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    private static MediaPlayer backgroundMusicPlayer;
    private static MediaPlayer hitSoundPlayer;
    private static MediaPlayer shootSoundPlayer;
    private static MediaPlayer monsterDeathSoundPlayer;

    // Phương thức để khởi tạo và phát nhạc nền
    public static void playBackgroundMusic() {
        // Chỉ khởi tạo lại MediaPlayer khi chưa có hoặc đã dừng lại
        try {
            if (backgroundMusicPlayer == null || backgroundMusicPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                Media backgroundMusic = new Media(Sound.class.getResource("/com/example/game/sound/background_" + Base.BACKGROUND_MUSIC.getInfo() + ".mp3").toExternalForm());

                backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp lại nhạc nền
                backgroundMusicPlayer.setVolume(0.5); // Điều chỉnh âm lượng
                backgroundMusicPlayer.play();
            }
        } catch (Exception e) {
            System.out.println("loi background music");
        }
    }

    // Phương thức để tạm dừng nhạc nền
    public static void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    // Phương thức phát âm thanh khi người chơi đánh trúng quái vật
    public static void playHitSound() {
        try {
            // Tạo đối tượng Media từ tệp âm thanh
            Media hitSound = new Media(Sound.class.getResource("/com/example/game/sound/player_hit_" + Base.PLAYER_HIT_MUSIC.getInfo() + ".mp3").toExternalForm());
            hitSoundPlayer = new MediaPlayer(hitSound);
            hitSoundPlayer.setVolume(1.0);

            // Chơi âm thanh ngay lập tức
            hitSoundPlayer.play();
        } catch (Exception e) {
            System.out.println("Lỗi khi phát âm thanh hit: " + e.getMessage());
        }
    }

    // Phương thức phát âm thanh khi người chơi bị đánh trúng bởi quái vật
    public static void playIsHitSound() {
        try {
            // Tạo đối tượng Media từ tệp âm thanh
            Media hitSound = new Media(Sound.class.getResource("/com/example/game/sound/player_isHit_" + Base.PLAYER_IS_HIT_MUSIC.getInfo() + ".mp3").toExternalForm());
            hitSoundPlayer = new MediaPlayer(hitSound);
            hitSoundPlayer.setVolume(1.0);

            // Chơi âm thanh ngay lập tức
            hitSoundPlayer.play();
        } catch (Exception e) {
            System.out.println("Lỗi khi phát âm thanh hit: " + e.getMessage());
        }
    }

    // Phương thức phát âm thanh khi người chơi chết
    public static void playPlayerDeathSound() {
        Media playerDeathSound = new Media(Sound.class.getResource("/com/example/game/sound/player_die_" + Base.PLAYER_DIE_MUSIC.getInfo() + ".mp3").toExternalForm());
        MediaPlayer playerDeathSoundPlayer = new MediaPlayer(playerDeathSound);
        playerDeathSoundPlayer.setVolume(1.0);
        playerDeathSoundPlayer.play();
    }


    // Phương thức phát âm thanh khi monster bắn đạn
    public static void playMonsterSound(String answer) {
        String musicPath = "";
        if (answer.equals("A")) {
            musicPath = "/com/example/game/sound/monster_A_hit_" + Base.MONSTER_A_HIT_MUSIC.getInfo() + ".mp3";
        }
        else if (answer.equals("B")) {
            musicPath = "/com/example/game/sound/monster_B_hit_" + Base.MONSTER_B_HIT_MUSIC.getInfo() + ".mp3";
        }
        else if (answer.equals("C")) {
            musicPath = "/com/example/game/sound/monster_C_hit_" + Base.MONSTER_C_HIT_MUSIC.getInfo() + ".mp3";
        }
        else if (answer.equals("D")) {
            musicPath = "/com/example/game/sound/monster_D_hit_" + Base.MONSTER_D_HIT_MUSIC.getInfo() + ".mp3";
        }

        Media shootSound = new Media(Sound.class.getResource(musicPath).toExternalForm());
        shootSoundPlayer = new MediaPlayer(shootSound);
        shootSoundPlayer.setVolume(0.8);
        shootSoundPlayer.play();
    }

    // Phương thức phát âm thanh khi quái vật chết
    public static void playMonsterDeathSound(String answer) {
        String musicPath = "";
        if (answer.equals("A")) {
            musicPath = "/com/example/game/sound/monster_A_die_" + Base.MONSTER_A_HIT_MUSIC.getInfo() + ".mp3";
        }
        else if (answer.equals("B")) {
            musicPath = "/com/example/game/sound/monster_B_die_" + Base.MONSTER_B_HIT_MUSIC.getInfo() + ".mp3";
        }
        else if (answer.equals("C")) {
            musicPath = "/com/example/game/sound/monster_C_die_" + Base.MONSTER_C_HIT_MUSIC.getInfo() + ".mp3";
        }
        else if (answer.equals("D")) {
            musicPath = "/com/example/game/sound/monster_D_die_" + Base.MONSTER_D_HIT_MUSIC.getInfo() + ".mp3";
        }

        Media deathSound = new Media(Sound.class.getResource(musicPath).toExternalForm());
        monsterDeathSoundPlayer = new MediaPlayer(deathSound);
        monsterDeathSoundPlayer.setVolume(1.0);
        monsterDeathSoundPlayer.play();
    }

    // Phương thức để phát lại nhạc nền khi game tiếp tục sau pause
    public static void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            backgroundMusicPlayer.play();
        }
    }

    // Phương thức để tạm dừng nhạc nền khi game bị pause
    public static void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusicPlayer.pause();
        }
    }

    public static void restartBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.play();
        }
    }
}
