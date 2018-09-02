package com.example.francesco.mytravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
                    setFragment(addTripFragment);
                    return true;
                case R.id.navigation_trips:
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

        setFragment(addTripFragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void logOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
