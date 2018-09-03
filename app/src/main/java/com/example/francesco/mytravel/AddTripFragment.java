package com.example.francesco.mytravel;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTripFragment extends Fragment implements View.OnClickListener {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String START =
            "com.example.francesco.mytravel.extra.START_DAY";

    private static final String END =
            "com.example.francesco.mytravel.extra.END_DAY";

    String token;
    TextView startText;
    TextView endText;
    EditText tripName;

    public AddTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        token = getArguments().getString(TOKEN);
        String start = getArguments().getString(START);
        String end = getArguments().getString(END);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_trip, container, false);

        startText = (TextView) v.findViewById(R.id.start_text);
        endText = (TextView) v.findViewById(R.id.end_text);
        tripName = (EditText) v.findViewById(R.id.trip_name_create);


        if (start != null) {
            startText.setText(start);
        }
        if (end != null) {
            endText.setText(end);
        }

        Button startDateButton = (Button) v.findViewById(R.id.start_date_button);
        Button endDateButton = (Button) v.findViewById(R.id.end_date_button);
        Button createTrip = (Button) v.findViewById(R.id.create_trip);

        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        createTrip.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date_button:
                showDatePickerDialog(v);
                break;

            case R.id.end_date_button:
                showDatePickerDialog(v);
                break;

            case R.id.create_trip:
                checkDataEntered();
                break;

            default:
                break;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),
                v.getTag().toString());
    }

    private boolean isEmpty(TextView text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void checkDataEntered() {
        if (isEmpty(tripName)) {
            Toast t = Toast.makeText(getContext(), "You must enter a trip name!", Toast.LENGTH_SHORT);
            t.show();
        } else if (isEmpty(startText)) {
            Toast t = Toast.makeText(getContext(), "You must enter a start date!", Toast.LENGTH_SHORT);
            t.show();
        } else if (isEmpty(endText)) {
            Toast t = Toast.makeText(getContext(), "You must enter an end date!", Toast.LENGTH_SHORT);
            t.show();
        } else {
            sendTrip();
        }
    }

    public void sendTrip() {
        String queryName = tripName.getText().toString();
        String queryStart = startText.getText().toString();
        String queryEnd = endText.getText().toString();
        new SendTripData(getContext()).execute(queryName, queryStart, queryEnd, token);
    }
}
