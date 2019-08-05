package com.koohpar.dstrbt.firebase;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.koohpar.dstrbt.utils.AppConstants;

import static android.content.ContentValues.TAG;

/**
 * Created by Behnaz on 06/10/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService implements AppConstants {

    private String refreshedToken;

    @Override
    public void onTokenRefresh() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        final Gson gson = new Gson();
        String serializedObject = gson.toJson(refreshedToken);
        sharedPreferencesEditor.putString(PREF_KEY_TOKEN, serializedObject);
        sharedPreferencesEditor.apply();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }



}