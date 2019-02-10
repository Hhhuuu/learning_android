package ru.mamapapa.task13.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.mamapapa.task13.R;

public class MainWeatherActivity extends AppCompatActivity {
    public static final String ACTION = "ru.mamapapa.DAY";
    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (presenter == null) {
            presenter = new WeatherPresenter(this, new WeatherView(this));
        }
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
    }
}
