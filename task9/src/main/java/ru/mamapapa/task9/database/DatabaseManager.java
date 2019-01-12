package ru.mamapapa.task9.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseManager {
    private static final String DATABASE_NAME = "database";

    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public NotesDatabase getDatabase() {
        return Room.databaseBuilder(context, NotesDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }
}
