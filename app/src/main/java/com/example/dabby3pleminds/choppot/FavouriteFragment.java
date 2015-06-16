package com.example.dabby3pleminds.choppot;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;

import com.example.dabby3pleminds.choppot.data.MyDbContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.dabby3pleminds.choppot.FavouriteFragment.OnFavouriteFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FAVOURITE_ADAPTER = 3;
    private static final String LOG_TAG = FavouriteFragment.class.getSimpleName();
    public static final String ARG_ITEM_ID = "item_id";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFavouriteFragmentInteractionListener mListener;
    private MyCursorAdapter favouriteAdapter ;
    private CursorLoader favouriteCLoader  ;
    private AdapterView.AdapterContextMenuInfo info;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        favouriteAdapter = new MyCursorAdapter(
                getActivity(),
                R.layout.list_item_text_history,
                null,
                new String[]{
                        MyDbContract.FavouriteEntry.COLUMN_ORDER,
                        MyDbContract.FavouriteEntry.COLUMN_DRINK,
                        MyDbContract.FavouriteEntry.COLUMN_WATER,
                        MyDbContract.FavouriteEntry.COLUMN_PROVIDER,
                        MyDbContract.FavouriteEntry.COLUMN_DATE,
                        MyDbContract.FavouriteEntry.COLUMN_TIME,
                        MyDbContract.FavouriteEntry.COLUMN_PRICE},
                new int[]{
                        R.id.history_order,
                        R.id.history_order_drink,
                        R.id.history_order_water,
                        R.id.order_provider,
                        R.id.order_date,
                        R.id.order_time,
                        R.id.order_price},
                FAVOURITE_ADAPTER);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_favourite, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_float_menu_favourite,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_favourite) {
            Intent fIntent = new Intent(getActivity(), AddFavourite.class);
            startActivity(fIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //you shall switch the various contextual menus
        int id = item.getItemId();
        switch (id) {
            case R.id.favourite_action_edit:
            {
                Intent eIntent = new Intent(getActivity(), AddFavourite.class);
                startActivity(eIntent);
                break;
            }
            case R.id.favourite_action_order: {
                //Toast.makeText(this, info.toString(), Toast.LENGTH_LONG).show();
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getActivity().getFragmentManager(), "QTY_DIALOG");
                break;
            }default:{

            }


        }
        return super.onContextItemSelected(item);
    }



   //this function updates value in the content provider
    public int update(ContentValues updatedValues) {
        int count = getActivity().getContentResolver().update(
                MyDbContract.HistoryEntry.CONTENT_URI, updatedValues, MyDbContract.HistoryEntry._ID + "= ?",
                null);
        return count;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favourite, container, false);

        //grab the favourite list View
        ListView listView = (ListView) rootView.findViewById(R.id.list_view_favourite);
        listView.setAdapter(favouriteAdapter);
        //begin codes for context menu
        registerForContextMenu(listView);//end code for context menu
        getLoaderManager().initLoader(FAVOURITE_ADAPTER, null, this); //init the loader

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = favouriteAdapter.getItem(position).toString();
                //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                //create new intent
                TextView orderView = (TextView) view.findViewById(R.id.history_order);
                String order = orderView.getText().toString();

                TextView orderProviderView = (TextView) view.findViewById(R.id.order_provider);
                String orderProvider = orderProviderView.getText().toString();

                TextView orderTimeView = (TextView) view.findViewById(R.id.order_time);
                String orderTime = orderTimeView.getText().toString();

                TextView orderDateView = (TextView) view.findViewById(R.id.order_date);
                String orderDate = orderDateView.getText().toString();

                TextView orderPrice = (TextView) view.findViewById(R.id.order_price);
                String orderCost = orderPrice.getText().toString();


                String[] extra = {order, orderProvider, orderDate, orderTime, orderCost};
//                Intent favouriteDetailIntent = new Intent(getActivity(), FavouriteDetail.class)
//                        .putExtra("strings", extra);
//                startActivity(favouriteDetailIntent);
                mListener.onFragmentInteraction(extra,3);
            }
        });
        return rootView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(String[] extra) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(extra);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFavouriteFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFavouriteFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loaded = null;
        switch (id) {
            case FAVOURITE_ADAPTER: {
                // Sort order:  Ascending, by date.
                // String sortOrder = MyDbContract.HistoryEntry.COLUMN_DATE + " ASC";

                Uri favouriteUri = MyDbContract.FavouriteEntry.buildHistoryUri();
                //Log.v(LOG_TAG, "History Uri " + favoriteUri.toString());


                favouriteCLoader = new CursorLoader(getActivity(),
                        favouriteUri,
                        null,
                        null,
                        null,
                        null);
                loaded = favouriteCLoader;
                break;
            }
            default: {
            }

        }
        return loaded;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        switch (loader.getId()) {
            case FAVOURITE_ADAPTER: {
                // Toast.makeText(this, "The id of the loader is f " + loader.getId(), Toast.LENGTH_LONG).show();
                favouriteAdapter.swapCursor(cursor);
                break;
            }
            default: {
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case FAVOURITE_ADAPTER: {
                favouriteAdapter.swapCursor(null);
                break;
            }
            default: {
            }
        }

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
    public interface OnFavouriteFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String[] extra, int id);
    }

}
