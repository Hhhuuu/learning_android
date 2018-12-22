package ru.mamapapa.task5;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static ru.mamapapa.task5.CustomBroadcastReceiver.ACTION;
import static ru.mamapapa.task5.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class MainActivity extends AppCompatActivity {

    private CustomBroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    private View changeState;
    private View buttonTo3Activity;
    private View buttonTo2Activity;
    private View buttonTo4Activity;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewOnActivity1);

        changeState = findViewById(R.id.changeState);
        buttonTo3Activity = findViewById(R.id.buttonStartToActivity3);
        buttonTo2Activity = findViewById(R.id.buttonStartToActivity2);
        buttonTo4Activity = findViewById(R.id.buttonStartToActivity4);

        intentFilter = new IntentFilter(ACTION);
        broadcastReceiver = new CustomBroadcastReceiver();
        broadcastReceiver.setCallback(data -> textView.setText(data));

        changeState.setOnClickListener(v -> startService(true));

        buttonTo3Activity.setOnClickListener(v -> startActivity(Main3Activity.class));

        buttonTo2Activity.setOnClickListener(v -> startActivity(Main2Activity.class));

        buttonTo4Activity.setOnClickListener(v -> startActivity(Main4Activity.class));
    }

    private void startActivity(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

    private void startService(boolean isNeedData) {
        Intent intent = StateIntentService.newInstance(MainActivity.this);
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
