package ru.mamapapa.task4;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;
import static ru.mamapapa.task4.CustomBroadcastReceiver.ACTION;
import static ru.mamapapa.task4.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class MainActivity extends AppCompatActivity {
    private CustomBroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private TextView textView;
    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        intentFilter = new IntentFilter(ACTION);
        broadcastReceiver = new CustomBroadcastReceiver();
        broadcastReceiver.setCallback(data -> textView.setText(data));

        button.setOnClickListener(v -> {
            Intent intent = StateIntentService.newInstance(MainActivity.this);
            startService(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
