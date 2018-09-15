package com.example.francesco.mytravel.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

    private static final String FORGOT_PSW =
            "com.example.francesco.mytravel.extra.FORGOT_PSW";

    private EditText email_login;
    private EditText psw_login;
    private CheckBox remember_me;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.hellosharedprefs";

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

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        verifyStoragePermissions(this);

        email_login = (EditText) findViewById(R.id.email_login);
        psw_login = (EditText) findViewById(R.id.psw_login);
        remember_me = (CheckBox) findViewById(R.id.remember_me);

        // Restore preferences
        email_login.setText(mPreferences.getString("EMAIL", null));
        psw_login.setText(mPreferences.getString("PSW", null));
        remember_me.setChecked(mPreferences.getBoolean("REMEMBER", false));

        // Check if back from password retrieve
        Intent intent = getIntent();
        String forgot_psw = intent.getStringExtra(FORGOT_PSW);
        if (forgot_psw != null) {
            Toast.makeText(this, forgot_psw, Toast.LENGTH_LONG).show();
        }

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
                Toast.makeText(getApplicationContext(), "Cancel by user", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Facebook error: ", exception.getMessage());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if (remember_me.isChecked()) {
            preferencesEditor.putString("EMAIL", email_login.getText().toString());
            preferencesEditor.putString("PSW", psw_login.getText().toString());
            preferencesEditor.putBoolean("REMEMBER", true);
        } else {
            preferencesEditor.putString("EMAIL", null);
            preferencesEditor.putString("PSW", null);
            preferencesEditor.putBoolean("REMEMBER", false);
        }
        preferencesEditor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        if (remember_me.isChecked()) {
            preferencesEditor.putString("EMAIL", email_login.getText().toString());
            preferencesEditor.putString("PSW", psw_login.getText().toString());
            preferencesEditor.putBoolean("REMEMBER", true);
        } else {
            preferencesEditor.putString("EMAIL", null);
            preferencesEditor.putString("PSW", null);
            preferencesEditor.putBoolean("REMEMBER", false);
        }
        preferencesEditor.apply();
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

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
