/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.android;

import com.flurry.android.bb;
import com.flurry.android.be;
import java.util.List;
import java.util.Map;

public class OfferHandler {
    public List<Map<String, String>> getOfferMapList() {
        return null;
    }

    public void onAdClicked(String string, Map<String, String> map) {
        ((be)null).a(string, "clicked", true, map);
    }

    public void onAdClosed(String string, Map<String, String> map) {
        ((be)null).a(string, "adClosed", true, map);
    }

    public void onAdFilled(String string, Map<String, String> map) {
        ((be)null).a(string, "filled", true, map);
    }

    public void onAdShown(String string, Map<String, String> map) {
        ((be)null).a(string, "rendered", true, map);
    }

    public void onAdUnFilled(String string, Map<String, String> map) {
        ((be)null).a(string, "unfilled", true, map);
    }
}

