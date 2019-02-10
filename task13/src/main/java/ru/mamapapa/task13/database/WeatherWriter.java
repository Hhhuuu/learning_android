package ru.mamapapa.task13.database;

import android.content.Context;

import java.util.List;

import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Hour;
import ru.mamapapa.task13.yandex.dto.Weather;

public class WeatherWriter {
    private static final EntityBuilder BUILDER = new EntityBuilder();

    public void writeToDatabase(Context context, Weather weather) {
        DatabaseManager manager = new DatabaseManager(context);
        WeatherOnCityEntity onCityEntity = insertCity(weather, manager);
        insertDay(weather.getForecasts(), onCityEntity.getId(), manager);
    }

    private WeatherOnCityEntity insertCity(Weather weather, DatabaseManager manager) {
        WeatherOnCityDao dao = manager.getDatabase().getOnCityDao();
        WeatherOnCityEntity entity = BUILDER.buildCityEntity(weather);
        dao.insert(entity);
        return entity;
    }

    private void insertDay(List<Forecast> forecasts, Long cityId, DatabaseManager manager) {
        WeatherOnDayDao dao = manager.getDatabase().getOnDayDao();
        for (Forecast forecast : forecasts) {
            WeatherOnDayEntity entity = BUILDER.buildDayEntity(cityId, forecast);
            dao.insert(entity);
            insertHour(forecast.getHours(), entity.getId(), manager);
        }
    }

    private void insertHour(List<Hour> hours, Long dayId, DatabaseManager manager) {
        WeatherOnHourDao dao = manager.getDatabase().getOnHourDao();
        for (Hour hour : hours) {
            WeatherOnHourEntity onHourEntity = BUILDER.buildHourEntity(dayId, hour);
            dao.insert(onHourEntity);
        }
    }
}
