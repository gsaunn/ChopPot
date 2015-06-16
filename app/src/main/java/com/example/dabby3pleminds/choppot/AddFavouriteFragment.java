package com.example.dabby3pleminds.choppot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddFavouriteFragment extends Fragment {

    private ArrayAdapter<CharSequence> pSpinnerAdapter;

    public AddFavouriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_favourite, container, false);

        pSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.providers_titles,
                android.R.layout.simple_spinner_dropdown_item);

        //do some things with the spinners, if the first spinner is selected, this chooses a value
        //for the second spinner base on the input of the first, for the first spinner
        Spinner providerSpinner = (Spinner) rootView.findViewById(R.id.spinner_provider);
        //specify list of layouts to use when the list of choices appear
        pSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        providerSpinner.setAdapter(pSpinnerAdapter);
        //Begin: add onItemSelected to the spinner adapter
        providerSpinner.setOnItemSelectedListener(new SpinnerActivity(getActivity(),rootView));
        //set on item click listener for the send and save button
        Button saveButton = (Button) rootView.findViewById(R.id.orderSaveButton);
        saveButton.setOnClickListener(new ButtonActivities(getActivity()));

        return rootView;
    }
}
