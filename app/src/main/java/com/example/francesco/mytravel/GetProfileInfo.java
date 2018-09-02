package com.example.francesco.mytravel;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class GetProfileInfo extends AsyncTask<String,Void,String> {

    private Context mContext;
    private TextView mUsernameInfo;
    private TextView mEmailInfo;

    public GetProfileInfo(Context mContext, TextView mUsernameInfo, TextView mEmailInfo) {
        this.mContext = mContext;
        this.mUsernameInfo = mUsernameInfo;
        this.mEmailInfo = mEmailInfo;
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

            String username = jsonObject.getString("username");
            String email = jsonObject.getString("email");

            mUsernameInfo.setText(username);
            mEmailInfo.setText(email);

        } catch (Exception e) {
            Toast.makeText(mContext, "Internal error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
