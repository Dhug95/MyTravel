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

public class SendNewParticipant extends AsyncTask<String, Void, String> {

    private String token;
    private Context mContext;

    public SendNewParticipant(String token, Context mContext) {
        this.token = token;
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String trip_id = strings[0];
        String partUsername = strings[1];
        return NetworkUtils.sendNewParticipant(trip_id, partUsername, token);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Add part result: ", s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String success;
            String message;

            success = jsonObject.getString("success");
            message = jsonObject.getString("message");

            if (success.equals("true")) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, ParticipantsPageActivity.class);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
