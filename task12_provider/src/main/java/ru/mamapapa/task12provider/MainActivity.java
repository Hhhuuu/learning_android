package ru.mamapapa.task12provider;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import ru.mamapapa.task12provider.storage.DataBaseSettingDataStorage;
import ru.mamapapa.task12provider.storage.DatabaseNoteDataStorage;
import ru.mamapapa.task12provider.storage.NoteDataStorage;
import ru.mamapapa.task12provider.storage.SettingDataStorage;
import ru.mamapapa.task12provider.storage.SettingKey;

import static ru.mamapapa.task12provider.EditNoteActivity.ACTION;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteDataStorage noteDataStorage;
    private SettingDataStorage settingDataStorage;
    private RecyclerAdapter adapter;

    private ContentObserver contentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteDataStorage = new DatabaseNoteDataStorage(this);
        settingDataStorage = new DataBaseSettingDataStorage(this);

        recyclerView = findViewById(R.id.recycler);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            String action = EditNoteActivity.Action.ADD.name();
            intent.putExtra(ACTION, action);
            startActivity(intent);
        });

        contentObserver = new CustomContentObserver(new Handler(Looper.getMainLooper()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setTextSize(settingDataStorage.getValue(SettingKey.TEXT_SIZE));
        adapter.addItems(noteDataStorage.getItems());
        adapter.notifyDataSetChanged();
        getContentResolver().registerContentObserver(CustomContentProvider.CONTENT_URI, true, contentObserver);
        Cursor query = getContentResolver().query(CustomContentProvider.CONTENT_URI, null, null, null, null);
        List<Note> notes = ContentConverter.convertCursorToNotes(query);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.clear();
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
