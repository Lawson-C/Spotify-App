package com.example.ourspotifyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class StorageSystem extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AppData.sqlite";
    public static final int DATABASE_VERSION = 7;

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
                        WrappedArtistEntry.COLUMN_RANK + " INTEGER PRIMARY KEY, " +
                        WrappedArtistEntry.COLUMN_NAME + " TEXT," +
                        WrappedArtistEntry.COLUMN_ACCOUNT_ID + " INTEGER," +
                        WrappedArtistEntry.COLUMN_DATE + " INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + WrappedSongEntry.TABLE_NAME + " (" +
                        WrappedSongEntry.COLUMN_RANK + " INTEGER PRIMARY KEY, " +
                        WrappedSongEntry.COLUMN_TITLE + " TEXT," +
                        WrappedSongEntry.COLUMN_ARTIST + " TEXT," +
                        WrappedSongEntry.COLUMN_ACCOUNT_ID + " INTEGER," +
                        WrappedSongEntry.COLUMN_DATE + " INTEGER," +
                        WrappedSongEntry.COLUMN_AUDIO + " INTEGER)"
        );
        db.execSQL("CREATE TABLE " + WrappedGenreEntry.TABLE_NAME + " (" +
                WrappedGenreEntry.COLUMN_RANK + " INTEGER PRIMARY KEY, " +
                WrappedGenreEntry.COLUMN_ACCOUNT_ID + " TEXT, " +
                WrappedGenreEntry.COLUMN_NAME + " INTEGER)"
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
    public static void writeLocalAccount(String name, String password, int spotifyID) {
        ContentValues values = new ContentValues();

        values.put(LocalAccountEntry.COLUMN_NAME, name);
        values.put(LocalAccountEntry.COLUMN_PASSWORD, password);
        values.put(LocalAccountEntry.COLUMN_SPOTIFY_ID, spotifyID);

        database.insert(LocalAccountEntry.TABLE_NAME, null, values);
    }

    /*
     * Returns a requested variable from a table entry which has a field that matches the given value
     */
    public static String readLocalAccountValue(String fieldToMatch, String valueToMatch, String fieldToReturn) {
        Cursor cursor = database.query(
                LocalAccountEntry.TABLE_NAME,
                null,
                fieldToMatch + " = ?",
                new String[]{valueToMatch},
                null,
                null,
                LocalAccountEntry.COLUMN_ID + " DESC"
        );

        cursor.moveToNext();
        int index = cursor.getColumnIndex(fieldToReturn);
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
    public static String readLocalAccountFromID(int id, String field) {
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
    public static void setLocalAccountFromID(int id, String field, String value) {
        ContentValues content = new ContentValues();

        content.put(field, value);

        database.update(
                LocalAccountEntry.TABLE_NAME,
                content,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[] {"" + id}
        );
    }

    // LocalAccount Delete
    public static void deleteLocalAccountFromID(int id) {
        database.delete(
                LocalAccountEntry.TABLE_NAME,
                LocalAccountEntry.COLUMN_ID + " = ?",
                new String[]{"" + id}
        );
    }

    // Wrapped Create
    public static void writeWrappedSong(String title, String artist, int accountID, int date, int duration, String audioURL) {
        ContentValues values = new ContentValues();

        values.put(WrappedSongEntry.COLUMN_TITLE, title);
        values.put(WrappedSongEntry.COLUMN_ARTIST, artist);
        values.put(WrappedSongEntry.COLUMN_ACCOUNT_ID, accountID);
        values.put(WrappedSongEntry.COLUMN_DATE, date);
        values.put(WrappedSongEntry.COLUMN_DURATION, duration);
        values.put(WrappedSongEntry.COLUMN_AUDIO, audioURL);

        database.insert(WrappedSongEntry.TABLE_NAME, null, values);
    }

    //  @param date yes, please!
    public static void writeWrappedArtist(String name, int accountID, int date, int duration) {
        ContentValues values = new ContentValues();

        values.put(WrappedArtistEntry.COLUMN_NAME, name);
        values.put(WrappedArtistEntry.COLUMN_ACCOUNT_ID, accountID);
        values.put(WrappedArtistEntry.COLUMN_DATE, date);
        values.put(WrappedArtistEntry.COLUMN_DURATION, duration);

        database.insert(WrappedArtistEntry.TABLE_NAME, null, values);
    }

    public static void writeWrappedGenre(String name, int accountID, int date, int duration) {
        ContentValues values = new ContentValues();

        values.put(WrappedGenreEntry.COLUMN_NAME, name);
        values.put(WrappedGenreEntry.COLUMN_ACCOUNT_ID, accountID);
        values.put(WrappedGenreEntry.COLUMN_DATE, date);
        values.put(WrappedGenreEntry.COLUMN_DURATION, duration);

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

