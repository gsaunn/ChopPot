package com.example.dabby3pleminds.choppot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

public class MySmsManager{
    protected String phoneNumber;
    protected String textBody;
    protected Context context;


    public MySmsManager(Context cont,String phone, String text) {
        this.phoneNumber = phone;
        this.textBody = text;
        this.context = cont;
    }
    public void setContext(Context context)
    {
       this.context=context;
    }

    public Context getContext()
    {
        return this.context;
    }

    public boolean sendSmsByManger() {
        boolean ret = false;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(
                    phoneNumber,
                    null,
                    textBody,
                    null,
                    null);
            ret = true;
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean sendSmsBySIntent() {
        boolean ret = false;
        //add the phone number in the data uri
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        //create a new sms Intent
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        //then add the message at the sms body extra
        smsIntent.putExtra("sms_body", textBody);
        try {
            getContext().startActivity(smsIntent);
            ret = true;
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    public boolean sendSmsByVIntent() {
        boolean ret = false;
        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        //prompts only sms-mms clients
        smsVIntent.setType("vnd.android-dir/mms-sms");

        //put extra fields for number and text respectively
        smsVIntent.putExtra("address", phoneNumber);
        smsVIntent.putExtra("sms_body", textBody);

        try {
            getContext().startActivity(smsVIntent);
            ret = true;
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }
}