package ru.mamapapa.task3;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import static ru.mamapapa.task3.MyService.SERVICE_DATA;

public class MainActivity extends Activity {
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private View startToActivity;

    private View startEmptyService;
    private View stopEmptyService;

    private MyService emptyService;
    private Intent intentEmptyService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            emptyService = (MyService) ((MyService.LocalBinder) service).getService();
            Log.v(LOG_TAG, "connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v(LOG_TAG, "disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startToActivity = findViewById(R.id.startToActivity);
        startEmptyService = findViewById(R.id.startEmptyService);
        stopEmptyService = findViewById(R.id.stopEmptyService);

        startToActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        });

        startEmptyService.setOnClickListener(v -> {
            startService();
        });

        stopEmptyService.setOnClickListener(v -> {
            stopService(MyService.newIntent(MainActivity.this));
        });
    }

    private void startService() {
        try {
            for (int i = 0; i < 5; i++) {
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

    @Override
    protected void onResume() {
        super.onResume();
        bindService(MyService.newIntent(MainActivity.this), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }
}
