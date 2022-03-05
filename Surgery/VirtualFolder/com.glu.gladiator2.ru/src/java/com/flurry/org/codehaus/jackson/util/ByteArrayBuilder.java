/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.OutputStream
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.util.LinkedList
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import java.io.OutputStream;
import java.util.LinkedList;

public final class ByteArrayBuilder
extends OutputStream {
    static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
    private static final int INITIAL_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 262144;
    private static final byte[] NO_BYTES = new byte[0];
    private final BufferRecycler _bufferRecycler;
    private byte[] _currBlock;
    private int _currBlockPtr;
    private final LinkedList<byte[]> _pastBlocks;
    private int _pastLen;

    public ByteArrayBuilder() {
        this(null);
    }

    public ByteArrayBuilder(int n) {
        super(null, n);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler) {
        super(bufferRecycler, 500);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler, int n) {
        this._pastBlocks = new LinkedList();
        this._bufferRecycler = bufferRecycler;
        if (bufferRecycler == null) {
            this._currBlock = new byte[n];
            return;
        }
        this._currBlock = bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER);
    }

    private void _allocMore() {
        this._pastLen += this._currBlock.length;
        int n = Math.max((int)(this._pastLen >> 1), (int)1000);
        if (n > 262144) {
            n = 262144;
        }
        this._pastBlocks.add((Object)this._currBlock);
        this._currBlock = new byte[n];
        this._currBlockPtr = 0;
    }

    public void append(int n) {
        if (this._currBlockPtr >= this._currBlock.length) {
            ByteArrayBuilder.super._allocMore();
        }
        byte[] arrby = this._currBlock;
        int n2 = this._currBlockPtr;
        this._currBlockPtr = n2 + 1;
        arrby[n2] = (byte)n;
    }

    public void appendThreeBytes(int n) {
        if (2 + this._currBlockPtr < this._currBlock.length) {
            byte[] arrby = this._currBlock;
            int n2 = this._currBlockPtr;
            this._currBlockPtr = n2 + 1;
            arrby[n2] = (byte)(n >> 16);
            byte[] arrby2 = this._currBlock;
            int n3 = this._currBlockPtr;
            this._currBlockPtr = n3 + 1;
            arrby2[n3] = (byte)(n >> 8);
            byte[] arrby3 = this._currBlock;
            int n4 = this._currBlockPtr;
            this._currBlockPtr = n4 + 1;
            arrby3[n4] = (byte)n;
            return;
        }
        this.append(n >> 16);
        this.append(n >> 8);
        this.append(n);
    }

    public void appendTwoBytes(int n) {
        if (1 + this._currBlockPtr < this._currBlock.length) {
            byte[] arrby = this._currBlock;
            int n2 = this._currBlockPtr;
            this._currBlockPtr = n2 + 1;
            arrby[n2] = (byte)(n >> 8);
            byte[] arrby2 = this._currBlock;
            int n3 = this._currBlockPtr;
            this._currBlockPtr = n3 + 1;
            arrby2[n3] = (byte)n;
            return;
        }
        this.append(n >> 8);
        this.append(n);
    }

    public void close() {
    }

    public byte[] completeAndCoalesce(int n) {
        this._currBlockPtr = n;
        return this.toByteArray();
    }

    public byte[] finishCurrentSegment() {
        this._allocMore();
        return this._currBlock;
    }

    public void flush() {
    }

    public byte[] getCurrentSegment() {
        return this._currBlock;
    }

    public int getCurrentSegmentLength() {
        return this._currBlockPtr;
    }

    public void release() {
        this.reset();
        if (this._bufferRecycler != null && this._currBlock != null) {
            this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_CONCAT_BUFFER, this._currBlock);
            this._currBlock = null;
        }
    }

    public void reset() {
        this._pastLen = 0;
        this._currBlockPtr = 0;
        if (!this._pastBlocks.isEmpty()) {
            this._pastBlocks.clear();
        }
    }

    public byte[] resetAndGetFirstSegment() {
        this.reset();
        return this._currBlock;
    }

    public void setCurrentSegmentLength(int n) {
        this._currBlockPtr = n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public byte[] toByteArray() {
        int n = this._pastLen + this._currBlockPtr;
        if (n == 0) {
            return NO_BYTES;
        }
        byte[] arrby = new byte[n];
        int n2 = 0;
        for (byte[] arrby2 : this._pastBlocks) {
            int n3 = arrby2.length;
            System.arraycopy((Object)arrby2, (int)0, (Object)arrby, (int)n2, (int)n3);
            n2 += n3;
        }
        System.arraycopy((Object)this._currBlock, (int)0, (Object)arrby, (int)n2, (int)this._currBlockPtr);
        int n4 = n2 + this._currBlockPtr;
        if (n4 != n) {
            throw new RuntimeException("Internal error: total len assumed to be " + n + ", copied " + n4 + " bytes");
        }
        if (this._pastBlocks.isEmpty()) return arrby;
        this.reset();
        return arrby;
    }

    public void write(int n) {
        this.append(n);
    }

    public void write(byte[] arrby) {
        this.write(arrby, 0, arrby.length);
    }

    public void write(byte[] arrby, int n, int n2) {
        do {
            int n3;
            if ((n3 = Math.min((int)(this._currBlock.length - this._currBlockPtr), (int)n2)) > 0) {
                System.arraycopy((Object)arrby, (int)n, (Object)this._currBlock, (int)this._currBlockPtr, (int)n3);
                n += n3;
                this._currBlockPtr = n3 + this._currBlockPtr;
                n2 -= n3;
            }
            if (n2 <= 0) {
                return;
            }
            ByteArrayBuilder.super._allocMore();
        } while (true);
    }
}

