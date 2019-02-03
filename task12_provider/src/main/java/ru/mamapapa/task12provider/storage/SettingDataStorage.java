package ru.mamapapa.task12provider.storage;

public interface SettingDataStorage {
    void setSetting(SettingKey setting, Object value);

    @SuppressWarnings("unchecked")
    <T> T getValue(SettingKey setting);
}
