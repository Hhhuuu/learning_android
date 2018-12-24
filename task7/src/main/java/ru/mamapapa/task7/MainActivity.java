package ru.mamapapa.task7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

import static ru.mamapapa.task7.CustomBroadcastReceiver.BROADCAST_DATA_KEY;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {

    private static final String FIRST_FRAGMENT = "firstFragment";
    private static final String SECOND_FRAGMENT = "secondFragment";

    private String text;
    private Fragment firstFragment;
    private Fragment secondFragment;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        if (bundle == null) {
            addingFragment();
            startService();
        }
    }

    private void addingFragment() {
        FragmentManager manager = getSupportFragmentManager();
        firstFragment = FirstBlankFragment.newInstance();
        secondFragment = SecondBlankFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.fragment_container, firstFragment, FIRST_FRAGMENT)
                .add(R.id.fragment_container, secondFragment, SECOND_FRAGMENT)
                .commit();
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
