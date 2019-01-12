package ru.mamapapa.task9;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ru.mamapapa.task9.storage.DataBaseSettingDataStorage;
import ru.mamapapa.task9.storage.DatabaseNoteDataStorage;
import ru.mamapapa.task9.storage.NoteDataStorage;
import ru.mamapapa.task9.storage.SettingDataStorage;
import ru.mamapapa.task9.storage.SettingKey;

import static ru.mamapapa.task9.EditNoteActivity.ACTION;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteDataStorage noteDataStorage;
    private SettingDataStorage settingDataStorage;
    private RecyclerAdapter adapter;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setTextSize(settingDataStorage.getValue(SettingKey.TEXT_SIZE));
        adapter.addItems(noteDataStorage.getItems());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.clear();
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
