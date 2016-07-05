package com.dclover.gpsutilities.khoihanh;


import android.app.Application;
import android.util.Log;

import com.dclover.gpsutilities.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class GPSTripTrackerGoogleAnalytics extends Application {
    public static int GENERAL_TRACKER = 0;
    private static final String PROPERTY_ID = "UA-51292118-49";
    public HashMap<TrackerName, Tracker> mTrackers;

    public enum TrackerName {
        APP_TRACKER,
        GLOBAL_TRACKER,
        ECOMMERCE_TRACKER
    }

    static {
        GENERAL_TRACKER = 0;
    }

    public GPSTripTrackerGoogleAnalytics() {
        this.mTrackers = new HashMap();
    }

    public synchronized Tracker getTracker(TrackerName appTracker) {
        if (!this.mTrackers.containsKey(appTracker)) {
            Log.v("Splash", "Splash splash test");
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Log.v("Splash", "Splash splash test");
            Tracker t = appTracker == TrackerName.APP_TRACKER ? analytics.newTracker(PROPERTY_ID) : appTracker == TrackerName.GLOBAL_TRACKER ? analytics.newTracker((int) R.xml.global_tracker) : analytics.newTracker((int) R.xml.ecommerce_tracker);
            this.mTrackers.put(appTracker, t);
            Log.v("Splash", "Splash splash test");
        }
        return (Tracker) this.mTrackers.get(appTracker);
    }
}

