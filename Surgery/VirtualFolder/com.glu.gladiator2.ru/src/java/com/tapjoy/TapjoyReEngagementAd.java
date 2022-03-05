/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Thread
 */
package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyHttpURLResponse;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyReEngagementAdNotifier;
import com.tapjoy.TapjoyReEngagementAdWebView;
import com.tapjoy.TapjoyURLConnection;

public class TapjoyReEngagementAd {
    private static String htmlData;
    private static TapjoyReEngagementAdNotifier reEngagementAdNotifier;
    public static String reEngagementAdURLParams;
    private static TapjoyURLConnection tapjoyURLConnection;
    final String TAPJOY_RE_ENGAGEMENT = "Re-engagement";
    private Context context;
    private String currencyID;

    static {
        tapjoyURLConnection = null;
    }

    public TapjoyReEngagementAd(Context context) {
        this.context = context;
        tapjoyURLConnection = new TapjoyURLConnection();
    }

    public void getReEngagementAd(TapjoyReEngagementAdNotifier tapjoyReEngagementAdNotifier) {
        TapjoyLog.i("Re-engagement", "Getting Re-engagement Ad");
        this.getReEngagementAdWithCurrencyID(null, tapjoyReEngagementAdNotifier);
    }

    public void getReEngagementAdWithCurrencyID(String string2, TapjoyReEngagementAdNotifier tapjoyReEngagementAdNotifier) {
        this.currencyID = string2;
        TapjoyLog.i("Re-engagement", "Getting Re-engagement ad userID: " + TapjoyConnectCore.getUserID() + ", currencyID: " + this.currencyID);
        reEngagementAdNotifier = tapjoyReEngagementAdNotifier;
        reEngagementAdURLParams = TapjoyConnectCore.getURLParams();
        reEngagementAdURLParams = reEngagementAdURLParams + "&publisher_user_id=" + TapjoyConnectCore.getUserID();
        if (this.currencyID != null) {
            reEngagementAdURLParams = reEngagementAdURLParams + "&currency_id=" + this.currencyID;
        }
        new Thread(new Runnable(){

            public void run() {
                TapjoyHttpURLResponse tapjoyHttpURLResponse = tapjoyURLConnection.getResponseFromURL("https://ws.tapjoyads.com/reengagement_rewards?", TapjoyReEngagementAd.reEngagementAdURLParams);
                if (tapjoyHttpURLResponse != null) {
                    switch (tapjoyHttpURLResponse.statusCode) {
                        default: {
                            return;
                        }
                        case 200: {
                            htmlData = tapjoyHttpURLResponse.response;
                            reEngagementAdNotifier.getReEngagementAdResponse();
                            return;
                        }
                        case 204: 
                    }
                    reEngagementAdNotifier.getReEngagementAdResponseFailed(1);
                    return;
                }
                reEngagementAdNotifier.getReEngagementAdResponseFailed(2);
            }
        }).start();
    }

    public void showReEngagementFullScreenAd() {
        TapjoyLog.i("Re-engagement", "Displaying Re-engagement ad...");
        if (htmlData != null && htmlData.length() != 0) {
            Intent intent = new Intent(this.context, TapjoyReEngagementAdWebView.class);
            intent.setFlags(268435456);
            intent.putExtra("RE_ENGAGEMENT_HTML_DATA", htmlData);
            this.context.startActivity(intent);
        }
    }

}

