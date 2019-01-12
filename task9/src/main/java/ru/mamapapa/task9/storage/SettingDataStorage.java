package ru.mamapapa.task9.storage;

public interface SettingDataStorage {
    void setSetting(SettingKey setting, Object value);

    @SuppressWarnings("unchecked")
    <T> T getValue(SettingKey setting);
}
