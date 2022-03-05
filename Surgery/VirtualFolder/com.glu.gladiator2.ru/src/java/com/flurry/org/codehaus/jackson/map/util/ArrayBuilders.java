/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$ArrayIterator
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$BooleanBuilder
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$ByteBuilder
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$DoubleBuilder
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$FloatBuilder
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$IntBuilder
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$LongBuilder
 *  com.flurry.org.codehaus.jackson.map.util.ArrayBuilders$ShortBuilder
 *  java.lang.Class
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.System
 *  java.lang.reflect.Array
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class ArrayBuilders {
    BooleanBuilder _booleanBuilder = null;
    ByteBuilder _byteBuilder = null;
    DoubleBuilder _doubleBuilder = null;
    FloatBuilder _floatBuilder = null;
    IntBuilder _intBuilder = null;
    LongBuilder _longBuilder = null;
    ShortBuilder _shortBuilder = null;

    public static <T> List<T> addToList(List<T> arrayList, T t) {
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        arrayList.add(t);
        return arrayList;
    }

    public static <T> Iterable<T> arrayAsIterable(T[] arrT) {
        return new ArrayIterator((Object[])arrT);
    }

    public static <T> Iterator<T> arrayAsIterator(T[] arrT) {
        return new ArrayIterator((Object[])arrT);
    }

    public static <T> HashSet<T> arrayToSet(T[] arrT) {
        HashSet hashSet = new HashSet();
        if (arrT != null) {
            int n = arrT.length;
            for (int i = 0; i < n; ++i) {
                hashSet.add(arrT[i]);
            }
        }
        return hashSet;
    }

    public static <T> T[] insertInList(T[] arrT, T t) {
        int n = arrT.length;
        Object[] arrobject = (Object[])Array.newInstance((Class)arrT.getClass().getComponentType(), (int)(n + 1));
        if (n > 0) {
            System.arraycopy(arrT, (int)0, (Object)arrobject, (int)1, (int)n);
        }
        arrobject[0] = t;
        return arrobject;
    }

    public static <T> T[] insertInListNoDup(T[] arrT, T t) {
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            if (arrT[i] != t) continue;
            if (i == 0) {
                return arrT;
            }
            Object[] arrobject = (Object[])Array.newInstance((Class)arrT.getClass().getComponentType(), (int)n);
            System.arraycopy(arrT, (int)0, (Object)arrobject, (int)1, (int)i);
            arrT[0] = t;
            return arrobject;
        }
        Object[] arrobject = (Object[])Array.newInstance((Class)arrT.getClass().getComponentType(), (int)(n + 1));
        if (n > 0) {
            System.arraycopy(arrT, (int)0, (Object)arrobject, (int)1, (int)n);
        }
        arrobject[0] = t;
        return arrobject;
    }

    public BooleanBuilder getBooleanBuilder() {
        if (this._booleanBuilder == null) {
            this._booleanBuilder = new BooleanBuilder();
        }
        return this._booleanBuilder;
    }

    public ByteBuilder getByteBuilder() {
        if (this._byteBuilder == null) {
            this._byteBuilder = new ByteBuilder();
        }
        return this._byteBuilder;
    }

    public DoubleBuilder getDoubleBuilder() {
        if (this._doubleBuilder == null) {
            this._doubleBuilder = new DoubleBuilder();
        }
        return this._doubleBuilder;
    }

    public FloatBuilder getFloatBuilder() {
        if (this._floatBuilder == null) {
            this._floatBuilder = new FloatBuilder();
        }
        return this._floatBuilder;
    }

    public IntBuilder getIntBuilder() {
        if (this._intBuilder == null) {
            this._intBuilder = new IntBuilder();
        }
        return this._intBuilder;
    }

    public LongBuilder getLongBuilder() {
        if (this._longBuilder == null) {
            this._longBuilder = new LongBuilder();
        }
        return this._longBuilder;
    }

    public ShortBuilder getShortBuilder() {
        if (this._shortBuilder == null) {
            this._shortBuilder = new ShortBuilder();
        }
        return this._shortBuilder;
    }
}

