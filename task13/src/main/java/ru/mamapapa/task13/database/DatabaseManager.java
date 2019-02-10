package ru.mamapapa.task13.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ru.mamapapa.task13.database.dao.WeatherOnCityDao;
import ru.mamapapa.task13.database.dao.WeatherOnDayDao;
import ru.mamapapa.task13.database.dao.WeatherOnHourDao;
import ru.mamapapa.task13.database.entities.WeatherOnCityEntity;
import ru.mamapapa.task13.database.entities.WeatherOnDayEntity;
import ru.mamapapa.task13.database.entities.WeatherOnHourEntity;
import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Hour;
import ru.mamapapa.task13.yandex.dto.Weather;

public class DatabaseManager {
    private static final String DATABASE_NAME = "database";
    private static final EntityBuilder BUILDER = new EntityBuilder();

    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public void writeToDatabase(Weather weather) {
        WeatherDatabase database = getDatabase();
        WeatherOnCityEntity onCityEntity = insertCity(weather, database);
        insertDay(weather.getForecasts(), onCityEntity.getId(), database);
    }

    private WeatherOnCityEntity insertCity(Weather weather, WeatherDatabase database) {
        WeatherOnCityDao dao = database.getOnCityDao();
        WeatherOnCityEntity entity = BUILDER.buildCityEntity(weather);
        dao.insert(entity);
        return entity;
    }

    private void insertDay(List<Forecast> forecasts, Long cityId, WeatherDatabase database) {
        WeatherOnDayDao dao = database.getOnDayDao();
        for (Forecast forecast : forecasts) {
            WeatherOnDayEntity entity = BUILDER.buildDayEntity(cityId, forecast);
            dao.insert(entity);
            insertHour(forecast.getHours(), entity.getId(), database);
        }
    }

    private void insertHour(List<Hour> hours, Long dayId, WeatherDatabase database) {
        WeatherOnHourDao dao = database.getOnHourDao();
        for (Hour hour : hours) {
            WeatherOnHourEntity onHourEntity = BUILDER.buildHourEntity(dayId, hour);
            dao.insert(onHourEntity);
        }
    }

    private WeatherDatabase getDatabase() {
        return Room.databaseBuilder(context, WeatherDatabase.class, DATABASE_NAME)
                .build();
    }
}
