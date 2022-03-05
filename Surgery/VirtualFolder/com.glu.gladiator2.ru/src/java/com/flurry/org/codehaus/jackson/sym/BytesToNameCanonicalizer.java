/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 */
package com.flurry.org.codehaus.jackson.sym;

import com.flurry.org.codehaus.jackson.sym.Name;
import com.flurry.org.codehaus.jackson.sym.Name1;
import com.flurry.org.codehaus.jackson.sym.Name2;
import com.flurry.org.codehaus.jackson.sym.Name3;
import com.flurry.org.codehaus.jackson.sym.NameN;
import com.flurry.org.codehaus.jackson.util.InternCache;
import java.util.Arrays;

public final class BytesToNameCanonicalizer {
    protected static final int DEFAULT_TABLE_SIZE = 64;
    static final int INITIAL_COLLISION_LEN = 32;
    static final int LAST_VALID_BUCKET = 254;
    static final int MAX_ENTRIES_FOR_REUSE = 6000;
    protected static final int MAX_TABLE_SIZE = 65536;
    static final int MIN_HASH_SIZE = 16;
    private int _collCount;
    private int _collEnd;
    private Bucket[] _collList;
    private boolean _collListShared;
    private int _count;
    final boolean _intern;
    private int[] _mainHash;
    private int _mainHashMask;
    private boolean _mainHashShared;
    private Name[] _mainNames;
    private boolean _mainNamesShared;
    private transient boolean _needRehash;
    final BytesToNameCanonicalizer _parent;

    /*
     * Enabled aggressive block sorting
     */
    private BytesToNameCanonicalizer(int n, boolean bl) {
        this._parent = null;
        this._intern = bl;
        if (n < 16) {
            n = 16;
        } else if ((n & n - 1) != 0) {
            int n2;
            for (n2 = 16; n2 < n; n2 += n2) {
            }
            n = n2;
        }
        BytesToNameCanonicalizer.super.initTables(n);
    }

