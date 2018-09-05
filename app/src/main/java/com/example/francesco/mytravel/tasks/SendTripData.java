package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class SendTripData extends AsyncTask<String, Void, String> {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private Context mContext;

    public SendTripData(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getTripResponse(strings[0], strings[1], strings[2], strings[3]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String message = jsonObject.getString("message");

            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Internal error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
