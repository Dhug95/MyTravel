package com.example.francesco.mytravel.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetCountryCurrency extends AsyncTask<String, Void, String> {

    private TextView outputCode;

    public GetCountryCurrency(TextView outputCode) {
        this.outputCode = outputCode;
    }

    @Override
    protected String doInBackground(String... strings) {
        String countryCode = strings[0];
        return NetworkUtils.getCountryCurrency(countryCode);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            // {"currencies":[{"code":"EUR","name":"Euro","symbol":"€"}
            JSONObject json = new JSONObject(s);
            JSONArray currencies = json.getJSONArray("currencies");

            JSONObject firstCurrency = currencies.getJSONObject(0);
            String currencyCode = firstCurrency.getString("code");

            outputCode.setText(currencyCode);

            Log.d("Currency info: ", "Finished");
        } catch (Exception e) {
            Log.d("Error: ", e.getMessage());
        }
    }
}
