package com.example.francesco.mytravel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class GetTripList extends AsyncTask<String, Void, String> {

    private Context mContext;
    private final LinkedList<TripItem> mTripList;
    private RecyclerView mRecyclerView;
    private TripListAdapter mAdapter;

    public GetTripList(Context mContext, LinkedList<TripItem> mTripList,
                       RecyclerView mRecyclerView, TripListAdapter mAdapter) {
        this.mContext = mContext;
        this.mTripList = mTripList;
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = mAdapter;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getTripList(strings[0]);
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
            reset(mTripList);

            JSONArray jsonObject = new JSONArray(s);
            for (int i = 0; i < jsonObject.length(); i++) {
                JSONObject nextJSON = jsonObject.getJSONObject(i);
                String name = nextJSON.getString("name");
                String startDate = nextJSON.getString("startDate");
                String endDate = nextJSON.getString("endDate");
                TripItem next = new TripItem(name, startDate, endDate);
                mTripList.add(next);
            }

            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            // Inflate the layout for this fragment

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
