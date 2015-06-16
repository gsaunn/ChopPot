package com.example.dabby3pleminds.choppot;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dabby3pleminds.choppot.data.MyDbContract;


public class AddFriends extends ActionBarActivity {

    private Toolbar mToolbar;
    private AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        mToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(R.string.app_name);
        //actionBarHome.setTitleTextAppearance(this, R.style.title);
        mToolbar.setLogo(R.mipmap.ic_launcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent sIntent = new Intent(this,SettingsActivity.class);
            startActivity(sIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //you shall switch the various contextual menus
        int id = item.getItemId();
        switch (id) {
            case R.id.friends_action_add_to_friends:
            {
                //get the current value from the info
                View view = info.targetView;
                view.callOnClick();
                //grab the items and add to favourite database
                TextView friendsNameView = (TextView) view.findViewById(R.id.friends_name);
                String friendsName = friendsNameView.getText().toString();

                TextView friendsPhoneView = (TextView) view.findViewById(R.id.friends_phone);
                String friendsPhone = friendsPhoneView.getText().toString();

                TextView friendsEmailView = (TextView) view.findViewById(R.id.friends_email);
                String friendsEmail = friendsEmailView.getText().toString();

                TextView friendsImageView = (TextView) view.findViewById(R.id.friends_image_url);
                String friendsImageUrl = friendsImageView.getText().toString();

                insertIntoFriendsDB(friendsName, friendsPhone, friendsEmail, friendsImageUrl);

                //Toast.makeText(this,friendsName+" "+friendsPhone+" "+friendsEmail+" "+friendsImageUrl,Toast.LENGTH_LONG).show();

            }default:{

            }
        }
        return super.onContextItemSelected(item);
    }

    private void insertIntoFriendsDB(String friendsName, String friendsPhone, String friendsEmail, String friendsImageUrl) {

        ContentValues values = createFriendsContentValues(friendsName, friendsPhone, friendsEmail, friendsImageUrl);
        Uri uri = MyDbContract.FriendsEntry.CONTENT_URI;

        Uri uriRet = getContentResolver().insert(uri, values);
        Toast.makeText(this, "Item added to friends Database", Toast.LENGTH_SHORT).show();

    }

    private ContentValues createFriendsContentValues(String friendsName, String friendsPhone, String friendsEmail, String friendsImageUrl) {
        ContentValues values = new ContentValues();
        values.put(MyDbContract.FriendsEntry.COLUMN_NAME, friendsName);
        values.put(MyDbContract.FriendsEntry.COLUMN_PHONE, friendsPhone);
        values.put(MyDbContract.FriendsEntry.COLUMN_EMAIL, friendsEmail);
        values.put(MyDbContract.FriendsEntry.COLUMN_PIC_NAME, friendsImageUrl);

        return values;
    }
}
