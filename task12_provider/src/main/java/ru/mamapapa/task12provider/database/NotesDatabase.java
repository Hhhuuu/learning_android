package ru.mamapapa.task12provider.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import ru.mamapapa.task12provider.database.dao.NoteDAO;
import ru.mamapapa.task12provider.database.dao.SettingDAO;
import ru.mamapapa.task12provider.database.entites.NoteEntity;
import ru.mamapapa.task12provider.database.entites.SettingEntity;

@Database(entities = {NoteEntity.class, SettingEntity.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDAO getNote();
    public abstract SettingDAO getSetting();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
