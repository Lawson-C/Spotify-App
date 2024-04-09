package com.example.spotify_app.database.database_entries;

public class SpotifyAccountEntry {
    // DO NOT instantiate
    private SpotifyAccountEntry() {
    }

    public static final String TABLE_NAME = "spotify_account";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DISPLAY_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SPOTIFY_URL = "url";
    public static final String COLUMN_HREF = "href";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_IMAGE_WIDTH = "image_width";
    public static final String COLUMN_IMAGE_HEIGHT = "image_height";
    public static final String COLUMN_URI = "uri";
}
