package com.example.francesco.mytravel.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.AddDestination;
import com.example.francesco.mytravel.tasks.DeleteTrip;
import com.example.francesco.mytravel.tasks.GetDestList;
import com.example.francesco.mytravel.utils.DestItem;
import com.example.francesco.mytravel.utils.DestListAdapter;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.LinkedList;

public class TripPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private static final String NUM_PARTICIPANTS =
            "com.example.francesco.mytravel.extra.NUM_PARTICIPANTS";

    private static final String PLACES = "Places SDK";

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private String token;
    private String trip_id;
    private String num_participants;

    private final LinkedList<DestItem> mDestinationList = new LinkedList<>();

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.hellosharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        num_participants = intent.getStringExtra(NUM_PARTICIPANTS);
        trip_id = intent.getStringExtra(TRIP_ID);

        mPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        if (token == null) {
            // Restore preferences
            token = mPreferences.getString(TOKEN, null);
        }
        if (trip_id == null) {
            trip_id = mPreferences.getString(TRIP_ID, null);
        }
        if (num_participants == null) {
            num_participants = mPreferences.getString(NUM_PARTICIPANTS, null);
        }

        // Get a handle to the RecyclerView.
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_destinations);
        // Create an adapter and supply the data to be displayed.
        DestListAdapter mAdapter = new DestListAdapter(this, mDestinationList);
        // Connect the adapter with the RecyclerView.

        new GetDestList(this, mDestinationList, mRecyclerView, mAdapter).execute(token, trip_id);

        TextView infoParticipants = (TextView) findViewById(R.id.num_participants);
        infoParticipants.setText("Participants: " + num_participants);

        TextView infoBalance = (TextView) findViewById(R.id.balance);
        infoBalance.setText("0");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_trip, menu);
        return true;
    }

    public void deleteTrip(View view) {
        new DeleteTrip(this).execute(trip_id, token);
    }

    public void addDestination(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d("RepairableException: ", e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("NotAvailableException: ", e.getMessage());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                String city = place.getName().toString();
                Log.d(PLACES, "Place: " + city);

                String country = "Random";

                String latitude = Double.toString(place.getLatLng().latitude);
                String longitude = Double.toString(place.getLatLng().longitude);

                new AddDestination(this, token).execute(city, country, trip_id, latitude, longitude);
                this.recreate();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
                Log.d(PLACES, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Task canceled by the user.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if (token != null) {
            preferencesEditor.putString(TOKEN, token);
            preferencesEditor.putString(TRIP_ID, trip_id);
            preferencesEditor.putString(NUM_PARTICIPANTS, num_participants);
        }

        preferencesEditor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if (token != null) {
            preferencesEditor.putString(TOKEN, token);
            preferencesEditor.putString(TRIP_ID, trip_id);
            preferencesEditor.putString(NUM_PARTICIPANTS, num_participants);
        }

        preferencesEditor.apply();
    }

    public void goToPage(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_participants:
                // Go to the participants page
                Intent intent = new Intent(this, ParticipantsPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TOKEN, token);
                intent.putExtra(TRIP_ID, trip_id);
                startActivity(intent);
                //finish();
                break;

            case R.id.action_payments:
                // Go to the participants page
                Intent newIntent = new Intent(this, PaymentPageActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.putExtra(TOKEN, token);
                newIntent.putExtra(TRIP_ID, trip_id);
                startActivity(newIntent);
                break;

            default:
                break;
        }
    }
}
