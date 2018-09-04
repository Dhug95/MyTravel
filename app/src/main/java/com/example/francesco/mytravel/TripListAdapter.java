package com.example.francesco.mytravel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    private final LinkedList<TripItem> mTripList;
    private LayoutInflater mInflater;

    public TripListAdapter(Context context, LinkedList<TripItem> tripList) {
        mInflater = LayoutInflater.from(context);
        this.mTripList = tripList;
    }


    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.triplist_item, parent, false);
        return new TripViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        TripItem mCurrent = mTripList.get(position);
        holder.tripNameView.setText(mCurrent.name);
        holder.tripStartView.setText(mCurrent.start);
        holder.tripEndView.setText(mCurrent.end);
    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder {

        public final TextView tripNameView;
        public final TextView tripStartView;
        public final TextView tripEndView;
        final TripListAdapter mAdapter;

        public TripViewHolder(View itemView, TripListAdapter adapter) {
            super(itemView);
            tripNameView = (TextView) itemView.findViewById(R.id.trip_name);
            tripStartView = (TextView) itemView.findViewById(R.id.trip_start);
            tripEndView = (TextView) itemView.findViewById(R.id.trip_end);
            this.mAdapter = adapter;
        }

    }
}
