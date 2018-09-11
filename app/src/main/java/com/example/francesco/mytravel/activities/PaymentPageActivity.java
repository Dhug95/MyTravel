package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.francesco.mytravel.ClickListener;
import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.GetDestList;
import com.example.francesco.mytravel.tasks.GetPaymentList;

import java.util.LinkedList;

public class PaymentPageActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private String token;
    private String trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);

        new GetPaymentList(this).execute(token, trip_id);
    }

    public void gotoAddPayment(View view) {
        Intent intent = new Intent(this, AddPaymentActivity.class);
        intent.putExtra(TOKEN, token);
        intent.putExtra(TRIP_ID, trip_id);
        startActivity(intent);
    }
}
