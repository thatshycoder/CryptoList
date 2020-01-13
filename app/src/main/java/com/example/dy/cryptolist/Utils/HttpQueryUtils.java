package com.example.dy.cryptolist.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Okay. This class, is where we store all helper methods needed to parse and fetch the JSON object
 * of whatever API we are working with.
 */

public final class HttpQueryUtils {
    private static String LOG_TAG = HttpQueryUtils.class.getSimpleName();

    public HttpQueryUtils() {

    }

    /**
     * We create a helper method that convert a string to a valid URL. This method could have been put
     * directly insider another method that queries the url, however, it's nice to make each method perform only a
     * specific task so it's easy to trace error. For example, if their's invalid url, this method will return an exception
     * that says that. This is the most basic task in this class
     *
     * @return url
     */
    private static URL createUrl(String strUrl) {
        URL url = null;

        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Something went wrong when buiding URL" + e);
        }

        return url;
    }

    /**
     * We create a helper method that takes an input of a input stream, read through it and generate a JSONObject
     *
     * @return string jsonobject
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        /**
         * We create a stringbuilder instance that helps us store the strings we'll be reading with a while loop
         */
        StringBuilder output = new StringBuilder();

        /**
         * We create an inputStreamReader that converts the input stream bits of data to chars
         */
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

        /**
         * We create a buffered reader that reads from the chars generated.
         */
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();

        /**
         * We read through the buffered line and loop through it while there are still line to read
         * and we append to our string builder during the process
         */
        while (line != null) {
            output.append(line);

            // like we'd say i = i+1; repeat.
            line = bufferedReader.readLine();
        }

        /**
         * We convert the string builder to a single string
         */
        return output.toString();
    }

    /**
     * Now we create a helper method that fetches JSONObject from a given URL
     */
    private static String makeHttpCall(URL url) {
        String jsonResponse = "";

        HttpURLConnection connection;
        InputStream inputStream;

        try {

            /**
             * First things first, we open an HTTP url connection
             */
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            /**
             * We set the inputstream(bits of data that we get from the server which will later be converted to a string,
             * then a proper JSONObject) input stream is bits of data we are reading from, while output stream is bits of
             * data we are writing to. In our case, we need to read from the server, hence inputstream
             */
            inputStream = connection.getInputStream();

            /**
             * We convert the inputstream to a string of JSONObject
             */
            jsonResponse = readFromStream(inputStream);

            /**
             * disconnect, close, empty what we're not using again so memory is not wasted
             */
            connection.disconnect();
            inputStream.close();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Something went wrong when trying to make the http request call" + e);
        }

        return jsonResponse;
    }

    /**
     * This helper method extracts the JSON Object from a JSONObject string and returns a list of each object
     */
    private static List<CryptoList> extractJsonFeature(String jsonString, String currency) {

        // this is where we add the earthquakes that the adapter will work on later
        List<CryptoList> result = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(jsonString);

            /**
             * We get a JSON Array of the sub object that has all we need
             */
            JSONArray cryptoArray = baseJsonObject.getJSONArray("data");

            /**
             * We looop through it and get each feature, name, if etc
             */
            for (int i = 0; i < cryptoArray.length(); i++) {
                // get a single crypto to work on
                JSONObject currentCrypto = cryptoArray.getJSONObject(i);

                // get the name
                String name = currentCrypto.getString("name");

                JSONObject quotes = currentCrypto.getJSONObject("quotes");

                JSONObject currencyToGet = quotes.getJSONObject(currency);
                String currencySymbol = getCurrencySymbol(currency);

                String price = formatPrice(currencyToGet.getDouble("price"), currency);
                price = currencySymbol + "" + price;

                String volume = Long.toString(currencyToGet.getLong("volume_24h"));

                String percentage = currencyToGet.getString("percent_change_24h");

                String marketcap = Long.toString(currencyToGet.getLong("market_cap"));

                CryptoList cryptoList = new CryptoList(name, price, marketcap, String.format(volume), percentage);

                result.add(cryptoList);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong when trying to parse the JSONObject" + e);
        }

        return result;
    }

    /**
     * Finally, the main method we call to put the helper methods to work
     */
    public static List<CryptoList> fetchData(String requestUrl, String currency) {

        URL url = createUrl(requestUrl);

        // perform http request on the url
        String jsonResponse = makeHttpCall(url);

        // extract data from jsonobject
        List<CryptoList> cryptoLists = extractJsonFeature(jsonResponse, currency);

        return cryptoLists;
    }

    /**
     * This method appends the price with a proper currency symbol. I'll be focusing only on BTC and USD for now..
     */

    private static String getCurrencySymbol(String currency) {
        String currencySymbol = "฿";

        if (currency.equals("USD")) {
            currencySymbol = "$";
        } else if (currency.equals("BTC")) {
            currencySymbol = "฿";
        }

        return currencySymbol;
    }

    private static String formatPrice(Double price, String currency) {
        String formattedPrice = "";

        if (currency.equals("USD")) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            formattedPrice = decimalFormat.format(price);

        } else if (currency.equals("BTC")) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00000000");
            formattedPrice = decimalFormat.format(price);
        }

        return formattedPrice;

    }
}