    private BytesToNameCanonicalizer(BytesToNameCanonicalizer bytesToNameCanonicalizer, boolean bl) {
        this._parent = bytesToNameCanonicalizer;
        this._intern = bl;
        this._count = bytesToNameCanonicalizer._count;
        this._mainHashMask = bytesToNameCanonicalizer._mainHashMask;
        this._mainHash = bytesToNameCanonicalizer._mainHash;
        this._mainNames = bytesToNameCanonicalizer._mainNames;
        this._collList = bytesToNameCanonicalizer._collList;
        this._collCount = bytesToNameCanonicalizer._collCount;
        this._collEnd = bytesToNameCanonicalizer._collEnd;
        this._needRehash = false;
        this._mainHashShared = true;
        this._mainNamesShared = true;
        this._collListShared = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _addSymbol(int n, Name name) {
        int n2;
        if (this._mainHashShared) {
            BytesToNameCanonicalizer.super.unshareMain();
        }
        if (this._needRehash) {
            BytesToNameCanonicalizer.super.rehash();
        }
        this._count = 1 + this._count;
        int n3 = n & this._mainHashMask;
        if (this._mainNames[n3] == null) {
            this._mainHash[n3] = n << 8;
            if (this._mainNamesShared) {
                BytesToNameCanonicalizer.super.unshareNames();
            }
            this._mainNames[n3] = name;
        } else {
            int n4;
            if (this._collListShared) {
                BytesToNameCanonicalizer.super.unshareCollision();
            }
            this._collCount = 1 + this._collCount;
            int n5 = this._mainHash[n3];
            int n6 = n5 & 255;
            if (n6 == 0) {
                if (this._collEnd <= 254) {
                    n4 = this._collEnd;
                    this._collEnd = 1 + this._collEnd;
                    if (n4 >= this._collList.length) {
                        BytesToNameCanonicalizer.super.expandCollision();
                    }
                } else {
                    n4 = BytesToNameCanonicalizer.super.findBestBucket();
                }
                this._mainHash[n3] = n5 & -256 | n4 + 1;
            } else {
                n4 = n6 - 1;
            }
            this._collList[n4] = new Bucket(name, this._collList[n4]);
        }
        if (this._count <= (n2 = this._mainHash.length) >> 1) return;
        {
            int n7 = n2 >> 2;
            if (this._count > n2 - n7) {
                this._needRehash = true;
                return;
            } else {
                if (this._collCount < n7) return;
                {
                    this._needRehash = true;
                    return;
                }
            }
        }
    }

    public static final int calcHash(int n) {
        int n2 = n ^ n >>> 16;
        return n2 ^ n2 >>> 8;
    }

    public static final int calcHash(int n, int n2) {
        int n3 = n2 + n * 31;
        int n4 = n3 ^ n3 >>> 16;
        return n4 ^ n4 >>> 8;
    }

    public static final int calcHash(int[] arrn, int n) {
        int n2 = arrn[0];
        for (int i2 = 1; i2 < n; ++i2) {
            n2 = n2 * 31 + arrn[i2];
        }
        int n3 = n2 ^ n2 >>> 16;
        return n3 ^ n3 >>> 8;
    }

    private static Name constructName(int n, String string2, int n2, int n3) {
        if (n3 == 0) {
            return new Name1(string2, n, n2);
        }
        return new Name2(string2, n, n2, n3);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Name constructName(int var0_1, String var1, int[] var2_3, int var3_2) {
        if (var3_2 >= 4) ** GOTO lbl-1000
        switch (var3_2) {
            default: lbl-1000: // 2 sources:
            {
                var4_4 = new int[var3_2];
                var5_5 = 0;
                while (var5_5 < var3_2) {
                    var4_4[var5_5] = var2_3[var5_5];
                    ++var5_5;
                }
                return new NameN(var1, var0_1, var4_4, var3_2);
            }
            case 1: {
                return new Name1(var1, var0_1, var2_3[0]);
            }
            case 2: {
                return new Name2(var1, var0_1, var2_3[0], var2_3[1]);
            }
            case 3: 
        }
        return new Name3(var1, var0_1, var2_3[0], var2_3[1], var2_3[2]);
    }

    public static BytesToNameCanonicalizer createRoot() {
        return new BytesToNameCanonicalizer(64, true);
    }

    private void expandCollision() {
        Bucket[] arrbucket = this._collList;
        int n = arrbucket.length;
        this._collList = new Bucket[n + n];
        System.arraycopy((Object)arrbucket, (int)0, (Object)this._collList, (int)0, (int)n);
    }

    private int findBestBucket() {
        Bucket[] arrbucket = this._collList;
        int n = Integer.MAX_VALUE;
        int n2 = -1;
        int n3 = this._collEnd;
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = arrbucket[i2].length();
            if (n4 >= n) continue;
            if (n4 == 1) {
                return i2;
            }
            n = n4;
            n2 = i2;
        }
        return n2;
    }

    public static Name getEmptyName() {
        return Name1.getEmptyName();
    }

    private void initTables(int n) {
        this._count = 0;
        this._mainHash = new int[n];
        this._mainNames = new Name[n];
        this._mainHashShared = false;
        this._mainNamesShared = false;
        this._mainHashMask = n - 1;
        this._collListShared = true;
        this._collList = null;
        this._collEnd = 0;
        this._needRehash = false;
    }

    private void markAsShared() {
        this._mainHashShared = true;
        this._mainNamesShared = true;
        this._collListShared = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void mergeChild(BytesToNameCanonicalizer bytesToNameCanonicalizer) {
        void var5_2 = this;
        synchronized (var5_2) {
            int n = bytesToNameCanonicalizer._count;
            int n2 = this._count;
            if (n > n2) {
                if (bytesToNameCanonicalizer.size() > 6000) {
                    BytesToNameCanonicalizer.super.initTables(64);
                } else {
                    this._count = bytesToNameCanonicalizer._count;
                    this._mainHash = bytesToNameCanonicalizer._mainHash;
                    this._mainNames = bytesToNameCanonicalizer._mainNames;
                    this._mainHashShared = true;
                    this._mainNamesShared = true;
                    this._mainHashMask = bytesToNameCanonicalizer._mainHashMask;
                    this._collList = bytesToNameCanonicalizer._collList;
                    this._collCount = bytesToNameCanonicalizer._collCount;
                    this._collEnd = bytesToNameCanonicalizer._collEnd;
                }
            }
            return;
        }
    }

    private void nukeSymbols() {
        this._count = 0;
        Arrays.fill((int[])this._mainHash, (int)0);
        Arrays.fill((Object[])this._mainNames, null);
        Arrays.fill((Object[])this._collList, null);
        this._collCount = 0;
        this._collEnd = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void rehash() {
        this._needRehash = false;
        this._mainNamesShared = false;
        int n = this._mainHash.length;
        int n2 = n + n;
        if (n2 > 65536) {
            this.nukeSymbols();
            return;
        }
        this._mainHash = new int[n2];
        this._mainHashMask = n2 - 1;
        Name[] arrname = this._mainNames;
        this._mainNames = new Name[n2];
        int n3 = 0;
        for (int i2 = 0; i2 < n; ++i2) {
            Name name = arrname[i2];
            if (name == null) continue;
            ++n3;
            int n4 = name.hashCode();
            int n5 = n4 & this._mainHashMask;
            this._mainNames[n5] = name;
            this._mainHash[n5] = n4 << 8;
        }
        int n6 = this._collEnd;
        if (n6 == 0) return;
        this._collCount = 0;
        this._collEnd = 0;
        this._collListShared = false;
        Bucket[] arrbucket = this._collList;
        this._collList = new Bucket[arrbucket.length];
        int n7 = 0;
        do {
            if (n7 >= n6) {
                if (n3 == this._count) return;
                throw new RuntimeException("Internal error: count after rehash " + n3 + "; should be " + this._count);
            }
            Bucket bucket = arrbucket[n7];
            while (bucket != null) {
                ++n3;
                Name name = bucket._name;
                int n8 = name.hashCode();
                int n9 = n8 & this._mainHashMask;
                int n10 = this._mainHash[n9];
                if (this._mainNames[n9] == null) {
                    this._mainHash[n9] = n8 << 8;
                    this._mainNames[n9] = name;
                } else {
                    Bucket bucket2;
                    int n11;
                    this._collCount = 1 + this._collCount;
                    int n12 = n10 & 255;
                    if (n12 == 0) {
                        if (this._collEnd <= 254) {
                            n11 = this._collEnd;
                            this._collEnd = 1 + this._collEnd;
                            if (n11 >= this._collList.length) {
                                this.expandCollision();
                            }
                        } else {
                            n11 = this.findBestBucket();
                        }
                        this._mainHash[n9] = n10 & -256 | n11 + 1;
                    } else {
                        n11 = n12 - 1;
                    }
                    Bucket[] arrbucket2 = this._collList;
                    arrbucket2[n11] = bucket2 = new Bucket(name, this._collList[n11]);
                }
                bucket = bucket._next;
            }
            ++n7;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void unshareCollision() {
        Bucket[] arrbucket = this._collList;
        if (arrbucket == null) {
            this._collList = new Bucket[32];
        } else {
            int n = arrbucket.length;
            this._collList = new Bucket[n];
            System.arraycopy((Object)arrbucket, (int)0, (Object)this._collList, (int)0, (int)n);
        }
        this._collListShared = false;
    }

    private void unshareMain() {
        int[] arrn = this._mainHash;
        int n = this._mainHash.length;
        this._mainHash = new int[n];
        System.arraycopy((Object)arrn, (int)0, (Object)this._mainHash, (int)0, (int)n);
        this._mainHashShared = false;
    }

    private void unshareNames() {
        Name[] arrname = this._mainNames;
        int n = arrname.length;
        this._mainNames = new Name[n];
        System.arraycopy((Object)arrname, (int)0, (Object)this._mainNames, (int)0, (int)n);
        this._mainNamesShared = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name addName(String string2, int n, int n2) {
        if (this._intern) {
            string2 = InternCache.instance.intern(string2);
        }
        int n3 = n2 == 0 ? BytesToNameCanonicalizer.calcHash(n) : BytesToNameCanonicalizer.calcHash(n, n2);
        Name name = BytesToNameCanonicalizer.constructName(n3, string2, n, n2);
        BytesToNameCanonicalizer.super._addSymbol(n3, name);
        return name;
    }

    public Name addName(String string2, int[] arrn, int n) {
        if (this._intern) {
            string2 = InternCache.instance.intern(string2);
        }
        int n2 = BytesToNameCanonicalizer.calcHash(arrn, n);
        Name name = BytesToNameCanonicalizer.constructName(n2, string2, arrn, n);
        BytesToNameCanonicalizer.super._addSymbol(n2, name);
        return name;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name findName(int n) {
        int n2;
        int n3;
        int n4;
        int n5 = BytesToNameCanonicalizer.calcHash(n);
        if ((n5 ^ (n2 = this._mainHash[n4 = n5 & this._mainHashMask]) >> 8) << 8 == 0) {
            Name name = this._mainNames[n4];
            if (name == null) {
                return null;
            }
            if (name.equals(n)) {
                return name;
            }
        } else if (n2 == 0) {
            return null;
        }
        if ((n3 = n2 & 255) <= 0) return null;
        int n6 = n3 - 1;
        Bucket bucket = this._collList[n6];
        if (bucket == null) return null;
        return bucket.find(n5, n, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Name findName(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = BytesToNameCanonicalizer.calcHash(n, n2);
        if ((n6 ^ (n3 = this._mainHash[n4 = n6 & this._mainHashMask]) >> 8) << 8 == 0) {
            Name name = this._mainNames[n4];
            if (name == null) {
                return null;
            }
            if (name.equals(n, n2)) {
                return name;
            }
        } else if (n3 == 0) {
            return null;
        }
        if ((n5 = n3 & 255) <= 0) return null;
        int n7 = n5 - 1;
        Bucket bucket = this._collList[n7];
        if (bucket == null) return null;
        return bucket.find(n6, n, n2);
    }

    public Name findName(int[] arrn, int n) {
        int n2;
        Bucket bucket;
        int n3;
        int n4;
        int n5;
        int n6 = BytesToNameCanonicalizer.calcHash(arrn, n);
        if ((n6 ^ (n2 = this._mainHash[n3 = n6 & this._mainHashMask]) >> 8) << 8 == 0) {
            Name name = this._mainNames[n3];
            if (name == null || name.equals(arrn, n)) {
                return name;
            }
        } else if (n2 == 0) {
            return null;
        }
        if ((n4 = n2 & 255) > 0 && (bucket = this._collList[n5 = n4 - 1]) != null) {
            return bucket.find(n6, arrn, n);
        }
        return null;
    }

    public BytesToNameCanonicalizer makeChild(boolean bl, boolean bl2) {
        void var5_3 = this;
        synchronized (var5_3) {
            BytesToNameCanonicalizer bytesToNameCanonicalizer = new BytesToNameCanonicalizer((BytesToNameCanonicalizer)this, bl2);
            return bytesToNameCanonicalizer;
        }
    }

    public boolean maybeDirty() {
        return !this._mainHashShared;
    }

    public void release() {
        if (this.maybeDirty() && this._parent != null) {
            this._parent.mergeChild(this);
            this.markAsShared();
        }
    }

    public int size() {
        return this._count;
    }

    static final class Bucket {
        protected final Name _name;
        protected final Bucket _next;

        Bucket(Name name, Bucket bucket) {
            this._name = name;
            this._next = bucket;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Name find(int n, int n2, int n3) {
            if (this._name.hashCode() == n && this._name.equals(n2, n3)) {
                return this._name;
            }
            Bucket bucket = this._next;
            while (bucket != null) {
                Name name = bucket._name;
                if (name.hashCode() == n) {
                    if (name.equals(n2, n3)) return name;
                }
                bucket = bucket._next;
            }
            return null;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Name find(int n, int[] arrn, int n2) {
            if (this._name.hashCode() == n && this._name.equals(arrn, n2)) {
                return this._name;
            }
            Bucket bucket = this._next;
            while (bucket != null) {
                Name name = bucket._name;
                if (name.hashCode() == n) {
                    if (name.equals(arrn, n2)) return name;
                }
                bucket = bucket._next;
            }
            return null;
        }

        public int length() {
            int n = 1;
            Bucket bucket = this._next;
            while (bucket != null) {
                ++n;
                bucket = bucket._next;
            }
            return n;
        }
    }

}

