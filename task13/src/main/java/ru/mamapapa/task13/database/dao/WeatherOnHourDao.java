package ru.mamapapa.task13.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mamapapa.task13.database.entities.WeatherOnHourEntity;

@Dao
public interface WeatherOnHourDao {
    @Query("select * from weather_on_hour where id = :id")
    WeatherOnHourEntity get(Long id);

    @Query("select * from weather_on_hour where weather_on_day_id = :weatherOnDayId")
    WeatherOnHourEntity getByDayId(Long weatherOnDayId);

    @Query("select * from weather_on_hour")
    List<WeatherOnHourEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherOnHourEntity entity);

    @Delete
    void delete(WeatherOnHourEntity entity);
}
