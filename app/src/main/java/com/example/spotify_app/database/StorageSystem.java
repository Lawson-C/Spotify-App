package com.example.spotify_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StorageSystem {
    private static StorageSystem instance;
    private static SQLiteOpenHelper dbHelper;

    private StorageSystem() {
        dbHelper = new AccountDBHelper(null);
    }

    public StorageSystem getInstance() {
        return instance == null ? new StorageSystem() : instance;
    }

    public void writeAccountDB(int id, String name, String email, String url, String href, String image_url, int image_width, int image_height, String uri) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AccountEntry.COLUMN_ID, id);
        values.put(AccountEntry.COLUMN_DISPLAY_NAME, name);
        values.put(AccountEntry.COLUMN_EMAIL, email);
        values.put(AccountEntry.COLUMN_SPOTIFY_URL, url);
        values.put(AccountEntry.COLUMN_HREF, href);
        values.put(AccountEntry.COLUMN_IMAGE_URL, image_url);
        values.put(AccountEntry.COLUMN_IMAGE_WIDTH, image_width);
        values.put(AccountEntry.COLUMN_IMAGE_HEIGHT, image_height);
        values.put(AccountEntry.COLUMN_URI, uri);

        db.insert(AccountEntry.TABLE_NAME, null, values);
    }

    public String readAccountDB(int id, String field) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                AccountEntry.TABLE_NAME,
                null,
                AccountEntry.COLUMN_ID + " = ?",
                new String[] { "" + id },
                null,
                null,
                AccountEntry.COLUMN_ID + " DESC"
                );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(field);
        String value = cursor.getString(index);
        cursor.close();

        return value;
    }

    private static class AccountDBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 0;
        public static final String DATABASE_NAME = "AppData.db";

        public AccountDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "CREATE TABLE " + AccountEntry.TABLE_NAME + " (" +
                            AccountEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                            AccountEntry.COLUMN_DISPLAY_NAME + " TEXT," +
                            AccountEntry.COLUMN_EMAIL + " TEXT," +
                            AccountEntry.COLUMN_SPOTIFY_URL + " TEXT," +
                            AccountEntry.COLUMN_HREF + " TEXT," +
                            AccountEntry.COLUMN_IMAGE_URL + " TEXT," +
                            AccountEntry.COLUMN_IMAGE_WIDTH + " INTEGER," +
                            AccountEntry.COLUMN_IMAGE_HEIGHT + " INTEGER," +
                            AccountEntry.COLUMN_URI + " TEXT)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + AccountEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    private static class AccountEntry {
        public static final String COLUMN_ID = "id";
        public static final String TABLE_NAME = "account";
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
