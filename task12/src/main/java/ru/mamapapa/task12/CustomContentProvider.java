package ru.mamapapa.task12;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class CustomContentProvider extends ContentProvider {
    public enum Action {
        all,
        create,
        edit,
        remove,
        ;
    }

    private static final String AUTHORITY = "ru.mamapapa.task12";
    private static final String TABLE = "notes";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE, Action.all.ordinal());
        URI_MATCHER.addURI(AUTHORITY, TABLE + "/create", Action.create.ordinal());
        URI_MATCHER.addURI(AUTHORITY, TABLE + "/edit", Action.edit.ordinal());
        URI_MATCHER.addURI(AUTHORITY, TABLE + "/remove", Action.remove.ordinal());
    }

    public CustomContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
