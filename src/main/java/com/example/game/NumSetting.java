package com.example.game;

import java.util.Map;

public enum NumSetting {


    TIME("TIME"), // Thời gian 1 ván đấu (đơn vị giây)


    // Sức mạnh quái vật
    MONSTER_A_LIVES("MONSTER_A_LIVES"), // Sinh mạng quái vật
    MONSTER_A_SPAWN_NUMBER("MONSTER_A_SPAWN_NUMBER"), // Sô quái vật spawn ra
    MONSTER_A_WIDTH("MONSTER_A_WIDTH"), // Kích thước chiều rộng
    MONSTER_A_HEIGHT("MONSTER_A_HEIGHT"), // Kích thước chiều dài

    MONSTER_A_MOVE_SPEED("MONSTER_A_MOVE_SPEED"), // Tốc độ di chuyển
    MONSTER_A_BULLET_SPEED("MONSTER_A_BULLET_SPEED"), // Tốc độ đạn
    MONSTER_A_BULLET_WIDTH("MONSTER_A_BULLET_WIDTH"), // Kích thước chiều rộng đạn
    MONSTER_A_BULLET_HEIGHT("MONSTER_A_BULLET_HEIGHT"), // Kích thước chiều dài đạn
    MONSTER_A_BULLET_TIME("MONSTER_A_BULLET_TIME"), // Thời gian ngừng bắn đạn sau mỗi lượt (đơn vị giây)
    MONSTER_A_NUMBER_OF_BULLET_PER_TURN("MONSTER_A_NUMBER_OF_BULLET_PER_TURN"), // Số đạn bắng trong mỗi lượt
    MONSTER_A_DAMAGE("MONSTER_A_DAMAGE"), // Sát thương gây ra

    MONSTER_A_HIT_MUSIC("MONSTER_A_HIT_MUSIC"), // Âm thanh gây sát thương số ... trong file sound
    MONSTER_A_DIE_MUSIC("MONSTER_A_DIE_MUSIC"), // Âm thanh khi chết số ... trong file sound


    MONSTER_B_LIVES("MONSTER_B_LIVES"),
    MONSTER_B_SPAWN_NUMBER("MONSTER_B_SPAWN_NUMBER"),
    MONSTER_B_WIDTH("MONSTER_B_WIDTH"),
    MONSTER_B_HEIGHT("MONSTER_B_HEIGHT"),

    MONSTER_B_MOVE_SPEED("MONSTER_B_MOVE_SPEED"),
    MONSTER_B_BULLET_SPEED("MONSTER_B_BULLET_SPEED"),
    MONSTER_B_BULLET_WIDTH("MONSTER_B_BULLET_WIDTH"),
    MONSTER_B_BULLET_HEIGHT("MONSTER_B_BULLET_HEIGHT"),
    MONSTER_B_BULLET_TIME("MONSTER_B_BULLET_TIME"),
    MONSTER_B_NUMBER_OF_BULLET_PER_TURN("MONSTER_B_NUMBER_OF_BULLET_PER_TURN"),
    MONSTER_B_DAMAGE("MONSTER_B_DAMAGE"),

    MONSTER_B_HIT_MUSIC("MONSTER_B_HIT_MUSIC"),
    MONSTER_B_DIE_MUSIC("MONSTER_B_DIE_MUSIC"),


    MONSTER_C_LIVES("MONSTER_C_LIVES"),
    MONSTER_C_SPAWN_NUMBER("MONSTER_C_SPAWN_NUMBER"),
    MONSTER_C_WIDTH("MONSTER_C_WIDTH"),
    MONSTER_C_HEIGHT("MONSTER_C_HEIGHT"),

    MONSTER_C_MOVE_SPEED("MONSTER_C_MOVE_SPEED"),
    MONSTER_C_BULLET_SPEED("MONSTER_C_BULLET_SPEED"),
    MONSTER_C_BULLET_WIDTH("MONSTER_C_BULLET_WIDTH"),
    MONSTER_C_BULLET_HEIGHT("MONSTER_C_BULLET_HEIGHT"),
    MONSTER_C_BULLET_TIME("MONSTER_C_BULLET_TIME"),
    MONSTER_C_NUMBER_OF_BULLET_PER_TURN("MONSTER_C_NUMBER_OF_BULLET_PER_TURN"),
    MONSTER_C_DAMAGE("MONSTER_C_DAMAGE"),

