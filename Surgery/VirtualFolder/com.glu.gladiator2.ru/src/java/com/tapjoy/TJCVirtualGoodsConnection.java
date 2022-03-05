/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.tapjoy;

import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyURLConnection;

public class TJCVirtualGoodsConnection {
    static final String TAPJOY_VIRTUAL_GOODS_CONNECTION = "TapjoyVirtualGoodsConnection";
    private static TapjoyURLConnection tapjoyURLConnection;
    private static String urlDomain;
    private static String urlParams;

    static {
        urlDomain = null;
        urlParams = null;
        tapjoyURLConnection = null;
    }

    public TJCVirtualGoodsConnection(String string2, String string3) {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS_CONNECTION, "base: " + string2 + ", params: " + string3);
        tapjoyURLConnection = new TapjoyURLConnection();
        urlDomain = string2;
        urlParams = string3;
    }

    public String getAllPurchasedItemsFromServer(int n, int n2) {
        TapjoyLog.i(TAPJOY_VIRTUAL_GOODS_CONNECTION, "getAllPurchasedItemsFromServer");
        String string2 = urlDomain + "get_vg_store_items/purchased?";
        String string3 = urlParams + "&start=" + n + "&max=" + n2;
        return tapjoyURLConnection.connectToURL(string2, string3);
    }

    public String getAllStoreItemsFromServer(int n, int n2) {
        String string2 = urlDomain + "get_vg_store_items/all?";
        String string3 = urlParams + "&start=" + n + "&max=" + n2;
        return tapjoyURLConnection.connectToURL(string2, string3);
    }

    public String purchaseVGFromServer(String string2) {
        String string3 = urlDomain + "points/purchase_vg?";
        String string4 = urlParams + "&virtual_good_id=" + string2;
        return tapjoyURLConnection.connectToURL(string3, string4);
    }
}

