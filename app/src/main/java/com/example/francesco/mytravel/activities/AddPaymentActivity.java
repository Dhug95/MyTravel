package com.example.francesco.mytravel.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.SendPayment;

public class AddPaymentActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private static final String TRIP_ID =
            "com.example.francesco.mytravel.extra.TRIP_ID";

    private EditText amount;

    private String token;
    private String trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);
        trip_id = intent.getStringExtra(TRIP_ID);

        amount = (EditText) findViewById(R.id.payment_amount);
    }

    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void checkPaymentData(View view) {
        if (isEmpty(amount)) {
            Toast t = Toast.makeText(this, "You must enter the amount you spent!", Toast.LENGTH_SHORT);
            t.show();
        } else {
            addPayment();
        }
    }

    public void addPayment() {
        String amountText = amount.getText().toString();
        new SendPayment(this).execute(trip_id, token, amountText);
    }
}

