package com.example.spotify_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.spotify_app.database.database_entries.SpotifyAccountEntry;

class SpotifyAccountDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public SpotifyAccountDBHelper(@Nullable Context context) {
        super(context, StorageSystem.DATABASE_NAME, null, DATABASE_VERSION);
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
