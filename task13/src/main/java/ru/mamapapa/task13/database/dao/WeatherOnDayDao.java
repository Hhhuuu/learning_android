package ru.mamapapa.task13.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mamapapa.task13.database.entities.WeatherOnDayEntity;

@Dao
public interface WeatherOnDayDao {
    @Query("select * from weather_on_day where id = :id")
    WeatherOnDayEntity get(Long id);

    @Query("select * from weather_on_day where weather_on_city_id = :weatherOnDayId")
    WeatherOnDayEntity getByCityId(Long weatherOnDayId);

    @Query("select * from weather_on_day")
    List<WeatherOnDayEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherOnDayEntity entity);

    @Delete
    void delete(WeatherOnDayEntity entity);
}
