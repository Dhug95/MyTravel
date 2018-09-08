package com.example.francesco.mytravel.tasks;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.francesco.mytravel.utils.NetworkUtils;

public class GetDestinationInfo extends AsyncTask<String, Void, String> {

    private TextView weatherInfo;

    public GetDestinationInfo(TextView weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    @Override
    protected String doInBackground(String... strings) {
        String lat = strings[0];
        String lng = strings[1];
        return NetworkUtils.getWeatherInfo(lat, lng);
    }

    @Override
    protected void onPostExecute(String s) {
        weatherInfo.setText(s);
    }
}
