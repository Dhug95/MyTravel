package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.francesco.mytravel.activities.AddPaymentActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

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
        Log.d("PAYMENT RESULT: ", s);
    }
}
