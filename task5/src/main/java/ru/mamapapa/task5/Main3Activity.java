package ru.mamapapa.task5;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static ru.mamapapa.task5.CustomBroadcastReceiver.ACTION;
import static ru.mamapapa.task5.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class Main3Activity extends AppCompatActivity {

    private CustomBroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    private View changeState;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textView = findViewById(R.id.textViewOnActivity3);
        changeState = findViewById(R.id.changeState);

        intentFilter = new IntentFilter(ACTION);
        broadcastReceiver = new CustomBroadcastReceiver();
        broadcastReceiver.setCallback(data -> textView.setText(data));

        changeState.setOnClickListener(v -> startService(true));
    }

    private void startService(boolean isNeedData) {
        Intent intent = StateIntentService.newInstance(Main3Activity.this);
        if (isNeedData) {
            intent.putExtra(BROADCAST_DATA_KEY, ACTION);
        }
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter, null, null);
        startService(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
