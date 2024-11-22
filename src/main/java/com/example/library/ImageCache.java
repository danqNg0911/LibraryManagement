package com.example.library;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    // Lưu trữ hình ảnh đã tải
    private static final Map<String, Image> imageCache = new HashMap<>();

    // Hàm lấy hình ảnh từ cache hoặc tạo mới nếu chưa có
    public static Image getImage(String imagePath) {
        // Kiểm tra nếu hình ảnh đã có trong cache
        if (imageCache.containsKey(imagePath)) {
            return imageCache.get(imagePath);
        } else {
            // Nếu chưa có, tạo mới và lưu vào cache
            Image image = new Image(imagePath);
            imageCache.put(imagePath, image);
            return image;
        }
    }
}

