package com.example.ourspotifyapp.database;

public class WrappedSongEntry {
    // DO NOT instantiate
    private WrappedSongEntry() {
    }

    public static final String TABLE_NAME = "top_songs";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SONG_ONE = "song_one";
    public static final String COLUMN_SONG_TWO = "song_two";
    public static final String COLUMN_SONG_THREE = "song_three";
    public static final String COLUMN_SONG_FOUR = "song_four";
    public static final String COLUMN_SONG_FIVE = "song_five";

    public static final String COLUMN_AUDIO_ONE = "audio_one";
    public static final String COLUMN_AUDIO_TWO = "audio_two";
    public static final String COLUMN_AUDIO_THREE = "audio_three";
    public static final String COLUMN_AUDIO_FOUR = "audio_four";
    public static final String COLUMN_AUDIO_FIVE = "audio_five";
    public static final String COLUMN_ACCOUNT_ID = "account_id";
}
