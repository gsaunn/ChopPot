package com.example.dabby3pleminds.choppot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by DABBY(3pleMinds) on 02-Jun-15.
 */
public class MyDialogFragment extends DialogFragment{
    private int ID;

    public MyDialogFragment() {
        super();
    }


    public interface MyDialogInterfaceListener
    {
        public void onDialogPositiveClick(MyDialogFragment dialog);
        public void onDialogNegativeClick(MyDialogFragment dialog);
    }

    //use the instance of the interface
    MyDialogInterfaceListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    //verify the host activity implements the call back listener
        try{
            //instantiate the listener so that we can send message to the host
            mListener= (MyDialogInterfaceListener) activity;


        }catch (ClassCastException e)
        {
            //the activity does implement it
            throw new ClassCastException(activity.toString()+
            "must implement  MyDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the interfcae

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (getTag())
        {
            case "HISTORY_DEL_DIALOG":
            case "HISTORY_DEL_ALL_DIALOG":
            case "FAVOURITE_DEL_DIALOG":
            case "FAVOURITE_DEL_ALL_DIALOG":
            case "FRIENDS_DEL_DIALOG":

            {
                builder.setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Toast.makeText(getActivity(),"Item Deleted",Toast.LENGTH_SHORT);
                                mListener.onDialogPositiveClick(MyDialogFragment.this);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Item Deleted", Toast.LENGTH_SHORT);
                                mListener.onDialogNegativeClick(MyDialogFragment.this);

                            }
                        });
                break;

            }
            case "ORDER_DIALOG":
            {
                builder.setMessage(R.string.sure_to_delete)
                        //.setView(R.layout.order)
                        .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getActivity(),"List Updated",Toast.LENGTH_SHORT);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Item Deleted", Toast.LENGTH_SHORT);

                            }
                        });
                break;


            }default:{

        }
        }



        return builder.create();
    }


}