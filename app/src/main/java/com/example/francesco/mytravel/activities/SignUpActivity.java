package com.example.francesco.mytravel.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.SendAccountCreation;

public class SignUpActivity extends AppCompatActivity {

    private EditText email_field;
    private EditText username_field;
    private EditText psw_field;
    private EditText psw_confirm_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email_field = (EditText) findViewById(R.id.email_signup);
        username_field = (EditText) findViewById(R.id.username_signup);
        psw_field = (EditText) findViewById(R.id.psw_signup);
        psw_confirm_field = (EditText) findViewById(R.id.psw_signup_confirm);
    }

    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void checkDataEntered(View view) {
        if (isEmpty(email_field)) {
            Toast t = Toast.makeText(this, "You must enter an email!", Toast.LENGTH_SHORT);
            t.show();
        } else if (isEmpty(username_field)) {
            Toast t = Toast.makeText(this, "You must enter a username!", Toast.LENGTH_SHORT);
            t.show();
        } else if (isEmpty(psw_field)) {
            Toast t = Toast.makeText(this, "You must enter a password!", Toast.LENGTH_SHORT);
            t.show();
        } else if (isEmpty(psw_confirm_field)) {
            Toast t = Toast.makeText(this, "You must enter password confirmation!", Toast.LENGTH_SHORT);
            t.show();
        } else {
            String psw = psw_field.getText().toString();
            String psw_confirm = psw_confirm_field.getText().toString();
            if (psw.equals(psw_confirm)) {
                createAccount(view);
            } else {
                Toast t = Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    public void createAccount(View view) {
        String queryEmail = email_field.getText().toString();
        String queryUsername = username_field.getText().toString();
        String queryPsw = psw_field.getText().toString();
        new SendAccountCreation(this).execute(queryEmail, queryUsername, queryPsw);
    }
}
