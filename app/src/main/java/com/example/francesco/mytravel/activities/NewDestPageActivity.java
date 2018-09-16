package com.example.francesco.mytravel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.fragments.CurrencyFragment;
import com.example.francesco.mytravel.fragments.WeatherFragment;
import com.example.francesco.mytravel.tasks.RemoveDest;

import org.w3c.dom.Text;

public class NewDestPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private static final String DEST_ID =
            "com.example.francesco.mytravel.extra.DEST_ID";

    private static final String NAME =
            "com.example.francesco.mytravel.extra.NAME";

    private static final String LATITUDE =
            "com.example.francesco.mytravel.extra.LATITUDE";

    private static final String LONGITUDE =
            "com.example.francesco.mytravel.extra.LONGITUDE";

    private String token;
    private String trip_id;
    private String dest_id;
    private String name;
    private String latitude;
    private String longitude;

    private Bundle bundle;

    private WeatherFragment weatherFragment;
    private CurrencyFragment currencyFragment;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dest_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);
        dest_id = intent.getStringExtra(DEST_ID);
        name = intent.getStringExtra(NAME);
        latitude = intent.getStringExtra(LATITUDE);
        longitude = intent.getStringExtra(LONGITUDE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bundle = new Bundle();
        bundle.putString(LATITUDE, latitude);
        bundle.putString(LONGITUDE, longitude);

        weatherFragment = new WeatherFragment();
        currencyFragment = new CurrencyFragment();

        weatherFragment.setArguments(bundle);
        currencyFragment.setArguments(bundle);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_dest_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.remove_dest) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Do you really want to remove destination?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            new RemoveDest(getApplicationContext()).execute(dest_id, trip_id, token);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    weatherFragment = new WeatherFragment();
                    weatherFragment.setArguments(bundle);
                    return weatherFragment;

                case 1:
                    currencyFragment = new CurrencyFragment();
                    currencyFragment.setArguments(bundle);
                    return currencyFragment;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "WEATHER";

                case 1:
                    return "CURRENCY";

                default:
                    return null;
            }
        }
    }
}
