package com.example.francesco.mytravel.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.GetForgotPassword;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email_forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_forgot = (EditText) findViewById(R.id.email_forgot_psw);
    }

    public void getForgotPassword(View view) {
        String queryEmail = email_forgot.getText().toString();
        new GetForgotPassword(this).execute(queryEmail);
    }
}
