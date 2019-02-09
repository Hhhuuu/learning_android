package ru.mamapapa.task13.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "weather_on_city")
public class WeatherOnCityEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String city;
    private String lastTempNow;
    private String lat;
    private String lon;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastTempNow() {
        return lastTempNow;
    }

    public void setLastTempNow(String lastTempNow) {
        this.lastTempNow = lastTempNow;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
