package ru.mamapapa.task13;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View updateButton;
    private WeatherOnDayBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new RecyclerAdapter();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        updateButton = findViewById(R.id.button);
        updateButton.setOnClickListener(v -> WeatherIntentService.getWeatherOn7Day(this, "", ""));

        broadcastReceiver = new WeatherOnDayBroadcastReceiver();
        broadcastReceiver.setCallback(data -> adapter.addItems(data));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(WeatherOnDayBroadcastReceiver.ACTION));
        WeatherIntentService.getWeatherOn7Day(this, "", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
