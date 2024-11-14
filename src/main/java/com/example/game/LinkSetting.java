package com.example.game;

public enum LinkSetting {

    // Câu hỏi
    QUESTION_PATH("D:\\LibraryManagement\\LibraryManagement\\data\\QuestionInGame.txt"),

    // Dữ liệu trận đấu
    GAME_ROUND_PATH("C:\\YEAR 2\\OOP\\JavaFX\\CLONE\\data\\bmw_round_"),

    // Ảnh nền
    BACKGROUND_IMAGE("/com/example/game/assets/sky_night/"),

    // Ảnh nhân vật phải, trái
    PLAYER_IMAGE_RIGHT("/com/example/game/assets/wukong/right/wukong_right.png"),
    PLAYER_IMAGE_LEFT("/com/example/game/assets/wukong/left/wukong_left.png"),

    // Ảnh đòn đánh của nhân vật phải, trái
    PLAYER_IMAGE_HIT_RIGHT("/com/example/game/assets/wukong/right/hit1.png"),
    PLAYER_IMAGE_HIT_LEFT("/com/example/game/assets/wukong/left/hit1.png"),

    // Ảnh của quái vật
    MONSTER_ANIMATION("/com/example/game/assets/"),

    // Ảnh đạn của quái vật
    MONSTER_A_BULLET("/com/example/game/assets/monsters/A/left/bullet_left.png"),
    MONSTER_B_BULLET("/com/example/game/assets/monsters/B/bullet.png"),
    MONSTER_C_BULLET("/com/example/game/assets/monsters/C/bullet_left.png"),
    MONSTER_D_BULLET("/com/example/game/assets/monsters/C/bullet_left.png"), // monster D không cần ảnh đạn

    // Nhạc nền
    BACKGROUND_SOUND("/com/example/game/sound/background_2.mp3"),

    // Âm thanh người chơi
    PLAYER_SOUND_HIT("/com/example/game/sound/player_hit_1.mp3"),
    PLAYER_SOUTD_IS_HIT("/com/example/game/sound/player_isHit_1.mp3"),
    PLAYER_SOUND_DEATH("/com/example/game/sound/player_die_1.mp3"),

    // Âm thanh quái vật gây sát thương
    MONSTER_A_SOUND_BULLET("/com/example/game/sound/monster_A_hit_1.mp3"),
    MONSTER_B_SOUND_BULLET("/com/example/game/sound/monster_B_hit_1.mp3"),
    MONSTER_C_SOUND_BULLET("/com/example/game/sound/monster_C_hit_1.mp3"),
    MONSTER_D_SOUND_BULLET("/com/example/game/sound/monster_D_hit_1.mp3"),

    // Âm thanh quái vật chết
    MONSTER_A_SOUND_DEATH("/com/example/game/sound/monster_A_die_1.mp3"),
    MONSTER_B_SOUND_DEATH("/com/example/game/sound/monster_B_die_1.mp3"),
    MONSTER_C_SOUND_DEATH("/com/example/game/sound/monster_C_die_1.mp3"),
    MONSTER_D_SOUND_DEATH("/com/example/game/sound/monster_D_die_1.mp3");


    private final String str;

    LinkSetting(String str) {
        this.str = str;
    }

    public String getLink() {
        return str;
    }
}
