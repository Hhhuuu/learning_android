package ru.mamapapa.task13.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.mamapapa.task13.R;
import ru.mamapapa.task13.yandex.dto.Weather;

public class MainWeatherActivity extends AppCompatActivity implements WeatherView {
    public static final String ACTION = "ru.mamapapa.DAY";
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View updateButton;
    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (presenter == null) {
            presenter = new WeatherPresenter(this)
                    .attachView(this);
        }

        initView();
    }

    private void initView() {
        adapter = new RecyclerAdapter();
        adapter.setPresenter(presenter);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        updateButton = findViewById(R.id.button);
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

    @Override
    protected void onResume() {
        super.onResume();
        presenter.registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregisterReceiver();
        presenter.deAttachView();
    }
}
