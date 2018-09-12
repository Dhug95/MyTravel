package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.activities.TripPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class RemoveDest extends AsyncTask<String, Void, String> {

    private Context mContext;
    private String dest_id;
    private String trip_id;
    private String token;

    public RemoveDest(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    protected String doInBackground(String... strings) {
        dest_id = strings[0];
        trip_id = strings[1];
        token = strings[2];
        return NetworkUtils.removeDest(dest_id, trip_id, token);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject result = new JSONObject(s);

            String success = result.getString("success");
            String message = result.getString("message");

            if (success.equals("false")) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, TripPageActivity.class);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
