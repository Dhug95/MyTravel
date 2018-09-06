package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.activities.LoggedHomeActivity;
import com.example.francesco.mytravel.activities.TripPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class DeleteTrip extends AsyncTask<String,Void,String> {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private Context mContext;
    private String token;

    public DeleteTrip(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        token = strings[1];
        return NetworkUtils.deleteTrip(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Delete resul: ", s);
        try {
            JSONObject json = new JSONObject(s);
            String result = json.getString("success");
            String message = json.getString("message");
            if (result.equals("true")) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                // Delete trip and go back
                Intent intent = new Intent(mContext, LoggedHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TOKEN, token);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            Toast.makeText(mContext, "Internal error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
