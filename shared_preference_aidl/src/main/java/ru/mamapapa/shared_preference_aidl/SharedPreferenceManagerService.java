package ru.mamapapa.shared_preference_aidl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceManagerService extends Service {
    private static final String FILE_NAME = "settings";

    public SharedPreferenceManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new SharedPreferenceManager.Stub() {
            @Override
            public void add(String key, String value) throws RemoteException {
                SharedPreferences prev = getSharedPreferences();
                SharedPreferences.Editor editor = prev.edit();
                editor.putString(key, value);
                editor.apply();
            }

            @Override
            public List<String> getPropertiesKeys() throws RemoteException {
                SharedPreferences prev = getSharedPreferences();
                return new ArrayList<>(prev.getAll().keySet());
            }
        };
    }


    private SharedPreferences getSharedPreferences() {
        return getBaseContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}
