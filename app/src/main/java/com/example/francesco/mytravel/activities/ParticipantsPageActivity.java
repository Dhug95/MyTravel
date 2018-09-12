package com.example.francesco.mytravel.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.francesco.mytravel.ClickListener;
import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.GetDestList;
import com.example.francesco.mytravel.tasks.GetPartList;
import com.example.francesco.mytravel.utils.DestListAdapter;
import com.example.francesco.mytravel.utils.PartListAdapter;

import java.util.LinkedList;

public class ParticipantsPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private String token;
    private String trip_id;

    private final LinkedList<String> mParticipantsList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private PartListAdapter mAdapter;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.hellosharedprefs";

    private TextView numberPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);

        numberPart = findViewById(R.id.number_part);

        mPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        if (token == null) {
            // Restore preferences
            token = mPreferences.getString(TOKEN, null);
        }

        if (trip_id == null) {
            trip_id = mPreferences.getString(TRIP_ID, null);
        }

        // Get a handle to the RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_participants);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new PartListAdapter(this, mParticipantsList, trip_id, token, new ClickListener() {
            @Override public void onPositionClicked(int position) {
                Log.d("Click: ", "Button pressed");
            }
        });

    }

    public void addParticipant(View view) {
        Intent intent = new Intent(this, AddParticipantActivity.class);
        intent.putExtra(TOKEN, token);
        intent.putExtra(TRIP_ID, trip_id);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if (token != null) {
            preferencesEditor.putString(TOKEN, token);
            preferencesEditor.putString(TRIP_ID, trip_id);
        }

        preferencesEditor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetPartList(this, mParticipantsList, mRecyclerView, mAdapter, numberPart).execute(token, trip_id);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if (token != null) {
            preferencesEditor.putString(TOKEN, token);
            preferencesEditor.putString(TRIP_ID, trip_id);
        }

        preferencesEditor.apply();
    }
}
