package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.DeleteTrip;

public class TripPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private static final String NUM_PARTICIPANTS =
            "com.example.francesco.mytravel.extra.NUM_PARTICIPANTS";

    private String token;
    private String num_participants;
    private String trip_id;

    private TextView infoParticipants;

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
}
