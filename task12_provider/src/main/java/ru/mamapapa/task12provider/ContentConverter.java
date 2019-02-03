package ru.mamapapa.task12provider;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import static ru.mamapapa.task12provider.Note.*;

public class ContentConverter {

    private ContentConverter() {
        throw new UnsupportedOperationException("Constructor is not available!");
    }

    public static Note convertNoteFromValues(ContentValues values) {
        return new Note(values.getAsString(ID_FIELD_NAME), values.getAsString(TITLE_FIELD_NAME), values.getAsString(BODY_FIELD_NAME));
    }

    public static ContentValues convertNoteToValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(ID_FIELD_NAME, note.getId());
        values.put(TITLE_FIELD_NAME, note.getTitle());
        values.put(BODY_FIELD_NAME, note.getBody());
        return values;
    }

    public static List<Note> convertCursorToNotes(Cursor cursor){
        List<Note> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(ID_FIELD_NAME));
            String title = cursor.getString(cursor.getColumnIndex(TITLE_FIELD_NAME));
            String body = cursor.getString(cursor.getColumnIndex(BODY_FIELD_NAME));
            result.add(new Note(id, title, body));
        }
        cursor.close();
        return result;
    }

}
