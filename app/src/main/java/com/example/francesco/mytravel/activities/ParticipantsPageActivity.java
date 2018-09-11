package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.francesco.mytravel.R;

public class ParticipantsPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private String token;
    private String trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);

    }

    public void addParticipant(View view) {
        Intent intent = new Intent(this, AddParticipantActivity.class);
        intent.putExtra(TOKEN, token);
        intent.putExtra(TRIP_ID, trip_id);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        // create Intent and start MainActivity again
    }
}
