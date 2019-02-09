package ru.mamapapa.task13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Weather;

public class WeatherOnDayBroadcastReceiver extends BroadcastReceiver {
    private static final Gson GSON = new Gson();

    public interface ViewCallback {
        void handle(Weather data);
    }

    public static final String ACTION = "ru.mamapapa.DAY";

    private ViewCallback callback;

    public void setCallback(ViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String weather = intent.getStringExtra(WeatherIntentService.EXTRA_PARAM_DAY);
        callback.handle(fromJson(weather));
    }

    private Weather fromJson(String value) {
        return GSON.fromJson(value, Weather.class);
    }
}
