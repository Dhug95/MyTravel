package com.example.francesco.mytravel.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.tasks.SendFacebookLogin;
import com.example.francesco.mytravel.tasks.SendLoginData;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText email_login;
    private EditText psw_login;

    CallbackManager callbackManager;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        email_login = (EditText) findViewById(R.id.email_login);
        psw_login = (EditText) findViewById(R.id.psw_login);

        LoginButton login_button = (LoginButton) findViewById(R.id.fb_login_button);
        login_button.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String facebookToken = loginResult.getAccessToken().getToken();
                Log.d("Facebook token: ", facebookToken);
                LoginManager.getInstance().logOut();
                new SendFacebookLogin(getApplicationContext()).execute(facebookToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Error: ", exception.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
