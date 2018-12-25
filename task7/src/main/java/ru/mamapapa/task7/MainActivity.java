package ru.mamapapa.task7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

import static ru.mamapapa.task7.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    private String text;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        if (bundle == null) {
            startService();
        }
    }

    private void startService() {
        Intent intent = GeneratedTextIntentService.newInstance(this);
        intent.putExtra(BROADCAST_DATA_KEY, UUID.randomUUID().toString());
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public String getTextFromEditText() {
        return text;
    }

    @Override
    public void setTextFromEditText(String text) {
        this.text = text;
    }
}
