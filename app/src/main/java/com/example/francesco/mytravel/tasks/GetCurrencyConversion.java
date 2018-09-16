package com.example.francesco.mytravel.tasks;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class GetCurrencyConversion extends AsyncTask<String, Void, String> {

    private String inputValue;
    private String inputCode;
    private TextView outputValueView;
    private String outputCode;

    public GetCurrencyConversion(String inputCode, TextView outputValueView) {
        this.inputCode = inputCode;
        this.outputValueView = outputValueView;
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

            outputValueView.setText(output.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
