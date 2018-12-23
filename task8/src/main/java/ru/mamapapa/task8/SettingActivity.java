package ru.mamapapa.task8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView exampleTextView;
    private int globalProgress;
    private TextView textSize;
    private Switch switchView;
    private SettingDataStorage settingDataStorage;
    private DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingDataStorage = new SettingDataStorage(this);

        switchView = findViewById(R.id.switchInversion);
        exampleTextView = findViewById(R.id.exampleTextView);
        textSize = findViewById(R.id.textView);

        seekBar = findViewById(R.id.seekBar);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float currentTextSize = exampleTextView.getTextSize();
                int progress = seekBar.getProgress();
                if (globalProgress != progress) {
                    float size;
                    if (globalProgress < progress) {
                        size = currentTextSize / metrics.scaledDensity + (progress - globalProgress);
                    } else {
                        size = currentTextSize / metrics.scaledDensity - (globalProgress - progress);
                    }
                    setTextSize(size);
                }
                globalProgress = progress;
            }
        });
    }

    private void setTextSize(float size) {
        exampleTextView.setTextSize(size);
        textSize.setTextSize(size);
        switchView.setTextSize(size);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Integer progress = settingDataStorage.getValue(SettingDataStorage.SettingKey.PROGRESS);
        seekBar.setProgress(progress);
        globalProgress = seekBar.getProgress();

        Float textSize = settingDataStorage.getValue(SettingDataStorage.SettingKey.TEXT_SIZE);
        if (textSize != 0.0f)
            setTextSize(textSize);

        Boolean isChecked = settingDataStorage.getValue(SettingDataStorage.SettingKey.SWITCH);
        switchView.setChecked(isChecked.booleanValue());
    }

    @Override
    protected void onPause() {
        super.onPause();
        settingDataStorage.setSetting(SettingDataStorage.SettingKey.TEXT_SIZE, exampleTextView.getTextSize() / metrics.scaledDensity );
        settingDataStorage.setSetting(SettingDataStorage.SettingKey.PROGRESS, seekBar.getProgress());
        settingDataStorage.setSetting(SettingDataStorage.SettingKey.SWITCH, switchView.isChecked());

    }
}
