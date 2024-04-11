package com.example.ourspotifyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

public class StorageSystem extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AppData.sqlite";
    public static final int DATABASE_VERSION = 9;

    private static StorageSystem instance;
    private static SQLiteDatabase database;

    private StorageSystem(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        db.execSQL(
                "CREATE TABLE " + WrappedArtistEntry.TABLE_NAME + " (" +
                        WrappedArtistEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        WrappedArtistEntry.COLUMN_ARTIST_ONE + " TEXT," +
                        WrappedArtistEntry.COLUMN_ARTIST_TWO + " TEXT," +
                        WrappedArtistEntry.COLUMN_ARTIST_THREE + " TEXT," +
                        WrappedArtistEntry.COLUMN_ARTIST_FOUR + " TEXT," +
                        WrappedArtistEntry.COLUMN_ARTIST_FIVE + " TEXT," +
                        WrappedArtistEntry.COLUMN_ACCOUNT_ID + " INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + WrappedSongEntry.TABLE_NAME + " (" +
                        WrappedSongEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        WrappedSongEntry.COLUMN_SONG_ONE + " TEXT," +
                        WrappedSongEntry.COLUMN_SONG_TWO + " TEXT," +
                        WrappedSongEntry.COLUMN_SONG_THREE + " TEXT," +
                        WrappedSongEntry.COLUMN_SONG_FOUR + " TEXT," +
                        WrappedSongEntry.COLUMN_SONG_FIVE + " TEXT," +
                        WrappedSongEntry.COLUMN_AUDIO_ONE + " TEXT," +
                        WrappedSongEntry.COLUMN_AUDIO_TWO + " TEXT," +
                        WrappedSongEntry.COLUMN_AUDIO_THREE + " TEXT," +
                        WrappedSongEntry.COLUMN_AUDIO_FOUR + " TEXT," +
                        WrappedSongEntry.COLUMN_AUDIO_FIVE + " TEXT," +
                        WrappedSongEntry.COLUMN_ACCOUNT_ID + " INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + WrappedGenreEntry.TABLE_NAME + " (" +
                        WrappedGenreEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        WrappedGenreEntry.COLUMN_GENRE_ONE + " TEXT," +
                        WrappedGenreEntry.COLUMN_GENRE_TWO + " TEXT," +
                        WrappedGenreEntry.COLUMN_GENRE_THREE + " TEXT," +
                        WrappedGenreEntry.COLUMN_GENRE_FOUR + " TEXT," +
                        WrappedGenreEntry.COLUMN_GENRE_FIVE + " TEXT," +
                        WrappedGenreEntry.COLUMN_ACCOUNT_ID + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocalAccountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WrappedSongEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WrappedArtistEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WrappedGenreEntry.TABLE_NAME);
        onCreate(db);
    }

    /*
     * StorageSystem must be initialized at startup
     */
    public static void init(@NonNull Context context) {
        if (instance != null) return;
        instance = new StorageSystem(context);
        database = instance.getWritableDatabase();
    }

    // LocalAccount Create
    public static void writeLocalAccount(int id, String name, String password, int spotifyID) {
        ContentValues values = new ContentValues();

        values.put(LocalAccountEntry.COLUMN_ID, id);
        values.put(LocalAccountEntry.COLUMN_NAME, name);
        values.put(LocalAccountEntry.COLUMN_PASSWORD, password);
        values.put(LocalAccountEntry.COLUMN_SPOTIFY_ID, spotifyID);

        database.insert(LocalAccountEntry.TABLE_NAME, null, values);
    }

    /*
     * Returns a requested variable from a table entry which has a field that matches the given value
     */
    public static String readLocalAccountValue(String requestField, String value, String responseField) {
        Cursor cursor = database.query(
                LocalAccountEntry.TABLE_NAME,
                null,
                requestField + " = ?",
                new String[]{value},
                null,
                null,
                LocalAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(responseField);
        String returnValue = cursor.getString(index);
        cursor.close();

        return returnValue;
    }

    /*
     * Returns a tuple containing the requested fields and requested values from a table entry where the requestedField matches
     * the given value
     */
    public static String[][] readLocalAccountValueArray(String fieldToMatch, String valueToMatch, String[] fieldsToReturn) {
        Cursor cursor = database.query(
                LocalAccountEntry.TABLE_NAME,
                null,
                fieldToMatch + " = ? ",
                new String[]{valueToMatch},
                null,
                null,
                LocalAccountEntry.COLUMN_ID + " DESC"
        );

        String[][] responseValues = new String[fieldsToReturn.length][];

        for (int i = 0; i < responseValues.length; i++) {
            cursor.moveToNext();
            int index = cursor.getColumnIndex(fieldsToReturn[i]);
            responseValues[i] = new String[]{fieldsToReturn[i], cursor.getString(index)};
        }

        cursor.close();

        return responseValues;
    }

    // LocalAccount Retrieve from ID
    public static String readLocalAccountID(int id, String field) {
        Cursor cursor = database.query(
                LocalAccountEntry.TABLE_NAME,
                null,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id},
                null,
                null,
                LocalAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(field);
        String value = cursor.getString(index);
        cursor.close();

        return value;
    }

    //  LocalAccount Update
    public static void setIntLocalAccount(int id, String field, String value) {
        ContentValues content = new ContentValues();

        content.put(field, value);

        database.update(
                LocalAccountEntry.TABLE_NAME,
                content,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id}
        );
    }

    // LocalAccount Delete
    public static void deleteLocalAccount(int id) {
        database.delete(
                LocalAccountEntry.TABLE_NAME,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id}
        );
    }

    // Wrapped Create
    public static void writeWrappedSong(int id, String[] songs, String[] audioUrls, int accountID) {
        if (songs.length != 5 || audioUrls.length != 5) {
            Log.d("adding stuff", "song or audio array lengths not working");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(WrappedSongEntry.COLUMN_ID, id);
        values.put(WrappedSongEntry.COLUMN_SONG_ONE, songs[0]);
        values.put(WrappedSongEntry.COLUMN_SONG_TWO, songs[1]);
        values.put(WrappedSongEntry.COLUMN_SONG_THREE, songs[2]);
        values.put(WrappedSongEntry.COLUMN_SONG_FOUR, songs[3]);
        values.put(WrappedSongEntry.COLUMN_SONG_FIVE, songs[4]);
        values.put(WrappedSongEntry.COLUMN_AUDIO_ONE, audioUrls[0]);
        values.put(WrappedSongEntry.COLUMN_AUDIO_TWO, audioUrls[1]);
        values.put(WrappedSongEntry.COLUMN_AUDIO_THREE, audioUrls[2]);
        values.put(WrappedSongEntry.COLUMN_AUDIO_FOUR, audioUrls[3]);
        values.put(WrappedSongEntry.COLUMN_AUDIO_FIVE, audioUrls[4]);
        values.put(WrappedSongEntry.COLUMN_ACCOUNT_ID, accountID);

        database.insert(WrappedSongEntry.TABLE_NAME, null, values);
    }

    //  @param date yes, please!
    public static void writeWrappedArtist(int id, String[] artists, int accountID) {
        if (artists.length != 5) {
            Log.d("adding stuff", "artist array lengths not working");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(WrappedArtistEntry.COLUMN_ID, id);
        values.put(WrappedArtistEntry.COLUMN_ARTIST_ONE, artists[0]);
        values.put(WrappedArtistEntry.COLUMN_ARTIST_TWO, artists[1]);
        values.put(WrappedArtistEntry.COLUMN_ARTIST_THREE, artists[2]);
        values.put(WrappedArtistEntry.COLUMN_ARTIST_FOUR, artists[3]);
        values.put(WrappedArtistEntry.COLUMN_ARTIST_FIVE, artists[4]);
        values.put(WrappedArtistEntry.COLUMN_ACCOUNT_ID, accountID);

        database.insert(WrappedArtistEntry.TABLE_NAME, null, values);
    }

    // Similar create for genres
    public static void writeWrappedGenre(int id, String[] genres, int accountID) {
        if (genres.length != 5) {
            Log.d("adding stuff", "genre array lengths not working");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(WrappedGenreEntry.COLUMN_ID, id);
        values.put(WrappedGenreEntry.COLUMN_GENRE_ONE, genres[0]);
        values.put(WrappedGenreEntry.COLUMN_GENRE_TWO, genres[1]);
        values.put(WrappedGenreEntry.COLUMN_GENRE_THREE, genres[2]);
        values.put(WrappedGenreEntry.COLUMN_GENRE_FOUR, genres[3]);
        values.put(WrappedGenreEntry.COLUMN_GENRE_FIVE, genres[4]);
        values.put(WrappedGenreEntry.COLUMN_ACCOUNT_ID, accountID);

        database.insert(WrappedGenreEntry.TABLE_NAME, null, values);
    }

    // Wrapped Retrieve
    public static String[] readWrappedEntryValue(String table, String fieldToMatch, String valueToMatch, String fieldToReceive) {
        Cursor cursor = database.query(
                table,
                null,
                fieldToMatch + " = ?",
                new String[]{valueToMatch},
                null,
                null,
                null);

        String[] output = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            if (!cursor.moveToNext()) break;
            int cIndex = cursor.getColumnIndex(fieldToReceive);
            output[i] = cursor.getString(cIndex);
        }
        cursor.close();

        return output;
    }

    public static String[][] readWrappedEntry(String table, String fieldToMatch, String valueToMatch) {
        Cursor cursor = database.query(
                table,
                null,
                fieldToMatch + " = ?",
                new String[]{valueToMatch},
                null,
                null,
                null);

        String[][] output = new String[cursor.getCount()][cursor.getColumnCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            if (!cursor.moveToNext()) break;
            for (int j = 0; j < cursor.getColumnCount(); j++) {
                output[i][j] = cursor.getString(j);
            }
        }
        cursor.close();

        return output;
    }

    // Wrapped Update
    public static void updateWrappedTable(String table, String fieldToMatch, String valueToMatch, String fieldToChange, String valueToSet) {
        ContentValues content = new ContentValues();

        content.put(fieldToChange, valueToSet);

        database.update(
                table,
                content,
                fieldToMatch + " = ?",
                new String[]{valueToMatch}
        );
    }

    // LocalAccount Delete
    public static void deleteWrappedTable(String table, String fieldToMatch, String valueToMatch) {
        database.delete(
                table,
                fieldToMatch + " = ?",
                new String[]{valueToMatch}
        );
    }
}

