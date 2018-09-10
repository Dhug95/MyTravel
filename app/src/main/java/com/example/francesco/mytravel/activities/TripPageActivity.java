package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
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

    private final LinkedList<DestItem> mDestinationList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        String num_participants = intent.getStringExtra(NUM_PARTICIPANTS);
        trip_id = intent.getStringExtra(TRIP_ID);

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

                new AddDestination(this, token).execute(city, country, trip_id);
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
}
