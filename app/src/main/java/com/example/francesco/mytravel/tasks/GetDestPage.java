package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.francesco.mytravel.activities.DestPageActivity;
import com.example.francesco.mytravel.activities.TripPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

public class GetDestPage extends AsyncTask<String, Void, String> {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private static final String DEST_ID =
            "com.example.francesco.mytravel.extra.DEST_ID";

    private static final String LATITUDE =
            "com.example.francesco.mytravel.extra.LATITUDE";

    private static final String LONGITUDE =
            "com.example.francesco.mytravel.extra.LONGITUDE";


    private String trip_id;
    private String dest_id;
    private String token;
    private String latitude;
    private String longitude;
    private Context mContext;

    public GetDestPage(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        trip_id = strings[0];
        dest_id = strings[1];
        token = strings[2];
        latitude = strings[3];
        longitude = strings[4];
        return NetworkUtils.getDestPage(trip_id, dest_id, token);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Dest page result: ", s);

        // Go to the dest page
        Intent intent = new Intent(mContext, DestPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TOKEN, token);
        intent.putExtra(TRIP_ID, trip_id);
        intent.putExtra(DEST_ID, dest_id);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        mContext.startActivity(intent);
    }
}
