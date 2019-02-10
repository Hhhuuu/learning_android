package ru.mamapapa.task13.detail.info;

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
import ru.mamapapa.task13.yandex.dto.Forecast;

import static ru.mamapapa.task13.detail.info.DetailInfoActivity.ACTION;

/**
 * Презентер для работы с детальной информацией о погоде
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class DetailInfoPresenter implements ItemViewPresenter {
    private static final Gson GSON = new Gson();
    private final Context context;
    private final DetailInfoView view;
    private WeatherModel model;
    private BroadcastReceiver broadcastReceiver;


    public DetailInfoPresenter(Context context, DetailInfoView view) {
        this.context = context;
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setPresenter(this);
        model = new WeatherModel(context);
    }

    /**
     * Обновление погоды при нажатии на кнопку
     */
    public void onClickRefresh(String date) {
        model.getWeatherOnHour(date);
    }

    /**
     * Скрытие блока с температурой
     *
     * @param item - объект погоды
     * @return если {@link WeatherItem#getTempFirst() == null}, то скрываем блок
     */
    @Override
    public int visibilityFirstTemp(WeatherItem item) {
        return View.GONE;
    }

    @Override
    public void onClickOnItem(WeatherItem item) {

    }

    /**
     * Подписка на изменения погоды
     */
    public void registerReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String weather = intent.getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE);
                if (weather != null) {
                    view.showDetailInfo(fromJson(weather));
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

    private Forecast fromJson(String value) {
        return GSON.fromJson(value, Forecast.class);
    }
}
