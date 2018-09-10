package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.francesco.mytravel.activities.TripPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class AddDestination extends AsyncTask<String, Void, String> {

    private Context mContext;
    private String token;

    public AddDestination(Context mContext, String token) {
        this.mContext = mContext;
        this.token = token;
    }

    @Override
    protected String doInBackground(String... strings) {
        String name = strings[0];
        String country = strings[1];
        String trip_id = strings[2];
        String latitude = strings[3];
        String longitude = strings[4];
        return NetworkUtils.sendDestData(name, country, trip_id, token, latitude, longitude);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String message = jsonObject.getString("message");

            //Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Internal error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
