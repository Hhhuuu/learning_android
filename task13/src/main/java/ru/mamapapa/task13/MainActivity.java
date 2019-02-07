package ru.mamapapa.task13;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Weather;

public class MainActivity extends AppCompatActivity {
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View updateButton;
    private WeatherOnDayCallback callback;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            WeatherIntentService service = (WeatherIntentService) ((WeatherIntentService.LocalBinder) binder).getService();
            service.setCallback(callback);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            callback = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new RecyclerAdapter();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        updateButton = findViewById(R.id.button);
        updateButton.setOnClickListener(v -> WeatherIntentService.getWeatherOn7Day(this, "", ""));
        callback = weather -> updateAdapter(weather);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(WeatherIntentService.intentForBind(this), serviceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }

    private void updateAdapter(Weather weather) {
        runOnUiThread(() -> adapter.addItems(weather));
    }
}
