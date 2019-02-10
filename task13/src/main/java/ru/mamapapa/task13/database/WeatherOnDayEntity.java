package ru.mamapapa.task13.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "weather_on_day")
public class WeatherOnDayEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String date;
    @ColumnInfo(name = "temp_day")
    private String tempDay;
    @ColumnInfo(name = "temp_night")
    private String tempNight;
    @ColumnInfo(name = "weather_on_city_id")
    private Long weatherOnCityId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTempDay() {
        return tempDay;
    }

    public void setTempDay(String tempDay) {
        this.tempDay = tempDay;
    }

    public String getTempNight() {
        return tempNight;
    }

    public void setTempNight(String tempNight) {
        this.tempNight = tempNight;
    }

    public Long getWeatherOnCityId() {
        return weatherOnCityId;
    }

    public void setWeatherOnCityId(Long weatherOnCityId) {
        this.weatherOnCityId = weatherOnCityId;
    }
}
