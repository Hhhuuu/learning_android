package ru.mamapapa.task5;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder bindService) {
            service = (StateIntentService) ((StateIntentService.LocalBinder) bindService).getService();
            textView.setText(service.getState());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private TextView textView;
    private StateIntentService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        textView = findViewById(R.id.textViewOnActivity4);

    }


    @Override
    protected void onResume() {
        super.onResume();
        bindService(StateIntentService.newInstance(this), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }
}
