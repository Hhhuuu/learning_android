package ru.mamapapa.task4;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class StateIntentService extends IntentService {
    private static final String SERVICE_NAME = "IntentService";

    public StateIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        StateManager stateManager = StateManager.getInstance();
        stateManager.setState(intent.getDataString());
    }
}
