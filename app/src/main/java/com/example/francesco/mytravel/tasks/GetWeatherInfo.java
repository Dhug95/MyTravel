package com.example.francesco.mytravel.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetWeatherInfo extends AsyncTask<String, Void, String> {

    private String latitude;
    private String longitude;
    private String APIKEY;

    private TextView cityCountry;
    private TextView weatherInfo;
    private TextView temperature;
    private TextView wind;

    private  TextView mCurrency;

    public GetWeatherInfo(TextView CC, TextView WI, TextView T, TextView W, TextView mC) {
        cityCountry = CC;
        weatherInfo = WI;
        temperature = T;
        wind = W;
        mCurrency = mC;
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

            cityCountry.setText(country);
            weatherInfo.setText("Status: " + main_condition + ", " + condition_desc);
            temperature.setText("Temperature: " + temp);
            wind.setText("Wind speed: " + windSpeed);

            Log.d("Weather info: ", "Finished");
            new GetCountryCurrency(mCurrency).execute(country);
        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
