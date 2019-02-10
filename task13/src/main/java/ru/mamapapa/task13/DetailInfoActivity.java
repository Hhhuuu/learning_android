package ru.mamapapa.task13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import ru.mamapapa.task13.yandex.dto.Forecast;

public class DetailInfoActivity extends AppCompatActivity {
    public static final String ACTION = "ru.mamapapa.DETAIL_INFO";
    private static final Gson GSON = new Gson();

    private RecyclerView recyclerView;
    private DetailInfoAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        adapter = new DetailInfoAdapter();
        recyclerView = findViewById(R.id.detail_info_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String forecast = intent.getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE);
                if (forecast != null) {
                    adapter.addItems(fromJson(forecast));
                }
            }
        };
        date = getIntent().getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, new IntentFilter(ACTION));
        WeatherIntentService.getDetailInfo(this, date);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }

    private Forecast fromJson(String value) {
        return GSON.fromJson(value, Forecast.class);
    }
}
