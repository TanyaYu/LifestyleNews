package com.tanyayuferova.lifestylenews.analitics;


import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.tanyayuferova.lifestylenews.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Tanya Yuferova on 12/27/2017.
 */

public class AnalyticsApplication extends Application {
    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
