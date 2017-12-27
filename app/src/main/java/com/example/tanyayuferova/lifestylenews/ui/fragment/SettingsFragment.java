package com.example.tanyayuferova.lifestylenews.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.sync.SyncUtils;

/**
 * Created by Tanya Yuferova on 11/6/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_screen);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (p instanceof ListPreference) {
                setPreferenceSummary((ListPreference) p, sharedPreferences.getString(p.getKey(), ""));
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            if (preference instanceof ListPreference) {
                setPreferenceSummary((ListPreference) preference, sharedPreferences.getString(preference.getKey(), ""));
            }

            if(key.equals(getString(R.string.pref_period_key))){
                SyncUtils.scheduleFirebaseJobDispatcherSync(getContext());
            }
        }
    }

    protected void setPreferenceSummary(ListPreference preference, String value) {
        int prefIndex = preference.findIndexOfValue(value);
        if (prefIndex >= 0) {
            preference.setSummary(preference.getEntries()[prefIndex]);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
