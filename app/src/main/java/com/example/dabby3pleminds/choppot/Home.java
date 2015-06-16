package com.example.dabby3pleminds.choppot;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dabby3pleminds.choppot.data.MyDbContract;
import com.example.dabby3pleminds.choppot.view.SlidingTabLayout;


public class Home extends ActionBarActivity implements
        HistoryFragment.OnHistoryFragmentInteractionListener,
        OrderFragment.OnOrderFragmentInteractionListener,
        PayForAFriendsFragment.OnPayFragmentInteractionListener,
        FavouriteFragment.OnFavouriteFragmentInteractionListener,
        MyDialogFragment.MyDialogInterfaceListener{

    private Toolbar mToolbar;

    private static final String LOG_TAG = Home.class.getSimpleName();
    private ShareActionProvider mShareActionProvider;

    //share tag
    private final String SHARE_HASH_TAG = "\n\n#Choppot\n http://";

    private final String APP_TRUE_TALK = "Choppot developed by Okafor Jerryhanks chinonso is an awsome app for making order for food across the campus, any where you are!";
    private Context mContext = this;

    //this is used for the context menus item selected once
    private AdapterView.AdapterContextMenuInfo info = null;
    private final int HISTORY_ADAPTER = 0; //history list adapter
    private final int ORDER_TAG = 1;//holds data for pay for a friend
    private final int FRIENDS_ADAPTER = 2; //friends adapter
    private final int FAVOURITE_ADAPTER = 3; //favourite adapter

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(R.string.app_name);
        //actionBarHome.setTitleTextAppearance(this, R.style.title);
        mToolbar.setLogo(R.mipmap.ic_launcher);

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had

        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        // END_INCLUDE (setup_viewpager)

        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)

        if (findViewById(R.id.detail) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
//            ((ItemListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.item_list))
//                    .setActivateOnItemClick(true);
        }else {
            mTwoPane = false;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent sIntent  = new Intent(this,SettingsActivity.class);
            startActivity(sIntent);
        }else if(id==R.id.history_action_clear_all)        {

            MyDialogFragment dialogFragment = new MyDialogFragment();
            dialogFragment.show(getFragmentManager(), "HISTORY_DEL_ALL_DIALOG");

        }else if(id==R.id.action_clear_favourites)
        {
            MyDialogFragment dialogFragment = new MyDialogFragment();
            dialogFragment.show(getFragmentManager(), "FAVOURITE_DEL_ALL_DIALOG");
        }else if(id==R.id.group_order)
        {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //you shall switch the various contextual menus
        int id = item.getItemId();
        switch (id) {
            case R.id.history_add_favourite: {
                View view = info.targetView;
                //grab the items and add to favourite database
                TextView orderView = (TextView) view.findViewById(R.id.history_order);
                String order = orderView.getText().toString();

                TextView orderDrinkView = (TextView) view.findViewById(R.id.history_order_drink);
                String orderDrink = orderDrinkView.getText().toString();

                TextView orderWaterView = (TextView) view.findViewById(R.id.history_order_water);
                String orderWater = orderWaterView.getText().toString();


                TextView orderProviderView = (TextView) view.findViewById(R.id.order_provider);
                String orderProvider = orderProviderView.getText().toString();

                TextView orderTimeView = (TextView) view.findViewById(R.id.order_time);
                String orderTime = orderTimeView.getText().toString();

                TextView orderDateView = (TextView) view.findViewById(R.id.order_date);
                String orderDate = orderDateView.getText().toString();

                TextView orderPrice = (TextView) view.findViewById(R.id.order_price);
                String orderCost = orderPrice.getText().toString();


                //Toast.makeText(this, order+" "+orderProvider+" "+orderTime+" "+orderDate+" "+orderCost, Toast.LENGTH_LONG).show();
                addValuesToFavourite(orderProvider, order, orderDrink, orderWater, orderCost, orderDate, orderTime);
                //get the provider that holds unto it to set or share the intent


                break;
            }
            case R.id.favourite_action_delete: {

                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getFragmentManager(), "FAVOURITE_DEL_DIALOG");
                break;
            }
            case R.id.history_action_delete: {
                //Toast.makeText(this,String.valueOf((int)info.id), Toast.LENGTH_LONG).show();
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getFragmentManager(), "HISTORY_DEL_DIALOG");
                break;
            }
            case R.id.favourite_action_order: {
                //Toast.makeText(this, info.toString(), Toast.LENGTH_LONG).show();
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getFragmentManager(), "QTY_DIALOG");
                break;
            }
        }
        return super.onContextItemSelected(item);

    }

    private void addValuesToFavourite(String orderProvider, String order, String orderDrink, String orderWater, String orderCost, String orderDate, String orderTime) {

        ContentValues values = createFavouriteValues(orderProvider, order, orderDrink, orderWater, orderCost, orderDate, orderTime);
        Uri uri = MyDbContract.FavouriteEntry.CONTENT_URI;
        Uri uriRet = insertValues(uri, values);

        Toast.makeText(this, "History added to Favourite with uri:" + uriRet, Toast.LENGTH_SHORT).show();

    }

    static ContentValues createFavouriteValues(String orderProvider, String order, String orderDrink, String orderWater, String orderCost, String orderDate, String orderTime) {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_PROVIDER, orderProvider);
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_ORDER, order);
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_DRINK, orderDrink);
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_WATER, orderWater);
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_DATE, orderDate);
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_TIME, orderTime);
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_PRICE, orderCost);
        testValues.put(MyDbContract.FavouriteEntry.COLUMN_PRIORITY, "1");

        return testValues;
    }


    //this function inserts value to the content provider
    public Uri insertValues(Uri uri, ContentValues values) {
        Uri locationUri = getContentResolver().
                insert(uri, values);
        return locationUri;
    }

    //this function deletes value from the database
    public int deleteValues(Uri uri, int rowId) {
        int rowsDeleted;
        rowsDeleted = getContentResolver().delete(
                uri,
                "_ID=?",
                new String[]{String.valueOf(rowId)});
        return rowsDeleted;
    }

    //this function deletes value from the database
    public int deleteAllValues(Uri uri) {
        int rowsDeleted;
        rowsDeleted = getContentResolver().delete(
                uri,
                null,
                null);
        return rowsDeleted;
    }


    private Intent createShareIntent(String shareString) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString + SHARE_HASH_TAG);

        return shareIntent;
    }

    @Override
    public void onFragmentInteraction(String[] extra,int type) {
        switch (type)
        {
            case HISTORY_ADAPTER:
            {
                if (mTwoPane) {
                    // In two-pane mode, show the detail view in this activity by
                    // adding or replacing the detail fragment using a
                    // fragment transaction.
                    HistoryDetailFragment fragment = HistoryDetailFragment.newInstance("strings",extra);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail, fragment)
                            .commit();


                } else {
                    // In single-pane mode, simply start the detail activity
                    // for the selected item ID.
                    Intent detailIntent = new Intent(this, HistoryDetail.class);
                    detailIntent.putExtra("strings", extra);
                    startActivity(detailIntent);
                }
                break;

            }case FAVOURITE_ADAPTER:
        {
            if (mTwoPane) {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.
                FavouriteDetailFragment fragment = FavouriteDetailFragment.newInstance("strings",extra);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail, fragment)
                        .commit();

            } else {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.
                Intent detailIntent = new Intent(this, FavouriteDetail.class);
                detailIntent.putExtra("strings", extra);
                startActivity(detailIntent);
            }

            break;

        }default:{

        }
        }



    }


    @Override
    public void onDialogPositiveClick(MyDialogFragment dialog) {
        //Toast.makeText(this, dialog.getTag(), Toast.LENGTH_LONG).show();
        switch (dialog.getTag()) {
            case "HISTORY_DEL_DIALOG": {
                // Toast.makeText(this, dialog.getId(), Toast.LENGTH_LONG).show();
                int rowCreatingContext = (int) info.id;
                Uri uri = Uri.parse(MyDbContract.HistoryEntry.CONTENT_URI + "/" + rowCreatingContext);
                deleteValues(uri, rowCreatingContext);
                break;
            }
            case "HISTORY_DEL_ALL_DIALOG": {
                // Toast.makeText(this, dialog.getId(), Toast.LENGTH_LONG).show();

                Uri uri = MyDbContract.HistoryEntry.CONTENT_URI;
                deleteAllValues(uri);
                break;
            }
            case "FAVOURITE_DEL_ALL_DIALOG": {
                // Toast.makeText(this, dialog.getId(), Toast.LENGTH_LONG).show();

                Uri uri = MyDbContract.FavouriteEntry.CONTENT_URI;
                deleteAllValues(uri);
                break;
            }
            case "FAVOURITE_DEL_DIALOG": {
                // Toast.makeText(this, dialog.getId(), Toast.LENGTH_LONG).show();
                int rowCreatingContext = (int) info.id;
                Uri uri = Uri.parse(MyDbContract.FavouriteEntry.CONTENT_URI + "/" + rowCreatingContext);
                deleteValues(uri, rowCreatingContext);
                break;
            }
            case "FRIENDS_DEL_DIALOG": {
                // Toast.makeText(this, dialog.getId(), Toast.LENGTH_LONG).show();
                int rowCreatingContext = (int) info.id;
                Uri uri = Uri.parse(MyDbContract.FriendsEntry.CONTENT_URI + "/" + rowCreatingContext);
                deleteValues(uri, rowCreatingContext);
                break;
            }
            case "EDIT_DIALOG": {
                //Toast.makeText(this, dialog.getId(), Toast.LENGTH_LONG).show();
                break;
            }
            default: {
            }

        }
    }

    @Override
    public void onDialogNegativeClick(MyDialogFragment dialog) {

    }


    //This is the new PageAdapter class that is used to inflate different layouts for now
    class SamplePagerAdapter extends FragmentStatePagerAdapter {
        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String LOG_TAG = SamplePagerAdapter.class.getSimpleName();

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 4;
        }

            /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p/>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case HISTORY_ADAPTER:
                    return "History";
                case ORDER_TAG:
                    return "Order";
                case FRIENDS_ADAPTER:
                    return "Pay 4 a Friend";
                case FAVOURITE_ADAPTER:
                    return "Favourite";
                default:
                    return "Item " + (position + 1);
            }
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case HISTORY_ADAPTER:
                    return new HistoryFragment();
                case ORDER_TAG:
                    return new OrderFragment();
                case FRIENDS_ADAPTER:
                    return new PayForAFriendsFragment();
                case FAVOURITE_ADAPTER:
                    return new FavouriteFragment();
                default:
                    return null;
            }
//            return new  HomeFragment();
        }

    } //end class sample page adapter

}//end main class

