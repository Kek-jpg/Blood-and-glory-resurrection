/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Map$Entry
 */
package com.flurry.org.codehaus.jackson.util;

import java.util.LinkedHashMap;
import java.util.Map;

public final class InternCache
extends LinkedHashMap<String, String> {
    private static final int MAX_ENTRIES = 192;
    public static final InternCache instance = new InternCache();

    private InternCache() {
        super(192, 0.8f, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String intern(String string2) {
        void var5_2 = this;
        synchronized (var5_2) {
            String string3 = (String)this.get((Object)string2);
            if (string3 == null) {
                string3 = string2.intern();
                this.put((Object)string3, (Object)string3);
            }
            return string3;
        }
    }

    protected boolean removeEldestEntry(Map.Entry<String, String> entry) {
        return this.size() > 192;
    }
}

