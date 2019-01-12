package ru.mamapapa.task9.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mamapapa.task9.database.entites.NoteEntity;

@Dao
public interface NoteDAO {
    @Query("select * from notes where id = :id")
    NoteEntity getNoteById(String id);

    @Query("select * from notes")
    List<NoteEntity> getNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity entity);

    @Delete
    void delete(NoteEntity entity);
}
