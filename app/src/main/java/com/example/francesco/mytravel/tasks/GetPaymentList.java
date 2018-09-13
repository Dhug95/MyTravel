package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.francesco.mytravel.ClickListener;
import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.activities.PaymentPageActivity;
import com.example.francesco.mytravel.utils.NetworkUtils;
import com.example.francesco.mytravel.utils.PaymentItem;
import com.example.francesco.mytravel.utils.PaymentListAdapter;
import com.example.francesco.mytravel.utils.TripItem;
import com.example.francesco.mytravel.utils.TripListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ContentHandler;
import java.util.LinkedList;

public class GetPaymentList extends AsyncTask<String, Void, String> {

    private Context mContext;
    private final LinkedList<PaymentItem> mPaymentList;
    private RecyclerView mRecyclerView;
    private PaymentListAdapter mAdapter;

    public GetPaymentList(Context mContext, LinkedList<PaymentItem> mPaymentList,
                          RecyclerView mRecyclerView, PaymentListAdapter mAdapter) {
        this.mContext = mContext;
        this.mPaymentList = mPaymentList;
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = mAdapter;

    }

    @Override
    protected String doInBackground(String... strings) {
        String token = strings[0];
        String trip_id = strings[1];
        return NetworkUtils.getPaymentList(token, trip_id);
    }

    private void reset(LinkedList<PaymentItem> list) {
        while (!list.isEmpty()) {
            list.removeFirst();
        }
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d("Get payments", s);

        reset(mPaymentList);

        try {
            JSONArray jsonObject = new JSONArray(s);
            for (int i = 0; i < jsonObject.length(); i++) {
                JSONObject nextJSON = jsonObject.getJSONObject(i);
                String username = nextJSON.getString("username");
                String amount = nextJSON.getString("amount");
                String id = nextJSON.getString("_id");
                PaymentItem next = new PaymentItem(username, amount, id);
                mPaymentList.add(next);
            }

            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            // Inflate the layout for this fragment
        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }
}
