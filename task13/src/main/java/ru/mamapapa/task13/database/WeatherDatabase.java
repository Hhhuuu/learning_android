package ru.mamapapa.task13.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.mamapapa.task13.database.dao.WeatherOnCityDao;
import ru.mamapapa.task13.database.dao.WeatherOnDayDao;
import ru.mamapapa.task13.database.dao.WeatherOnHourDao;
import ru.mamapapa.task13.database.entities.WeatherOnCityEntity;
import ru.mamapapa.task13.database.entities.WeatherOnDayEntity;
import ru.mamapapa.task13.database.entities.WeatherOnHourEntity;


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
