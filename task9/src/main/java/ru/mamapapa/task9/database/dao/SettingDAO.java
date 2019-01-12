package ru.mamapapa.task9.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import ru.mamapapa.task9.database.entites.SettingEntity;

@Dao
public interface SettingDAO {
    @Query("select * from settings where id = :id")
    SettingEntity getSettingById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SettingEntity entity);

    @Delete
    void delete(SettingEntity entity);

}
