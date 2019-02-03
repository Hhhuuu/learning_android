package ru.mamapapa.task12provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import ru.mamapapa.task12provider.storage.DatabaseNoteDataStorage;
import ru.mamapapa.task12provider.storage.NoteDataStorage;

import static ru.mamapapa.task12provider.CustomContentProvider.Action.all;
import static ru.mamapapa.task12provider.CustomContentProvider.Action.getByOrder;

public class CustomContentProvider extends ContentProvider {
    public enum Action {
        all,
        get,
        create,
        edit,
        remove,
        ;

        public static Action getByOrder(int order){
            Action[] values = Action.values();
            if (0 > order || order > values.length){
                throw new IllegalArgumentException("Invalid order value!");
            }
            return values[order];
        }
    }

    private static final String LOG_TAG = CustomContentProvider.class.getCanonicalName();
    private static final String AUTHORITY = "ru.mamapapa.task12provider";
    private static final String TABLE = "notes";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String ALL = String.format("/%s", TABLE);
    private static final String GET = String.format("/%s/get", TABLE);
    private static final String CREATE = String.format("/%s/create", TABLE);
    private static final String EDIT = String.format("/%s/edit", TABLE);
    private static final String REMOVE = String.format("/%s/remove", TABLE);

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + ALL);
    public static final Uri GET_CONTENT_URI = Uri.parse("content://" + AUTHORITY + GET);
    public static final Uri CREATE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + CREATE);
    public static final Uri EDIT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + EDIT);
    public static final Uri REMOVE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + REMOVE);

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE, all.ordinal());
        URI_MATCHER.addURI(AUTHORITY, GET, Action.get.ordinal());
        URI_MATCHER.addURI(AUTHORITY, CREATE, Action.create.ordinal());
        URI_MATCHER.addURI(AUTHORITY, EDIT, Action.edit.ordinal());
        URI_MATCHER.addURI(AUTHORITY, REMOVE, Action.remove.ordinal());
    }

    private NoteDataStorage dataStorage;

    public CustomContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Action action = getActionFromUri(uri);
        int count = 1;
        switch (action){
            case all:
                List<Note> items = dataStorage.getItems();
                count = items.size();
                items.forEach(item -> dataStorage.removeItem(item.getId()));
                break;
            case remove:
                validateParameters(selection, selectionArgs);
                dataStorage.removeItem(selectionArgs[0]);
                break;
            default:
                throw new IllegalArgumentException("Invalid action type!");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
      return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Action action = getActionFromUri(uri);
        Note note = ContentConverter.convertNoteFromValues(values);
        switch (action){
            case all:
                dataStorage.addItem(note);
                break;
            default:
                throw new IllegalArgumentException("Invalid action type!");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(String.format("%s/create/%s", TABLE, note.getId()));
    }

    @Override
    public boolean onCreate() {
        try {
            dataStorage = new DatabaseNoteDataStorage(getContext());
        }catch (Exception e){
            Log.e(LOG_TAG, "Manager data storage is not created!", e);
        }
        return dataStorage != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (getActionFromUri(uri)){
            case all:
                cursor = dataStorage.getCursorItems();
                break;
            case get:
                validateParameters(selection, selectionArgs);
                cursor = dataStorage.getCursorItem(selectionArgs[0]);
                break;
            default:
                throw new IllegalArgumentException("Invalid action type!");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private Action getActionFromUri(Uri uri) {
        int uriMather = URI_MATCHER.match(uri);
        return getByOrder(uriMather);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Action action = getActionFromUri(uri);
        switch (action){
            case edit:
                dataStorage.addItem(ContentConverter.convertNoteFromValues(values));
                break;
            default:
                throw new IllegalArgumentException("Invalid action type!");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 1;
    }

    private void validateParameters(String selection, String[] selectionArgs) {
        if (!Note.ID_FIELD_NAME.equals(selection)){
            throw new IllegalArgumentException("Invalid parameter selection, need " + Note.ID_FIELD_NAME);
        }
        if (selectionArgs.length != 1){
            throw new IllegalArgumentException("Invalid parameter selectionArgs, need one!");
        }
    }
}
