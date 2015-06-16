package com.example.dabby3pleminds.choppot;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//this class works for spinner
class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


    private String itemSelected;
    private Context context;
    private ViewGroup rootView;
    public SpinnerActivity(Context context,ViewGroup rootView)
    {
        this.context= context;
        this.rootView = rootView;
    }

    private void SetContext(Context context)
    {
        this.context= context;

    }
    private Context getContext()
    {
        return this.context;

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //get the item
        itemSelected = parent.getItemAtPosition(position).toString();
        switch (itemSelected) {
            case "Chitis": {
                //Toast.makeText(Home.this, itemSelected, Toast.LENGTH_SHORT).show();
                //we shall populate the second spinner based on the menus from the first spinner
                Spinner menuSpinner = (Spinner) rootView.findViewById(R.id.spinner_order);
                //create an array adapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> mSpinnerAdapter = ArrayAdapter.createFromResource(
                        getContext(),
                        R.array.chitis_menu,
                        android.R.layout.simple_spinner_dropdown_item);
                //specify list of layouts to use when the list of choices appear
                mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerAdapter.notifyDataSetChanged();
                //Apply the adapter to the spinner
                menuSpinner.setAdapter(mSpinnerAdapter);

                //populate the water and drink menu
                //Toast.makeText(Home.this, itemSelected, Toast.LENGTH_SHORT).show();
                //we shall populate the second spinner based on the menus from the first spinner
                Spinner drinkSpinner = (Spinner) rootView.findViewById(R.id.spinner_order_drinks);
                //create an array adapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> dSpinnerAdapter = ArrayAdapter.createFromResource(
                        getContext(),
                        R.array.chitis_menu_drink,
                        android.R.layout.simple_spinner_dropdown_item);
                //specify list of layouts to use when the list of choices appear
                dSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dSpinnerAdapter.notifyDataSetChanged();
                //Apply the adapter to the spinner
                drinkSpinner.setAdapter(dSpinnerAdapter);

                //for water
                //Toast.makeText(Home.this, itemSelected, Toast.LENGTH_SHORT).show();
                //we shall populate the second spinner based on the menus from the first spinner
                Spinner waterSpinner = (Spinner) rootView.findViewById(R.id.spinner_order_water);
                //create an array adapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> wSpinnerAdapter = ArrayAdapter.createFromResource(
                        getContext(),
                        R.array.chitis_menu_water,
                        android.R.layout.simple_spinner_dropdown_item);
                //specify list of layouts to use when the list of choices appear
                wSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                wSpinnerAdapter.notifyDataSetChanged();
                //Apply the adapter to the spinner
                waterSpinner.setAdapter(wSpinnerAdapter);

            }
            default: {

            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
