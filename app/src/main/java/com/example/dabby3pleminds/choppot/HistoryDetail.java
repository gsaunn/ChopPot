package com.example.dabby3pleminds.choppot;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class HistoryDetail extends ActionBarActivity{

    private ShareActionProvider mShareActionProvider;

    private static final String APP_TRUE_TALK = "Okafor Jerry Hanks like to make the following orders using choppot app. You can start naking yourd today";

    private final String SHARE_HASH_TAG = "\n\n#Choppot";
    private final String LOG_TAG = HistoryDetail.class.getSimpleName();
    private String[] extra;
    private Toolbar mToolbar    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(R.string.app_name);
        //actionBarHome.setTitleTextAppearance(this, R.style.title);
        mToolbar.setLogo(R.mipmap.ic_launcher);
        //mToolbar.setNavigationIcon(R.mipmap.ic_back);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction
            Fragment detailFrag = new HistoryDetailFragment();
            int Container = R.id.detail;
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(Container, detailFrag)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        //fetch and store share action provider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        //set the intent
        if (mShareActionProvider != null) {

            mShareActionProvider.setShareIntent(createShareIntent(APP_TRUE_TALK));
        } else {
            Log.d(LOG_TAG, "Share Action Provider is Null!");
        }
        return true;
    }

    private Intent createShareIntent(String shareString) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString + SHARE_HASH_TAG);

        return shareIntent;
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
}
