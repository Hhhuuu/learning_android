package ru.mamapapa.task13.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "weather_on_hour")
public class WeatherOnHourEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String hour;
    private String temp;
    @ColumnInfo(name = "weather_on_day_id")
    private Long weatherOnDayId;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Long getWeatherOnDayId() {
        return weatherOnDayId;
    }

    public void setWeatherOnDayId(Long weatherOnDayId) {
        this.weatherOnDayId = weatherOnDayId;
    }
}
