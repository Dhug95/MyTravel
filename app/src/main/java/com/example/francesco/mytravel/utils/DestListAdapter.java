package com.example.francesco.mytravel.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.fragments.TripsFragment;
import com.example.francesco.mytravel.tasks.GetDestPage;
import com.example.francesco.mytravel.tasks.GetTripPage;

import java.util.LinkedList;

public class DestListAdapter extends RecyclerView.Adapter<DestListAdapter.DestViewHolder> {

    private final LinkedList<DestItem> mDestList;
    private LayoutInflater mInflater;

    public DestListAdapter(Context context, LinkedList<DestItem> destList) {
        mInflater = LayoutInflater.from(context);
        this.mDestList = destList;
    }


    @Override
    public DestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.destlist_item, parent, false);
        return new DestViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(DestViewHolder holder, int position) {
        DestItem mCurrent = mDestList.get(position);
        holder.destNameView.setText(mCurrent.name);
        holder.destCountryView.setText(mCurrent.country);
    }

    @Override
    public int getItemCount() {
        return mDestList.size();
    }

    class DestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView destNameView;
        public final TextView destCountryView;

        final DestListAdapter mAdapter;

        private static final String TOKEN =
                "com.example.francesco.mytravel.extra.TOKEN";

        private static final String DEST_ID =
                "com.example.francesco.mytravel.extra.DEST_ID";

        private Context mContext;

        public DestViewHolder(View itemView, DestListAdapter adapter) {
            super(itemView);
            mContext = itemView.getContext();
            destNameView = (TextView) itemView.findViewById(R.id.dest_name);
            destCountryView = (TextView) itemView.findViewById(R.id.dest_country);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            DestItem element = mDestList.get(mPosition);

            String token = TripsFragment.getToken();

            // Go to the dest page
            new GetDestPage(mContext).execute(element.trip_id, element.id, token);

        }
    }
}
