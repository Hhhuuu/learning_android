package ru.mamapapa.task13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import ru.mamapapa.task13.yandex.dto.Forecast;

public class DetailInfoBroadcastReceiver extends BroadcastReceiver {
    private static final Gson GSON = new Gson();

    public interface ViewCallback {
        void handle(Forecast data);
    }

    public static final String ACTION = "ru.mamapapa.DETAIL_INFO";

    private ViewCallback callback;

    public void setCallback(ViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String forecast = intent.getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE);
        callback.handle(fromJson(forecast));
    }

    private Forecast fromJson(String value) {
        return GSON.fromJson(value, Forecast.class);
    }
}
