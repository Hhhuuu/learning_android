package ru.mamapapa.task7;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;
import static ru.mamapapa.task7.CustomBroadcastReceiver.ACTION;
import static ru.mamapapa.task7.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class GeneratedTextIntentService extends IntentService {
    private static final String SERVICE_NAME = "GeneratedTextIntentService";

    public GeneratedTextIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendBroadcast(intent.getStringExtra(BROADCAST_DATA_KEY));
    }

    private void sendBroadcast(String text) {
        Intent broadcastIntent = new Intent(ACTION);
        broadcastIntent.putExtra(BROADCAST_DATA_KEY, text);
        broadcastIntent.addFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(broadcastIntent);
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, GeneratedTextIntentService.class);
    }
}
