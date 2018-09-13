package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.activities.AddPaymentActivity;
import com.example.francesco.mytravel.activities.ParticipantsPageActivity;
import com.example.francesco.mytravel.activities.PaymentPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class SendPayment extends AsyncTask<String, Void, String> {

    private Context mContext;

    private String trip_id;
    private String token;
    private String amount;

    public SendPayment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        trip_id = strings[0];
        token = strings[1];
        amount = strings[2];
        return NetworkUtils.sendPayment(trip_id, token, amount);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Add payment result: ", s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String success;
            String message;

            success = jsonObject.getString("success");
            message = jsonObject.getString("message");

            if (success.equals("true")) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, PaymentPageActivity.class);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
