package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.francesco.mytravel.activities.PaymentPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;

import java.net.ContentHandler;

public class GetPaymentList extends AsyncTask<String, Void, String> {

    private Context mContext;

    public GetPaymentList(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String token = strings[0];
        String trip_id = strings[1];
        return NetworkUtils.getPaymentList(token, trip_id);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Get payments", s);
    }
}
