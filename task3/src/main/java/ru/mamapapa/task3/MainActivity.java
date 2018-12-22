package ru.mamapapa.task3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static ru.mamapapa.task3.MyService.SERVICE_DATA;

public class MainActivity extends Activity {
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private View startToActivity;

    private View startService;
    private View stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startToActivity = findViewById(R.id.startToActivity);
        startService = findViewById(R.id.startEmptyService);
        stopService = findViewById(R.id.stopEmptyService);

        startToActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        });

        startService.setOnClickListener(v -> startService());

        stopService.setOnClickListener(v -> stopService(MyService.newIntent(MainActivity.this)));
    }

    private void startService() {
        try {
            for (int i = 0; i < 100; i++) {
                String value = "data " + System.currentTimeMillis();
                Intent intent = MyService.newIntent(MainActivity.this);
                intent.putExtra(SERVICE_DATA, value);
                Log.v(LOG_TAG, "Send data " + value);
                startService(intent);
            }
        } catch (Exception e) {
            Log.v(LOG_TAG, e.getMessage());
        }
    }
}
