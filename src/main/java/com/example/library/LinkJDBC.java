package com.example.library;

public interface LinkJDBC {
    String databaseUserURL = LinkSetting.DATABASE_USER_URL.getLink();
    String databaseUser = LinkSetting.DATABASE_USER_NAME.getLink();
    String databaseUserPassword = LinkSetting.DATABASE_USER_PASSWORD.getLink();

    String databaseManagerURL = LinkSetting.DATABASE_MANAGER_URL.getLink();
    String databaseManager = LinkSetting.DATABASE_MANAGER_NAME.getLink();
    String databaseManagerPassword = LinkSetting.DATABASE_MANAGER_PASSWORD.getLink();
}
