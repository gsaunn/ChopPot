package com.example.dabby3pleminds.choppot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dabby3pleminds.choppot.data.MyDbContract.FavouriteEntry;
import com.example.dabby3pleminds.choppot.data.MyDbContract.FriendsEntry;
import com.example.dabby3pleminds.choppot.data.MyDbContract.HistoryEntry;


/**
 * Created by DABBY(3pleMinds) on 28-May-15.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;

    public static final String DATABASE_NAME = "chopPotDb";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //this string creates the history table
        final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                HistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                // the ID of the location entry associated with this weather data
                HistoryEntry.COLUMN_PROVIDER + " TEXT NOT NULL, " +
                HistoryEntry.COLUMN_ORDER + " TEXT NOT NULL, " +
                HistoryEntry.COLUMN_DRINK + " TEXT NOT NULL, " +
                HistoryEntry.COLUMN_WATER + " TEXT NOT NULL, " +
                HistoryEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +
                HistoryEntry.COLUMN_QUANTITY + " TEXT NOT NULL, " +
                HistoryEntry.COLUMN_TIME + " INTEGER NOT NULL, " +
                HistoryEntry.COLUMN_DATE + " INTEGER NOT NULL, "+
                HistoryEntry.COLUMN_COMMENT + " TEXT NOT NULL, " +
                HistoryEntry.COLUMN_PRICE + " FLOAT NOT NULL " +
                " );";
        //this string creates the history table
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + FavouriteEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouriteEntry.COLUMN_PROVIDER + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_ORDER + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_DRINK + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_WATER + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_TIME + " INTEGER NOT NULL, " +
                FavouriteEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                FavouriteEntry.COLUMN_PRICE + " FLOAT NOT NULL, " +
                FavouriteEntry.COLUMN_PRIORITY + " FLOAT NOT NULL " +
                " );";
        //this string creates the history table
        final String SQL_CREATE_FRIENDS_TABLE = "CREATE TABLE " + FriendsEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.
                FriendsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                // the ID of the location entry associated with this weather data
                FriendsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FriendsEntry.COLUMN_EMAIL + " TEXT NOT NULL," +
                FriendsEntry.COLUMN_PIC_NAME + " TEXT NOT NULL," +
                FriendsEntry.COLUMN_PHONE + " INTEGER NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_HISTORY_TABLE);
        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);
        db.execSQL(SQL_CREATE_FRIENDS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FriendsEntry.TABLE_NAME);
        onCreate(db);
    }
}
