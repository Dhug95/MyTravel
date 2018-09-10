package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.francesco.mytravel.utils.DestItem;
import com.example.francesco.mytravel.utils.DestListAdapter;
import com.example.francesco.mytravel.utils.NetworkUtils;
import com.example.francesco.mytravel.utils.TripItem;
import com.example.francesco.mytravel.utils.TripListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class GetDestList extends AsyncTask<String, Void, String> {

    private Context mContext;
    private final LinkedList<DestItem> mDestinationList;
    private RecyclerView mRecyclerView;
    private DestListAdapter mAdapter;

    public GetDestList(Context mContext, LinkedList<DestItem> mDestinationList,
                       RecyclerView mRecyclerView, DestListAdapter mAdapter) {
        this.mContext = mContext;
        this.mDestinationList = mDestinationList;
        this.mRecyclerView = mRecyclerView;
        this.mAdapter = mAdapter;
    }

    @Override
    protected String doInBackground(String... strings) {
        String token = strings[0];
        String trip_id = strings[1];
        return NetworkUtils.getDestList(token, trip_id);
    }

    private void reset(LinkedList<DestItem> list) {
        while (!list.isEmpty()) {
            list.removeFirst();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            reset(mDestinationList);

            JSONArray jsonObject = new JSONArray(s);
            for (int i = 0; i < jsonObject.length(); i++) {
                JSONObject nextJSON = jsonObject.getJSONObject(i);
                String name = nextJSON.getString("name");
                String country = nextJSON.getString("country");
                String id = nextJSON.getString("_id");
                String trip = nextJSON.getString("trip");
                String latitude = nextJSON.getString("latitude");
                String longitude = nextJSON.getString("longitude");

                Log.d("TRIP_ID: ", trip);
                Log.d("DEST_ID: ", id);

                DestItem next = new DestItem(name, country, id, trip, latitude, longitude);
                mDestinationList.add(next);
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
