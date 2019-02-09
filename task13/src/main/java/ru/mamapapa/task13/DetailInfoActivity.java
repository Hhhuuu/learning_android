package ru.mamapapa.task13;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DetailInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DetailInfoAdapter adapter;
    private DetailInfoBroadcastReceiver broadcastReceiver;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        adapter = new DetailInfoAdapter();
        recyclerView = findViewById(R.id.detail_info_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        broadcastReceiver = new DetailInfoBroadcastReceiver();
        broadcastReceiver.setCallback(data -> adapter.addItems(data));

        date = getIntent().getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(DetailInfoBroadcastReceiver.ACTION));
        WeatherIntentService.getDetailInfo(this, date);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
