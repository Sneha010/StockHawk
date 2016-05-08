package com.sam_chordas.android.stockhawk.rest;

import android.content.ContentProviderOperation;
import android.text.TextUtils;
import android.util.Log;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    private static String LOG_TAG = Utils.class.getSimpleName();

    public static boolean showPercent = true;

    public static ArrayList quoteJsonToContentVals(String JSON) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray resultsArray = null;
        try {
            jsonObject = new JSONObject(JSON);
            if (jsonObject != null && jsonObject.length() != 0) {
                jsonObject = jsonObject.getJSONObject("query");
                int count = Integer.parseInt(jsonObject.getString("count"));

                Log.d("@@@count", "quoteJsonToContentVals count: " + count);

                if (count == 1) {
                    jsonObject = jsonObject.getJSONObject("results")
                            .getJSONObject("quote");
                    if (checkForValidStockSymbol(jsonObject))
                        batchOperations.add(buildBatchOperation(jsonObject));

                    Log.d("@@@jsonObject", "quoteJsonToContentVals jsonObject when count == 1: " + jsonObject.toString());
                } else {
                    resultsArray = jsonObject.getJSONObject("results").getJSONArray("quote");

                    Log.d("@@@resultsArray", "quoteJsonToContentVals resultsArray: " + resultsArray);

                    if (resultsArray != null && resultsArray.length() != 0) {
                        for (int i = 0; i < resultsArray.length(); i++) {
                            jsonObject = resultsArray.getJSONObject(i);
                            Log.d("@@@jsonObject", "quoteJsonToContentVals jsonObject when count != 1: " + jsonObject.toString());
                            if (checkForValidStockSymbol(jsonObject))
                                batchOperations.add(buildBatchOperation(jsonObject));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "String to JSON failed: " + e);
        }
        return batchOperations;
    }

    public static String truncateBidPrice(String bidPrice) {
        Log.d("@@@ In truncateBidPrice", "bidPrice" + bidPrice);

        if(TextUtils.isEmpty(bidPrice))
            bidPrice = String.format("%.2f", Float.parseFloat(bidPrice));

        return bidPrice;
    }

    public static String truncateChange(String change, boolean isPercentChange) {
        String weight = change.substring(0, 1);
        String ampersand = "";
        if (isPercentChange) {
            ampersand = change.substring(change.length() - 1, change.length());
            change = change.substring(0, change.length() - 1);
        }
        change = change.substring(1, change.length());
        double round = (double) Math.round(Double.parseDouble(change) * 100) / 100;
        change = String.format("%.2f", round);
        StringBuffer changeBuffer = new StringBuffer(change);
        changeBuffer.insert(0, weight);
        changeBuffer.append(ampersand);
        change = changeBuffer.toString();
        return change;
    }

    public static ContentProviderOperation buildBatchOperation(JSONObject jsonObject) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);
        try {
            String change = jsonObject.getString("Change");
            if (!TextUtils.isEmpty(change)) {
                builder.withValue(QuoteColumns.SYMBOL, jsonObject.getString("symbol"));
                builder.withValue(QuoteColumns.BIDPRICE, truncateBidPrice(jsonObject.getString("Bid")));
                builder.withValue(QuoteColumns.PERCENT_CHANGE, truncateChange(
                        jsonObject.getString("ChangeinPercent"), true));
                builder.withValue(QuoteColumns.CHANGE, truncateChange(change, false));
                builder.withValue(QuoteColumns.ISCURRENT, 1);
                if (change.charAt(0) == '-') {
                    builder.withValue(QuoteColumns.ISUP, 0);
                } else {
                    builder.withValue(QuoteColumns.ISUP, 1);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    private static boolean checkForValidStockSymbol(JSONObject jsonObject) {


        try {
            if (jsonObject != null && jsonObject.has("Bid") &&
                    jsonObject.getString("Bid")!=null && !jsonObject.getString("Bid").equalsIgnoreCase("null")) {
                return true;

            } else {
               EventBus.getEventBus().post("No data available!");
                Log.d("@@@", "checkForValidStockSymbol: No data available!");

                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            EventBus.getEventBus().post("No data available!");
            Log.d("@@@", "checkForValidStockSymbol: No data available!");
            return false;
        }
    }
}
