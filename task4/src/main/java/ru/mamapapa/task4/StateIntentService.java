package ru.mamapapa.task4;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;
import static ru.mamapapa.task4.CustomBroadcastReceiver.ACTION;
import static ru.mamapapa.task4.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class StateIntentService extends IntentService {
    private static final String SERVICE_NAME = "IntentService";

    public StateIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        StateManager stateManager = changeState();
        sendBroadcast(stateManager);
    }

    @NonNull
    private StateManager changeState() {
        StateManager stateManager = StateManager.getInstance();
        stateManager.setNextState();
        return stateManager;
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
}
