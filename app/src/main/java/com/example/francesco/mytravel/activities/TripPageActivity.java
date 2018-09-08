package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.DeleteTrip;
import com.example.francesco.mytravel.tasks.GetDestinationInfo;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

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
    private String num_participants;
    private String trip_id;

    private TextView infoParticipants;

    private TextView cityName;
    private TextView weatherInfo;
    private TextView countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        num_participants = intent.getStringExtra(NUM_PARTICIPANTS);
        trip_id = intent.getStringExtra(TRIP_ID);

        infoParticipants = (TextView) findViewById(R.id.num_participants);
        infoParticipants.setText("Participants: " + num_participants);

        cityName = findViewById(R.id.city_name);
        weatherInfo = findViewById(R.id.weather_info);
        countryName = findViewById(R.id.country_name);
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

        /* Go to the add destination page
        Intent intent = new Intent(this, AddDestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TOKEN, token);
        startActivity(intent); */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                String city = place.getName().toString();
                Log.d(PLACES, "Place: " + city);

                String placeID = place.getId();

                LatLng latLng = place.getLatLng();
                String lat = Double.toString(latLng.latitude);
                String lng = Double.toString(latLng.longitude);

                new GetDestinationInfo(weatherInfo).execute(lat, lng);

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
