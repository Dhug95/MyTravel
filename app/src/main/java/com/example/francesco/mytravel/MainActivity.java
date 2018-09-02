package com.example.francesco.mytravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText email_login;
    private EditText psw_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_login = (EditText) findViewById(R.id.email_login);
        psw_login = (EditText) findViewById(R.id.psw_login);
    }

    public void launchSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void checkDataEntered(View view) {
        if (isEmpty(email_login)) {
            Toast t = Toast.makeText(this, "You must enter an email!", Toast.LENGTH_SHORT);
            t.show();
        } else if (isEmpty(psw_login)) {
            Toast t = Toast.makeText(this, "You must enter a password!", Toast.LENGTH_SHORT);
            t.show();
        } else {
            sendLoginData(view);
        }
    }

    public void sendLoginData(View view) {
        String queryEmail = email_login.getText().toString();
        String queryPsw = psw_login.getText().toString();
        new SendLoginData(this).execute(queryEmail, queryPsw);
    }
}
