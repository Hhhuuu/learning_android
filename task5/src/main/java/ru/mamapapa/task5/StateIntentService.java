package ru.mamapapa.task5;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;
import static ru.mamapapa.task5.CustomBroadcastReceiver.ACTION;
import static ru.mamapapa.task5.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class StateIntentService extends IntentService {
    private static final String SERVICE_NAME = "IntentService";
    private IBinder binder = new LocalBinder();
    public StateIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        StateManager stateManager = StateManager.getInstance();
        String data = intent.getStringExtra(BROADCAST_DATA_KEY);
        if (data != null && !data.isEmpty()){
            stateManager.setNextState();
        }
        sendBroadcast(stateManager);
    }

    private void sendBroadcast(StateManager stateManager) {
        Intent broadcastIntent = new Intent(ACTION);
        broadcastIntent.putExtra(BROADCAST_DATA_KEY, stateManager.getState());
        broadcastIntent.addFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(broadcastIntent);
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, StateIntentService.class);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        Service getService() {
            return StateIntentService.this;
        }
    }

    public String getState(){
        return StateManager.getInstance().getState();
    }
}
