package ru.mamapapa.task13;

/**
 * Интерфейс презентера для элемента {@link android.support.v7.widget.RecyclerView}
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public interface ItemViewPresenter {
    /**
     * Скрытие блока с температурой
     *
     * @param item - объект с информацией
     * @return одно из состояний View
     */
    int visibilityFirstTemp(WeatherItem item);

    /**
     * Действия при нажатии на элемент с погодой
     *
     * @param item - объект с информацией
     */
    void onClickOnItem(WeatherItem item);
}
