package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.SendNewParticipant;

public class AddParticipantActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private String token;
    private String trip_id;

    private EditText newParticipant;
    private Button addParticipantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);

        newParticipant = findViewById(R.id.part_name);
        addParticipantButton = findViewById(R.id.add_p_button);

    }

    public void addNewParticipant(View view) {
        String username = newParticipant.getText().toString();
        new SendNewParticipant(token, this).execute(trip_id, username);
    }
}
