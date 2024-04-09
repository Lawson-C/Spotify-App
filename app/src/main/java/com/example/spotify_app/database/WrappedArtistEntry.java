package com.example.spotify_app.database;

public class WrappedArtistEntry {
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
