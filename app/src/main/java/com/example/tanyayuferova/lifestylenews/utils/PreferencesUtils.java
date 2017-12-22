package com.example.tanyayuferova.lifestylenews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.tanyayuferova.lifestylenews.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tanya Yuferova on 12/22/2017.
 */

public class PreferencesUtils {

    /**
     * Gets notification enabled preference value
     * @param context
     * @return
     */
    public static boolean getNotificationEnabled(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(context.getString(R.string.pref_notifications_key),
                context.getResources().getBoolean(R.bool.pref_notifications_default));
    }

    /**
     * Gets background refresh period preference value
     * @param context
     * @return
     */
    public static float getBackgroundRefreshPeriodHours(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String stringValue = preferences.getString(context.getString(R.string.pref_period_key),
                context.getResources().getString(R.string.pref_period_12h_value));
        return Float.valueOf(stringValue);
    }

    /**
     * Sets topics preferences
     * @param context
     * @param topics
     */
    public static void setTopicsPreferences(Context context, Set<String> topics) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(context.getString(R.string.pref_topics_key), topics);
        editor.commit();
    }

    /**
     * Gets topics preferences
     * @param context
     * @return
     */
    public static Set<String> getTopicsPreferences(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getStringSet(context.getString(R.string.pref_topics_key), new HashSet<String>());
    }
}
