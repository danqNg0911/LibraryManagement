package com.example.library;

public enum LinkSetting {

    API_URL("AIzaSyCZ9Ee9DuenXY5gvW6IcKh_NhTMnB5U4qM"),

    DATABASE_USER_URL("jdbc:mysql://localhost:3307/useraccount"),
    DATABASE_USER_NAME("root"),
    DATABASE_USER_PASSWORD("bongbibo9"),

    DATABASE_MANAGER_URL("jdbc:mysql://localhost:3307/manageraccount"),
    DATABASE_MANAGER_NAME("root"),
    DATABASE_MANAGER_PASSWORD("bongbibo9"),

    MANAGER_LIST_FILE_PATH("C:\\YEAR 2\\OOP\\JavaFX\\Bai tap lon _ Thu VIen\\data\\ListOfManagers.txt"),

    IMAGE_NULL("file:/C:/YEAR 2/OOP/JavaFX/Bai tap lon _ Thu VIen/src/main/resources/com/example/library/assets/Picture_is_not_available.png"),

    SOUND_CLICK_MOUSE("C:\\YEAR 2\\OOP\\JavaFX\\Bai tap lon _ Thu VIen\\src\\main\\resources\\com\\example\\library\\assets\\mouse-click-153941.mp3");
    private final String str;

    LinkSetting(String str) {
        this.str = str;
    }

    public String getLink() {
        return str;
    }
}
