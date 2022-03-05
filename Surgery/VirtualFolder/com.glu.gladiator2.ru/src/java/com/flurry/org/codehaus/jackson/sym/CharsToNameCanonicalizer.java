/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Error
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 */
package com.flurry.org.codehaus.jackson.sym;

import com.flurry.org.codehaus.jackson.util.InternCache;
import java.util.Arrays;

public final class CharsToNameCanonicalizer {
    protected static final int DEFAULT_TABLE_SIZE = 64;
    static final int MAX_ENTRIES_FOR_REUSE = 12000;
    protected static final int MAX_TABLE_SIZE = 65536;
    static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer();
    protected Bucket[] _buckets;
    protected final boolean _canonicalize;
    protected boolean _dirty;
    protected int _indexMask;
    protected final boolean _intern;
    protected CharsToNameCanonicalizer _parent;
    protected int _size;
    protected int _sizeThreshold;
    protected String[] _symbols;

    private CharsToNameCanonicalizer() {
        this._canonicalize = true;
        this._intern = true;
        this._dirty = true;
        this.initTables(64);
    }

    private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, boolean bl, boolean bl2, String[] arrstring, Bucket[] arrbucket, int n) {
        this._parent = charsToNameCanonicalizer;
        this._canonicalize = bl;
        this._intern = bl2;
        this._symbols = arrstring;
        this._buckets = arrbucket;
        this._size = n;
        int n2 = arrstring.length;
        this._sizeThreshold = n2 - (n2 >> 2);
        this._indexMask = n2 - 1;
        this._dirty = false;
    }

    public static int calcHash(String string2) {
        int n = string2.charAt(0);
        int n2 = string2.length();
        for (int i2 = 1; i2 < n2; ++i2) {
            n = n * 31 + string2.charAt(i2);
        }
        return n;
    }

    public static int calcHash(char[] arrc, int n, int n2) {
        int n3 = arrc[0];
        for (int i2 = 1; i2 < n2; ++i2) {
            n3 = n3 * 31 + arrc[i2];
        }
        return n3;
    }

    private void copyArrays() {
        String[] arrstring = this._symbols;
        int n = arrstring.length;
        this._symbols = new String[n];
        System.arraycopy((Object)arrstring, (int)0, (Object)this._symbols, (int)0, (int)n);
        Bucket[] arrbucket = this._buckets;
        int n2 = arrbucket.length;
        this._buckets = new Bucket[n2];
        System.arraycopy((Object)arrbucket, (int)0, (Object)this._buckets, (int)0, (int)n2);
    }

    public static CharsToNameCanonicalizer createRoot() {
        return sBootstrapSymbolTable.makeOrphan();
    }

    private void initTables(int n) {
        this._symbols = new String[n];
        this._buckets = new Bucket[n >> 1];
        this._indexMask = n - 1;
        this._size = 0;
        this._sizeThreshold = n - (n >> 2);
    }

    private CharsToNameCanonicalizer makeOrphan() {
        return new CharsToNameCanonicalizer(null, true, true, this._symbols, this._buckets, this._size);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void mergeChild(CharsToNameCanonicalizer charsToNameCanonicalizer) {
        void var3_2 = this;
        synchronized (var3_2) {
            block6 : {
                block5 : {
                    block4 : {
                        if (charsToNameCanonicalizer.size() <= 12000) break block4;
                        CharsToNameCanonicalizer.super.initTables(64);
                        break block5;
                    }
                    if (charsToNameCanonicalizer.size() <= this.size()) break block6;
                    this._symbols = charsToNameCanonicalizer._symbols;
                    this._buckets = charsToNameCanonicalizer._buckets;
                    this._size = charsToNameCanonicalizer._size;
                    this._sizeThreshold = charsToNameCanonicalizer._sizeThreshold;
                    this._indexMask = charsToNameCanonicalizer._indexMask;
                }
                this._dirty = false;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void rehash() {
        int n = this._symbols.length;
        int n2 = n + n;
        if (n2 > 65536) {
            this._size = 0;
            Arrays.fill((Object[])this._symbols, null);
            Arrays.fill((Object[])this._buckets, null);
            this._dirty = true;
            return;
        }
        String[] arrstring = this._symbols;
        Bucket[] arrbucket = this._buckets;
        this._symbols = new String[n2];
        this._buckets = new Bucket[n2 >> 1];
        this._indexMask = n2 - 1;
        this._sizeThreshold += this._sizeThreshold;
        int n3 = 0;
        for (int i2 = 0; i2 < n; ++i2) {
            String string2 = arrstring[i2];
            if (string2 == null) continue;
            ++n3;
            int n4 = CharsToNameCanonicalizer.calcHash(string2) & this._indexMask;
            if (this._symbols[n4] == null) {
                this._symbols[n4] = string2;
                continue;
            }
            int n5 = n4 >> 1;
            this._buckets[n5] = new Bucket(string2, this._buckets[n5]);
        }
        int n6 = n >> 1;
        int n7 = 0;
        do {
            if (n7 >= n6) {
                if (n3 == this._size) return;
                throw new Error("Internal error on SymbolTable.rehash(): had " + this._size + " entries; now have " + n3 + ".");
            }
            for (Bucket bucket = arrbucket[n7]; bucket != null; bucket = bucket.getNext()) {
                ++n3;
                String string3 = bucket.getSymbol();
                int n8 = CharsToNameCanonicalizer.calcHash(string3) & this._indexMask;
                if (this._symbols[n8] == null) {
                    this._symbols[n8] = string3;
                    continue;
                }
                int n9 = n8 >> 1;
                this._buckets[n9] = new Bucket(string3, this._buckets[n9]);
            }
            ++n7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String findSymbol(char[] arrc, int n, int n2, int n3) {
        if (n2 < 1) {
            return "";
        }
        if (!this._canonicalize) {
            return new String(arrc, n, n2);
        }
        int n4 = n3 & this._indexMask;
        String string2 = this._symbols[n4];
        if (string2 != null) {
            Bucket bucket;
            if (string2.length() == n2) {
                int n5 = 0;
                while (string2.charAt(n5) == arrc[n + n5] && ++n5 < n2) {
                }
                if (n5 == n2) return string2;
            }
            if ((bucket = this._buckets[n4 >> 1]) != null) {
                string2 = bucket.find(arrc, n, n2);
                if (string2 != null) return string2;
            }
        }
        if (!this._dirty) {
            CharsToNameCanonicalizer.super.copyArrays();
            this._dirty = true;
        } else if (this._size >= this._sizeThreshold) {
            CharsToNameCanonicalizer.super.rehash();
            n4 = CharsToNameCanonicalizer.calcHash(arrc, n, n2) & this._indexMask;
        }
        this._size = 1 + this._size;
        String string3 = new String(arrc, n, n2);
        if (this._intern) {
            string3 = InternCache.instance.intern(string3);
        }
        if (this._symbols[n4] == null) {
            this._symbols[n4] = string3;
            return string3;
        }
        int n6 = n4 >> 1;
        this._buckets[n6] = new Bucket(string3, this._buckets[n6]);
        return string3;
    }

    public CharsToNameCanonicalizer makeChild(boolean bl, boolean bl2) {
        void var5_3 = this;
        synchronized (var5_3) {
            CharsToNameCanonicalizer charsToNameCanonicalizer = new CharsToNameCanonicalizer((CharsToNameCanonicalizer)this, bl, bl2, this._symbols, this._buckets, this._size);
            return charsToNameCanonicalizer;
        }
    }

    public boolean maybeDirty() {
        return this._dirty;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void release() {
        if (!this.maybeDirty() || this._parent == null) {
            return;
        }
        this._parent.mergeChild(this);
        this._dirty = false;
    }

    public int size() {
        return this._size;
    }

    static final class Bucket {
        private final String _symbol;
        private final Bucket mNext;

        public Bucket(String string2, Bucket bucket) {
            this._symbol = string2;
            this.mNext = bucket;
        }

        /*
         * Enabled aggressive block sorting
         */
        public String find(char[] arrc, int n, int n2) {
            String string2 = this._symbol;
            Bucket bucket = this.mNext;
            do {
                if (string2.length() == n2) {
                    int n3 = 0;
                    while (string2.charAt(n3) == arrc[n + n3] && ++n3 < n2) {
                    }
                    if (n3 == n2) {
                        return string2;
                    }
                }
                if (bucket == null) {
                    return null;
                }
                string2 = bucket.getSymbol();
                bucket = bucket.getNext();
            } while (true);
        }

        public Bucket getNext() {
            return this.mNext;
        }

        public String getSymbol() {
            return this._symbol;
        }
    }

}

