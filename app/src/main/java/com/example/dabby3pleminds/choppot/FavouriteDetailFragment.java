package com.example.dabby3pleminds.choppot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteDetailFragment extends Fragment {

    private static final String ARG_PARAM1 ="item_id" ;
    private static final String ARG_PARAM2 = "strings";
    private Intent intent;
    private String[] extra;

    public FavouriteDetailFragment() {
    }

    public static FavouriteDetailFragment newInstance(String param1, String[] param2)


    {
        FavouriteDetailFragment fragment = new FavouriteDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putStringArray(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getActivity().getIntent();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favourite_detail, container, false);
        if (intent != null && intent.hasExtra("strings")) {
            extra = intent.getStringArrayExtra("strings");
            ((TextView) rootView.findViewById(R.id.favourite_order_desc))
                    .setText(extra[0]);
            ((TextView) rootView.findViewById(R.id.favourite_order_provider))
                    .setText(extra[1]);
            ((TextView) rootView.findViewById(R.id.favourite_order_date))
                    .setText(extra[2]);
            ((TextView) rootView.findViewById(R.id.favourite_order_time))
                    .setText(extra[3]);
            ((TextView) rootView.findViewById(R.id.favourite_order_cost))
                    .setText(extra[4]);
//            //get the button
//            Button fDelButton = (Button) rootView.findViewById(R.id.fdetail_delButton);
//            Button fUpdateButton = (Button) rootView.findViewById(R.id.fdetail_update);
//            Button fReorderButton = (Button) rootView.findViewById(R.id.fdetail_reorder);
//            //set the onclick listener
//            fDelButton.setOnClickListener(new ButtonActivities(getActivity()));
//            fUpdateButton.setOnClickListener(new ButtonActivities(getActivity()));
//            fReorderButton.setOnClickListener(new ButtonActivities(getActivity()));
        }
        return rootView;
    }
}
