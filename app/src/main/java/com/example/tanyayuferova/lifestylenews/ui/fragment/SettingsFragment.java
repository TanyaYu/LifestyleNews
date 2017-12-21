package com.example.tanyayuferova.lifestylenews.ui.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.example.tanyayuferova.lifestylenews.R;

/**
 * Created by Tanya Yuferova on 11/6/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_screen);
    }
}
