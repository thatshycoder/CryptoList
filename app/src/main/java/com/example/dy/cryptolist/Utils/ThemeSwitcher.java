package com.example.dy.cryptolist.Utils;

import android.content.Context;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;

import com.example.dy.cryptolist.R;

public class ThemeSwitcher extends AppCompatActivity {
    protected Context mContext;

    public ThemeSwitcher(Context context) {
        mContext = context;
    }

    public void switchThemeOnStart() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        Boolean useDarkTheme = sharedPreferences.getBoolean(mContext.getString(R.string.nightmode_key), true);

        if (!useDarkTheme) {
            mContext.setTheme(R.style.mLightTheme);
        } else {
            mContext.setTheme(R.style.mDarkTheme);
        }

    }
}
