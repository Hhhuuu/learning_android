package ru.mamapapa.task3;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class Main2Activity extends Activity {
    private static final String LOG_TAG = Main2Activity.class.getCanonicalName();

    private ServiceConnection serviceConnection= new ServiceConnection() {
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

    private MyService emptyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService();
    }

    public void bindService() {
        bindService(MyService.newIntent(Main2Activity.this), serviceConnection, BIND_AUTO_CREATE);
    }

    public void unbindService(){
        unbindService(serviceConnection);
    }
}
