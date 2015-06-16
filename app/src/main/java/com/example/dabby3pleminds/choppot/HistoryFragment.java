package com.example.dabby3pleminds.choppot;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
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
 * {@link HistoryFragment.OnHistoryFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOG_TAG = HistoryFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnHistoryFragmentInteractionListener mListener;
    private MyCursorAdapter historyAdapter;
    private final int HISTORY_ADAPTER = 0;
    //define the loaders
    private CursorLoader historyCLoader; //history cursor loader
    private AdapterView.AdapterContextMenuInfo info;

    public static final String ARG_ITEM_ID = "item_id";

       /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
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

        //init the adapters
        historyAdapter = new MyCursorAdapter(
                getActivity(),
                R.layout.list_item_text_history,
                null,
                new String[]{
                        MyDbContract.HistoryEntry.COLUMN_ORDER,
                        MyDbContract.HistoryEntry.COLUMN_DRINK,
                        MyDbContract.HistoryEntry.COLUMN_WATER,
                        MyDbContract.HistoryEntry.COLUMN_PROVIDER,
                        MyDbContract.HistoryEntry.COLUMN_DATE,
                        MyDbContract.HistoryEntry.COLUMN_TIME,
                        MyDbContract.HistoryEntry.COLUMN_PRICE},
                new int[]{
                        R.id.history_order,
                        R.id.history_order_drink,
                        R.id.history_order_water,
                        R.id.order_provider,
                        R.id.order_date,
                        R.id.order_time,
                        R.id.order_price},
                HISTORY_ADAPTER);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_float_menu_history,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //you shall switch the various contextual menus
        int id = item.getItemId();
        switch(id)
        {

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu_hstory, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_history_fragmnet, container, false);

        //grab the favourite list View
        ListView listView = (ListView) rootView.findViewById(R.id.list_view_history);
        listView.setAdapter(historyAdapter);
        //begin codes for context menu

        registerForContextMenu(listView);
        //end code for context menu
        getLoaderManager().initLoader(HISTORY_ADAPTER, null, this);
        //add a code for only click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = historyAdapter.getItem(position).toString();
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


//                //Toast.makeText(getActivity(),item, Toast.LENGTH_SHORT).show();
//                //create a  new intent
//                Intent intent = new Intent(getActivity(),HistoryDetail.class);
//                intent.putExtra("strings", extra);
//                startActivity(intent);
                mListener.onFragmentInteraction(extra,0);

            }
        });

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
            mListener = (OnHistoryFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHistoryFragmentInteractionListener");
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
            case HISTORY_ADAPTER: {
                // Sort order:  Ascending, by date.
                // String sortOrder = MyDbContract.HistoryEntry.COLUMN_DATE + " ASC";

                Uri historyUri = MyDbContract.HistoryEntry.buildHistoryUri();
                Log.v(LOG_TAG, "History Uri " + historyUri.toString());


                historyCLoader = new CursorLoader(getActivity(),
                        historyUri,
                        null,
                        null,
                        null,
                        null);
                loaded = historyCLoader;
            }
            default: {
            }

        }
        return loaded;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        switch (loader.getId()) {
            case HISTORY_ADAPTER: {
                // Toast.makeText(this, "The id of the loader is f " + loader.getId(), Toast.LENGTH_LONG).show();
                historyAdapter.swapCursor(cursor);
                break;
            }default:{
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case HISTORY_ADAPTER: {
                historyAdapter.swapCursor(null);
                break;
            }default:{
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
    public interface OnHistoryFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String[] extra, int id);
    }

}
