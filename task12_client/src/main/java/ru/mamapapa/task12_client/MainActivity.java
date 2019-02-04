package ru.mamapapa.task12_client;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String AUTHORITY = "ru.mamapapa.task12provider";
    private static final String TABLE = "notes";
    private static final String ALL = String.format("/%s", TABLE);
    private static final String GET = String.format("/%s/get", TABLE);
    private static final String EDIT = String.format("/%s/edit", TABLE);
    private static final String REMOVE = String.format("/%s/remove", TABLE);

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + ALL);
    public static final Uri GET_CONTENT_URI = Uri.parse("content://" + AUTHORITY + GET);
    public static final Uri EDIT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + EDIT);
    public static final Uri REMOVE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + REMOVE);

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private View createButton;
    private View editButton;
    private View removeButton;

    private EditText id;
    private EditText title;
    private EditText body;

    private CallbackNote callbackNote;
    private ContentObserver contentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentObserver = new CustomContentObserver(new Handler(Looper.getMainLooper()));

        adapter = new RecyclerAdapter();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        id = findViewById(R.id.idEditText);
        title = findViewById(R.id.titleEditText);
        body = findViewById(R.id.bodyEditText);

        createButton = findViewById(R.id.createButton);
        editButton = findViewById(R.id.editButton);
        removeButton = findViewById(R.id.removeButton);

        createButton.setOnClickListener(v -> {
            getContentResolver().insert(CONTENT_URI, getValues(true));
        });

        editButton.setOnClickListener(v -> {
            getContentResolver().update(EDIT_CONTENT_URI, getValues(false), null, null);
        });

        removeButton.setOnClickListener(v -> {
            getContentResolver().delete(REMOVE_CONTENT_URI, Note.ID_FIELD_NAME, new String[]{id.getText().toString()});
        });

        callbackNote = note -> {
            Cursor cursor = getContentResolver().query(GET_CONTENT_URI, null, Note.ID_FIELD_NAME, new String[]{note.getId()}, null, null);
            Note result = ContentConverter.convertCursorToNotes(cursor).get(0);
            id.setText(result.getId());
            title.setText(result.getTitle());
            body.setText(result.getBody());
        };

        adapter.setCallbackNote(callbackNote);
    }

    private ContentValues getValues(boolean isNew) {
        return ContentConverter.convertNoteToValues(new Note(isNew ? UUID.randomUUID().toString(): id.getText().toString(), title.getText().toString(), body.getText().toString()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(CONTENT_URI, true, contentObserver);
        updateAdapter();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(contentObserver);
        adapter.clear();
        adapter.setCallbackNote(null);
    }

    class CustomContentObserver extends ContentObserver {
        CustomContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            updateAdapter();
        }
    }

    private void updateAdapter() {
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        adapter.addItems(ContentConverter.convertCursorToNotes(cursor));
        adapter.notifyDataSetChanged();
    }
}
