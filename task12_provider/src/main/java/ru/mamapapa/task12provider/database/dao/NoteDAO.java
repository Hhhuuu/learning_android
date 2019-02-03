package ru.mamapapa.task12provider.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

import ru.mamapapa.task12provider.database.entites.NoteEntity;

@Dao
public interface NoteDAO {
    @Query("select * from notes where id = :id")
    NoteEntity getNoteById(String id);

    @Query("select * from notes where id = :id")
    Cursor getCursorNoteById(String id);

    @Query("select * from notes")
    List<NoteEntity> getNotes();

    @Query("select * from notes")
    Cursor getCursorNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity entity);

    @Delete
    void delete(NoteEntity entity);
}
