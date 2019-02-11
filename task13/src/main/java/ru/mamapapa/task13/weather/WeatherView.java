package ru.mamapapa.task13.weather;

import ru.mamapapa.task13.yandex.dto.Weather;

/**
 * View представление для отображения погоды
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public interface WeatherView {

    /**
     * Показать погоду
     *
     * @param weather - объект с информацией по погоде
     */
    void showWeather(Weather weather);
}
