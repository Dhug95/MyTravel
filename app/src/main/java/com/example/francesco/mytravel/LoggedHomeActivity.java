package com.example.francesco.mytravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoggedHomeActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private AddTripFragment addTripFragment;
    private TripsFragment tripsFragment;
    private ProfileFragment profileFragment;

    private Bundle bundle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_add_trip:
                    addTripFragment.setArguments(bundle);
                    setFragment(addTripFragment);
                    return true;
                case R.id.navigation_trips:
                    tripsFragment.setArguments(bundle);
                    setFragment(tripsFragment);
                    return true;
                case R.id.navigation_profile:
                    profileFragment.setArguments(bundle);
                    setFragment(profileFragment);
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment addTripFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, addTripFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_home);

        Intent intent = getIntent();
        String token = intent.getStringExtra(TOKEN);

        bundle = new Bundle();
        bundle.putString(TOKEN, token);

        addTripFragment = new AddTripFragment();
        tripsFragment = new TripsFragment();
        profileFragment = new ProfileFragment();

        addTripFragment.setArguments(bundle);
        setFragment(addTripFragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void processDatePickerResult(int year, int month, int day, String tag) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        // Assign the concatenated strings to dateMessage.
        String dateMessage = (month_string + "/" +
                day_string + "/" + year_string);
        Toast.makeText(this, tag + ": " + dateMessage,
                Toast.LENGTH_SHORT).show();
    }
}
