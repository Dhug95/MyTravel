package com.example.francesco.mytravel.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francesco.mytravel.ClickListener;
import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.fragments.TripsFragment;
import com.example.francesco.mytravel.tasks.DeletePart;
import com.example.francesco.mytravel.tasks.DeleteTrip;
import com.example.francesco.mytravel.tasks.GetTripPage;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class PartListAdapter extends RecyclerView.Adapter<PartListAdapter.PartViewHolder> {

    private final LinkedList<String> mPartList;
    private LayoutInflater mInflater;

    private final ClickListener listener;

    private String trip_id;
    private String token;

    public PartListAdapter(Context context, LinkedList<String> partList, String trip_id, String token, ClickListener listener) {
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        this.mPartList = partList;
        this.trip_id = trip_id;
        this.token = token;
    }


    @Override
    public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.participantlist_item, parent, false);
        return new PartViewHolder(mItemView, this, listener);
    }

    @Override
    public void onBindViewHolder(PartViewHolder holder, int position) {
        String mCurrent = mPartList.get(position);
        holder.partNameView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mPartList.size();
    }

    class PartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView partNameView;
        private Button deleteButton;

        final PartListAdapter mAdapter;

        private static final String TOKEN =
                "com.example.francesco.mytravel.extra.TOKEN";

        private static final String TRIP_ID =
                "com.example.francesco.mytravel.extra.TRIP_ID";

        private Context mContext;
        private WeakReference<ClickListener> listenerRef;

        public PartViewHolder(View itemView, PartListAdapter adapter, ClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);


            mContext = itemView.getContext();
            partNameView = itemView.findViewById(R.id.participant_username);
            this.mAdapter = adapter;

            deleteButton = itemView.findViewById(R.id.delete_participant);

            deleteButton.setOnClickListener(this);
        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == deleteButton.getId()) {
                final String username = mPartList.get(getAdapterPosition());
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to remove participant?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                new DeletePart(mContext).execute(trip_id, username, token);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

            } else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }


}
