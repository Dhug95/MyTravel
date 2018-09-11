package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.GetCountryCurrency;
import com.example.francesco.mytravel.tasks.GetWeatherInfo;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

public class DestPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private static final String DEST_ID =
            "com.example.francesco.mytravel.extra.DEST_ID";

    private static final String LATITUDE =
            "com.example.francesco.mytravel.extra.LATITUDE";

    private static final String LONGITUDE =
            "com.example.francesco.mytravel.extra.LONGITUDE";


    private String token;
    private String trip_id;
    private String dest_id;
    private String latitude;
    private String longitude;

    private TextView cityCountry;
    private TextView weatherInfo;
    private TextView temperature;
    private TextView wind;

    private ImageView weatherImage;

    private TextView mCurrency;
    private TextView inputCurrencyCode;
    private EditText inputCurrency;
    private TextView outputCurrency;

    private CurrencyPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);
        dest_id = intent.getStringExtra(DEST_ID);
        latitude = intent.getStringExtra(LATITUDE);
        longitude = intent.getStringExtra(LONGITUDE);

        cityCountry = findViewById(R.id.city_country);
        weatherInfo = findViewById(R.id.weather_status);
        temperature = findViewById(R.id.weather_temp);
        wind = findViewById(R.id.weather_wind);

        weatherImage = findViewById(R.id.weather_image);

        mCurrency = findViewById(R.id.currency_code);
        inputCurrency = findViewById(R.id.currency_input);
        outputCurrency = findViewById(R.id.currency_output);
        inputCurrencyCode = findViewById(R.id.currency_input_code);

        String APIKEY = "a20716cacf4034bea5188e75d6a0b44b";

        new GetWeatherInfo(weatherImage, cityCountry, weatherInfo, temperature, wind, mCurrency).execute(latitude, longitude, APIKEY);

        picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
        picker.setListener(new CurrencyPickerListener() {
            @Override
            public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                inputCurrencyCode.setText(code);
                picker.dismiss();
            }
        });

    }

    public void convertCurrency(View view) {
        if (inputCurrency.getText().toString().equals("")) {
            Toast.makeText(this, "Insert a value", Toast.LENGTH_LONG).show();
        } else if (inputCurrencyCode.getText().toString().equals("")) {
            Toast.makeText(this, "Choose a currency to convert", Toast.LENGTH_LONG).show();
        } else {
            String input = inputCurrency.getText().toString();
            String inputCode = inputCurrencyCode.getText().toString();
            new getCurrencyConversion(inputCode, outputCurrency).execute(input, mCurrency.getText().toString());
        }
    }

    public void showCurrency(View view) {
        picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
    }
}
