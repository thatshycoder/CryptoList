package com.example.dy.cryptolist;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dy.cryptolist.Adapters.FragmentAdapter;
import com.example.dy.cryptolist.Utils.ThemeSwitcher;

public class MainActivity extends AppCompatActivity {
    protected ThemeSwitcher mThemeSwitcher = new ThemeSwitcher(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set theme as app starts
        mThemeSwitcher.switchThemeOnStart();

        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(fragmentAdapter);
    }

}
