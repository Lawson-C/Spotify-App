package com.example.spotify_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class StorageSystem extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AppData.sqlite";
    public static final int DATABASE_VERSION = 1;

    private static StorageSystem instance;
    private static SQLiteDatabase database;

    private StorageSystem(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
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
                        WrappedArtistEntry.COLUMN_NAME + " TEXT," +
                        WrappedArtistEntry.COLUMN_ACCOUNT_ID + " INTEGER," +
                        WrappedArtistEntry.COLUMN_DATE + " INTEGER," +
                        WrappedArtistEntry.COLUMN_IMAGE_REF + " INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + WrappedSongEntry.TABLE_NAME + " (" +
                        WrappedSongEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        WrappedSongEntry.COLUMN_TITLE + " TEXT," +
                        WrappedSongEntry.COLUMN_ARTIST + " TEXT," +
                        WrappedSongEntry.COLUMN_ACCOUNT_ID + " INTEGER," +
                        WrappedSongEntry.COLUMN_DATE + " INTEGER," +
                        WrappedSongEntry.COLUMN_IMAGE_REF + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocalAccountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WrappedSongEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WrappedArtistEntry.TABLE_NAME);
        onCreate(db);
    }

    /*
     * StorageSystem must be initialized at startup
     */
    public static void init(@NonNull Context context) {
        instance = instance == null ? new StorageSystem(context) : instance;
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
    public static void setIntLocalAccount(int id, String field, int value) {
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
    //  @param date yes, please!
    public static void writeWrappedSong(String title, String artist, int accountID, int date, String imageURL) {
        ContentValues values = new ContentValues();

        values.put(WrappedSongEntry.COLUMN_TITLE, title);
        values.put(WrappedSongEntry.COLUMN_ARTIST, artist);
        values.put(WrappedSongEntry.COLUMN_ID, accountID);
        values.put(WrappedSongEntry.COLUMN_DATE, date);
        values.put(WrappedSongEntry.COLUMN_IMAGE_REF, imageURL);

        database.insert(LocalAccountEntry.TABLE_NAME, null, values);
    }

    public static void writeWrappedArtist(String name, int accountID, int date, String imageURL) {
        ContentValues values = new ContentValues();

        values.put(WrappedArtistEntry.COLUMN_NAME, name);
        values.put(WrappedArtistEntry.COLUMN_ACCOUNT_ID, accountID);
        values.put(WrappedArtistEntry.COLUMN_DATE, date);
        values.put(WrappedArtistEntry.COLUMN_IMAGE_REF, imageURL);

        database.insert(LocalAccountEntry.TABLE_NAME, null, values);
    }

    // Wrapped Retrieve
    public static String[] readWrappedTable(String table, String fieldToMatch, String valueToMatch, String fieldToReceive) {
        Cursor cursor = database.query(
                table,
                null,
                fieldToMatch + " = ?",
                new String[]{valueToMatch},
                null,
                null,
                WrappedSongEntry.COLUMN_ID + " DESC");

        String[] output = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            if (!cursor.moveToNext()) break;
            int cIndex = cursor.getColumnIndex(fieldToReceive);
            output[i] = cursor.getString(cIndex);
        }
        cursor.close();

        return  output;
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

