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

    SOUND_CLICK_MOUSE("D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\mouse-click-153941.mp3"),
    MAIN_SOUND_1("D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\mainSound1.mp3"),
    MAIN_SOUND_2("D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\mainSound2.mp3"),
    MAIN_SOUND_3("D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\mainSound3.mp3"),
    MAIN_SOUND_4("D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\mainSound4.mp3"),
    MAIN_SOUND_5("D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\mainSound5.mp3"),

    AVATAR_1("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava1.gif"),
    AVATAR_2("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava2.gif"),
    AVATAR_3("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava3.gif"),
    AVATAR_4("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava4.gif"),
    AVATAR_5("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava5.gif"),
    AVATAR_6("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava6.gif"),
    AVATAR_7("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava7.gif"),
    AVATAR_8("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava8.gif"),
    AVATAR_9("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava9.gif"),
    AVATAR_0("file:/D:\\LibraryManagement\\LibraryManagement\\src\\main\\resources\\com\\example\\library\\assets\\ava0.gif");

    private final String str;

    LinkSetting(String str) {
        this.str = str;
    }

    public String getLink() {
        return str;
    }
}
