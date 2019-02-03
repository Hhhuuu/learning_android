package ru.mamapapa.task12provider.storage;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.mamapapa.task12provider.Note;
import ru.mamapapa.task12provider.database.DatabaseManager;
import ru.mamapapa.task12provider.database.dao.NoteDAO;
import ru.mamapapa.task12provider.database.entites.NoteEntity;

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
    public Cursor getCursorItems() {
        return getNoteTable().getCursorNotes();
    }

    @Override
    public Note getItem(String id){
        return toNote(getNoteTable().getNoteById(id));
    }

    @Override
    public Cursor getCursorItem(String id){
        return getNoteTable().getCursorNoteById(id);
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
