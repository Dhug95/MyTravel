package com.example.francesco.mytravel.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetCountryCode extends AsyncTask<String, Void, String> {

    private TextView countryCode;
    private TextView outputCode;

    public GetCountryCode(TextView countryCode, TextView outputCode) {
        this.countryCode = countryCode;
        this.outputCode = outputCode;
    }

    @Override
    protected String doInBackground(String... strings) {
        String latitude = strings[0];
        String longitude = strings[1];
        String APIKEY = strings[2];
        return NetworkUtils.getCountryCode(latitude, longitude, APIKEY);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject allInfo = new JSONObject(s);
            JSONObject sys = allInfo.getJSONObject("sys");
            String country = sys.getString("country");

            Log.d("COUNTRY CODE: ", country);

            countryCode.setText(country);

            new GetCountryCurrency(outputCode).execute(countryCode.getText().toString());

            Log.d("Weather info: ", "Finished");

        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
