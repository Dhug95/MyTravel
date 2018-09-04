package com.example.francesco.mytravel;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TripsFragment extends Fragment {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static String token;

    private final LinkedList<TripItem> mTripList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private TripListAdapter mAdapter;

    public TripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        token = getArguments().getString(TOKEN);

        View v = inflater.inflate(R.layout.fragment_trips, container, false);

        // Get a handle to the RecyclerView.
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_trips);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new TripListAdapter(getContext(), mTripList);
        // Connect the adapter with the RecyclerView.

        new GetTripList(getContext(), mTripList, mRecyclerView, mAdapter).execute(token);

        return v;
    }


    public static String getToken() {
        return token;
    }
}
