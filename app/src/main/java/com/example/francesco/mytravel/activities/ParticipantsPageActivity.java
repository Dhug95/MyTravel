package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);

        // Get a handle to the RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_participants);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new PartListAdapter(this, mParticipantsList, new ClickListener() {
            @Override public void onPositionClicked(int position) {
                Log.d("Click: ", "Button pressed");
            }
        });

        new GetPartList(this, mParticipantsList, mRecyclerView, mAdapter).execute(token, trip_id);

    }

    public void addParticipant(View view) {
        Intent intent = new Intent(this, AddParticipantActivity.class);
        intent.putExtra(TOKEN, token);
        intent.putExtra(TRIP_ID, trip_id);
        startActivity(intent);
    }
}
