package ru.mamapapa.task13.yandex.dto;

import com.google.gson.annotations.SerializedName;

public class Parts {
    @SerializedName("day_short")
    private Short dayShort;
    @SerializedName("night_short")
    private Short nightShort;

    public Short getDayShort() {
        return dayShort;
    }

    public void setDayShort(Short dayShort) {
        this.dayShort = dayShort;
    }

    public Short getNightShort() {
        return nightShort;
    }

    public void setNightShort(Short nightShort) {
        this.nightShort = nightShort;
    }
}
