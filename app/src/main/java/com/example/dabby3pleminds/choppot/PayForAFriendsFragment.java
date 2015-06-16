package com.example.dabby3pleminds.choppot;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dabby3pleminds.choppot.data.MyDbContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.dabby3pleminds.choppot.PayForAFriendsFragment.OnPayFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PayForAFriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayForAFriendsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FRIENDS_ADAPTER = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPayFragmentInteractionListener mListener;
    private MyCursorAdapter friendsAdapter ;
    private CursorLoader friendsCLoader; //history cursor loader

    //this arrays are used to determine from and to
    private final String[] unBindFrom = {
            MyDbContract.FriendsEntry.COLUMN_NAME,
            MyDbContract.FriendsEntry.COLUMN_EMAIL,
            MyDbContract.FriendsEntry.COLUMN_PHONE,
            MyDbContract.FriendsEntry.COLUMN_PIC_NAME};

    private final int[] bindTo = {
            R.id.friends_name,
            R.id.friends_phone,
            R.id.friends_email,
            R.id.friends_image_url};
    private AdapterView.AdapterContextMenuInfo info;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayForAFriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayForAFriendsFragment newInstance(String param1, String param2) {
        PayForAFriendsFragment fragment = new PayForAFriendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PayForAFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_pay_for_a_friend,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //you shall switch the various contextual menus
        int id = item.getItemId();
        switch (id) {

        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_pay_for_a_friend, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_friends) {
            Intent sIntent = new Intent(getActivity(), AddFriends.class);
            startActivity(sIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

        //for friends list adapter
        friendsAdapter = new MyCursorAdapter(
                getActivity(),
                R.layout.list_item_friends,
                null,
                unBindFrom,
                bindTo,
                FRIENDS_ADAPTER);

        getLoaderManager().initLoader(FRIENDS_ADAPTER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pay_for_afriends, container, false);
        //grab the friends list View
        ListView listView = (ListView) rootView.findViewById(R.id.list_view_friends);
        listView.setAdapter(friendsAdapter);
        //begin codes for context menu
        registerForContextMenu(listView);
        //end code for context menu
        getLoaderManager().initLoader(FRIENDS_ADAPTER, null, this);
        // Add the newly created View to the ViewPager

        return rootView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPayFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPayFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        CursorLoader loaded = null;
        switch (id) {
            case FRIENDS_ADAPTER: {
                String sortOrder = ContactsContract.Contacts._ID + " DESC";
                String[] projection = new String[]{MyDbContract.FriendsEntry._ID,
                        MyDbContract.FriendsEntry.COLUMN_NAME,
                        MyDbContract.FriendsEntry.COLUMN_PHONE,
                        MyDbContract.FriendsEntry.COLUMN_EMAIL,
                        MyDbContract.FriendsEntry.COLUMN_PIC_NAME};

                Uri friendsListUri = MyDbContract.FriendsEntry.CONTENT_URI;
                //Log.v(LOG_TAG, "History Uri " + historyUri.toString());


                friendsCLoader = new CursorLoader(
                        getActivity(),
                        friendsListUri,
                        null,
                        null,
                        null,
                        sortOrder);
                loaded = friendsCLoader;
                break;
            }
            default: {
                break;
            }
        }

        return loaded;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        switch (loader.getId()) {
              case FRIENDS_ADAPTER: {
                // Toast.makeText(this, "The id of the loader is f " + loader.getId(), Toast.LENGTH_LONG).show();
                friendsAdapter.swapCursor(cursor);
                // historyAdapter.swapCursor(cursor);
                break;
            }
            default: {
            }
        }//end switch statement

    }

    @Override
    public void onLoaderReset(Loader loader) {
        switch (loader.getId()) {
            case FRIENDS_ADAPTER: {
                friendsAdapter.swapCursor(null);
                break;
            }
            default: {
                break;
            }
        }//end switch statement

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPayFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String[] extra,int id);
    }

}
