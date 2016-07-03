package com.dclover.gpsutilities.ketnoi.Message;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by MY PC on 10/06/2016.
 */
public class ChatLife extends Application {
    private static final String TAG = "ChatLife";
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}