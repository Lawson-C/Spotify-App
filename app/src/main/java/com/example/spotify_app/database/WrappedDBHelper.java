package com.example.spotify_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class WrappedDBHelper extends SQLiteOpenHelper {

    public WrappedDBHelper(@Nullable Context context) {
        super(context, StorageSystem.DATABASE_NAME, null, StorageSystem.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + WrappedArtistEntry.TABLE_NAME + " (" +
                        WrappedArtistEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        WrappedArtistEntry.COLUMN_NAME + " TEXT," +
                        WrappedArtistEntry.COLUMN_ACCOUNT_ID + " INTEGER," +
                        WrappedArtistEntry.COLUMN_DATE + " INTEGER," +
                        WrappedArtistEntry.COLUMN_IMAGE_REF + " INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + WrappedSongEntry.TABLE_NAME + " (" +
                        WrappedSongEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        WrappedSongEntry.COLUMN_TITLE + " TEXT," +
                        WrappedSongEntry.COLUMN_ARTIST + " TEXT," +
                        WrappedSongEntry.COLUMN_ACCOUNT_ID + " INTEGER," +
                        WrappedSongEntry.COLUMN_DATE + " INTEGER," +
                        WrappedSongEntry.COLUMN_IMAGE_REF + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WrappedSongEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WrappedArtistEntry.TABLE_NAME);
        onCreate(db);
    }

    public static class WrappedArtistEntry {
        // DO NOT instantiate
        private WrappedArtistEntry() {
        }

        public static final String TABLE_NAME = "top_artists";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ACCOUNT_ID = "account_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_IMAGE_REF = "image_ref";
    }

    public static class WrappedSongEntry {
        // DO NOT instantiate
        private WrappedSongEntry() {
        }

        public static final String TABLE_NAME = "top_songs";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ARTIST = "artist";
        public static final String COLUMN_ACCOUNT_ID = "account_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_IMAGE_REF = "image_ref";
    }
}
