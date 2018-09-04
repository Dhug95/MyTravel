package com.example.francesco.mytravel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

class SendAccountUpdate extends AsyncTask<String, Void, String> {

    private Context mContext;

    public SendAccountUpdate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String username = strings[0];
        String email = strings[1];
        String oldpsw = strings[2];
        String newpsw = strings[3];
        String token = strings[4];
        return NetworkUtils.sendProfileUpdate(username, email, oldpsw, newpsw, token);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String success;
            String message;

            try {
                success = jsonObject.getString("success");
                message = jsonObject.getString("message");

                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            Toast.makeText(mContext, "Internal error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
