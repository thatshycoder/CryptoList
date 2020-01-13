package com.example.dy.cryptolist.Utils;

/**
 * Here is the java class that stores each attributes we are are interested in generating.
 */

public class CryptoList {
    private String mCoinName;
    private String mCoinPrice;
    String mMarketcap;
    private String mVolume;
    private String mPercentChange;

    /**
     * Here we create a constructor that collects the necessary attributes of each coin.
     */
    public CryptoList(String name, String price, String marketcap, String volume, String percentage) {
        mCoinName = name;
        mCoinPrice = price;
        mMarketcap = marketcap;
        mVolume = volume;
        mPercentChange = percentage;

    }

    public String getCoinName() {
        return mCoinName;
    }

    public String getCoinPrice() {
        return mCoinPrice;
    }

    public String getVolume() {
        return mVolume;
    }

    public String getMarketcap() {
        return mMarketcap;
    }

    public String getPercentChange() {
        return mPercentChange;
    }
}
