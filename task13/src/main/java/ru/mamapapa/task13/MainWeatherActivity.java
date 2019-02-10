package ru.mamapapa.task13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import ru.mamapapa.task13.yandex.dto.Weather;

public class MainWeatherActivity extends AppCompatActivity {
    private static final Gson GSON = new Gson();
    public static final String ACTION = "ru.mamapapa.DAY";

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View updateButton;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new RecyclerAdapter();
        adapter.setOnClickCallback(this::startActivity);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        updateButton = findViewById(R.id.button);
        updateButton.setOnClickListener(v -> WeatherIntentService.getWeatherOn7Day(this, "", ""));

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String weather = intent.getStringExtra(WeatherIntentService.EXTRA_PARAM_DAY);
                if (weather != null) {
                    adapter.addItems(fromJson(weather));
                }
            }
        };
    }

    private void startActivity(String date) {
        Intent intent = new Intent(this, DetailInfoActivity.class);
        intent.putExtra(WeatherIntentService.EXTRA_PARAM_DATE, date);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, new IntentFilter(ACTION));
        WeatherIntentService.getWeatherOn7Day(this, "", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }

    private Weather fromJson(String value) {
        return GSON.fromJson(value, Weather.class);
    }
}
