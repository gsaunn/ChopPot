package com.example.dabby3pleminds.choppot.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by DABBY(3pleMinds) on 28-May-15.
 */
public class MyDbContract {
    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.example.dabby3pleminds.choppot";

    /**
     * Base URI. (content://com.example.android.basicsyncadapter)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path component for "entry"-type resources..
     */
    public static final String PATH_HISTORY_ENTRIES = "history";
    public static final String PATH_FRIENDS_ENTRIES = "friends";
    public static final String PATH_FAVOURITE_ENTRIES = "favourite";

    /**
     * Columns supported by "entries" records.
     *
     *
     */
    //for Easy querying of the database

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.setToNow();
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    public static final class HistoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_HISTORY_ENTRIES).build();
        //for all the history

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY_ENTRIES;
        //for single history
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HISTORY_ENTRIES;

        public static final String TABLE_NAME = "history";
        public static final String COLUMN_PROVIDER = "history_provider";
        public static final String COLUMN_ORDER = "history_order";
        public static final String COLUMN_DRINK = "history_drink";
        public static final String COLUMN_WATER = "history_water";
        public static final String COLUMN_LOCATION = "history_location";
        public static final String COLUMN_QUANTITY = "history_quantity";
        public static final String COLUMN_TIME = "history_time";
        public static final String COLUMN_DATE = "history_date";
        public static final String COLUMN_COMMENT = "history_comment";
        public static final String COLUMN_PRICE = "history_price";

        public static Uri buildHistoryUri() {
            return CONTENT_URI;
        }

    }

    //
    public static final class FriendsEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FRIENDS_ENTRIES).build();
        public static final String TABLE_NAME = "friends";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PIC_NAME = "pic_name";

        public static Uri buildHistoryUri() {
            return CONTENT_URI;
        }

    }

    //
    public static final class FavouriteEntry implements BaseColumns {
            public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_ENTRIES).build();
            //for all the history

            public static final String CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE_ENTRIES;
            //for single history
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE_ENTRIES;

            public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_PROVIDER = "favourite_provider";
        public static final String COLUMN_ORDER = "favourite_order";
        public static final String COLUMN_DRINK = "favourite_drink";
        public static final String COLUMN_WATER = "favourite_water";
        public static final String COLUMN_TIME = "favourite_time";
        public static final String COLUMN_DATE = "favourite_date";
        public static final String COLUMN_PRICE = "favourite_price";
        public static final String COLUMN_PRIORITY = "priority";

        public static Uri buildHistoryUri() {
            return CONTENT_URI;
        }

}
}
