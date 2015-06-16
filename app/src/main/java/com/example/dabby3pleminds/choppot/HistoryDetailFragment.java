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
public class HistoryDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "item_id";
    private static final String ARG_PARAM2 = "strings";
    private Intent intent;
    private final String LOG_TAG = HistoryDetail.class.getSimpleName();
    private String[] extra;
    public HistoryDetailFragment() {
    }

    public static HistoryDetailFragment newInstance(String param1, String[] param2)


    {
        HistoryDetailFragment fragment = new HistoryDetailFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_history_detail, container, false);
        if (intent != null && intent.hasExtra("strings")) {
            extra = intent.getStringArrayExtra("strings");
            ((TextView) rootView.findViewById(R.id.history_order_desc))
                    .setText(extra[0]);
            ((TextView) rootView.findViewById(R.id.history_order_provider))
                    .setText(extra[1]);
            ((TextView) rootView.findViewById(R.id.history_order_date))
                    .setText(extra[2]);
            ((TextView) rootView.findViewById(R.id.history_order_time))
                    .setText(extra[3]);
            ((TextView) rootView.findViewById(R.id.history_order_cost))
                    .setText(extra[4]);
//            //get the button
//            Button hDelButton = (Button) rootView.findViewById(R.id.hdetail_delButton);
//            Button hAddFavButton = (Button) rootView.findViewById(R.id.hdetail_addFav);
//            Button hReorderButton = (Button) rootView.findViewById(R.id.hdetail_reorder);
//            //set the onclick listener
//            hDelButton.setOnClickListener(new ButtonActivities(getActivity()));
//            hAddFavButton.setOnClickListener(new ButtonActivities(getActivity()));
//            hReorderButton.setOnClickListener(new ButtonActivities(getActivity()));
        }
        return rootView;
    }
}
