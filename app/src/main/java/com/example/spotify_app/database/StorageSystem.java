package com.example.spotify_app.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.spotify_app.loginScreen.LoginActivity;

import java.util.Locale;

public class StorageSystem {
    public static final String DATABASE_NAME = "/data/AppData.db";

    private static StorageSystem instance;
    private static SpotifyAccountDBHelper spotifyDBHelper;
    private static LocalAccountDBHelper localDBHelper;

    private StorageSystem(@Nullable Context context) {
        spotifyDBHelper = new SpotifyAccountDBHelper(context);
        localDBHelper = new LocalAccountDBHelper(context);
    }

    /*
    * StorageSystem must be initialized at startup
     */
    public static StorageSystem init(Context context) {
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

    private static class LocalAccountDBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;

        public LocalAccountDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + LocalAccountEntry.TABLE_NAME + " (" +
                            LocalAccountEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                            LocalAccountEntry.COLUMN_NAME + " TEXT," +
                            LocalAccountEntry.COLUMN_PASSWORD + " TEXT," +
                            LocalAccountEntry.COLUMN_SPOTIFY_ID + " INTEGER)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + LocalAccountEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    public static class LocalAccountEntry {
        // DO NOT instantiate
        private LocalAccountEntry() {
        }

        public static final String TABLE_NAME = "local_account";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_SPOTIFY_ID = "spotify_id";
    }

    private static class SpotifyAccountDBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;

        public SpotifyAccountDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + SpotifyAccountEntry.TABLE_NAME + " (" +
                            SpotifyAccountEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                            SpotifyAccountEntry.COLUMN_DISPLAY_NAME + " TEXT," +
                            SpotifyAccountEntry.COLUMN_EMAIL + " TEXT," +
                            SpotifyAccountEntry.COLUMN_SPOTIFY_URL + " TEXT," +
                            SpotifyAccountEntry.COLUMN_HREF + " TEXT," +
                            SpotifyAccountEntry.COLUMN_IMAGE_URL + " TEXT," +
                            SpotifyAccountEntry.COLUMN_IMAGE_WIDTH + " INTEGER," +
                            SpotifyAccountEntry.COLUMN_IMAGE_HEIGHT + " INTEGER," +
                            SpotifyAccountEntry.COLUMN_URI + " TEXT)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SpotifyAccountEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    public static class SpotifyAccountEntry {
        // DO NOT instantiate
        private SpotifyAccountEntry() {
        }

        public static final String TABLE_NAME = "spotify_account";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DISPLAY_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_SPOTIFY_URL = "url";
        public static final String COLUMN_HREF = "href";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_IMAGE_WIDTH = "image_width";
        public static final String COLUMN_IMAGE_HEIGHT = "image_height";
        public static final String COLUMN_URI = "uri";
    }
}
