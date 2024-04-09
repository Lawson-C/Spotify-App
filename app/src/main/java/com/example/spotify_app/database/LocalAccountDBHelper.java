package com.example.spotify_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class LocalAccountDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public LocalAccountDBHelper(@Nullable Context context) {
        super(context, StorageSystem.DATABASE_NAME, null, DATABASE_VERSION);
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
}