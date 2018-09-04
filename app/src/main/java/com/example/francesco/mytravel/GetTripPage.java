package com.example.francesco.mytravel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

public class GetTripPage extends AsyncTask<String, Void, String> {

    private Context mContext;
    private String token;
    private String trip_id;

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_INFO =
            "com.example.francesco.mytravel.extra.TRIP_INFO";

    public GetTripPage(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        trip_id = strings[0];
        token = strings[1];
        return NetworkUtils.getTripPage(trip_id, token);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            // Go to the trip page
            Intent intent = new Intent(mContext, TripPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(TOKEN, token);
            intent.putExtra(TRIP_INFO, s);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

