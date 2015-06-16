package com.example.dabby3pleminds.choppot.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by DABBY(3pleMinds) on 28-May-15.
 */
public class MyProvider extends ContentProvider {
    private final String LOG_TAG = MyProvider.class.getSimpleName();
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MyDbHelper mOpenHelper;

    //all Other parameters are defined in the contract class

    static final int HISTORY = 100;
    static final int FAVOURITE = 101;
    static final int HISTORY_ITEM = 102;
    static final int FAVOURITE_ITEM = 103;
    static final int FRIENDS = 104;
    static final int FRIENDS_ITEM = 104;

    //history.history

    // iam using sqliteBuilder class instead of direct database connection

    private static final SQLiteQueryBuilder sHistoryQueryBuilder;
    private static final SQLiteQueryBuilder sFriendsQueryBuilder;
    private static final SQLiteQueryBuilder sFavouriteQueryBuilder;
    static {
        sHistoryQueryBuilder = new SQLiteQueryBuilder();
        //set the table to access
        sHistoryQueryBuilder.setTables(MyDbContract.HistoryEntry.TABLE_NAME);

        sFavouriteQueryBuilder = new SQLiteQueryBuilder();
        //set the table to access
        sFavouriteQueryBuilder.setTables(MyDbContract.FavouriteEntry.TABLE_NAME);

        sFriendsQueryBuilder = new SQLiteQueryBuilder();
        //set the table to access
        sFriendsQueryBuilder.setTables(MyDbContract.FriendsEntry.TABLE_NAME);
    }

    //Determine the URI
    private static final String sHistorySelection = MyDbContract.HistoryEntry.TABLE_NAME;//denotes filter for the selection


    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MyDbContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MyDbContract.PATH_HISTORY_ENTRIES,HISTORY);
        matcher.addURI(authority,MyDbContract.PATH_FAVOURITE_ENTRIES,FAVOURITE);
        matcher.addURI(authority, MyDbContract.PATH_FRIENDS_ENTRIES, FRIENDS);
        matcher.addURI(authority, MyDbContract.PATH_HISTORY_ENTRIES + "/*", HISTORY_ITEM);
        matcher.addURI(authority, MyDbContract.PATH_FAVOURITE_ENTRIES + "/*", FAVOURITE_ITEM);
        matcher.addURI(authority, MyDbContract.PATH_FRIENDS_ENTRIES + "/*", FRIENDS_ITEM);


        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MyDbHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        int  matchedUri = sUriMatcher.match(uri);
        switch (matchedUri) {
            // "HISTORY/"
            case HISTORY: {
                Log.v(LOG_TAG, "matched Uri: " + uri);
                retCursor = getAllHistory(uri, projection, sortOrder);
                break;
            }
            case FAVOURITE: {
                Log.v(LOG_TAG, "matched Uri: " + uri);
                retCursor = getAllFavourite(uri, projection, sortOrder);
                break;
            }
            case FRIENDS: {
                Log.v(LOG_TAG, "matched Uri: " + uri);
                retCursor = getAllFriends(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    //this function retrieves all history
    private Cursor getAllHistory(Uri uri, String[] projection, String sortOrder) {


            return sHistoryQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );
    }

    //this function retrieves all history
    private Cursor getAllFriends(Uri uri, String[] projection, String sortOrder) {


        return sFriendsQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    //this function retrieves all history
    private Cursor getAllFavourite(Uri uri, String[] projection, String sortOrder) {


        return sFavouriteQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        String ret = "";
        switch (match) {

            case HISTORY:
                ret= MyDbContract.HistoryEntry.CONTENT_ITEM_TYPE;
                break;

            case FAVOURITE:
                ret= MyDbContract.FavouriteEntry.CONTENT_TYPE;
                break;
        }
        return ret;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case HISTORY: {
                normalizeDateHistory(values);
                long _id = db.insert(MyDbContract.HistoryEntry.TABLE_NAME, null, values);

                if (_id > 0)
                    returnUri = Uri.parse(MyDbContract.HistoryEntry.CONTENT_URI+"/"+_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
                case FAVOURITE: {
                    normalizeDateHistory(values);
                    long _id = db.insert(MyDbContract.FavouriteEntry.TABLE_NAME, null, values);

                    if (_id > 0)
                        returnUri = Uri.parse(MyDbContract.FavouriteEntry.CONTENT_URI + "/" + _id);
                    else
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    break;
                }
                    case FRIENDS: {
                        normalizeDateHistory(values);
                        long _id = db.insert(MyDbContract.FriendsEntry.TABLE_NAME, null, values);

                        if (_id > 0)
                            returnUri = Uri.parse(MyDbContract.FriendsEntry.CONTENT_URI + "/" + _id);
                        else
                            throw new android.database.SQLException("Failed to insert row into " + uri);
                        break;
                    }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case HISTORY_ITEM:
                rowsDeleted = db.delete(
                        MyDbContract.HistoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITE_ITEM:
                rowsDeleted = db.delete(
                        MyDbContract.FavouriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case HISTORY:
                rowsDeleted = db.delete(
                        MyDbContract.HistoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITE:
                rowsDeleted = db.delete(
                        MyDbContract.FavouriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FRIENDS_ITEM:
                rowsDeleted = db.delete(
                        MyDbContract.FriendsEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case HISTORY:
                //normalizeDate(values);
                rowsUpdated = db.update(MyDbContract.HistoryEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case FAVOURITE:
                rowsUpdated = db.update(MyDbContract.FavouriteEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    //this is used to normalize all date
    private void normalizeDateHistory(ContentValues values) {
        // normalize the date value
        if (values.containsKey(MyDbContract.HistoryEntry.COLUMN_DATE) ) {
            long dateValue = values.getAsLong(MyDbContract.HistoryEntry.COLUMN_DATE);
            values.put(MyDbContract.HistoryEntry.COLUMN_DATE, MyDbContract.normalizeDate(dateValue));
        }
    }

//    private void normalizeDateFavourite(ContentValues values) {
//        // normalize the date value
//        if (values.containsKey(MyDbContract.FavouriteEntry.COLUMN_DATE)) {
//            long dateValue = values.getAsLong(MyDbContract.FavouriteEntry.COLUMN_DATE);
//            values.put(MyDbContract.FavouriteEntry.COLUMN_DATE, MyDbContract.normalizeDate(dateValue));
//        }
//    }
}
