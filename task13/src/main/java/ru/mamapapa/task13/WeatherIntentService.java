package ru.mamapapa.task13;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mamapapa.task13.database.DatabaseManager;
import ru.mamapapa.task13.database.WeatherDatabase;
import ru.mamapapa.task13.database.WeatherOnCityDao;
import ru.mamapapa.task13.database.WeatherOnCityEntity;
import ru.mamapapa.task13.database.WeatherOnDayDao;
import ru.mamapapa.task13.database.WeatherOnDayEntity;
import ru.mamapapa.task13.database.WeatherOnHourDao;
import ru.mamapapa.task13.database.WeatherOnHourEntity;
import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Hour;
import ru.mamapapa.task13.yandex.dto.Info;
import ru.mamapapa.task13.yandex.dto.Weather;


public class WeatherIntentService extends IntentService {
    private static final String LOG_TAG = WeatherIntentService.class.getCanonicalName();
    private static final Gson GSON = new Gson();
    private static final String BASE_URL = "https://api.weather.yandex.ru/";

    private static final String ACTION_DETAIL_INFO = "ru.mamapapa.task13.action.DETAIL.INFO";
    private static final String ACTION_WEATHER_7_DAY = "ru.mamapapa.task13.action.WEATHER.ON.7.DAY";

    public static final String EXTRA_PARAM_DAY = "ru.mamapapa.task13.extra.DAY";
    public static final String EXTRA_PARAM_DATE = "ru.mamapapa.task13.extra.DATE";
    private static final String EXTRA_PARAM_LAT = "ru.mamapapa.task13.extra.LAT";
    private static final String EXTRA_PARAM_LON = "ru.mamapapa.task13.extra.LON";

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
            Weather weather = getWeather(lat, lon);
            writeToDatabase(weather);
            String jsonWeather = toJson(weather);
            sendBroadcast(jsonWeather, WeatherOnDayBroadcastReceiver.ACTION, EXTRA_PARAM_DAY);
        } catch (Exception e) {
            Log.e(LOG_TAG, "", e);
        }
    }

    private void writeToDatabase(Weather weather) {
        DatabaseManager manager = new DatabaseManager(getApplicationContext());
        WeatherOnCityEntity onCityEntity = insertCity(weather, manager);
        insertDay(weather.getForecasts(), onCityEntity.getId(), manager);
    }

    private void insertDay(List<Forecast> forecasts, Long cityId, DatabaseManager manager) {
        WeatherOnDayDao dao = manager.getDatabase().getOnDayDao();
        for (Forecast forecast : forecasts) {
            WeatherOnDayEntity entity = buildDayEntity(cityId, forecast);
            dao.insert(entity);
            insertHour(forecast.getHours(), entity.getId(), manager);
        }
    }

    @NonNull
    private WeatherOnDayEntity buildDayEntity(Long cityId, Forecast forecast) {
        WeatherOnDayEntity onDayEntity = new WeatherOnDayEntity();
        onDayEntity.setDate(forecast.getDate());
        onDayEntity.setTempDay(forecast.getParts().getDayShort().getTemp());
        onDayEntity.setTempNight(forecast.getParts().getNightShort().getTemp());
        onDayEntity.setWeatherOnCityId(cityId);
        return onDayEntity;
    }

    @NonNull
    private void insertHour(List<Hour> hours, Long dayId, DatabaseManager manager) {
        WeatherOnHourDao dao = manager.getDatabase().getOnHourDao();
        for (Hour hour : hours) {
            WeatherOnHourEntity onHourEntity = buildHourEntity(dayId, hour);
            dao.insert(onHourEntity);
        }
    }

    @NonNull
    private WeatherOnHourEntity buildHourEntity(Long dayId, Hour hour) {
        WeatherOnHourEntity onHourEntity = new WeatherOnHourEntity();
        onHourEntity.setTemp(hour.getTemp());
        onHourEntity.setHour(hour.getHour());
        onHourEntity.setWeatherOnDayId(dayId);
        return onHourEntity;
    }

    @NonNull
    private WeatherOnCityEntity insertCity(Weather weather, DatabaseManager manager) {
        WeatherOnCityDao dao = manager.getDatabase().getOnCityDao();
        WeatherOnCityEntity entity = buildCityEntity(weather);
        dao.insert(entity);
        return entity;
    }

    @NonNull
    private WeatherOnCityEntity buildCityEntity(Weather weather) {
        WeatherOnCityEntity entity = new WeatherOnCityEntity();
        Info info = weather.getInfo();
        entity.setCity(info.getTzinfo().getName());
        entity.setLastTempNow(weather.getFact().getTemp());
        entity.setLat(info.getLat());
        entity.setLon(info.getLon());
        return entity;
    }

    private void handleActionDetailInfo(String date) {
        try {
//             TODO В данный момент, при некорректных lat и lon будет возвращаться Москва
            Weather weather = getWeather("", "");
            List<Forecast> forecasts = weather.getForecasts().stream().filter(item -> item.getDate().equals(date)).collect(Collectors.toList());
            if (forecasts.size() == 1) {
                String jsonForecast = toJson(forecasts.get(0));
                sendBroadcast(jsonForecast, DetailInfoBroadcastReceiver.ACTION, EXTRA_PARAM_DATE);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "", e);
        }
    }

    private void sendBroadcast(String data, String action, String param) {
        Intent intent = new Intent(action);
        intent.putExtra(param, data);
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
