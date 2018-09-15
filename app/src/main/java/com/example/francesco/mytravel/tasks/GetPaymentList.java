package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.francesco.mytravel.ClickListener;
import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.activities.ParticipantsPageActivity;
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

    private TextView currentBalance;

    private String myUsername;
    private Float numberUsers;

    public GetPaymentList(Context mContext, LinkedList<PaymentItem> mPaymentList,
                          RecyclerView mRecyclerView, PaymentListAdapter mAdapter, TextView currentBalance) {
        this.mContext = mContext;
        this.mPaymentList = mPaymentList;
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = mAdapter;
        this.currentBalance = currentBalance;

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
            JSONObject porcoddio = new JSONObject(s);

            JSONArray jsonObject = porcoddio.getJSONArray("payments");

            myUsername = porcoddio.getString("user");
            numberUsers = Float.valueOf(porcoddio.getString("number"));

            for (int i = 0; i < jsonObject.length(); i++) {
                JSONObject nextJSON = jsonObject.getJSONObject(i);
                String username = nextJSON.getString("username");
                String amount = nextJSON.getString("amount");
                String id = nextJSON.getString("_id");
                PaymentItem next = new PaymentItem(username, amount, id);
                mPaymentList.add(next);
            }

            calculateBalance();

            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            // Inflate the layout for this fragment
        } catch (Exception e) {
            Log.d("Exception: ", e.getMessage());
        }
    }

    private void calculateBalance() {
        Float balance = 0.f;
        for(PaymentItem p: mPaymentList) {
            Float x = Float.valueOf(p.amount);
            Float single_pay = x / numberUsers;
            if (myUsername.equals(p.user)) {
                balance = balance + (x - single_pay);
            } else {
                balance = balance - single_pay;
            }
        }
        if (balance >= 0.f) {
            currentBalance.setTextColor(Color.GREEN);
        } else {
            currentBalance.setTextColor(Color.RED);

        }
        currentBalance.setText("Balance: " + Float.toString(balance));
    }
}
