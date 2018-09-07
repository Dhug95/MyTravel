package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.francesco.mytravel.activities.LoggedHomeActivity;
import com.example.francesco.mytravel.activities.SignUpActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class SendFacebookLogin extends AsyncTask<String, Void, String> {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private Context mContext;
    private String facebookToken;

    public SendFacebookLogin(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        facebookToken = strings[0];
        return NetworkUtils.facebookLogin(facebookToken);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Fb login result: ", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            // Show the token
            String token = jsonObject.getString("token");

            // Go to the logged home
            Intent intent = new Intent(mContext, LoggedHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(TOKEN, token);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
