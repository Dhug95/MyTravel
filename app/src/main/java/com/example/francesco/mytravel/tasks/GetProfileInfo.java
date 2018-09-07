package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

public class GetProfileInfo extends AsyncTask<String,Void,String> {

    private Context mContext;
    private TextView mUsernameInfo;
    private TextView mEmailInfo;
    private FloatingActionButton fab;

    public GetProfileInfo(Context mContext, TextView mUsernameInfo, TextView mEmailInfo, FloatingActionButton fab) {
        this.mContext = mContext;
        this.mUsernameInfo = mUsernameInfo;
        this.mEmailInfo = mEmailInfo;
        this.fab = fab;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getProfileInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        try {
            JSONObject jsonObject = new JSONObject(s);
            String info = jsonObject.toString();
            Log.d("INFO PROFILE", info);

            String username = jsonObject.getString("username");
            String email = jsonObject.getString("email");
            String social = jsonObject.getString("facebook");

            mUsernameInfo.setText(username);
            mEmailInfo.setText(email);

            if (social.equals("false")) {
                fab.show();
            }

        } catch (Exception e) {
            Toast.makeText(mContext, "Internal error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
