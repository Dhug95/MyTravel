package com.example.francesco.mytravel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

public class SendAccountCreation extends AsyncTask<String, Void, String> {

    public static final String EXTRA_MESSAGE =
            "com.example.francesco.mytravel.extra.MESSAGE";

    private Context mContext;

    public SendAccountCreation(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getSignUpResponse(strings[0], strings[1], strings[2]);
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

                Toast t = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
                t.show();

                if (success.equals("true")) { // Go to the home
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(EXTRA_MESSAGE, s);
                    mContext.startActivity(intent);
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
