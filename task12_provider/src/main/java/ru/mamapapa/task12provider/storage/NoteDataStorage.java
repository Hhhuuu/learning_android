package ru.mamapapa.task12provider.storage;

import android.database.Cursor;

import java.util.List;

import ru.mamapapa.task12provider.Note;

public interface NoteDataStorage {
    List<Note> getItems();
    Cursor getCursorItems();

    Note getItem(String id);
    Cursor getCursorItem(String id);

    void addItem(Note note);

    void removeItem(String id);
}
