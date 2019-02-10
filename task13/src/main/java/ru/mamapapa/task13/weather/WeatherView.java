package ru.mamapapa.task13.weather;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.mamapapa.task13.R;
import ru.mamapapa.task13.yandex.dto.Weather;

/**
 * View представление для отображения погоды
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class WeatherView {
    private final Activity activity;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View updateButton;

    public WeatherView(Activity activity) {
        this.activity = activity;
        this.adapter = new RecyclerAdapter();
        initView();
    }

    private void initView() {
        recyclerView = activity.findViewById(R.id.recycler);
        updateButton = activity.findViewById(R.id.button);
        recyclerView.setAdapter(adapter);
    }

    public void setPresenter(WeatherPresenter presenter) {
        adapter.setPresenter(presenter);
        updateButton.setOnClickListener(v -> presenter.onClickRefresh());
    }

    /**
     * Показать погоду
     *
     * @param weather - объект с информацией по погоде
     */
    public void showWeather(Weather weather) {
        adapter.addItems(weather);
    }
}
