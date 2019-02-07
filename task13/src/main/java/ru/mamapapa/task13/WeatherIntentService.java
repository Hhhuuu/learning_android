package ru.mamapapa.task13;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Weather;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;


public class WeatherIntentService extends IntentService {
    public class LocalBinder extends Binder {
        Service getService() {
            return WeatherIntentService.this;
        }
    }

    private static final String LOG_TAG = WeatherIntentService.class.getCanonicalName();
    private static final Gson GSON = new Gson();
    private static final String BASE_URL = "https://api.weather.yandex.ru/";

    private static final String ACTION_DEFAULT = "ru.mamapapa.task13.action.DEFAULT";
    private static final String ACTION_DETAIL_INFO = "ru.mamapapa.task13.action.DETAIL.INFO";
    private static final String ACTION_WEATHER_7_DAY = "ru.mamapapa.task13.action.WEATHER.ON.7.DAY";

    public static final String EXTRA_PARAM_DATE = "ru.mamapapa.task13.extra.DATE";
    private static final String EXTRA_PARAM_LAT = "ru.mamapapa.task13.extra.LAT";
    private static final String EXTRA_PARAM_LON = "ru.mamapapa.task13.extra.LON";

    private IBinder binder = new LocalBinder();
    private WeatherOnDayCallback callback;
    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    public static Intent intentForBind(Context context) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_DEFAULT);
        return intent;
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
    public IBinder onBind(Intent intent) {
        return binder;
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
            Weather weather = getWeather(lat, lon);
            callback.setWeather(weather);
        } catch (Exception e) {
            Log.e(LOG_TAG, "", e);
        }
    }

    public void setCallback(WeatherOnDayCallback callback) {
        this.callback = callback;
    }

    private void handleActionDetailInfo(String date) {
        try {
            Weather weather = getWeather("", "");
            List<Forecast> forecasts = weather.getForecasts().stream().filter(item -> item.getDate().equals(date)).collect(Collectors.toList());
            if (forecasts.size() == 1){
                String jsonForecast = toJson(forecasts.get(0));
                sendBroadcast(jsonForecast);
            }

        }catch (Exception e){
            Log.e(LOG_TAG, "", e);
        }
    }

    private void sendBroadcast(String data) {
        Intent intent = new Intent(DetailInfoBroadcastReceiver.ACTION);
        intent.putExtra(EXTRA_PARAM_DATE, data);
        sendBroadcast(intent);
    }

    private String toJson(Object o) {
        return GSON.toJson(o);
    }

    private Weather getWeather(String lat, String lon) throws IOException {
        WeatherApi weatherApi = getRetrofit().create(WeatherApi.class);
        Call<Weather> weatherCall = weatherApi.getWeather(lat, lon);
        Response<Weather> response = weatherCall.execute();
        if (!response.isSuccessful())
            throw new IOException("Сервер погоды не доступен!");
        return response.body();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
