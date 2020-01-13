package com.example.dy.cryptolist.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dy.cryptolist.SettingsActivity;
import com.example.dy.cryptolist.Utils.CryptoList;
import com.example.dy.cryptolist.Adapters.CryptoListAdapter;
import com.example.dy.cryptolist.CryptoListLoader;
import com.example.dy.cryptolist.R;

import java.util.ArrayList;
import java.util.List;

public class CryptoListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CryptoList>>, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String DATA_URL = "https://api.coinmarketcap.com/v2/ticker/?structure=array";
    private static final int LOADER_ID = 1;

    private CryptoListAdapter mAdapter;

    public CryptoListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);

        if (isAdded()) {
            ListView listView = rootView.findViewById(R.id.listview);

            mAdapter = new CryptoListAdapter(getActivity(), new ArrayList<CryptoList>());
            TextView emptyTextView = rootView.findViewById(R.id.empty_view);

            listView.setAdapter(mAdapter);
            listView.setEmptyView(emptyTextView);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);

            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {

                LoaderManager loaderManager = getLoaderManager();

                loaderManager.initLoader(LOADER_ID, null, this);
            } else {
                //emptyTextView.setText("No internet Connection");;
               Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }

        return rootView;
    }


    // inflate menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:

                refresh();
                return true;

            case R.id.settings:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<CryptoList>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String limit = sharedPreferences.getString(getString(R.string.limit_key), getString(R.string.limit_default));
        String currency = sharedPreferences.getString(getString(R.string.currency_key), getString(R.string.currency_default));

        Uri baseUri = Uri.parse(DATA_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // append limit
        uriBuilder.appendQueryParameter("limit", limit);
        // append currency
        uriBuilder.appendQueryParameter("convert", currency);

        return new CryptoListLoader(getActivity(), uriBuilder.toString(), currency);
    }

    @Override
    public void onLoadFinished
            (@NonNull android.support.v4.content.Loader<List<CryptoList>> loader, List<CryptoList> data) {
        mAdapter.clear();

        mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<CryptoList>> loader) {
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (getActivity() != null) {
            if (key.equals(getString(R.string.limit_key)) || key.equals(getString(R.string.currency_key))) {
                mAdapter.clear();
                getLoaderManager().restartLoader(LOADER_ID, null, this);
            }
        }
    }

    public void refresh() {
        if (!mAdapter.isEmpty()) {
            mAdapter.clear();
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.clear();
    }

}
