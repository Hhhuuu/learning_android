package ru.mamapapa.task8;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingDataStorage {
    private static final String FILE_NAME = "settings";

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

    private Context context;

    public SettingDataStorage(Context context) {
        this.context = context;
    }

    public void setSetting(SettingKey setting, Object value) {
        SharedPreferences prev = getSharedPreferences();
        SharedPreferences.Editor editor = prev.edit();
        switch (setting) {
            case SWITCH:
                editor.putBoolean(setting.getKey(), (Boolean) value);
                break;
            case PROGRESS:
                editor.putInt(setting.getKey(), (Integer) value);
                break;
            case TEXT_SIZE:
                editor.putFloat(setting.getKey(), (Float) value);
                break;
            case DARK_COLOR:
            case LIGHT_COLOR:
                editor.putString(setting.getKey(), (String) value);
                break;
        }
        editor.apply();
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(SettingKey setting) {
        SharedPreferences prev = getSharedPreferences();
        Object value = null;
        switch (setting) {
            case SWITCH:
                value = prev.getBoolean(setting.getKey(), false);
                break;
            case PROGRESS:
                value = prev.getInt(setting.getKey(), 0);
                break;
            case TEXT_SIZE:
                value = prev.getFloat(setting.getKey(), 0.0f);
                break;
            case DARK_COLOR:
            case LIGHT_COLOR:
                value = prev.getString(setting.getKey(), "");
                break;
        }
        return (T) value;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
