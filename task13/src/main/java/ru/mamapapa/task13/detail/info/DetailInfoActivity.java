package ru.mamapapa.task13.detail.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.mamapapa.task13.R;
import ru.mamapapa.task13.service.WeatherIntentService;

public class DetailInfoActivity extends AppCompatActivity {
    public static final String ACTION = "ru.mamapapa.DETAIL_INFO";

    private DetailInfoPresenter presenter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        date = getIntent().getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE);

        if (presenter == null) {
            presenter = new DetailInfoPresenter(this, new DetailInfoView(this));
            presenter.onClickRefresh(date);
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