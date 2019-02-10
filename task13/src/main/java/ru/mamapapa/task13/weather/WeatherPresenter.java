package ru.mamapapa.task13.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.google.gson.Gson;

import ru.mamapapa.task13.ItemViewPresenter;
import ru.mamapapa.task13.service.WeatherIntentService;
import ru.mamapapa.task13.WeatherItem;
import ru.mamapapa.task13.WeatherModel;
import ru.mamapapa.task13.detail.info.DetailInfoActivity;
import ru.mamapapa.task13.yandex.dto.Weather;

import static ru.mamapapa.task13.weather.MainWeatherActivity.ACTION;

/**
 * Презентер для работы с погодой
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class WeatherPresenter implements ItemViewPresenter {
    private static final Gson GSON = new Gson();
    private final Context context;
    private final WeatherView view;
    private WeatherModel model;
    private BroadcastReceiver broadcastReceiver;


    public WeatherPresenter(Context context, WeatherView view) {
        this.context = context;
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setPresenter(this);
        model = new WeatherModel(context);
    }

    /**
     * Переход на детальную информацию о погоде
     *
     * @param item - объект с информацией
     */
    @Override
    public void onClickOnItem(WeatherItem item) {
        startActivity(item.getMainText());
    }

    /**
     * Обновление погоды при нажатии на кнопку
     */
    public void onClickRefresh() {
        model.getWeatherOnDay();
    }

    /**
     * Скрытие блока с температурой
     *
     * @param item - объект с информацией
     * @return если {@link WeatherItem#getTempFirst() == null}, то скрываем блок
     */
    @Override
    public int visibilityFirstTemp(WeatherItem item) {
        return item.getTempFirst() == null ? View.INVISIBLE : View.VISIBLE;
    }

    private void startActivity(String date) {
        Intent intent = new Intent(context, DetailInfoActivity.class);
        intent.putExtra(WeatherIntentService.EXTRA_PARAM_DATE, date);
        context.startActivity(intent);
    }

    /**
     * Подписка на изменения погоды
     */
    public void registerReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String weather = intent.getStringExtra(WeatherIntentService.EXTRA_PARAM_DAY);
                if (weather != null) {
                    view.showWeather(fromJson(weather));
                }
            }
        };
        getBroadcastManager().
                registerReceiver(broadcastReceiver, new IntentFilter(ACTION));
    }

    /**
     * Отписка от изменений погоды
     */
    public void unregisterReceiver() {
        getBroadcastManager().unregisterReceiver(broadcastReceiver);
    }

    private LocalBroadcastManager getBroadcastManager() {
        return LocalBroadcastManager.getInstance(context);
    }

    private Weather fromJson(String value) {
        return GSON.fromJson(value, Weather.class);
    }
}
