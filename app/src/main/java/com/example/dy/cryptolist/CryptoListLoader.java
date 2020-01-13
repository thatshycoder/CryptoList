package com.example.dy.cryptolist;

import android.content.Context;

import com.example.dy.cryptolist.Utils.CryptoList;
import com.example.dy.cryptolist.Utils.HttpQueryUtils;

import java.util.List;


/**
 * Here is the loader for the task. Main purpose of this is so tasks are moved from the
 * UI Thread and if orientation changes, tasks are not restarted.
 */

public class CryptoListLoader extends android.support.v4.content.AsyncTaskLoader<List<CryptoList>> {
    String mUrl;
    String mCurrency;

    public CryptoListLoader(Context context, String url, String currency) {
        super(context);
        mUrl = url;
        mCurrency = currency;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<CryptoList> loadInBackground() {
        List<CryptoList> cryptoLists = HttpQueryUtils.fetchData(mUrl, mCurrency);

        return cryptoLists;
    }
}
