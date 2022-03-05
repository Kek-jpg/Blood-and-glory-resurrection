/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.NoSuchElementException
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BeanPropertyMap {
    private final Bucket[] _buckets;
    private final int _hashMask;
    private final int _size;

    public BeanPropertyMap(Collection<SettableBeanProperty> collection) {
        this._size = collection.size();
        int n = BeanPropertyMap.findSize(this._size);
        this._hashMask = n - 1;
        Bucket[] arrbucket = new Bucket[n];
        for (SettableBeanProperty settableBeanProperty : collection) {
            String string = settableBeanProperty.getName();
            int n2 = string.hashCode() & this._hashMask;
            arrbucket[n2] = new Bucket(arrbucket[n2], string, settableBeanProperty);
        }
        this._buckets = arrbucket;
    }

    private SettableBeanProperty _findWithEquals(String string, int n) {
        Bucket bucket = this._buckets[n];
        while (bucket != null) {
            if (string.equals((Object)bucket.key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final int findSize(int n) {
        int n2 = n <= 32 ? n + n : n + (n >> 2);
        int n3 = 2;
        while (n3 < n2) {
            n3 += n3;
        }
        return n3;
    }

    public Iterator<SettableBeanProperty> allProperties() {
        return new IteratorImpl(this._buckets);
    }

    public void assignIndexes() {
        int n = 0;
        for (Bucket bucket : this._buckets) {
            int n2 = n;
            while (bucket != null) {
                SettableBeanProperty settableBeanProperty = bucket.value;
                int n3 = n2 + 1;
                settableBeanProperty.assignIndex(n2);
                bucket = bucket.next;
                n2 = n3;
            }
            n = n2;
        }
    }

    public SettableBeanProperty find(String string) {
        int n = string.hashCode() & this._hashMask;
        Bucket bucket = this._buckets[n];
        if (bucket == null) {
            return null;
        }
        if (bucket.key == string) {
            return bucket.value;
        }
        while ((bucket = bucket.next) != null) {
            if (bucket.key != string) continue;
            return bucket.value;
        }
        return BeanPropertyMap.super._findWithEquals(string, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void remove(SettableBeanProperty settableBeanProperty) {
        String string = settableBeanProperty.getName();
        int n = string.hashCode() & -1 + this._buckets.length;
        boolean bl = false;
        Bucket bucket = this._buckets[n];
        Bucket bucket2 = null;
        while (bucket != null) {
            Bucket bucket3;
            if (!bl && bucket.key.equals((Object)string)) {
                bl = true;
                bucket3 = bucket2;
            } else {
                bucket3 = new Bucket(bucket2, bucket.key, bucket.value);
            }
            bucket = bucket.next;
            bucket2 = bucket3;
        }
        if (!bl) {
            throw new NoSuchElementException("No entry '" + settableBeanProperty + "' found, can't remove");
        }
        this._buckets[n] = bucket2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void replace(SettableBeanProperty settableBeanProperty) {
        String string = settableBeanProperty.getName();
        int n = string.hashCode() & -1 + this._buckets.length;
        boolean bl = false;
        Bucket bucket = this._buckets[n];
        Bucket bucket2 = null;
        while (bucket != null) {
            Bucket bucket3;
            if (!bl && bucket.key.equals((Object)string)) {
                bl = true;
                bucket3 = bucket2;
            } else {
                bucket3 = new Bucket(bucket2, bucket.key, bucket.value);
            }
            bucket = bucket.next;
            bucket2 = bucket3;
        }
        if (!bl) {
            throw new NoSuchElementException("No entry '" + settableBeanProperty + "' found, can't replace");
        }
        this._buckets[n] = new Bucket(bucket2, string, settableBeanProperty);
    }

    public int size() {
        return this._size;
    }

    private static final class Bucket {
        public final String key;
        public final Bucket next;
        public final SettableBeanProperty value;

        public Bucket(Bucket bucket, String string, SettableBeanProperty settableBeanProperty) {
            this.next = bucket;
            this.key = string;
            this.value = settableBeanProperty;
        }
    }

    private static final class IteratorImpl
    implements Iterator<SettableBeanProperty> {
        private final Bucket[] _buckets;
        private Bucket _currentBucket;
        private int _nextBucketIndex;

        /*
         * Enabled aggressive block sorting
         */
        public IteratorImpl(Bucket[] arrbucket) {
            int n;
            block2 : {
                this._buckets = arrbucket;
                int n2 = this._buckets.length;
                int n3 = 0;
                while (n3 < n2) {
                    Bucket[] arrbucket2 = this._buckets;
                    n = n3 + 1;
                    Bucket bucket = arrbucket2[n3];
                    if (bucket != null) {
                        this._currentBucket = bucket;
                        break block2;
                    }
                    n3 = n;
                }
                n = n3;
            }
            this._nextBucketIndex = n;
        }

        public boolean hasNext() {
            return this._currentBucket != null;
        }

        public SettableBeanProperty next() {
            Bucket bucket = this._currentBucket;
            if (bucket == null) {
                throw new NoSuchElementException();
            }
            Bucket bucket2 = bucket.next;
            while (bucket2 == null && this._nextBucketIndex < this._buckets.length) {
                Bucket[] arrbucket = this._buckets;
                int n = this._nextBucketIndex;
                this._nextBucketIndex = n + 1;
                bucket2 = arrbucket[n];
            }
            this._currentBucket = bucket2;
            return bucket.value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

