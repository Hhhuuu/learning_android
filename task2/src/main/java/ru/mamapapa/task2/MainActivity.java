package ru.mamapapa.task2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String STATE_COUNTER = "counter";

    private Button edit;
    private Button reset;
    private TextView textCounter;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.editButton);
        reset = findViewById(R.id.resetButton);
        textCounter = findViewById(R.id.textCounter);

        edit.setOnClickListener(v -> {
            counter++;
            updateCounter();
        });

        reset.setOnClickListener(v -> {
            counter = 0;
            updateCounter();
        });

        if (savedInstanceState != null){
            counter = savedInstanceState.getInt(STATE_COUNTER, 0);
        }
        updateCounter();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(STATE_COUNTER, counter);
    }

    public void updateCounter() {
        textCounter.setText(Integer.toString(counter));
        reset.setVisibility(counter == 0 ? View.INVISIBLE : View.VISIBLE);
    }
}
