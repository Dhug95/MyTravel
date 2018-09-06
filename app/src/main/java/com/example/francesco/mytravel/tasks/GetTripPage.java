package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.francesco.mytravel.utils.NetworkUtils;
import com.example.francesco.mytravel.activities.TripPageActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetTripPage extends AsyncTask<String, Void, String> {

    private Context mContext;
    private String token;
    private String trip_id;

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String NUM_PARTICIPANTS =
            "com.example.francesco.mytravel.extra.NUM_PARTICIPANTS";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

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
            JSONObject jsonObject = new JSONObject(s);
            JSONArray participants = jsonObject.getJSONArray("participants");
            String numParticipants = Integer.toString(participants.length());

            // Go to the trip page
            Intent intent = new Intent(mContext, TripPageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(TOKEN, token);
            intent.putExtra(NUM_PARTICIPANTS, numParticipants);
            intent.putExtra(TRIP_ID, trip_id);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

