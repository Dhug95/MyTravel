package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.utils.NetworkUtils;

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
        Toast t = Toast.makeText(mContext, "User correctly added.", Toast.LENGTH_SHORT);
        t.show();
    }
}
