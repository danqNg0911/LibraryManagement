package com.example.game;

public enum Base {

    TIME(60), // Thời gian 1 ván đấu (đơn vị giây)

    // Sức mạnh quái vật
    MONSTER_A_LIVES(3), // Sinh mạng quái vật
    MONSTER_A_SPAWN_NUMBER(3), // Sô quái vật spawn ra
    MONSTER_A_WIDTH(60), // Kích thước chiều rộng
    MONSTER_A_HEIGHT(60), // Kích thước chiều dài

    MONSTER_A_MOVE_SPEED(10), // Tốc độ di chuyển
    MONSTER_A_BULLET_SPEED(10), // Tốc độ đạn
    MONSTER_A_BULLET_WIDTH(20), // Kích thước chiều rộng đạn
    MONSTER_A_BULLET_HEIGHT(20), // Kích thước chiều dài đạn
    MONSTER_A_BULLET_TIME(2), // Thời gian ngừng bắn đạn sau mỗi lượt (đơn vị giây)
    MONSTER_A_NUMBER_OF_BULLET_PER_TURN(3), // Số đạn bắng trong mỗi lượt
    MONSTER_A_DAMAGE(2), // Sát thương gây ra

    MONSTER_A_HIT_MUSIC(1), // Âm thanh gây sát thương số ... trong file sound
    MONSTER_A_DIE_MUSIC(1), // Âm thanh khi chết số ... trong file sound


    MONSTER_B_LIVES(3),
    MONSTER_B_SPAWN_NUMBER(1),
    MONSTER_B_WIDTH(60),
    MONSTER_B_HEIGHT(40),

    MONSTER_B_MOVE_SPEED(5),
    MONSTER_B_BULLET_SPEED(10),
    MONSTER_B_BULLET_WIDTH(20),
    MONSTER_B_BULLET_HEIGHT(20),
    MONSTER_B_BULLET_TIME(3),
    MONSTER_B_NUMBER_OF_BULLET_PER_TURN(1),
    MONSTER_B_DAMAGE(2),

    MONSTER_B_HIT_MUSIC(1),
    MONSTER_B_DIE_MUSIC(1),


    MONSTER_C_LIVES(3),
    MONSTER_C_SPAWN_NUMBER(1),
    MONSTER_C_WIDTH(50),
    MONSTER_C_HEIGHT(50),

    MONSTER_C_MOVE_SPEED(5),
    MONSTER_C_BULLET_SPEED(5),
    MONSTER_C_BULLET_WIDTH(20),
    MONSTER_C_BULLET_HEIGHT(20),
    MONSTER_C_BULLET_TIME(3),
    MONSTER_C_NUMBER_OF_BULLET_PER_TURN(1),
    MONSTER_C_DAMAGE(2),

    MONSTER_C_HIT_MUSIC(1),
    MONSTER_C_DIE_MUSIC(1),


    MONSTER_D_LIVES(5),
    MONSTER_D_SPAWN_NUMBER(3),
    MONSTER_D_WIDTH(60),
    MONSTER_D_HEIGHT(60),

    MONSTER_D_MOVE_SPEED(5),
    MONSTER_D_BULLET_SPEED(0), // Cận chiến nên không cần đạn
    MONSTER_D_BULLET_WIDTH(0), // Cận chiến nên không cần đạn
    MONSTER_D_BULLET_HEIGHT(0), // Cận chiến nên không cần đạn
    MONSTER_D_TELEPORT_TIME(10), // Thời gian nghỉ giữa mỗi lần dịch chuyển tức thời (đơn vị giây)
    MONSTER_D_NUMBER_OF_HIT_PER_TURN(2), // Sô lần đánh trong 1 lần dịch chuyển tức thời
    MONSTER_D_DAMAGE(1),

    MONSTER_D_HIT_MUSIC(1),
    MONSTER_D_DIE_MUSIC(1),


    // Thời gian spawn quái vật (đơn vị giây)
    MONSTER_A_SPAWN_TIME(3),
    MONSTER_B_SPAWN_TIME(3),
    MONSTER_C_SPAWN_TIME(2),
    MONSTER_D_SPAWN_TIME(5),


    // Người chơi
    PLAYER_WIDTH(65), // Chiều rộng  người chơi
    PLAYER_HEIGHT(65), // Chiều dài người chơi
    PLAYER_LIVES(10), // Sinh mạng người chơi
    PLAYER_SET_PAUSE_TIME(2), // Thời gian bất tử của người chơi sau khi restart game ( cần lưu ý MONSTER_?_SPAWN_TIME) (đơn vị giây)
    PlAYER_HIT_TIME(50), // Thời gian chém kiếm (đơn vị ms)
    PLAYER_HIT_MUSIC(1), // Âm thanh khi đánh số ... trong file sound
    PLAYER_IS_HIT_MUSIC(1), // Âm thanh khi bị đánh số ... trong file sound
    PLAYER_DIE_MUSIC(1), // Âm thanh khi chết số ... trong file sound

    // Ảnh nền
    BACKGROUND_IMAGE(1), // Ảnh nền thứ i trong asset
    BACKGROUND_SPEED(1), // Tốc độ ảnh nền

    BACKGROUND_MUSIC(2); // Bản nhạc nền số ... trong file sound


    private final int info;

    Base(int info) {
        this.info = info;
    }

    public int getInfo() {
        return info;
    }

}
