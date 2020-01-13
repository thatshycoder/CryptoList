package com.example.dy.cryptolist.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dy.cryptolist.Utils.CryptoList;
import com.example.dy.cryptolist.R;

import java.util.ArrayList;

/**
 * Here is the listview adapter so we are able to create a listview with our custom layout that
 * supports more than a single textviews
 */

public class CryptoListAdapter extends ArrayAdapter<CryptoList> {
    private Context mContext;

    public CryptoListAdapter(@NonNull Context context, ArrayList<CryptoList> cryptoLists) {
        super(context, 0, cryptoLists);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final CryptoList currentCrypto = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.crypto_list_layout, parent, false);
        }

        TextView name = convertView.findViewById(R.id.coinName);
        TextView price = convertView.findViewById(R.id.coinPrice);
        TextView marketcap = convertView.findViewById(R.id.marketcap);
        TextView volume = convertView.findViewById(R.id.volume);
        TextView percentage = convertView.findViewById(R.id.percentage);

        name.setText(currentCrypto.getCoinName());
        price.setText(currentCrypto.getCoinPrice());

        marketcap.setText(mContext.getString(R.string.marketcap, currentCrypto.getMarketcap()));
        volume.setText(mContext.getString(R.string.volume, currentCrypto.getVolume()));

        String percentageText = mContext.getString(R.string.percentage, currentCrypto.getPercentChange());

        percentage.setText(percentageText);
        percentage.setTextColor(getPercentageColor(currentCrypto.getPercentChange()));

        return convertView;
    }

    private int getPercentageColor(String percentage) {
        int color;
        Double percentageDouble = Double.parseDouble(percentage);

        if (percentageDouble >= 0) {
            color = mContext.getResources().getColor(R.color.colorPositive);
        } else {
            color = mContext.getResources().getColor(R.color.colorAccent);
        }

        return color;
    }
}
