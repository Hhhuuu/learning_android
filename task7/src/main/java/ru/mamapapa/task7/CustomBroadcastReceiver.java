package ru.mamapapa.task7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CustomBroadcastReceiver extends BroadcastReceiver {
    public interface ViewCallback {
        void handle(String data);
    }

    public static final String BROADCAST_DATA_KEY = "broadcastData";
    public static final String ACTION = "ru.mamapapa.SEND_MESSAGE";
    private ViewCallback callback;

    public void setCallback(ViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        callback.handle(intent.getStringExtra(BROADCAST_DATA_KEY));
    }
}
