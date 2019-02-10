package ru.mamapapa.task13;

import android.content.Context;

import ru.mamapapa.task13.service.WeatherIntentService;

/**
 * Модель для отображения погоды
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class WeatherModel {
    private final Context context;

    public WeatherModel(Context context) {
        this.context = context;
    }

    /**
     * Запрос погоды на несколько дней
     */
    public void getWeatherOnDay() {
        WeatherIntentService.getWeatherOn7Day(context, "", "");
    }

    /**
     * Получение детельной информации о погоде (по часам)
     *
     * @param date - дата за которую необходимо получить детальную информацию
     */
    public void getWeatherOnHour(String date) {
        WeatherIntentService.getDetailInfo(context, date);
    }
}
