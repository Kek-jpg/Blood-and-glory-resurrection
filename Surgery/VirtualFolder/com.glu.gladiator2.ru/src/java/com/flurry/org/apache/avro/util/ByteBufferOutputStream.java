/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.LinkedList
 *  java.util.List
 */
package com.flurry.org.apache.avro.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ByteBufferOutputStream
extends OutputStream {
    public static final int BUFFER_SIZE = 8192;
    private List<ByteBuffer> buffers;

    public ByteBufferOutputStream() {
        this.reset();
    }

    public void append(List<ByteBuffer> list) {
        for (ByteBuffer byteBuffer : list) {
            byteBuffer.position(byteBuffer.limit());
        }
        this.buffers.addAll(list);
    }

    public List<ByteBuffer> getBufferList() {
        List<ByteBuffer> list = this.buffers;
        this.reset();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            ((ByteBuffer)iterator.next()).flip();
        }
        return list;
    }

    public void prepend(List<ByteBuffer> list) {
        for (ByteBuffer byteBuffer : list) {
            byteBuffer.position(byteBuffer.limit());
        }
        this.buffers.addAll(0, list);
    }

    public void reset() {
        this.buffers = new LinkedList();
        this.buffers.add((Object)ByteBuffer.allocate((int)8192));
    }

    public void write(int n2) {
        ByteBuffer byteBuffer = (ByteBuffer)this.buffers.get(-1 + this.buffers.size());
        if (byteBuffer.remaining() < 1) {
            byteBuffer = ByteBuffer.allocate((int)8192);
            this.buffers.add((Object)byteBuffer);
        }
        byteBuffer.put((byte)n2);
    }

    public void write(ByteBuffer byteBuffer) {
        this.buffers.add((Object)byteBuffer);
    }

    public void write(byte[] arrby, int n2, int n3) {
        ByteBuffer byteBuffer = (ByteBuffer)this.buffers.get(-1 + this.buffers.size());
        int n4 = byteBuffer.remaining();
        while (n3 > n4) {
            byteBuffer.put(arrby, n2, n4);
            n3 -= n4;
            n2 += n4;
            byteBuffer = ByteBuffer.allocate((int)8192);
            this.buffers.add((Object)byteBuffer);
            n4 = byteBuffer.remaining();
        }
        byteBuffer.put(arrby, n2, n3);
    }

    public void writeBuffer(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() < 8192) {
            this.write(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
            return;
        }
        ByteBuffer byteBuffer2 = byteBuffer.duplicate();
        byteBuffer2.position(byteBuffer.limit());
        this.buffers.add((Object)byteBuffer2);
    }
}

