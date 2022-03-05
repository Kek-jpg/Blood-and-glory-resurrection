/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.flurry.org.codehaus.jackson.map.util;

public abstract class PrimitiveArrayBuilder<T> {
    static final int INITIAL_CHUNK_SIZE = 12;
    static final int MAX_CHUNK_SIZE = 262144;
    static final int SMALL_CHUNK_SIZE = 16384;
    Node<T> _bufferHead;
    Node<T> _bufferTail;
    int _bufferedEntryCount;
    T _freeBuffer;

    protected PrimitiveArrayBuilder() {
    }

    protected abstract T _constructArray(int var1);

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
    public final T appendCompletedChunk(T t, int n) {
        int n2;
        Node<T> node = new Node<T>(t, n);
        if (this._bufferHead == null) {
            this._bufferTail = node;
            this._bufferHead = node;
        } else {
            this._bufferTail.linkNext(node);
            this._bufferTail = node;
        }
        this._bufferedEntryCount = n + this._bufferedEntryCount;
        if (n < 16384) {
            n2 = n + n;
            return this._constructArray(n2);
        }
        n2 = n + (n >> 2);
        return this._constructArray(n2);
    }

    public T completeAndClearBuffer(T t, int n) {
        int n2 = n + this._bufferedEntryCount;
        T t2 = this._constructArray(n2);
        int n3 = 0;
        for (Node<T> node = this._bufferHead; node != null; node = node.next()) {
            n3 = node.copyData(t2, n3);
        }
        System.arraycopy(t, (int)0, t2, (int)n3, (int)n);
        int n4 = n3 + n;
        if (n4 != n2) {
            throw new IllegalStateException("Should have gotten " + n2 + " entries, got " + n4);
        }
        return t2;
    }

    public T resetAndStart() {
        this._reset();
        if (this._freeBuffer == null) {
            return this._constructArray(12);
        }
        return this._freeBuffer;
    }

    static final class Node<T> {
        final T _data;
        final int _dataLength;
        Node<T> _next;

        public Node(T t, int n) {
            this._data = t;
            this._dataLength = n;
        }

        public int copyData(T t, int n) {
            System.arraycopy(this._data, (int)0, t, (int)n, (int)this._dataLength);
            return n + this._dataLength;
        }

        public T getData() {
            return this._data;
        }

        public void linkNext(Node<T> node) {
            if (this._next != null) {
                throw new IllegalStateException();
            }
            this._next = node;
        }

        public Node<T> next() {
            return this._next;
        }
    }

}

