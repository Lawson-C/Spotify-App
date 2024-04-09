package com.example.spotify_app.database.database_entries;

public class WrappedSongEntry {
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
