package ru.mamapapa.task13.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {
        WeatherOnCityEntity.class,
        WeatherOnDayEntity.class,
        WeatherOnHourEntity.class
}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherOnCityDao getOnCityDao();
    public abstract WeatherOnDayDao getOnDayDao();
    public abstract WeatherOnHourDao getOnHourDao();
}
