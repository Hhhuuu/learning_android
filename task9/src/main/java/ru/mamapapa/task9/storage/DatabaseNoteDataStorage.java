package ru.mamapapa.task9.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.mamapapa.task9.Note;
import ru.mamapapa.task9.database.DatabaseManager;
import ru.mamapapa.task9.database.dao.NoteDAO;
import ru.mamapapa.task9.database.entites.NoteEntity;

public class DatabaseNoteDataStorage implements NoteDataStorage {
    private DatabaseManager databaseManager;

    public DatabaseNoteDataStorage(Context context) {
        this.databaseManager = new DatabaseManager(context);
    }

    @Override
    public List<Note> getItems(){
        List<NoteEntity> noteEntities = getNoteTable().getNotes();
        List<Note> notes = new ArrayList<>();
        noteEntities.forEach(item -> notes.add(toNote(item)));
        return notes;
    }

    @Override
    public Note getItem(String id){
        return toNote(getNoteTable().getNoteById(id));
    }

    @Override
    public void addItem(Note note){
        getNoteTable().insert(fromNote(note));
    }

    @Override
    public void removeItem(String id){
        getNoteTable().delete(new NoteEntity(id, null, null));
    }

    private NoteDAO getNoteTable() {
        return databaseManager.getDatabase().getNote();
    }

    private NoteEntity fromNote(Note note) {
        return new NoteEntity(note.getId(), note.getTitle(), note.getBody());
    }

    private Note toNote(NoteEntity entity) {
        return new Note(entity.getId(), entity.getTitle(), entity.getBody());
    }
}
