package ru.mamapapa.task13.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mamapapa.task13.database.entities.WeatherOnCityEntity;

@Dao
public interface WeatherOnCityDao {
    @Query("select * from weather_on_city where id = :id")
    WeatherOnCityEntity get(Long id);

    @Query("select * from weather_on_city")
    List<WeatherOnCityEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherOnCityEntity entity);

    @Delete
    void delete(WeatherOnCityEntity entity);
}
