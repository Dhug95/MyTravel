package com.example.francesco.mytravel.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.SendTripData;

import java.io.FileNotFoundException;


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
    String filePath;
    TextView startText;
    TextView endText;
    EditText tripName;

    TextView uploadResult;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.hellosharedprefs";

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

        uploadResult = (TextView) v.findViewById(R.id.image_result);

        if (start != null) {
            startText.setText(start);
        }
        if (end != null) {
            endText.setText(end);
        }

        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        if (!startText.getText().toString().equals("") || !endText.getText().toString().equals("")) {
            // Restore preferences
            tripName.setText(mPreferences.getString("NAME", null));
            uploadResult.setText(mPreferences.getString("IMAGE", null));
        }

        Button startDateButton = (Button) v.findViewById(R.id.start_date_button);
        Button endDateButton = (Button) v.findViewById(R.id.end_date_button);
        FloatingActionButton createTrip = (FloatingActionButton) v.findViewById(R.id.create_trip);
        Button uploadImage = (Button) v.findViewById(R.id.upload_image);
        Button resetButton = (Button) v.findViewById(R.id.button_reset);

        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
        createTrip.setOnClickListener(this);
        resetButton.setOnClickListener(this);

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

            case R.id.upload_image:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                break;

            case R.id.button_reset:
                tripName.setText("");
                startText.setText("");
                endText.setText("");
                uploadResult.setText("");
                break;

            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        preferencesEditor.putString("NAME", tripName.getText().toString());
        preferencesEditor.putString("IMAGE", uploadResult.getText().toString());

        preferencesEditor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        preferencesEditor.putString("NAME", tripName.getText().toString());
        preferencesEditor.putString("IMAGE", uploadResult.getText().toString());

        preferencesEditor.apply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                filePath = getRealPathFromURI(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                uploadResult.setText(filePath);

                try {
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                        //FINE
                    } else {
                        //NOT IN REQUIRED FORMAT
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
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
        new SendTripData(getContext()).execute(queryName, queryStart, queryEnd, filePath, token);
    }
}
