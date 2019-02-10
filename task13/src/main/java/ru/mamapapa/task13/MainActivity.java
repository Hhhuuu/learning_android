package ru.mamapapa.task13;

import android.content.Context;
import android.content.Intent;
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
        adapter.setOnClickCallback(data -> startActivity(this, data));
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        updateButton = findViewById(R.id.button);
        updateButton.setOnClickListener(v -> WeatherIntentService.getWeatherOn7Day(this, "", ""));

        broadcastReceiver = new WeatherOnDayBroadcastReceiver();
        broadcastReceiver.setCallback(data -> adapter.addItems(data));
    }

    private void startActivity(Context context, String date) {
        Intent intent = new Intent(context, DetailInfoActivity.class);
        intent.putExtra(WeatherIntentService.EXTRA_PARAM_DATE, date);
        context.startActivity(intent);
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
