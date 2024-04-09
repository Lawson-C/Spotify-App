package com.example.ourspotifyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StorageSystem {
    public static final String DATABASE_NAME = "AppData.db";

    public static StorageSystem instance;
    private static SpotifyAccountDBHelper spotifyDBHelper;
    private static LocalAccountDBHelper localDBHelper;

    private StorageSystem() {
        spotifyDBHelper = new SpotifyAccountDBHelper(null);
        localDBHelper = new LocalAccountDBHelper(null);
    }

    public StorageSystem(Context context) {
        spotifyDBHelper = new SpotifyAccountDBHelper(context);
        localDBHelper = new LocalAccountDBHelper(context);
    }

    public static StorageSystem getInstance() {
        return instance == null ? new StorageSystem() : instance;
    }

    // LocalAccount Create
    public void writeLocalAccount(int id, String name, String password, int spotifyID) {
        SQLiteDatabase db = localDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LocalAccountDBHelper.LocalAccountEntry.COLUMN_ID, id);
        values.put(LocalAccountDBHelper.LocalAccountEntry.COLUMN_NAME, name);
        values.put(LocalAccountDBHelper.LocalAccountEntry.COLUMN_PASSWORD, password);
        values.put(LocalAccountDBHelper.LocalAccountEntry.COLUMN_SPOTIFY_ID, spotifyID);

        db.insert(LocalAccountDBHelper.LocalAccountEntry.TABLE_NAME, null, values);
    }

    // LocalAccount Retrieve
    public String readLocalAccount(int id, String field) {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        Cursor cursor = db.query(
                LocalAccountDBHelper.LocalAccountEntry.TABLE_NAME,
                null,
                LocalAccountDBHelper.LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id},
                null,
                null,
                LocalAccountDBHelper.LocalAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(field);
        String value = cursor.getString(index);
        cursor.close();

        return value;
    }

    // LocalAccount Retrieve (by user & password)
    public String readLocalAccountByUserPass(String user, String pass, String field) {
        SQLiteDatabase db = localDBHelper.getReadableDatabase();

        String userQuery = LocalAccountDBHelper.LocalAccountEntry.COLUMN_NAME + " = ?";
        String passQuery = LocalAccountDBHelper.LocalAccountEntry.COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.query(
                LocalAccountDBHelper.LocalAccountEntry.TABLE_NAME,
                null,
                userQuery + " AND " + passQuery,
                new String[]{user, pass},
                null,
                null,
                LocalAccountDBHelper.LocalAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(field);
        String value = "";
        try {
            value = cursor.getString(index);
        } catch (CursorIndexOutOfBoundsException c) {
            return null;
        }
        cursor.close();

        return value;
    }

    //  LocalAccount Update
    public void setIntLocalAccount(int id, String field, int value) {
        SQLiteDatabase db = localDBHelper.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(field, value);

        db.update(
                LocalAccountDBHelper.LocalAccountEntry.TABLE_NAME,
                content,
                LocalAccountDBHelper.LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id}
        );
    }

    // LocalAccount Delete
    public void deleteLocalAccount(int id) {
        SQLiteDatabase db = localDBHelper.getWritableDatabase();

        db.delete(
                LocalAccountDBHelper.LocalAccountEntry.TABLE_NAME,
                LocalAccountDBHelper.LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id}
        );
    }

    // SpotifyAccount Create
    public void writeSpotifyAccount(int id, String name, String email, String url, String href, String image_url, int image_width, int image_height, String uri) {
        SQLiteDatabase db = spotifyDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_ID, id);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_DISPLAY_NAME, name);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_EMAIL, email);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_SPOTIFY_URL, url);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_HREF, href);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_IMAGE_URL, image_url);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_IMAGE_WIDTH, image_width);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_IMAGE_HEIGHT, image_height);
        values.put(SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_URI, uri);

        db.insert(SpotifyAccountDBHelper.SpotifyAccountEntry.TABLE_NAME, null, values);
    }

    // SpotifyAccount Retrieve
    public String readSpotifyAccount(int id, String field) {
        SQLiteDatabase db = spotifyDBHelper.getReadableDatabase();

        Cursor cursor = db.query(
                SpotifyAccountDBHelper.SpotifyAccountEntry.TABLE_NAME,
                null,
                SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id},
                null,
                null,
                SpotifyAccountDBHelper.SpotifyAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(field);
        String value = cursor.getString(index);
        cursor.close();

        return value;
    }
}
