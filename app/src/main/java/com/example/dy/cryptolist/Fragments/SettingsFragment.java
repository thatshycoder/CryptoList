package com.example.dy.cryptolist.Fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.dy.cryptolist.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // inflate the preference screen created in xml folder
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}
