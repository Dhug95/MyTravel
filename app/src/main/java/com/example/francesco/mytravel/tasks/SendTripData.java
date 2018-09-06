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
    private String filePath;
    private String token;
    private String imageString;

    public SendTripData(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        queryName = strings[0];
        queryStart = strings[1];
        queryEnd = strings[2];
        filePath = strings[3];
        token = strings[4];

        try {
            FileInputStream in = new FileInputStream(filePath);
            Log.d("FILE PATH", filePath);
            BufferedInputStream buf = new BufferedInputStream(in);
            byte[] bMapArray = new byte[buf.available()];
            buf.read(bMapArray);
            Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
            imageString = getEncoded64ImageStringFromBitmap(bMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NetworkUtils.getTripResponse(queryName, queryStart, queryEnd, token, imageString);
    }


    private String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
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
