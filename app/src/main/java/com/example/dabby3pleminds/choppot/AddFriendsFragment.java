package com.example.dabby3pleminds.choppot;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddFriendsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorLoader friendsCLoader; //history cursor loader

    private final int FRIENDS_ADAPTER = 0; //friends adapter
    //this arrays are used to determine from and to
    private final String[] unBindFrom = {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME};

    private final int[] bindTo = {
            R.id.friends_name
//            R.id.last_called_date,
//            R.id.friends_phone,
//            R.id.friends_email,
//            R.id.friends_image_url
    };

    //define the selction args
    private static final String SELECTION = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
            ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    //search String
    private String mSearchString = "jerry";
    //define the array to hold the search
    private String[] mSelectionArgs = {mSearchString};

    private String mLookupKey;
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME


    };


    //sort order
    private String mSortOrder = ContactsContract.Contacts.LAST_TIME_CONTACTED + " DESC";
    //the cintact id Value
    long mContactId;
    //a content uri for the selected conatct
    String mContatKey;


    private MyCursorAdapter friendsAdapter;

    public AddFriendsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friendsAdapter = new MyCursorAdapter(
                getActivity(),
                R.layout.list_item_friends_from_contacts,
                null,
                unBindFrom,
                bindTo,
                FRIENDS_ADAPTER);


        getLoaderManager().initLoader(FRIENDS_ADAPTER, null, this);
        //add a code for only click
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_friends, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list_view_friends);
        listView.setAdapter(friendsAdapter);
        //begin codes for context menu
        registerForContextMenu(listView);
        //end code for context menu
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_friends_activity, menu);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri friendsListUri = ContactsContract.Contacts.CONTENT_URI;
        friendsCLoader = new CursorLoader(
                getActivity(),
                friendsListUri,
                PROJECTION,
                null,
                null,
                mSortOrder);
        return friendsCLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        friendsAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        friendsAdapter.swapCursor(null);

    }
}
