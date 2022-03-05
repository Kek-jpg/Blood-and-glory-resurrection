/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 */
package com.tapjoy;

import android.content.Context;
import android.net.Uri;
import com.tapjoy.TapjoyConnectCore;
import com.tapjoy.TapjoyHttpURLResponse;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyURLConnection;

public class TapjoyEvent {
    public static final int EVENT_TYPE_IAP = 1;
    public static final int EVENT_TYPE_SHUTDOWN = 2;
    static final String TAPJOY_EVENT = "Event";
    private static TapjoyURLConnection tapjoyURLConnection = null;
    private Context context;

    public TapjoyEvent(Context context) {
        this.context = context;
        tapjoyURLConnection = new TapjoyURLConnection();
    }

    public String createEventParameter(String string2) {
        return "ue[" + string2 + "]";
    }

    public void sendEvent(int n, String string2) {
        TapjoyLog.i(TAPJOY_EVENT, "sendEvent type: " + n);
        String string3 = TapjoyConnectCore.getURLParams();
        String string4 = string3 + "&publisher_user_id=" + TapjoyConnectCore.getUserID();
        String string5 = string4 + "&event_type_id=" + n;
        if (string2 != null && string2.length() > 0) {
            string5 = string5 + "&" + string2;
        }
        new Thread((Runnable)(TapjoyEvent)this.new EventThread(string5)).start();
    }

    public void sendIAPEvent(String string2, float f2, int n, String string3) {
        String string4 = this.createEventParameter("name") + "=" + Uri.encode((String)string2);
        String string5 = string4 + "&" + this.createEventParameter("price") + "=" + Uri.encode((String)new StringBuilder().append("").append(f2).toString());
        String string6 = string5 + "&" + this.createEventParameter("quantity") + "=" + Uri.encode((String)new StringBuilder().append("").append(n).toString());
        this.sendEvent(1, string6 + "&" + this.createEventParameter("currency_code") + "=" + Uri.encode((String)string3));
    }

    public void sendShutDownEvent() {
        this.sendEvent(2, null);
    }

    public class EventThread
    implements Runnable {
        private String params;

        public EventThread(String string2) {
            this.params = string2;
        }

        public void run() {
            TapjoyHttpURLResponse tapjoyHttpURLResponse = tapjoyURLConnection.getResponseFromURL("https://ws.tapjoyads.com/user_events?", this.params, 1);
            if (tapjoyHttpURLResponse != null) {
                switch (tapjoyHttpURLResponse.statusCode) {
                    default: {
                        TapjoyLog.e(TapjoyEvent.TAPJOY_EVENT, "Server/network error: " + tapjoyHttpURLResponse.statusCode);
                        return;
                    }
                    case 200: {
                        TapjoyLog.i(TapjoyEvent.TAPJOY_EVENT, "Successfully sent Tapjoy event");
                        return;
                    }
                    case 400: 
                }
                TapjoyLog.e(TapjoyEvent.TAPJOY_EVENT, "Error sending event: " + tapjoyHttpURLResponse.response);
                return;
            }
            TapjoyLog.e(TapjoyEvent.TAPJOY_EVENT, "Server/network error");
        }
    }

}

