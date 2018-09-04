package com.example.francesco.mytravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateUserActivity extends AppCompatActivity {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private String token;

    private EditText new_username;
    private EditText new_email;
    private EditText old_psw;
    private EditText new_psw;
    private EditText new_psw_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra(TOKEN);

        setContentView(R.layout.activity_update_user);

        new_username = findViewById(R.id.new_username);
        new_email = findViewById(R.id.new_email);
        old_psw = findViewById(R.id.old_psw);
        new_psw = findViewById(R.id.new_psw);
        new_psw_confirm = findViewById(R.id.confirm_new_psw);

    }

    public void sendUserUpdate(View view) {
        String queryUsername = new_username.getText().toString();
        String queryEmail = new_email.getText().toString();
        String queryOldPsw = old_psw.getText().toString();
        String queryNewPsw = new_psw.getText().toString();
        String queryNewPswConfirm = new_psw_confirm.getText().toString();
        if (!queryNewPsw.equals(queryNewPswConfirm)) {
            Toast.makeText(this, "New password don't match", Toast.LENGTH_LONG).show();
        } else {
            new SendAccountUpdate(this).execute(queryUsername,
                    queryEmail, queryOldPsw, queryNewPsw, token);
        }
    }
}
