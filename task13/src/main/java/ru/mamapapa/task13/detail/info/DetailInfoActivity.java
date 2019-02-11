package ru.mamapapa.task13.detail.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.mamapapa.task13.R;
import ru.mamapapa.task13.service.WeatherIntentService;
import ru.mamapapa.task13.yandex.dto.Forecast;

public class DetailInfoActivity extends AppCompatActivity implements DetailInfoView {
    public static final String ACTION = "ru.mamapapa.DETAIL_INFO";

    private DetailInfoAdapter adapter;
    private RecyclerView recyclerView;
    private DetailInfoPresenter presenter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        date = getIntent().getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE);

        if (presenter == null) {
            presenter = new DetailInfoPresenter(this)
                    .attachView(this);
            presenter.onClickRefresh(date);
        }

        initView();
    }

    private void initView() {
        adapter = new DetailInfoAdapter();
        adapter.setPresenter(presenter);

        recyclerView = findViewById(R.id.detail_info_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    /**
     * Показать детальную информацию о погоде
     *
     * @param forecast - объект с детальной информацией о погоде
     */
    public void showDetailInfo(Forecast forecast) {
        adapter.addItems(forecast);
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