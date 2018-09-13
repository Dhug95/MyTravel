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
import com.example.francesco.mytravel.tasks.DeletePayment;
import com.example.francesco.mytravel.tasks.DeleteTrip;
import com.example.francesco.mytravel.tasks.GetTripPage;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.PaymentViewHolder> {

    private final LinkedList<PaymentItem> mPaymentList;
    private LayoutInflater mInflater;

    private final ClickListener listener;

    private String trip_id;
    private String token;

    public PaymentListAdapter(Context context, LinkedList<PaymentItem> paymentList, String trip_id, String token, ClickListener listener) {
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        this.mPaymentList = paymentList;
        this.trip_id = trip_id;
        this.token = token;
    }


    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.paymentlist_item, parent, false);
        return new PaymentViewHolder(mItemView, this, listener);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        PaymentItem mCurrent = mPaymentList.get(position);
        holder.paymentUserView.setText(mCurrent.user);
        holder.paymentAmountView.setText(mCurrent.amount);
    }

    @Override
    public int getItemCount() {
        return mPaymentList.size();
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView paymentUserView;
        public final TextView paymentAmountView;
        private Button deleteButton;

        final PaymentListAdapter mAdapter;

        private static final String TOKEN =
                "com.example.francesco.mytravel.extra.TOKEN";

        private static final String TRIP_ID =
                "com.example.francesco.mytravel.extra.TRIP_ID";

        private Context mContext;
        private WeakReference<ClickListener> listenerRef;

        public PaymentViewHolder(View itemView, PaymentListAdapter adapter, ClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);


            mContext = itemView.getContext();
            paymentUserView = itemView.findViewById(R.id.payment_username);
            paymentAmountView = itemView.findViewById(R.id.payment_amount);
            this.mAdapter = adapter;

            deleteButton = itemView.findViewById(R.id.delete_payment);

            deleteButton.setOnClickListener(this);
        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == deleteButton.getId()) {
                final PaymentItem payment = mPaymentList.get(getAdapterPosition());
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to remove payment?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                new DeletePayment(mContext).execute(trip_id, payment.id, token, payment.user, payment.amount);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

            } else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }


}
