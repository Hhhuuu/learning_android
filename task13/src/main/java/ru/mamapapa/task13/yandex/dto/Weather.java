package ru.mamapapa.task13.yandex.dto;

import java.util.ArrayList;
import java.util.List;

public class Weather {
    private Info info;
    private Fact fact;
    private List<Forecast> forecasts;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public List<Forecast> getForecasts() {
        if (forecasts == null){
            forecasts = new ArrayList<>();
        }
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
