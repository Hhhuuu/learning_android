package ru.mamapapa.task8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class EditNoteActivity extends AppCompatActivity {
    public static final String ACTION = "action";
    public static final String NOTE_ID = "id";

    public enum Action {
        ADD,
        EDIT
    }

    private View add;
    private View save;
    private View remove;
    private EditText title;
    private EditText body;
    private NoteDataStorage noteDataStorage;
    private SettingDataStorage settingDataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = findViewById(R.id.titleEditText);
        body = findViewById(R.id.bodyEditText);

        add = findViewById(R.id.buttonAdd);
        save = findViewById(R.id.buttonSave);
        remove = findViewById(R.id.buttonRemove);

        noteDataStorage = new NoteDataStorage(this);
        settingDataStorage = new SettingDataStorage(this);
    }

    private void setTextSize(TextView textView) {
        Float value = settingDataStorage.getValue(SettingDataStorage.SettingKey.TEXT_SIZE);
        if (value != 0.0f)
            textView.setTextSize(value);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTextSize(title);
        setTextSize(body);
        Intent intent = getIntent();
        String action = intent.getStringExtra(ACTION);
        switch (Action.valueOf(action)) {
            case ADD:
                setVisibility(add);
                add.setOnClickListener(v -> {
                    setData(UUID.randomUUID().toString());
                });
                break;
            case EDIT:
                String id = intent.getStringExtra(NOTE_ID);
                if (id != null && !id.isEmpty()) {
                    setValueNote(id);
                    setVisibility(save);
                    setVisibility(remove);

                    save.setOnClickListener(v -> setData(id));
                    remove.setOnClickListener(v -> {
                        noteDataStorage.removeItem(id);
                        finish();
                    });
                } else {
                    throw new IllegalArgumentException("Отсутсвует идентификатор");
                }
                break;
            default:
                throw new IllegalArgumentException("Неизвестное действие");
        }
    }

    private void setValueNote(String id) {
        Note item = noteDataStorage.getItem(id);
        title.setText(item.getTitle());
        body.setText(item.getBody());
    }

    private void setVisibility(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void setData(String id) {
        String titleText = title.getText().toString();
        String bodyText = body.getText().toString();
        noteDataStorage.addItem(new Note(id, titleText, bodyText));
        finish();
    }
}
