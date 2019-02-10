package ru.mamapapa.task13.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.mamapapa.task13.database.DatabaseManager;
import ru.mamapapa.task13.detail.info.DetailInfoActivity;
import ru.mamapapa.task13.network.NetworkManager;
import ru.mamapapa.task13.weather.MainWeatherActivity;
import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Weather;


/**
 * Сервис для работы с погодой
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class WeatherIntentService extends IntentService {
    private static final String LOG_TAG = WeatherIntentService.class.getCanonicalName();
    private static final Gson GSON = new Gson();
    private static final NetworkManager NETWORK_MANAGER = new NetworkManager();

    private static final String ACTION_DETAIL_INFO = "ru.mamapapa.task13.action.DETAIL.INFO";
    private static final String ACTION_WEATHER_7_DAY = "ru.mamapapa.task13.action.WEATHER.ON.7.DAY";
    private static final String EXTRA_PARAM_LAT = "ru.mamapapa.task13.extra.LAT";
    private static final String EXTRA_PARAM_LON = "ru.mamapapa.task13.extra.LON";

    public static final String EXTRA_PARAM_DAY = "ru.mamapapa.task13.extra.DAY";
    public static final String EXTRA_PARAM_DATE = "ru.mamapapa.task13.extra.DATE";

    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    public static void getWeatherOn7Day(Context context, String lat, String lon) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_WEATHER_7_DAY);
        intent.putExtra(EXTRA_PARAM_LAT, lat);
        intent.putExtra(EXTRA_PARAM_LON, lon);
        context.startService(intent);
    }

    public static void getDetailInfo(Context context, String date) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_DETAIL_INFO);
        intent.putExtra(EXTRA_PARAM_DATE, date);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WEATHER_7_DAY.equals(action)) {
                final String lat = intent.getStringExtra(EXTRA_PARAM_LAT);
                final String lon = intent.getStringExtra(EXTRA_PARAM_LON);
                handleActionWeather(lat, lon);
            } else if (ACTION_DETAIL_INFO.equals(action)) {
                final String date = intent.getStringExtra(EXTRA_PARAM_DATE);
                handleActionDetailInfo(date);
            }
        }
    }

    private void handleActionWeather(String lat, String lon) {
        try {
            Weather weather = NETWORK_MANAGER.getWeather(lat, lon);
            DatabaseManager manager = new DatabaseManager(getApplicationContext());
            manager.writeToDatabase(weather);
            sendBroadcast(toJson(weather), MainWeatherActivity.ACTION, EXTRA_PARAM_DAY);
        } catch (Exception e) {
            Log.e(LOG_TAG, "", e);
        }
    }

    private void handleActionDetailInfo(String date) {
        try {
//             TODO В данный момент, при некорректных lat и lon будет возвращаться Москва
            Weather weather = NETWORK_MANAGER.getWeather("", "");
            List<Forecast> forecasts = new ArrayList<>();
            for (Forecast item : weather.getForecasts()) {
                if (item.getDate().equals(date)) {
                    forecasts.add(item);
                    String jsonForecast = toJson(forecasts.get(0));
                    sendBroadcast(jsonForecast, DetailInfoActivity.ACTION, EXTRA_PARAM_DATE);
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "", e);
        }
    }

    private void sendBroadcast(String data, String action, String param) {
        Intent intent = new Intent(action);
        intent.putExtra(param, data);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private String toJson(Object o) {
        return GSON.toJson(o);
    }
}
