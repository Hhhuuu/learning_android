package ru.mamapapa.task3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    private static final String LOG_TAG = MyService.class.getCanonicalName();
    public static final String SERVICE_DATA = "data";

    private static final int MODE = Service.START_NOT_STICKY;
    private IBinder binder = new LocalBinder();
    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "Create");
        executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(LOG_TAG, "onStartCommand Start");
        executorService.submit(() -> {
            try {
                String data = intent.getStringExtra(SERVICE_DATA);
                setData(data);
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {
                Log.v(LOG_TAG, e.getMessage());
            }
        });
        Log.v(LOG_TAG, "onStartCommand End");
        return MODE;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        Service getService() {
            return MyService.this;
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MyService.class);
    }

    public void setData(String data){
        Log.v(LOG_TAG, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "Destroy");
    }
}
