package com.example.game;
import java.io.*;
import java.util.*;

public class GameRound {
    private static final String GAME_FILE_PATH = LinkSetting.GAME_ROUND_PATH.getLink(); // Đường dẫn tới file cấu hình

    public static Map<String, Integer> loadConfig(int round) throws Exception {
        Map<String, Integer> config = new HashMap<>();
        config.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(GAME_FILE_PATH + (round) + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Bỏ qua các dòng trống hoặc dòng comment
                if (line.trim().isEmpty() || line.startsWith("//")) {
                    continue;
                }

                String[] parts = line.split("=");
                if (parts.length == 2) {
                    // Lưu vào Map: key = tên thuộc tính, value = giá trị
                    config.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                    //System.out.println(parts[0] + " = " + parts[1]);
                }
            }
        }
        return config;
    }
}
