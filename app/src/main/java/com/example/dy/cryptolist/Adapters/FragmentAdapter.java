package com.example.dy.cryptolist.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dy.cryptolist.Fragments.CryptoListFragment;
import com.example.dy.cryptolist.Fragments.GlobalDataFragment;
import com.example.dy.cryptolist.Fragments.WatchlistFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CryptoListFragment();
            case 1:
                return new WatchlistFragment();
            default:
                return new CryptoListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}