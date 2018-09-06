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
import com.example.francesco.mytravel.tasks.GetTripPage;

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

        Log.d("Image", mCurrent.img);
        byte[] decodedString = Base64.decode(mCurrent.img, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.tripImage.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView tripNameView;
        public final TextView tripStartView;
        public final TextView tripEndView;
        public final ImageView tripImage;

        final TripListAdapter mAdapter;

        private static final String TOKEN =
                "com.example.francesco.mytravel.extra.TOKEN";

        private static final String TRIP_ID =
                "com.example.francesco.mytravel.extra.TRIP_ID";

        private Context mContext;

        public TripViewHolder(View itemView, TripListAdapter adapter) {
            super(itemView);
            mContext = itemView.getContext();
            tripNameView = (TextView) itemView.findViewById(R.id.trip_name);
            tripStartView = (TextView) itemView.findViewById(R.id.trip_start);
            tripEndView = (TextView) itemView.findViewById(R.id.trip_end);
            tripImage = (ImageView) itemView.findViewById(R.id.image_trip);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            TripItem element = mTripList.get(mPosition);

            String token = TripsFragment.getToken();

            // Go to the trip page
            new GetTripPage(mContext).execute(element.id, token);
        }
    }
}
