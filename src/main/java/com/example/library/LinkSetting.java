package com.example.library;

public enum LinkSetting {

    API_URL("AIzaSyCZ9Ee9DuenXY5gvW6IcKh_NhTMnB5U4qM"),

    DATABASE_USER_URL("jdbc:mysql://localhost:3306/librarymanagement"),
    DATABASE_USER_NAME("root"),
    DATABASE_USER_PASSWORD("Hieu@123456"),

    DATABASE_MANAGER_URL("jdbc:mysql://localhost:3306/librarymanagement"),
    DATABASE_MANAGER_NAME("root"),
    DATABASE_MANAGER_PASSWORD("Hieu@123456"),

    MANAGER_LIST_FILE_PATH("D:\\LibraryManagement\\LibraryManagement\\data\\ListOfManagers.txt"),

    IMAGE_NULL("file:/D:\\LibraryManagement\\LibraryManagement/src/main/resources/com/example/library/assets/Picture_is_not_available.png"),

    SOUND_CLICK_MOUSE("D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\mouse-click-153941.mp3");
    private final String str;

    LinkSetting(String str) {
        this.str = str;
    }

    public String getLink() {
        return str;
    }
}
