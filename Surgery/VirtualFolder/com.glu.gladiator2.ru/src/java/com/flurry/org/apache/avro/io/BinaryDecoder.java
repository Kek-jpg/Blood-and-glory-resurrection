/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.util.Utf8;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BinaryDecoder
extends Decoder {
    private byte[] buf;
    private int limit;
    private int minPos;
    private int pos;
    private final Utf8 scratchUtf8;
    private ByteSource source;

    protected BinaryDecoder() {
        this.source = null;
        this.buf = null;
        this.minPos = 0;
        this.pos = 0;
        this.limit = 0;
        this.scratchUtf8 = new Utf8();
    }

    BinaryDecoder(InputStream inputStream, int n2) {
        this.source = null;
        this.buf = null;
        this.minPos = 0;
        this.pos = 0;
        this.limit = 0;
        this.scratchUtf8 = new Utf8();
        this.configure(inputStream, n2);
    }

    BinaryDecoder(byte[] arrby, int n2, int n3) {
        this.source = null;
        this.buf = null;
        this.minPos = 0;
        this.pos = 0;
        this.limit = 0;
        this.scratchUtf8 = new Utf8();
        this.configure(arrby, n2, n3);
    }

    private void configureSource(int n2, ByteSource byteSource) {
        if (this.source != null) {
            this.source.detach();
        }
        byteSource.attach(n2, (BinaryDecoder)this);
        this.source = byteSource;
    }

    private long doSkipItems() throws IOException {
        long l2 = this.readInt();
        while (l2 < 0L) {
            this.doSkipBytes(this.readLong());
            l2 = this.readInt();
        }
        return l2;
    }

    private void ensureBounds(int n2) throws IOException {
        int n3 = this.limit - this.pos;
        if (n3 < n2) {
            this.source.compactAndFill(this.buf, this.pos, this.minPos, n3);
        }
    }

    private long innerLongDecode(long l2) throws IOException {
        int n2 = 1;
        int n3 = 255 & this.buf[this.pos];
        long l3 = l2 ^ (127L & (long)n3) << 28;
        if (n3 > 127) {
            byte[] arrby = this.buf;
            int n4 = this.pos;
            int n5 = n2 + 1;
            int n6 = 255 & arrby[n4 + n2];
            l3 ^= (127L & (long)n6) << 35;
            if (n6 > 127) {
                byte[] arrby2 = this.buf;
                int n7 = this.pos;
                n2 = n5 + 1;
                int n8 = 255 & arrby2[n7 + 2];
                l3 ^= (127L & (long)n8) << 42;
                if (n8 > 127) {
                    byte[] arrby3 = this.buf;
                    int n9 = this.pos;
                    ++n2;
                    int n10 = 255 & arrby3[n9 + 3];
                    l3 ^= (127L & (long)n10) << 49;
                    if (n10 > 127) {
                        byte[] arrby4 = this.buf;
                        int n11 = this.pos;
                        ++n2;
                        int n12 = 255 & arrby4[n11 + 4];
                        l3 ^= (127L & (long)n12) << 56;
                        if (n12 > 127) {
                            byte[] arrby5 = this.buf;
                            int n13 = this.pos;
                            ++n2;
                            int n14 = 255 & arrby5[n13 + 5];
                            l3 ^= (127L & (long)n14) << 63;
                            if (n14 > 127) {
                                throw new IOException("Invalid long encoding");
                            }
                        }
                    }
                }
            } else {
                n2 = n5;
            }
        }
        this.pos = n2 + this.pos;
        return l3;
    }

    @Override
    public long arrayNext() throws IOException {
        return this.doReadItemCount();
    }

    BinaryDecoder configure(InputStream inputStream, int n2) {
        BinaryDecoder.super.configureSource(n2, new InputStreamByteSource(inputStream, null));
        return this;
    }

    BinaryDecoder configure(byte[] arrby, int n2, int n3) {
        BinaryDecoder.super.configureSource(8192, new ByteArrayByteSource(arrby, n2, n3, null));
        return this;
    }

    protected void doReadBytes(byte[] arrby, int n2, int n3) throws IOException {
        int n4 = this.limit - this.pos;
        if (n3 <= n4) {
            System.arraycopy((Object)this.buf, (int)this.pos, (Object)arrby, (int)n2, (int)n3);
            this.pos = n3 + this.pos;
            return;
        }
        System.arraycopy((Object)this.buf, (int)this.pos, (Object)arrby, (int)n2, (int)n4);
        int n5 = n2 + n4;
        int n6 = n3 - n4;
        this.pos = this.limit;
        this.source.readRaw(arrby, n5, n6);
    }

    protected long doReadItemCount() throws IOException {
        long l2 = this.readLong();
        if (l2 < 0L) {
            this.readLong();
            l2 = -l2;
        }
        return l2;
    }

    protected void doSkipBytes(long l2) throws IOException {
        int n2 = this.limit - this.pos;
        if (l2 <= (long)n2) {
            this.pos = (int)(l2 + (long)this.pos);
            return;
        }
        this.pos = 0;
        this.limit = 0;
        long l3 = l2 - (long)n2;
        this.source.skipSourceBytes(l3);
    }

    BufferAccessor getBufferAccessor() {
        return new BufferAccessor(this, null);
    }

    public InputStream inputStream() {
        return this.source;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isEnd() throws IOException {
        block5 : {
            block4 : {
                if (this.limit - this.pos > 0) break block4;
                if (this.source.isEof()) {
                    return true;
                }
                int n2 = this.source.tryReadRaw(this.buf, 0, this.buf.length);
                this.pos = 0;
                this.limit = n2;
                if (n2 == 0) break block5;
            }
            return false;
        }
        return true;
    }

    @Override
    public long mapNext() throws IOException {
        return this.doReadItemCount();
    }

    @Override
    public long readArrayStart() throws IOException {
        return this.doReadItemCount();
    }

    @Override
    public boolean readBoolean() throws IOException {
        if (this.limit == this.pos) {
            this.limit = this.source.tryReadRaw(this.buf, 0, this.buf.length);
            this.pos = 0;
            if (this.limit == 0) {
                throw new EOFException();
            }
        }
        byte[] arrby = this.buf;
        int n2 = this.pos;
        this.pos = n2 + 1;
        return (255 & arrby[n2]) == 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ByteBuffer readBytes(ByteBuffer byteBuffer) throws IOException {
        ByteBuffer byteBuffer2;
        int n2 = this.readInt();
        if (byteBuffer != null && n2 <= byteBuffer.capacity()) {
            byteBuffer2 = byteBuffer;
            byteBuffer2.clear();
        } else {
            byteBuffer2 = ByteBuffer.allocate((int)n2);
        }
        this.doReadBytes(byteBuffer2.array(), byteBuffer2.position(), n2);
        byteBuffer2.limit(n2);
        return byteBuffer2;
    }

    @Override
    public double readDouble() throws IOException {
        this.ensureBounds(8);
        int n2 = 255 & this.buf[this.pos];
        byte[] arrby = this.buf;
        int n3 = this.pos;
        int n4 = 1 + 1;
        int n5 = n2 | (255 & arrby[n3 + 1]) << 8;
        byte[] arrby2 = this.buf;
        int n6 = this.pos;
        int n7 = n4 + 1;
        int n8 = n5 | (255 & arrby2[n6 + 2]) << 16;
        byte[] arrby3 = this.buf;
        int n9 = this.pos;
        int n10 = n7 + 1;
        int n11 = n8 | (255 & arrby3[n9 + 3]) << 24;
        byte[] arrby4 = this.buf;
        int n12 = this.pos;
        int n13 = n10 + 1;
        int n14 = 255 & arrby4[n12 + 4];
        byte[] arrby5 = this.buf;
        int n15 = this.pos;
        int n16 = n13 + 1;
        int n17 = n14 | (255 & arrby5[n15 + 5]) << 8;
        byte[] arrby6 = this.buf;
        int n18 = this.pos;
        int n19 = n16 + 1;
        int n20 = n17 | (255 & arrby6[n18 + 6]) << 16;
        byte[] arrby7 = this.buf;
        int n21 = this.pos;
        n19 + 1;
        int n22 = n20 | (255 & arrby7[n21 + 7]) << 24;
        if (8 + this.pos > this.limit) {
            throw new EOFException();
        }
        this.pos = 8 + this.pos;
        return Double.longBitsToDouble((long)(0xFFFFFFFFL & (long)n11 | (long)n22 << 32));
    }

    @Override
    public int readEnum() throws IOException {
        return this.readInt();
    }

    @Override
    public void readFixed(byte[] arrby, int n2, int n3) throws IOException {
        this.doReadBytes(arrby, n2, n3);
    }

    @Override
    public float readFloat() throws IOException {
        this.ensureBounds(4);
        int n2 = 255 & this.buf[this.pos];
        byte[] arrby = this.buf;
        int n3 = this.pos;
        int n4 = 1 + 1;
        int n5 = n2 | (255 & arrby[n3 + 1]) << 8;
        byte[] arrby2 = this.buf;
        int n6 = this.pos;
        int n7 = n4 + 1;
        int n8 = n5 | (255 & arrby2[n6 + 2]) << 16;
        byte[] arrby3 = this.buf;
        int n9 = this.pos;
        n7 + 1;
        int n10 = n8 | (255 & arrby3[n9 + 3]) << 24;
        if (4 + this.pos > this.limit) {
            throw new EOFException();
        }
        this.pos = 4 + this.pos;
        return Float.intBitsToFloat((int)n10);
    }

    @Override
    public int readIndex() throws IOException {
        return this.readInt();
    }

    @Override
    public int readInt() throws IOException {
        this.ensureBounds(5);
        int n2 = 1;
        int n3 = 255 & this.buf[this.pos];
        int n4 = n3 & 127;
        if (n3 > 127) {
            byte[] arrby = this.buf;
            int n5 = this.pos;
            int n6 = n2 + 1;
            int n7 = 255 & arrby[n5 + n2];
            n4 ^= (n7 & 127) << 7;
            if (n7 > 127) {
                byte[] arrby2 = this.buf;
                int n8 = this.pos;
                n2 = n6 + 1;
                int n9 = 255 & arrby2[n8 + 2];
                n4 ^= (n9 & 127) << 14;
                if (n9 > 127) {
                    byte[] arrby3 = this.buf;
                    int n10 = this.pos;
                    ++n2;
                    int n11 = 255 & arrby3[n10 + 3];
                    n4 ^= (n11 & 127) << 21;
                    if (n11 > 127) {
                        byte[] arrby4 = this.buf;
                        int n12 = this.pos;
                        ++n2;
                        int n13 = 255 & arrby4[n12 + 4];
                        n4 ^= (n13 & 127) << 28;
                        if (n13 > 127) {
                            throw new IOException("Invalid int encoding");
                        }
                    }
                }
            } else {
                n2 = n6;
            }
        }
        this.pos = n2 + this.pos;
        if (this.pos > this.limit) {
            throw new EOFException();
        }
        return n4 >>> 1 ^ -(n4 & 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long readLong() throws IOException {
        long l2;
        this.ensureBounds(10);
        byte[] arrby = this.buf;
        int n2 = this.pos;
        this.pos = n2 + 1;
        int n3 = 255 & arrby[n2];
        int n4 = n3 & 127;
        if (n3 > 127) {
            byte[] arrby2 = this.buf;
            int n5 = this.pos;
            this.pos = n5 + 1;
            int n6 = 255 & arrby2[n5];
            int n7 = n4 ^ (n6 & 127) << 7;
            if (n6 > 127) {
                byte[] arrby3 = this.buf;
                int n8 = this.pos;
                this.pos = n8 + 1;
                int n9 = 255 & arrby3[n8];
                int n10 = n7 ^ (n9 & 127) << 14;
                if (n9 > 127) {
                    byte[] arrby4 = this.buf;
                    int n11 = this.pos;
                    this.pos = n11 + 1;
                    int n12 = 255 & arrby4[n11];
                    int n13 = n10 ^ (n12 & 127) << 21;
                    l2 = n12 > 127 ? this.innerLongDecode(n13) : (long)n13;
                } else {
                    l2 = n10;
                }
            } else {
                l2 = n7;
            }
        } else {
            l2 = n4;
        }
        if (this.pos > this.limit) {
            throw new EOFException();
        }
        return l2 >>> 1 ^ -(1L & l2);
    }

    @Override
    public long readMapStart() throws IOException {
        return this.doReadItemCount();
    }

    @Override
    public void readNull() throws IOException {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Utf8 readString(Utf8 utf8) throws IOException {
        int n2 = this.readInt();
        Utf8 utf82 = utf8 != null ? utf8 : new Utf8();
        utf82.setByteLength(n2);
        if (n2 != 0) {
            this.doReadBytes(utf82.getBytes(), 0, n2);
        }
        return utf82;
    }

    @Override
    public String readString() throws IOException {
        return this.readString(this.scratchUtf8).toString();
    }

    @Override
    public long skipArray() throws IOException {
        return this.doSkipItems();
    }

    @Override
    public void skipBytes() throws IOException {
        this.doSkipBytes(this.readInt());
    }

    @Override
    public void skipFixed(int n2) throws IOException {
        this.doSkipBytes(n2);
    }

    @Override
    public long skipMap() throws IOException {
        return this.doSkipItems();
    }

    @Override
    public void skipString() throws IOException {
        this.doSkipBytes(this.readInt());
    }

    static class BufferAccessor {
        private byte[] buf;
        private final BinaryDecoder decoder;
        boolean detached;
        private int limit;
        private int pos;

        private BufferAccessor(BinaryDecoder binaryDecoder) {
            this.detached = false;
            this.decoder = binaryDecoder;
        }

        /* synthetic */ BufferAccessor(BinaryDecoder binaryDecoder, 1 var2_2) {
            super(binaryDecoder);
        }

        void detach() {
            this.buf = this.decoder.buf;
            this.pos = this.decoder.pos;
            this.limit = this.decoder.limit;
            this.detached = true;
        }

        byte[] getBuf() {
            if (this.detached) {
                return this.buf;
            }
            return this.decoder.buf;
        }

        int getLim() {
            if (this.detached) {
                return this.limit;
            }
            return this.decoder.limit;
        }

        int getPos() {
            if (this.detached) {
                return this.pos;
            }
            return this.decoder.pos;
        }

        void setBuf(byte[] arrby, int n2, int n3) {
            if (this.detached) {
                this.buf = arrby;
                this.limit = n2 + n3;
                this.pos = n2;
                return;
            }
            this.decoder.buf = arrby;
            this.decoder.limit = n2 + n3;
            this.decoder.pos = n2;
            this.decoder.minPos = n2;
        }

        void setLimit(int n2) {
            if (this.detached) {
                this.limit = n2;
                return;
            }
            this.decoder.limit = n2;
        }

        void setPos(int n2) {
            if (this.detached) {
                this.pos = n2;
                return;
            }
            this.decoder.pos = n2;
        }
    }

    private static class ByteArrayByteSource
    extends ByteSource {
        private boolean compacted;
        private byte[] data;
        private int max;
        private int position;

        private ByteArrayByteSource(byte[] arrby, int n2, int n3) {
            this.compacted = false;
            if (arrby.length < 16 || n3 < 16) {
                this.data = new byte[16];
                System.arraycopy((Object)arrby, (int)n2, (Object)this.data, (int)0, (int)n3);
                this.position = 0;
                this.max = n3;
                return;
            }
            this.data = arrby;
            this.position = n2;
            this.max = n2 + n3;
        }

        /* synthetic */ ByteArrayByteSource(byte[] arrby, int n2, int n3, 1 var4) {
            super(arrby, n2, n3);
        }

        @Override
        protected void attach(int n2, BinaryDecoder binaryDecoder) {
            binaryDecoder.buf = this.data;
            binaryDecoder.pos = this.position;
            binaryDecoder.minPos = this.position;
            binaryDecoder.limit = this.max;
            this.ba = new BufferAccessor(binaryDecoder, null);
        }

        public void close() throws IOException {
            this.ba.setPos(this.ba.getLim());
        }

        @Override
        protected void compactAndFill(byte[] arrby, int n2, int n3, int n4) throws IOException {
            if (!this.compacted) {
                byte[] arrby2 = new byte[n4 + 16];
                System.arraycopy((Object)arrby, (int)n2, (Object)arrby2, (int)0, (int)n4);
                this.ba.setBuf(arrby2, 0, n4);
                this.compacted = true;
            }
        }

        @Override
        public boolean isEof() {
            return this.ba.getLim() - this.ba.getPos() == 0;
        }

        public int read() throws IOException {
            this.max = this.ba.getLim();
            this.position = this.ba.getPos();
            if (this.position >= this.max) {
                return -1;
            }
            byte[] arrby = this.ba.getBuf();
            int n2 = this.position;
            this.position = n2 + 1;
            int n3 = 255 & arrby[n2];
            this.ba.setPos(this.position);
            return n3;
        }

        @Override
        protected void readRaw(byte[] arrby, int n2, int n3) throws IOException {
            if (this.tryReadRaw(arrby, n2, n3) < n3) {
                throw new EOFException();
            }
        }

        @Override
        protected void skipSourceBytes(long l2) throws IOException {
            if (this.trySkipBytes(l2) < l2) {
                throw new EOFException();
            }
        }

        @Override
        protected int tryReadRaw(byte[] arrby, int n2, int n3) throws IOException {
            return 0;
        }

        @Override
        protected long trySkipBytes(long l2) throws IOException {
            this.max = this.ba.getLim();
            this.position = this.ba.getPos();
            long l3 = this.max - this.position;
            if (l3 >= l2) {
                this.position = (int)(l2 + (long)this.position);
                this.ba.setPos(this.position);
                return l2;
            }
            this.position = (int)(l3 + (long)this.position);
            this.ba.setPos(this.position);
            return l3;
        }
    }

    static abstract class ByteSource
    extends InputStream {
        protected BufferAccessor ba;

        protected ByteSource() {
        }

        protected void attach(int n2, BinaryDecoder binaryDecoder) {
            binaryDecoder.buf = new byte[n2];
            binaryDecoder.pos = 0;
            binaryDecoder.minPos = 0;
            binaryDecoder.limit = 0;
            this.ba = new BufferAccessor(binaryDecoder, null);
        }

        public int available() throws IOException {
            return this.ba.getLim() - this.ba.getPos();
        }

        protected void compactAndFill(byte[] arrby, int n2, int n3, int n4) throws IOException {
            System.arraycopy((Object)arrby, (int)n2, (Object)arrby, (int)n3, (int)n4);
            this.ba.setPos(n3);
            int n5 = n4 + this.tryReadRaw(arrby, n3 + n4, arrby.length - n4);
            this.ba.setLimit(n5);
        }

        protected void detach() {
            this.ba.detach();
        }

        abstract boolean isEof();

        public int read(byte[] arrby, int n2, int n3) throws IOException {
            int n4 = this.ba.getLim();
            int n5 = this.ba.getPos();
            byte[] arrby2 = this.ba.getBuf();
            int n6 = n4 - n5;
            if (n6 >= n3) {
                System.arraycopy((Object)arrby2, (int)n5, (Object)arrby, (int)n2, (int)n3);
                int n7 = n5 + n3;
                this.ba.setPos(n7);
                return n3;
            }
            System.arraycopy((Object)arrby2, (int)n5, (Object)arrby, (int)n2, (int)n6);
            int n8 = n5 + n6;
            this.ba.setPos(n8);
            int n9 = n6 + this.tryReadRaw(arrby, n2 + n6, n3 - n6);
            if (n9 == 0) {
                return -1;
            }
            return n9;
        }

        protected abstract void readRaw(byte[] var1, int var2, int var3) throws IOException;

        public long skip(long l2) throws IOException {
            int n2;
            int n3 = this.ba.getLim();
            int n4 = n3 - (n2 = this.ba.getPos());
            if ((long)n4 > l2) {
                int n5 = (int)(l2 + (long)n2);
                this.ba.setPos(n5);
                return l2;
            }
            this.ba.setPos(n3);
            return this.trySkipBytes(l2 - (long)n4) + (long)n4;
        }

        protected abstract void skipSourceBytes(long var1) throws IOException;

        protected abstract int tryReadRaw(byte[] var1, int var2, int var3) throws IOException;

        protected abstract long trySkipBytes(long var1) throws IOException;
    }

    private static class InputStreamByteSource
    extends ByteSource {
        private InputStream in;
        protected boolean isEof;

        private InputStreamByteSource(InputStream inputStream) {
            this.isEof = false;
            this.in = inputStream;
        }

        /* synthetic */ InputStreamByteSource(InputStream inputStream, 1 var2_2) {
            super(inputStream);
        }

        public void close() throws IOException {
            this.in.close();
        }

        @Override
        public boolean isEof() {
            return this.isEof;
        }

        public int read() throws IOException {
            if (this.ba.getLim() - this.ba.getPos() == 0) {
                return this.in.read();
            }
            int n2 = this.ba.getPos();
            int n3 = 255 & this.ba.getBuf()[n2];
            this.ba.setPos(n2 + 1);
            return n3;
        }

        @Override
        protected void readRaw(byte[] arrby, int n2, int n3) throws IOException {
            while (n3 > 0) {
                int n4 = this.in.read(arrby, n2, n3);
                if (n4 < 0) {
                    this.isEof = true;
                    throw new EOFException();
                }
                n3 -= n4;
                n2 += n4;
            }
        }

        @Override
        protected void skipSourceBytes(long l2) throws IOException {
            boolean bl = false;
            while (l2 > 0L) {
                long l3 = this.in.skip(l2);
                if (l3 > 0L) {
                    l2 -= l3;
                    continue;
                }
                if (l3 == 0L) {
                    if (bl) {
                        this.isEof = true;
                        throw new EOFException();
                    }
                    bl = true;
                    continue;
                }
                this.isEof = true;
                throw new EOFException();
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        protected int tryReadRaw(byte[] arrby, int n2, int n3) throws IOException {
            int n4 = n3;
            while (n4 > 0) {
                int n5;
                block3 : {
                    n5 = this.in.read(arrby, n2, n4);
                    if (n5 >= 0) break block3;
                    this.isEof = true;
                    return n3 - n4;
                }
                n4 -= n5;
                n2 += n5;
            }
            return n3 - n4;
            catch (EOFException eOFException) {
                this.isEof = true;
                return n3 - n4;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected long trySkipBytes(long l2) throws IOException {
            long l3 = l2;
            boolean bl = false;
            while (l3 > 0L) {
                block7 : {
                    try {
                        long l4 = this.in.skip(l2);
                        if (l4 > 0L) {
                            l3 -= l4;
                            continue;
                        }
                        if (l4 == 0L) {
                            if (bl) {
                                this.isEof = true;
                                return l2 - l3;
                            }
                            break block7;
                        }
                        this.isEof = true;
                        return l2 - l3;
                    }
                    catch (EOFException eOFException) {
                        this.isEof = true;
                    }
                    return l2 - l3;
                }
                bl = true;
            }
            return l2 - l3;
        }
    }

}

