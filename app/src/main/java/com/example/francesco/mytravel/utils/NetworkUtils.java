package com.example.francesco.mytravel.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class NetworkUtils {

    //private static final String RAILS_BASE_URL = "https://my-travel-backend.herokuapp.com/app"; // Base URI for the Node JS App
    private static final String RAILS_BASE_URL = "http://192.168.1.2:8080/app"; // Base URI for the Node JS App
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String OLD_PASSWORD = "old_password";
    private static final String TOKEN = "token";
    private static final String FACEBOOK_TOKEN = "facebook_token";
    private static final String NAME = "name";
    private static final String START = "start";
    private static final String END = "end";
    private static final String IMAGE = "image";
    private static final String TRIP_ID = "trip_id";
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static String getSignUpResponse(String email, String username, String password) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/register").buildUpon()
                .appendQueryParameter(EMAIL, email)
                .appendQueryParameter(USERNAME, username)
                .appendQueryParameter(PASSWORD, password)
                .build();

        return sendRequest(builtURI, "POST");
    }

    public static String getLoginResponse(String email, String psw) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/authenticate").buildUpon()
                .appendQueryParameter(EMAIL, email)
                .appendQueryParameter(PASSWORD, psw)
                .build();

        return sendRequest(builtURI, "POST");
    }


    public static String getProfileInfo(String token) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/myprofile").buildUpon()
                .appendQueryParameter(TOKEN, token)
                .build();

        return sendRequest(builtURI, "GET");
    }

    public static String getTripResponse(String name, String start, String end, String token, String image) {

        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/trips").buildUpon()
                .appendQueryParameter(NAME, name)
                .appendQueryParameter(START, start)
                .appendQueryParameter(END, end)
                .appendQueryParameter(TOKEN, token)
                .appendQueryParameter(IMAGE, image)
                .build();

        return sendRequest(builtURI, "POST");
    }

    public static String getTripList(String token) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/mytrips").buildUpon()
                .appendQueryParameter(TOKEN, token)
                .build();

        return sendRequest(builtURI, "GET");
    }

    public static String sendProfileUpdate(String username, String email, String oldpsw,
                                           String newpsw, String token) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/myprofile").buildUpon()
                .appendQueryParameter(USERNAME, username)
                .appendQueryParameter(EMAIL, email)
                .appendQueryParameter(OLD_PASSWORD, oldpsw)
                .appendQueryParameter(PASSWORD, newpsw)
                .appendQueryParameter(TOKEN, token)
                .build();

        return sendRequest(builtURI, "PUT");
    }

    public static String getTripPage(String trip_id, String token) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/trips/" + trip_id).buildUpon()
                .appendQueryParameter(TOKEN, token)
                .build();

        return sendRequest(builtURI, "GET");
    }

    public static String deleteTrip(String trip_id, String token) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/trips/" + trip_id).buildUpon()
                .appendQueryParameter(TOKEN, token)
                .build();

        return sendRequest(builtURI, "DELETE");
    }

    public static String facebookLogin(String facebookToken) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/facebook_login").buildUpon()
                .appendQueryParameter(FACEBOOK_TOKEN, facebookToken)
                .build();

        return sendRequest(builtURI, "POST");
    }

    public static String requestPassword(String queryEmail) {
        //Build up your query URI
        Uri builtURI = Uri.parse(RAILS_BASE_URL + "/forgot_password").buildUpon()
                .appendQueryParameter(EMAIL, queryEmail)
                .build();

        return sendRequest(builtURI, "GET");
    }

    private static String sendRequest(Uri builtURI, String method) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONresponse = null;

        try {
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            //urlConnection.setRequestProperty("connection", "close");
            urlConnection.connect();

            Map<String, List<String>> map = urlConnection.getHeaderFields();

            Log.d(LOG_TAG, "Printing Response Header...\n");

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                Log.d(LOG_TAG, "Key : " + entry.getKey()
                        + " ,Value : " + entry.getValue());
            }


            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {

            /* Since it's JSON, adding a newline isn't necessary (it won't affect
            parsing) but it does make debugging a *lot* easier if you print out the
            completed buffer for debugging. */
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            JSONresponse = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, JSONresponse);

        return JSONresponse;
    }
}
