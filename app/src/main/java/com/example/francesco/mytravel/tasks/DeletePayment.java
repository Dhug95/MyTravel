package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.activities.ParticipantsPageActivity;
import com.example.francesco.mytravel.activities.PaymentPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class DeletePayment extends AsyncTask<String, Void, String> {

    private Context mContext;

    public DeletePayment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String trip_id = strings[0];
        String payment_id = strings[1];
        String token = strings[2];
        String username = strings[3];
        String amount = strings[4];
        return NetworkUtils.removePayment(trip_id, payment_id, token, username, amount);
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
                Intent intent = new Intent(mContext, PaymentPageActivity.class);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
