package ru.mamapapa.lesson2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import static android.content.Intent.ACTION_BATTERY_CHANGED;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int maxCharge = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);
            int currentCharge = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            float percent = currentCharge / (float)maxCharge;

            int drawableLevel = (int)(10000 * percent);

            batteryView.getDrawable().setLevel(drawableLevel);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private ImageView batteryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryView = findViewById(R.id.batteryView);
    }
}
