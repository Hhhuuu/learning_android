package ru.mamapapa.task13;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class DetailInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private DetailInfoBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        adapter = new RecyclerAdapter();

        recyclerView = findViewById(R.id.detail_info_recycler);
        recyclerView.setAdapter(adapter);

        broadcastReceiver = new DetailInfoBroadcastReceiver();
        broadcastReceiver.setCallback(data -> {

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(DetailInfoBroadcastReceiver.ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
