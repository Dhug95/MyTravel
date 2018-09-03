package com.example.francesco.mytravel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

public class SendLoginData extends AsyncTask<String, Void, String> {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private Context mContext;

    public SendLoginData(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getLoginResponse(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String success;
            String message;
            String token;

            try {
                success = jsonObject.getString("success");
                message = jsonObject.getString("message");

                if (success.equals("true")) {
                    // Show the token
                    token = jsonObject.getString("token");

                    // Go to the logged home
                    Intent intent = new Intent(mContext, LoggedHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(TOKEN, token);
                    mContext.startActivity(intent);
                } else {
                    Toast t = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
                    t.show();
                }
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
