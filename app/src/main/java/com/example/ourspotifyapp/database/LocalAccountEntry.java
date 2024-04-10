package com.example.ourspotifyapp.database;

public class LocalAccountEntry {
    // DO NOT instantiate
    private LocalAccountEntry() {
    }

    public static final String TABLE_NAME = "local_account";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_SPOTIFY_ID = "spotify_id";
}
