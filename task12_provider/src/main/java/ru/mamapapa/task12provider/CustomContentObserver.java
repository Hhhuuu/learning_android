package ru.mamapapa.task12provider;

import android.database.ContentObserver;
import android.os.Handler;

public class CustomContentObserver extends ContentObserver {
    public CustomContentObserver(Handler handler) {
        super(handler);
    }
}
