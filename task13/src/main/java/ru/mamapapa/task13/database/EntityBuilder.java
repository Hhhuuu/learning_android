package ru.mamapapa.task13.database;

import android.support.annotation.NonNull;

import ru.mamapapa.task13.database.entities.WeatherOnCityEntity;
import ru.mamapapa.task13.database.entities.WeatherOnDayEntity;
import ru.mamapapa.task13.database.entities.WeatherOnHourEntity;
import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Hour;
import ru.mamapapa.task13.yandex.dto.Info;
import ru.mamapapa.task13.yandex.dto.Weather;

public class EntityBuilder {
    @NonNull
    public WeatherOnHourEntity buildHourEntity(Long dayId, Hour hour) {
        WeatherOnHourEntity onHourEntity = new WeatherOnHourEntity();
        onHourEntity.setTemp(hour.getTemp());
        onHourEntity.setHour(hour.getHour());
        onHourEntity.setWeatherOnDayId(dayId);
        return onHourEntity;
    }

    @NonNull
    public WeatherOnCityEntity buildCityEntity(Weather weather) {
        WeatherOnCityEntity entity = new WeatherOnCityEntity();
        Info info = weather.getInfo();
        entity.setCity(info.getTzinfo().getName());
        entity.setLastTempNow(weather.getFact().getTemp());
        entity.setLat(info.getLat());
        entity.setLon(info.getLon());
        return entity;
    }

    @NonNull
    public WeatherOnDayEntity buildDayEntity(Long cityId, Forecast forecast) {
        WeatherOnDayEntity onDayEntity = new WeatherOnDayEntity();
        onDayEntity.setDate(forecast.getDate());
        onDayEntity.setTempDay(forecast.getParts().getDayShort().getTemp());
        onDayEntity.setTempNight(forecast.getParts().getNightShort().getTemp());
        onDayEntity.setWeatherOnCityId(cityId);
        return onDayEntity;
    }
}
