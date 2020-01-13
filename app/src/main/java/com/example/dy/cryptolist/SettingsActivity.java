package com.example.dy.cryptolist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.dy.cryptolist.Fragments.SettingsFragment;
import com.example.dy.cryptolist.Utils.ThemeSwitcher;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    protected ThemeSwitcher mThemeSwitcher = new ThemeSwitcher(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set theme as app starts
        mThemeSwitcher.switchThemeOnStart();


        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // show settings fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.settingsContainer, new SettingsFragment()).commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(getString(R.string.nightmode_key))) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        super.onBackPressed();
    }


    //    @Override
//    public void onResume() {
//        super.onResume();
//        getPreferenceScreen().getSharedPreferences()
//                .registerOnSharedPreferenceChangeListener(this);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        getPreferenceScreen().getSharedPreferences()
//                .unregisterOnSharedPreferenceChangeListener(this);
//    }

}
