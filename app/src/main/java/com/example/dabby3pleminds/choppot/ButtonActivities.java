package com.example.dabby3pleminds.choppot;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dabby3pleminds.choppot.data.MyDbContract;

public class ButtonActivities extends Activity implements View.OnClickListener {

    private static final String MY_PREFS ="MyPrefdFile" ;
    private Context context;
    private SharedPreferences sPrefs;

    public ButtonActivities(Context context)
    {
     this.context=context;
    }

    public boolean makeOrder(View v)
    {
       return true;
    }

    private static ContentValues createHistoryValues(String qty,String loc,String cmt, String drink,String price,String water,String time,String date,String order,String provider) {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MyDbContract.HistoryEntry.COLUMN_QUANTITY, qty);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_LOCATION,loc);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_COMMENT,cmt);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_DATE,date);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_PRICE,price);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_WATER,water);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_TIME, time);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_DRINK,drink);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_ORDER, order);
        testValues.put(MyDbContract.HistoryEntry.COLUMN_PROVIDER,provider);

        return testValues;
    }

    private static ContentValues createFavouriteValues(String restaurantItemStr, String orderItemStr, String drinkItemStr, String waterItemStr) {

        ContentValues values = new ContentValues();
        values.put(MyDbContract.FavouriteEntry.COLUMN_PROVIDER,restaurantItemStr);
        values.put(MyDbContract.FavouriteEntry.COLUMN_ORDER,orderItemStr);
        values.put(MyDbContract.FavouriteEntry.COLUMN_DRINK,drinkItemStr);
        values.put(MyDbContract.FavouriteEntry.COLUMN_WATER,waterItemStr);
        values.put(MyDbContract.FavouriteEntry.COLUMN_DATE,"23456433");
        values.put(MyDbContract.FavouriteEntry.COLUMN_TIME,"22:22");
        values.put(MyDbContract.FavouriteEntry.COLUMN_PRIORITY,"2");
        values.put(MyDbContract.FavouriteEntry.COLUMN_PRICE,"2345");
        return values;

    }


    private Context getContext()
    {
        return this.context;
    }
    private void setContext(Context context)
    {
        this.context = context;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderButton: {
                //start grabbing all the values in the layout
                //for provider
                Spinner provider = (Spinner) v.getRootView().findViewById(R.id.spinner_provider);
                String providerStr = provider.getAdapter().getItem(provider.getSelectedItemPosition()).toString();

                //for order
                //for provider
                Spinner orderItem = (Spinner) v.getRootView().findViewById(R.id.spinner_order);
                String orderItemStr = orderItem.getAdapter().getItem(orderItem.getSelectedItemPosition()).toString();

                //for drink
                //for provider
                Spinner drinkItem = (Spinner) v.getRootView().findViewById(R.id.spinner_order_drinks);
                String drinkItemStr = drinkItem.getAdapter().getItem(drinkItem.getSelectedItemPosition()).toString();


                //for water
                //for provider
                Spinner waterItem = (Spinner) v.getRootView().findViewById(R.id.spinner_order_water);
                String waterItemStr = waterItem.getAdapter().getItem(waterItem.getSelectedItemPosition()).toString();

                //grab the location
                EditText locationText = (EditText) v.getRootView().findViewById(R.id.order_location_value);
                String locationTextStr = locationText.getText().toString();

                //for quantity
                EditText quantityText = (EditText) v.getRootView().findViewById(R.id.order_quantity_value);
                String quantityTextStr = quantityText.getText().toString();

                //for comment
                EditText commentText = (EditText) v.getRootView().findViewById(R.id.order_comment_value);
                String commentTextStr = commentText.getText().toString();
                //then make the order
                //you shall switch provider and get contact details and preference of the user befor
                //sending message
                Toast.makeText(getContext(), drinkItemStr, Toast.LENGTH_LONG);
                String[] values = {quantityTextStr, locationTextStr, commentTextStr,drinkItemStr, "N300", waterItemStr, "22:22","334455677", orderItemStr, providerStr};
                if(validateOrderValue(values))
                {
                    sPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String providerPhone = getProviderContact(providerStr);
                    String senderName = SenderNameFromPreference();
                    String senderPhone = senderPhoneNumber();
                    MySmsManager mgr = new MySmsManager(getContext(),providerPhone, "Order:[123AJDGJKJE] From:"+senderName+">"+senderPhone+",Item:" + orderItemStr + "," + drinkItemStr + "," + waterItemStr + ", Loc:" + locationTextStr + ", Qty:" + quantityTextStr + ",Cmmt:" + commentTextStr);
                    if (mgr.sendSmsByManger()) {
                        Toast.makeText(getContext(), "Message sent Successfully", Toast.LENGTH_LONG).show();
                        //create the content values for database insert
                        ContentValues testValues = createHistoryValues(quantityTextStr, locationTextStr, commentTextStr, drinkItemStr, "N300", waterItemStr, "22:22", "334455677", orderItemStr, providerStr);

                        Uri id = getContext().getContentResolver().insert(MyDbContract.HistoryEntry.CONTENT_URI, testValues);
                        Toast.makeText(getContext(), "Order Saved To Database with id=  " + id.toString(), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getContext(), "Unable To Send Your Message", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getContext(), "You must complete all fields!", Toast.LENGTH_LONG).show();

                }




                break;
            }
            case R.id.orderSaveButton: {

                //start grabbing all the values in the layout
                //for provider
                Spinner restaurantItem = (Spinner) v.getRootView().findViewById(R.id.spinner_provider);
                String restaurantItemStr = restaurantItem.getAdapter().getItem(restaurantItem.getSelectedItemPosition()).toString();

                //for order
                //for provider
                Spinner orderItem = (Spinner) v.getRootView().findViewById(R.id.spinner_order);
                String orderItemStr = orderItem.getAdapter().getItem(orderItem.getSelectedItemPosition()).toString();

                //for drink
                //for provider
                Spinner drinkItem = (Spinner) v.getRootView().findViewById(R.id.spinner_order_drinks);
                String drinkItemStr = drinkItem.getAdapter().getItem(drinkItem.getSelectedItemPosition()).toString();

                //for water
                //for provider
                Spinner waterItem = (Spinner) v.getRootView().findViewById(R.id.spinner_order_water);
                String waterItemStr = waterItem.getAdapter().getItem(waterItem.getSelectedItemPosition()).toString();

               //create content value
               ContentValues testValues = createFavouriteValues(restaurantItemStr, orderItemStr, drinkItemStr, waterItemStr);
                Uri id = getContext().getContentResolver().insert(MyDbContract.FavouriteEntry.CONTENT_URI, testValues);

                Toast.makeText(getContext() , "Order Saved To Database with id=  "+id.toString(), Toast.LENGTH_LONG).show();

                  }
//            case R.id.hdetail_delButton: {
//
//            }
//            case R.id.hdetail_addFav: {
//
//            }
//            case R.id.hdetail_reorder: {
//
//            }
//            case R.id.fdetail_delButton:
//            {
//
//                break;
//            }
//            case R.id.fdetail_update:
//            {
//
//            }
//            case R.id.fdetail_reorder:
//            {
//
//            }
            default: {

            }
        }
    }

    private String senderPhoneNumber() {

        String sPhone = sPrefs.getString("preferred_phone",getContext().getString(R.string.pref_default_display_phone));
        return sPhone;


    }

    private String SenderNameFromPreference() {
        String sName = sPrefs.getString("preferred_name",getContext().getString(R.string.pref_default_display_name));
        return sName;
    }

    private String getProviderContact(String providerStr) {
        String out = "";
        switch (providerStr)
        {
            case"chitis": {
                out = getContext().getString(R.string.chitis_contact);
                break;
            }
            default: {
                out = getContext().getString(R.string.chitis_contact);
                break;
            }
        }
     return out;
    }


    private boolean validateOrderValue(String[] values)
    {
        boolean ret = false;
        for (int i =0; i<values.length; i++)
        {
            if(values[i]==null) {
                ret = false;
            }
            else
            {ret= true;}
            break;
        }
        return ret;
    }

}
