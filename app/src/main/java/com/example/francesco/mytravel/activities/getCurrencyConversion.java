package com.example.francesco.mytravel.activities;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

class getCurrencyConversion extends AsyncTask<String, Void, String> {

    private TextView outputCurrencyView;
    private String outputCode;
    private String inputValue;


    public getCurrencyConversion(TextView outputCurrencyView) {
        this.outputCurrencyView = outputCurrencyView;
    }


    @Override
    protected String doInBackground(String... strings) {
        inputValue = strings[0];
        outputCode = strings[1];
        return NetworkUtils.getCurrencyConversion(inputValue, outputCode);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject result = new JSONObject(s);
            String label = "EUR_" + outputCode;

            JSONObject valueObj = result.getJSONObject(label);

            Double input = Double.valueOf(inputValue);
            Double value = Double.valueOf(valueObj.getString("val"));

            Double output = input * value;

            outputCurrencyView.setText(output.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
