package com.example.francesco.mytravel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.GetWeatherInfo;

public class WeatherFragment extends Fragment {

    private static final String LATITUDE =
            "com.example.francesco.mytravel.extra.LATITUDE";

    private static final String LONGITUDE =
            "com.example.francesco.mytravel.extra.LONGITUDE";

    private String latitude;
    private String longitude;
    private String APIKEY_WEATHER ="a20716cacf4034bea5188e75d6a0b44b";

    private ImageView weatherImage;
    private TextView countryCode;
    private TextView statusInfo;
    private TextView tempInfo;
    private TextView windInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        try {
            latitude = getArguments().getString(LATITUDE);
            longitude = getArguments().getString(LONGITUDE);

            Log.d("LATITUDE: ", latitude);
            Log.d("LONGITUDE: ", longitude);

            weatherImage = v.findViewById(R.id.weather_image);
            countryCode = v.findViewById(R.id.country_code);
            statusInfo = v.findViewById(R.id.weather_status);
            tempInfo = v.findViewById(R.id.weather_temp);
            windInfo = v.findViewById(R.id.weather_wind);

            new GetWeatherInfo(weatherImage, countryCode, statusInfo, tempInfo, windInfo).execute(latitude, longitude, APIKEY_WEATHER);

        } catch (Exception e) {
            Log.d("Exception in weather: ", e.getMessage());
        }

        return v;
    }

}
