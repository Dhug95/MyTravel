package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.activities.NewDestPageActivity;
import com.example.francesco.mytravel.fragments.CurrencyFragment;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetWeatherInfo extends AsyncTask<String, Void, String> {

    private static final String COUNTRY_CODE =
            "com.example.francesco.mytravel.extra.COUNTRY_CODE";

    private String latitude;
    private String longitude;
    private String APIKEY;

    private ImageView weatherImage;
    private TextView countryCode;
    private TextView statusInfo;
    private TextView tempInfo;
    private TextView windInfo;

    public GetWeatherInfo(ImageView weatherImage, TextView countryCode,
                          TextView statusInfo, TextView tempInfo, TextView windInfo) {
        this.weatherImage = weatherImage;
        this.countryCode = countryCode;
        this.statusInfo = statusInfo;
        this.tempInfo = tempInfo;
        this.windInfo = windInfo;
    }

    @Override
    protected String doInBackground(String... strings) {
        latitude = strings[0];
        longitude = strings[1];
        APIKEY = strings[2];
        return NetworkUtils.getWeatherInfo(latitude, longitude, APIKEY);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject allInfo = new JSONObject(s);
            JSONArray weather = allInfo.getJSONArray("weather");

            JSONObject firstElem = weather.getJSONObject(0);
            String main_condition = firstElem.getString("main");
            String condition_desc = firstElem.getString("description");

            JSONObject windObj = allInfo.getJSONObject("wind");
            String windSpeed = windObj.getString("speed");

            JSONObject main = allInfo.getJSONObject("main");
            String temp = main.getString("temp");

            JSONObject sys = allInfo.getJSONObject("sys");
            String country = sys.getString("country");

            String name = allInfo.getString("name");

            Log.d("WEATHER INFO: ", main_condition + " " +
                    condition_desc + " " + windSpeed + " " +
                    temp + " " + country);

            countryCode.setText(country);
            statusInfo.setText(main_condition + ", " + condition_desc);
            tempInfo.setText(temp + " °C");
            windInfo.setText(windSpeed + " km/h");

            if (main_condition.equals("Clear")) {
                weatherImage.setBackgroundResource(R.drawable.sun);
            } else {
                weatherImage.setBackgroundResource(R.drawable.clouds);
            }

            weatherImage.setVisibility(View.VISIBLE);

            Log.d("Weather info: ", "Finished");

        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
