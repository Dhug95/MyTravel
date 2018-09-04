package com.example.francesco.mytravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TripPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_INFO =
            "com.example.francesco.mytravel.extra.TRIP_INFO";

    private String token;
    private String trip_info;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_info = intent.getStringExtra(TRIP_INFO);

        infoText = (TextView) findViewById(R.id.trip_info);
        infoText.setText(trip_info);
    }
}
