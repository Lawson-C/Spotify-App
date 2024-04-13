package com.example.ourspotifyapp.database;

public class WrappedArtistEntry {
    // DO NOT instantiate
    private WrappedArtistEntry() {
    }

    public static final String TABLE_NAME = "top_artists";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ACCOUNT_ID = "account_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DURATION = "duration";
}
