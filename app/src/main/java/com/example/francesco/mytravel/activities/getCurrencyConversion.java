package com.example.francesco.mytravel.activities;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

class getCurrencyConversion extends AsyncTask<String, Void, String> {

    private TextView outputCurrencyView;
    private String outputCode;
    private String inputValue;
    private String inputCode;


    public getCurrencyConversion(String inputCode, TextView outputCurrencyView) {
        this.inputCode = inputCode;
        this.outputCurrencyView = outputCurrencyView;
    }


    @Override
    protected String doInBackground(String... strings) {
        inputValue = strings[0];
        outputCode = strings[1];
        return NetworkUtils.getCurrencyConversion(inputCode, outputCode);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject result = new JSONObject(s);
            String label = inputCode + "_" + outputCode;

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