    MONSTER_C_HIT_MUSIC("MONSTER_C_HIT_MUSIC"),
    MONSTER_C_DIE_MUSIC("MONSTER_C_DIE_MUSIC"),


    MONSTER_D_LIVES("MONSTER_D_LIVES"),
    MONSTER_D_SPAWN_NUMBER("MONSTER_D_SPAWN_NUMBER"),
    MONSTER_D_WIDTH("MONSTER_D_WIDTH"),
    MONSTER_D_HEIGHT("MONSTER_D_HEIGHT"),

    MONSTER_D_MOVE_SPEED("MONSTER_D_MOVE_SPEED"),
    MONSTER_D_BULLET_SPEED("MONSTER_D_BULLET_SPEED"), // Cận chiến nên không cần đạn
    MONSTER_D_BULLET_WIDTH("MONSTER_D_BULLET_WIDTH"), // Cận chiến nên không cần đạn
    MONSTER_D_BULLET_HEIGHT("MONSTER_D_BULLET_HEIGHT"), // Cận chiến nên không cần đạn
    MONSTER_D_TELEPORT_TIME("MONSTER_D_TELEPORT_TIME"), // Thời gian nghỉ giữa mỗi lần dịch chuyển tức thời (đơn vị giây)
    MONSTER_D_NUMBER_OF_HIT_PER_TURN("MONSTER_D_NUMBER_OF_HIT_PER_TURN"), // Sô lần đánh trong 1 lần dịch chuyển tức thời
    MONSTER_D_DAMAGE("MONSTER_D_DAMAGE"),

    MONSTER_D_HIT_MUSIC("MONSTER_D_HIT_MUSIC"),
    MONSTER_D_DIE_MUSIC("MONSTER_D_DIE_MUSIC"),


    // Thời gian spawn quái vật (đơn vị giây)
    MONSTER_A_SPAWN_TIME("MONSTER_A_SPAWN_TIME"),
    MONSTER_B_SPAWN_TIME("MONSTER_B_SPAWN_TIME"),
    MONSTER_C_SPAWN_TIME("MONSTER_C_SPAWN_TIME"),
    MONSTER_D_SPAWN_TIME("MONSTER_D_SPAWN_TIME"),


    // Người chơi
    PLAYER_WIDTH("PLAYER_WIDTH"), // Chiều rộng  người chơi
    PLAYER_HEIGHT("PLAYER_HEIGHT"), // Chiều dài người chơi
    PLAYER_LIVES("PLAYER_LIVES"), // Sinh mạng người chơi
    PLAYER_SET_PAUSE_TIME("PLAYER_SET_PAUSE_TIME"), // Thời gian bất tử của người chơi sau khi restart game ( cần lưu ý MONSTER_?_SPAWN_TIME) (đơn vị giây)
    PLAYER_HIT_TIME("PLAYER_HIT_TIME"), // Thời gian chém kiếm (đơn vị ms)
    PLAYER_HIT_MUSIC("PLAYER_HIT_MUSIC"), // Âm thanh khi đánh số ... trong file sound
    PLAYER_IS_HIT_MUSIC("PLAYER_IS_HIT_MUSIC"), // Âm thanh khi bị đánh số ... trong file sound
    PLAYER_DIE_MUSIC("PLAYER_DIE_MUSIC"), // Âm thanh khi chết số ... trong file sound


    // Ảnh nền
    BACKGROUND_IMAGE("BACKGROUND_IMAGE"), // Ảnh nền thứ i trong asset
    BACKGROUND_SPEED("BACKGROUND_SPEED"), // Tốc độ ảnh nền

    BACKGROUND_MUSIC("BACKGROUND_MUSIC"); // Bản nhạc nền số ... trong file sound


    private String key;  // Khóa để tra cứu trong file cấu hình
    private int value;   // Giá trị sẽ được cập nhật từ file

    NumSetting(String key) {
        this.key = key;
        this.value = 0; // Giá trị mặc định là 0
    }

    public String getKey() {
        return key;
    }

    public int getNum() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    // Phương thức cập nhật tất cả giá trị trong Enum từ file bmw_round
    public static void updateSettingsFromConfig(Map<String, Integer> config) {
        for (NumSetting setting : NumSetting.values()) {
            // Kiểm tra xem trong file có chứa khóa của enum hay không
            if (config.containsKey(setting.getKey())) {
                setting.setValue(config.get(setting.getKey()));  // Cập nhật giá trị
                // VD: Map[MONSTER_A_LIVES, 3] -> MONSTER_A_LIVES(3)
            }
        }
    }

}