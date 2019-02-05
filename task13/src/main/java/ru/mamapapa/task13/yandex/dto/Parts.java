package ru.mamapapa.task13.yandex.dto;

import com.google.gson.annotations.SerializedName;

public class Parts {
    @SerializedName("day_short")
    private Short datShort;
    @SerializedName("night_short")
    private Short nightShort;

    public Short getDatShort() {
        return datShort;
    }

    public void setDatShort(Short datShort) {
        this.datShort = datShort;
    }

    public Short getNightShort() {
        return nightShort;
    }

    public void setNightShort(Short nightShort) {
        this.nightShort = nightShort;
    }
}
