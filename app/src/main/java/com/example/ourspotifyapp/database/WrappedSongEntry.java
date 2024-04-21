package com.example.ourspotifyapp.database;

public class WrappedSongEntry {
    // DO NOT instantiate
    private WrappedSongEntry() {
    }

    public static final String TABLE_NAME = "top_songs";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ACCOUNT_ID = "account_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_AUDIO = "audio";
}
