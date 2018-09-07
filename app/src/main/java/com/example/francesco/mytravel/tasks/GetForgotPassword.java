package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.activities.LoggedHomeActivity;
import com.example.francesco.mytravel.activities.MainActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class GetForgotPassword extends AsyncTask<String, Void, String> {

    private static final String FORGOT_PSW =
            "com.example.francesco.mytravel.extra.FORGOT_PSW";

    private Context mContext;

    public GetForgotPassword(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String queryEmail = strings[0];
        return NetworkUtils.requestPassword(queryEmail);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Forgot result: ", s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String success = jsonObject.getString("success");
            String message = jsonObject.getString("message");

            if (success.equals("false")) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            } else {
                // Go back to main activity
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(FORGOT_PSW, message);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
