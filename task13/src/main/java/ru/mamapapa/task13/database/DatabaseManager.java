package ru.mamapapa.task13.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseManager {
    private static final String DATABASE_NAME = "database";

    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public WeatherDatabase getDatabase() {
        return Room.databaseBuilder(context, WeatherDatabase.class, DATABASE_NAME)
                .build();
    }
}
