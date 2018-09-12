package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.activities.ParticipantsPageActivity;
import com.example.francesco.mytravel.activities.TripPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class DeletePart extends AsyncTask<String, Void, String> {

    private Context mContext;

    public DeletePart(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String trip_id = strings[0];
        String username = strings[1];
        String token = strings[2];
        return NetworkUtils.removeParticipant(trip_id, username, token);
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
                Intent intent = new Intent(mContext, ParticipantsPageActivity.class);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }

}
