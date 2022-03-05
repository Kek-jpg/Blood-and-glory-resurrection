/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Map
 */
package com.amazon.device.ads;

import com.amazon.device.ads.MraidView;
import java.util.ArrayList;
import java.util.Map;

abstract class MraidCommand {
    protected Map<String, String> mParams;
    protected MraidView mView;

    MraidCommand(Map<String, String> map, MraidView mraidView) {
        this.mParams = map;
        this.mView = mraidView;
    }

    abstract void execute();

    protected boolean getBooleanFromParamsForKey(String string) {
        return "true".equals(this.mParams.get((Object)string));
    }

    protected float getFloatFromParamsForKey(String string) {
        if ((String)this.mParams.get((Object)string) == null) {
            return 0.0f;
        }
        try {
            float f2 = Float.parseFloat((String)string);
            return f2;
        }
        catch (NumberFormatException numberFormatException) {
            return 0.0f;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Integer[] getIntArrayFromParamsFroKey(String string) {
        String string2 = (String)this.mParams.get((Object)string);
        if (string2 == null) {
            return null;
        }
        String[] arrstring = string2.split(",");
        ArrayList arrayList = new ArrayList();
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            String string3 = arrstring[n3];
            try {
                arrayList.add((Object)Integer.parseInt((String)string3, (int)10));
            }
            catch (NumberFormatException numberFormatException) {
                arrayList.add((Object)-1);
            }
            ++n3;
        }
        return (Integer[])arrayList.toArray((Object[])new Integer[arrayList.size()]);
    }

    protected int getIntFromParamsForKey(String string) {
        String string2 = (String)this.mParams.get((Object)string);
        if (string2 == null) {
            return -1;
        }
        try {
            int n2 = Integer.parseInt((String)string2, (int)10);
            return n2;
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    protected String getStringFromParamsForKey(String string) {
        return (String)this.mParams.get((Object)string);
    }
}

