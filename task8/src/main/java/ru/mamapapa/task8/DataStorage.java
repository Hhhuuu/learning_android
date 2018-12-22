package ru.mamapapa.task8;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStorage {
    private static final String FILE_NAME = "notes";
    private static final Gson GSON = new Gson();
    private Context context;

    public DataStorage(Context context) {
        this.context = context;
    }

    public List<Note> getItems(){
        SharedPreferences prev = getSharedPreferences();
        Map<String, String> items = (Map<String, String>) prev.getAll();
        List<Note> notes = new ArrayList<>();
        items.forEach((key, value)-> notes.add(fromJson(value)));
        return notes;
    }

    public Note getItem(String id){
        SharedPreferences prev = getSharedPreferences();
        return fromJson(prev.getString(id, null));
    }

    public void addItem(Note note){
        SharedPreferences prev = getSharedPreferences();
        SharedPreferences.Editor editor = prev.edit();
        editor.putString(note.getId(), toJson(note));
        editor.apply();
    }

    public void removeItem(String id){
        SharedPreferences prev = getSharedPreferences();
        SharedPreferences.Editor editor = prev.edit();
        editor.remove(id);
        editor.apply();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private String toJson(Object o) {
        return GSON.toJson(o);
    }

    private Note fromJson(String value) {
        return GSON.fromJson(value, Note.class);
    }
}
