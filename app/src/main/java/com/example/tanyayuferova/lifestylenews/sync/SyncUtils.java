package com.example.tanyayuferova.lifestylenews.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.tanyayuferova.lifestylenews.utils.PreferencesUtils;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tanya Yuferova on 12/23/2017.
 */

public class SyncUtils {

    private static final String ARTICLES_SYNC_TAG = "articles-sync";

    synchronized public static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        int syncIntervalSeconds = getSyncIntervalSeconds(context);
        int syncFlexTimeSeconds = syncIntervalSeconds / 4;

        Job syncSunshineJob = dispatcher.newJobBuilder()
                .setService(FirebaseJobService.class)
                .setTag(ARTICLES_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        syncIntervalSeconds,
                        syncIntervalSeconds + syncFlexTimeSeconds))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncSunshineJob);
    }

    private static int getSyncIntervalSeconds(Context context) {
        long hours = (long) PreferencesUtils.getBackgroundRefreshPeriodHours(context);
        return (int) TimeUnit.HOURS.toSeconds(hours);
    }
}
