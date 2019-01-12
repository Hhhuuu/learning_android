package ru.mamapapa.task9.storage;

import java.util.List;

import ru.mamapapa.task9.Note;

public interface NoteDataStorage {
    List<Note> getItems();

    Note getItem(String id);

    void addItem(Note note);

    void removeItem(String id);
}
