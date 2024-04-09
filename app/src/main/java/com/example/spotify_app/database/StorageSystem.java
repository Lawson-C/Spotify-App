package com.example.spotify_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.spotify_app.database.database_entries.LocalAccountEntry;
import com.example.spotify_app.database.database_entries.SpotifyAccountEntry;

public class StorageSystem {
    public static final String DATABASE_NAME = "AppData.sqlite";
    public static final int DATABASE_VERSION = 1;

    private static StorageSystem instance;
    private static SpotifyAccountDBHelper spotifyDBHelper;
    private static LocalAccountDBHelper localDBHelper;

    private StorageSystem(@NonNull Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        spotifyDBHelper = new SpotifyAccountDBHelper(context);
        localDBHelper = new LocalAccountDBHelper(context);
    }

    /*
     * StorageSystem must be initialized at startup
     */
    public static StorageSystem init(@NonNull Context context) {
        return instance == null ? new StorageSystem(context) : instance;
    }

    // LocalAccount Create
    public static void writeLocalAccount(int id, String name, String password, int spotifyID) {
        SQLiteDatabase db = localDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LocalAccountEntry.COLUMN_ID, id);
        values.put(LocalAccountEntry.COLUMN_NAME, name);
        values.put(LocalAccountEntry.COLUMN_PASSWORD, password);
        values.put(LocalAccountEntry.COLUMN_SPOTIFY_ID, spotifyID);

        db.insert(LocalAccountEntry.TABLE_NAME, null, values);
    }

    /*
     * Returns a requested variable from a table entry which has a field that matches the given value
     *   @param  requestField  : the name of the field required to match the given value
     *   @param  value         : the value to match
     *   @param  responseField : the name of the variable requested from the database
     */
    public static String readLocalAccountValue(String requestField, String value, String responseField) {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor = db.query(
                LocalAccountEntry.TABLE_NAME,
                null,
                requestField + " = ?",
                new String[]{value},
                null,
                null,
                LocalAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(responseField);
        String returnValue = cursor.getString(index);
        cursor.close();

        return returnValue;
    }

    /*
     * Returns a tuple containing the requested fields and requested values from a table entry where the requestedField matches
     * the given value
     *   @param  requestField  : the name of the field required to match the given value
     *   @param  value         : the value to match
     *   @param  responseField : the names of the variable requested from the database
     */
    public static String[][] readLocalAccountValueArray(String requestField, String value, String[] responseField) {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor = db.query(
                LocalAccountEntry.TABLE_NAME,
                null,
                requestField + " = ? ",
                new String[]{value},
                null,
                null,
                LocalAccountEntry.COLUMN_ID + " DESC"
        );

        String[][] responseValues = new String[responseField.length][];

        for (int i = 0; i < responseValues.length; i++) {
            cursor.moveToNext();
            int index = cursor.getColumnIndex(responseField[i]);
            responseValues[i] = new String[]{responseField[i], cursor.getString(index)};
        }

        cursor.close();

        return responseValues;
    }

    // LocalAccount Retrieve from ID
    public static String readLocalAccountID(int id, String field) {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor = db.query(
                LocalAccountEntry.TABLE_NAME,
                null,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id},
                null,
                null,
                LocalAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(field);
        String value = cursor.getString(index);
        cursor.close();

        return value;
    }

    //  LocalAccount Update
    public static void setIntLocalAccount(int id, String field, int value) {
        SQLiteDatabase db = localDBHelper.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(field, value);

        db.update(
                LocalAccountEntry.TABLE_NAME,
                content,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id}
        );
    }

    // LocalAccount Delete
    public static void deleteLocalAccount(int id) {
        SQLiteDatabase db = localDBHelper.getWritableDatabase();

        db.delete(
                LocalAccountEntry.TABLE_NAME,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id}
        );
    }

    // SpotifyAccount Create
    public static void writeSpotifyAccount(int id, String name, String email, String url, String href, String image_url, int image_width, int image_height, String uri) {
        SQLiteDatabase db = spotifyDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SpotifyAccountEntry.COLUMN_ID, id);
        values.put(SpotifyAccountEntry.COLUMN_DISPLAY_NAME, name);
        values.put(SpotifyAccountEntry.COLUMN_EMAIL, email);
        values.put(SpotifyAccountEntry.COLUMN_SPOTIFY_URL, url);
        values.put(SpotifyAccountEntry.COLUMN_HREF, href);
        values.put(SpotifyAccountEntry.COLUMN_IMAGE_URL, image_url);
        values.put(SpotifyAccountEntry.COLUMN_IMAGE_WIDTH, image_width);
        values.put(SpotifyAccountEntry.COLUMN_IMAGE_HEIGHT, image_height);
        values.put(SpotifyAccountEntry.COLUMN_URI, uri);

        db.insert(SpotifyAccountEntry.TABLE_NAME, null, values);
    }

    // SpotifyAccount Retrieve
    public static String readSpotifyAccount(int id, String field) {
        SQLiteDatabase db = spotifyDBHelper.getReadableDatabase();

        Cursor cursor = db.query(
                SpotifyAccountEntry.TABLE_NAME,
                null,
                SpotifyAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id},
                null,
                null,
                SpotifyAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(field);
        String value = cursor.getString(index);
        cursor.close();

        return value;
    }
}

