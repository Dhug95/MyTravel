package com.example.francesco.mytravel.tasks;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.francesco.mytravel.utils.NetworkUtils;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SendTripData extends AsyncTask<String, Void, String> {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private Context mContext;

    private String queryName;
    private String queryStart;
    private String queryEnd;
    private String token;

    public SendTripData(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        queryName = strings[0];
        queryStart = strings[1];
        queryEnd = strings[2];
        token = strings[3];

        return NetworkUtils.getTripResponse(queryName, queryStart, queryEnd, token);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String message = jsonObject.getString("message");

            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(mContext, "Internal error",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
