/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map.ser.impl;

import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ser.impl.SerializerCache;
import java.util.Map;
import java.util.Set;

public class JsonSerializerMap {
    private final Bucket[] _buckets;
    private final int _size;

    public JsonSerializerMap(Map<SerializerCache.TypeKey, JsonSerializer<Object>> map) {
        int n;
        this._size = n = JsonSerializerMap.findSize(map.size());
        int n2 = n - 1;
        Bucket[] arrbucket = new Bucket[n];
        for (Map.Entry entry : map.entrySet()) {
            SerializerCache.TypeKey typeKey = (SerializerCache.TypeKey)entry.getKey();
            int n3 = n2 & typeKey.hashCode();
            arrbucket[n3] = new Bucket(arrbucket[n3], typeKey, (JsonSerializer)entry.getValue());
        }
        this._buckets = arrbucket;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final int findSize(int n) {
        int n2 = n <= 64 ? n + n : n + (n >> 2);
        int n3 = 8;
        while (n3 < n2) {
            n3 += n3;
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public JsonSerializer<Object> find(SerializerCache.TypeKey typeKey) {
        int n = typeKey.hashCode() & -1 + this._buckets.length;
        Bucket bucket = this._buckets[n];
        if (bucket == null) {
            return null;
        }
        if (typeKey.equals(bucket.key)) {
            return bucket.value;
        }
        do {
            if ((bucket = bucket.next) == null) return null;
        } while (!typeKey.equals(bucket.key));
        return bucket.value;
    }

    public int size() {
        return this._size;
    }

    private static final class Bucket {
        public final SerializerCache.TypeKey key;
        public final Bucket next;
        public final JsonSerializer<Object> value;

        public Bucket(Bucket bucket, SerializerCache.TypeKey typeKey, JsonSerializer<Object> jsonSerializer) {
            this.next = bucket;
            this.key = typeKey;
            this.value = jsonSerializer;
        }
    }

}

