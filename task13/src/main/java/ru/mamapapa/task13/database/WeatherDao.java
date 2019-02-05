package ru.mamapapa.task13.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("select * from weather_info where id = :id")
    WeatherEntity get(Long id);

    @Query("select * from weather_info")
    List<WeatherEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity entity);

    @Delete
    void delete(WeatherEntity entity);
}
