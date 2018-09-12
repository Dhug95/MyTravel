package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.francesco.mytravel.utils.NetworkUtils;
import com.example.francesco.mytravel.utils.PartListAdapter;
import com.example.francesco.mytravel.utils.TripItem;
import com.example.francesco.mytravel.utils.TripListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class GetPartList extends AsyncTask<String, Void, String> {

    private Context mContext;
    private final LinkedList<String> mPartList;
    private RecyclerView mRecyclerView;
    private PartListAdapter mAdapter;

    private TextView numberPart;

    public GetPartList(Context mContext, LinkedList<String> mPartList,
                       RecyclerView mRecyclerView, PartListAdapter mAdapter, TextView numberPart) {
        this.mContext = mContext;
        this.mPartList = mPartList;
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = mAdapter;
        this.numberPart = numberPart;
    }

    @Override
    protected String doInBackground(String... strings) {
        String token = strings[0];
        String trip_id = strings[1];
        return NetworkUtils.getPartList(trip_id, token);
    }

    private void reset(LinkedList<TripItem> list) {
        while (!list.isEmpty()) {
            list.removeFirst();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            mPartList.clear();

            JSONArray jsonObject = new JSONArray(s);
            for (int i = 0; i < jsonObject.length(); i++) {
                String next = jsonObject.getString(i);
                mPartList.add(next);
            }

            numberPart.setText("Number of participants: " + mPartList.size());

            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            // Inflate the layout for this fragment

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
