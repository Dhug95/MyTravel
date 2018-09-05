package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.francesco.mytravel.R;

public class TripPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_INFO =
            "com.example.francesco.mytravel.extra.TRIP_INFO";

    private static final String NUM_PARTICIPANTS =
            "com.example.francesco.mytravel.extra.NUM_PARTICIPANTS";

    private String token;
    private String trip_info;
    private String num_participants;

    private TextView infoText;
    private TextView infoParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_info = intent.getStringExtra(TRIP_INFO);
        num_participants = intent.getStringExtra(NUM_PARTICIPANTS);

        infoText = (TextView) findViewById(R.id.trip_info);
        infoText.setText(trip_info);

        infoParticipants = (TextView) findViewById(R.id.num_participants);
        infoParticipants.setText("Participants: " + num_participants);
    }
}
