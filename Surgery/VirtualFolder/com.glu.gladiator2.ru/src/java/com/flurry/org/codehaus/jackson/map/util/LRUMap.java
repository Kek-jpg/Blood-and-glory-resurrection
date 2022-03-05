/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Map$Entry
 */
package com.flurry.org.codehaus.jackson.map.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap<K, V>
extends LinkedHashMap<K, V> {
    protected final int _maxEntries;

    public LRUMap(int n, int n2) {
        super(n, 0.8f, true);
        this._maxEntries = n2;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
        return this.size() > this._maxEntries;
    }
}

