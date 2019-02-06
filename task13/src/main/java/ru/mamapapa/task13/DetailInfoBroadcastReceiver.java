package ru.mamapapa.task13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DetailInfoBroadcastReceiver extends BroadcastReceiver {
    public interface ViewCallback {
        void handle(String data);
    }

    public static final String ACTION = "ru.mamapapa.DETAIL_INFO";

    private ViewCallback callback;

    public void setCallback(ViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        callback.handle(intent.getStringExtra(WeatherIntentService.EXTRA_PARAM_DATE));
    }
}
