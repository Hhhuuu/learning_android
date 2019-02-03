package ru.mamapapa.task12provider.storage;

import android.content.Context;

import ru.mamapapa.task12provider.database.DatabaseManager;
import ru.mamapapa.task12provider.database.dao.SettingDAO;
import ru.mamapapa.task12provider.database.entites.SettingEntity;

public class DataBaseSettingDataStorage implements SettingDataStorage {
    private DatabaseManager databaseManager;

    public DataBaseSettingDataStorage(Context context) {
        this.databaseManager = new DatabaseManager(context);
    }

    @Override
    public void setSetting(SettingKey setting, Object value) {
        getSettingTable().insert(new SettingEntity(setting.toString(), value.toString()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(SettingKey setting) {
        SettingEntity entity = getSettingTable().getSettingById(setting.toString());
        Object value = null;
        switch (setting) {
            case SWITCH:
                value = entity != null ? Boolean.valueOf(entity.getValue()) : false;
                break;
            case PROGRESS:
                value = entity != null ? Integer.valueOf(entity.getValue()) : 0;
                break;
            case TEXT_SIZE:
                value = entity != null ? Float.valueOf(entity.getValue()) : 0.0f;
                break;
            case DARK_COLOR:
            case LIGHT_COLOR:
                value = entity != null ? entity.getValue() : "";
                break;
        }
        return (T) value;
    }

    private SettingDAO getSettingTable() {
        return databaseManager.getDatabase().getSetting();
    }
}
