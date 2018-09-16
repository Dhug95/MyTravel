package com.example.francesco.mytravel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.activities.NewDestPageActivity;
import com.example.francesco.mytravel.tasks.GetCountryCode;
import com.example.francesco.mytravel.tasks.GetCurrencyConversion;
import com.example.francesco.mytravel.tasks.GetWeatherInfo;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

public class CurrencyFragment extends Fragment implements View.OnClickListener {

    private static final String LATITUDE =
            "com.example.francesco.mytravel.extra.LATITUDE";

    private static final String LONGITUDE =
            "com.example.francesco.mytravel.extra.LONGITUDE";

    private String latitude;
    private String longitude;
    private String APIKEY_WEATHER = "a20716cacf4034bea5188e75d6a0b44b";

    private TextView countryCode;

    private EditText inputValue;
    private TextView inputCode;
    private TextView outputValue;
    private TextView outputCode;

    private Button selectCurrency;
    private Button convertCurrency;
    private Button reverseCodes;

    private CurrencyPicker picker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency, container, false);

        countryCode = v.findViewById(R.id.country_code);

        inputValue = v.findViewById(R.id.currency_input_value);
        inputCode = v.findViewById(R.id.currency_input_code);
        outputValue = v.findViewById(R.id.currency_output_value);
        outputCode = v.findViewById(R.id.currency_output_code);

        selectCurrency = v.findViewById(R.id.select_currency);
        convertCurrency = v.findViewById(R.id.convert_button);
        reverseCodes = v.findViewById(R.id.reverse_codes);

        selectCurrency.setOnClickListener(this);
        convertCurrency.setOnClickListener(this);
        reverseCodes.setOnClickListener(this);

        try {
            latitude = getArguments().getString(LATITUDE);
            longitude = getArguments().getString(LONGITUDE);

            Log.d("LATITUDE: ", latitude);
            Log.d("LONGITUDE: ", longitude);

            new GetCountryCode(countryCode, outputCode).execute(latitude, longitude, APIKEY_WEATHER);

            picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
            picker.setListener(new CurrencyPickerListener() {
                @Override
                public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                    inputCode.setText(code);
                    picker.dismiss();
                }
            });

        } catch (Exception e) {
            Log.d("Exception in currency: ", e.getMessage());
        }

        return v;
    }

    public void showPicker(View view) {
        picker.show(getActivity().getSupportFragmentManager(), "CURRENCY_PICKER");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_currency: {
                showPicker(view);
                return;
            }
            case R.id.convert_button: {
                if (inputValue.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Insert a value", Toast.LENGTH_LONG).show();
                } else if (inputCode.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Choose a currency to convert", Toast.LENGTH_LONG).show();
                } else {
                    String inputValueString = inputValue.getText().toString();
                    String inputCodeString = inputCode.getText().toString();
                    String outputCodeString = outputCode.getText().toString();
                    new GetCurrencyConversion(inputCodeString, outputValue).execute(inputValueString, outputCodeString);
                }
                return;
            }
            case R.id.reverse_codes: {
                switchInput(view);
                return;
            }
        }
    }

    public void switchInput(View view) {
        inputValue.setText("");
        outputValue.setText("");
        String inputString = inputCode.getText().toString();
        String outputString = outputCode.getText().toString();
        inputCode.setText(outputString);
        outputCode.setText(inputString);
    }
}
