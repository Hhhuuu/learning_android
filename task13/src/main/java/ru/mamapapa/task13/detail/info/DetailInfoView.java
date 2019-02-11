package ru.mamapapa.task13.detail.info;

import ru.mamapapa.task13.yandex.dto.Forecast;

/**
 * Представление детальной информации о погоде
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public interface DetailInfoView {

    /**
     * Показать детальную информацию о погоде
     *
     * @param forecast - объект с детальной информацией о погоде
     */
    void showDetailInfo(Forecast forecast);
}
