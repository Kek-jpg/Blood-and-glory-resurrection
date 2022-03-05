/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.util.ObjectBuffer$Node
 *  java.lang.Class
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.reflect.Array
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.util;

import com.flurry.org.codehaus.jackson.map.util.ObjectBuffer;
import java.lang.reflect.Array;
import java.util.List;

public final class ObjectBuffer {
    static final int INITIAL_CHUNK_SIZE = 12;
    static final int MAX_CHUNK_SIZE = 262144;
    static final int SMALL_CHUNK_SIZE = 16384;
    private Node _bufferHead;
    private Node _bufferTail;
    private int _bufferedEntryCount;
    private Object[] _freeBuffer;

    protected final void _copyTo(Object object, int n, Object[] arrobject, int n2) {
        int n3 = 0;
        for (Node node = this._bufferHead; node != null; node = node.next()) {
            Object[] arrobject2 = node.getData();
            int n4 = arrobject2.length;
            System.arraycopy((Object)arrobject2, (int)0, (Object)object, (int)n3, (int)n4);
            n3 += n4;
        }
        System.arraycopy((Object)arrobject, (int)0, (Object)object, (int)n3, (int)n2);
        int n5 = n3 + n2;
        if (n5 != n) {
            throw new IllegalStateException("Should have gotten " + n + " entries, got " + n5);
        }
    }

    protected void _reset() {
        if (this._bufferTail != null) {
            this._freeBuffer = this._bufferTail.getData();
        }
        this._bufferTail = null;
        this._bufferHead = null;
        this._bufferedEntryCount = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object[] appendCompletedChunk(Object[] arrobject) {
        int n;
        Node node = new Node(arrobject);
        if (this._bufferHead == null) {
            this._bufferTail = node;
            this._bufferHead = node;
        } else {
            this._bufferTail.linkNext(node);
            this._bufferTail = node;
        }
        int n2 = arrobject.length;
        this._bufferedEntryCount = n2 + this._bufferedEntryCount;
        if (n2 < 16384) {
            n = n2 + n2;
            return new Object[n];
        }
        n = n2 + (n2 >> 2);
        return new Object[n];
    }

    public int bufferedSize() {
        return this._bufferedEntryCount;
    }

    public void completeAndClearBuffer(Object[] arrobject, int n, List<Object> list) {
        for (Node node = this._bufferHead; node != null; node = node.next()) {
            Object[] arrobject2 = node.getData();
            int n2 = arrobject2.length;
            for (int i = 0; i < n2; ++i) {
                list.add(arrobject2[i]);
            }
        }
        for (int i = 0; i < n; ++i) {
            list.add(arrobject[i]);
        }
    }

    public Object[] completeAndClearBuffer(Object[] arrobject, int n) {
        int n2 = n + this._bufferedEntryCount;
        Object[] arrobject2 = new Object[n2];
        this._copyTo(arrobject2, n2, arrobject, n);
        return arrobject2;
    }

    public <T> T[] completeAndClearBuffer(Object[] arrobject, int n, Class<T> class_) {
        int n2 = n + this._bufferedEntryCount;
        Object[] arrobject2 = (Object[])Array.newInstance(class_, (int)n2);
        this._copyTo(arrobject2, n2, arrobject, n);
        this._reset();
        return arrobject2;
    }

    public int initialCapacity() {
        if (this._freeBuffer == null) {
            return 0;
        }
        return this._freeBuffer.length;
    }

    public Object[] resetAndStart() {
        this._reset();
        if (this._freeBuffer == null) {
            return new Object[12];
        }
        return this._freeBuffer;
    }
}

