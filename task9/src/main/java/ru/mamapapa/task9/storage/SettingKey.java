package ru.mamapapa.task9.storage;

public enum SettingKey {
    TEXT_SIZE("textSize"),
    PROGRESS("progress"),
    SWITCH("switch"),
    LIGHT_COLOR("light_color"),
    DARK_COLOR("dark_color");

    private final String key;

    SettingKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
