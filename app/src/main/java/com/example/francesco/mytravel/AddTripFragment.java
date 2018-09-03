package com.example.francesco.mytravel;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTripFragment extends Fragment implements View.OnClickListener {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    public AddTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String token = getArguments().getString(TOKEN);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_trip, container, false);

        TextView mTripName = (TextView) v.findViewById(R.id.trip_name_create);
        Button startDateButton = (Button) v.findViewById(R.id.start_date_button);
        Button endDateButton = (Button) v.findViewById(R.id.end_date_button);

        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);

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

            default:
                break;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),
                v.getTag().toString());
    }

}
