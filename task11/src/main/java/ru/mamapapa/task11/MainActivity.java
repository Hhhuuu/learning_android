package ru.mamapapa.task11;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.mamapapa.shared_preference_aidl.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();

    private SharedPreferenceManager sharedPreferenceManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sharedPreferenceManager = SharedPreferenceManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sharedPreferenceManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent("SharedPreferenceManagerService");
        intent.setPackage(SharedPreferenceManager.class.getPackage().getName());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(connection);
    }

    public List<String> getPropertiesKeys() {
        try {
            return sharedPreferenceManager.getPropertiesKeys();
        } catch (RemoteException e) {
            Log.e(LOG_TAG, e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void setProperty(String key, String value) {
        try {
            sharedPreferenceManager.add(key, value);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}
