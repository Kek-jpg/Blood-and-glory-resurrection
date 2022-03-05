/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Closeable
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.nio.ByteBuffer
 *  java.util.Arrays
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.file.DataFileReader;
import com.flurry.org.apache.avro.file.FileReader;
import com.flurry.org.apache.avro.file.SeekableInput;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DecoderFactory;
import com.flurry.org.apache.avro.util.Utf8;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataFileReader12<D>
implements FileReader<D>,
Closeable {
    private static final String CODEC = "codec";
    private static final String COUNT = "count";
    private static final long FOOTER_BLOCK = -1L;
    static final byte[] MAGIC = new byte[]{79, 98, 106, 0};
    private static final String NULL_CODEC = "null";
    private static final String SCHEMA = "schema";
    private static final String SYNC = "sync";
    private static final int SYNC_INTERVAL = 16000;
    private static final int SYNC_SIZE = 16;
    private static final byte VERSION;
    private long blockCount;
    private long blockStart;
    private long count;
    private DataFileReader.SeekableInputStream in;
    private Map<String, byte[]> meta = new HashMap();
    private D peek;
    private DatumReader<D> reader;
    private Schema schema;
    private byte[] sync = new byte[16];
    private byte[] syncBuffer = new byte[16];
    private BinaryDecoder vin;

    public DataFileReader12(SeekableInput seekableInput, DatumReader<D> datumReader) throws IOException {
        this.in = new DataFileReader.SeekableInputStream(seekableInput);
        byte[] arrby = new byte[4];
        this.in.read(arrby);
        if (!Arrays.equals((byte[])MAGIC, (byte[])arrby)) {
            throw new IOException("Not a data file.");
        }
        long l2 = this.in.length();
        this.in.seek(l2 - 4L);
        this.seek(l2 - (long)((this.in.read() << 24) + (this.in.read() << 16) + (this.in.read() << 8) + this.in.read()));
        long l3 = this.vin.readMapStart();
        if (l3 > 0L) {
            do {
                for (long i2 = 0L; i2 < l3; ++i2) {
                    String string = this.vin.readString(null).toString();
                    ByteBuffer byteBuffer = this.vin.readBytes(null);
                    byte[] arrby2 = new byte[byteBuffer.remaining()];
                    byteBuffer.get(arrby2);
                    this.meta.put((Object)string, (Object)arrby2);
                }
            } while ((l3 = this.vin.mapNext()) != 0L);
        }
        this.sync = this.getMeta(SYNC);
        this.count = this.getMetaLong(COUNT);
        String string = this.getMetaString(CODEC);
        if (string != null && !string.equals((Object)NULL_CODEC)) {
            throw new IOException("Unknown codec: " + string);
        }
        this.schema = Schema.parse(this.getMetaString(SCHEMA));
        this.reader = datumReader;
        datumReader.setSchema(this.schema);
        this.seek(MAGIC.length);
    }

    private void skipSync() throws IOException {
        this.vin.readFixed(this.syncBuffer);
        if (!Arrays.equals((byte[])this.syncBuffer, (byte[])this.sync)) {
            throw new IOException("Invalid sync!");
        }
    }

    public void close() throws IOException {
        DataFileReader12 dataFileReader12 = this;
        synchronized (dataFileReader12) {
            this.in.close();
            return;
        }
    }

    public byte[] getMeta(String string) {
        void var4_2 = this;
        synchronized (var4_2) {
            byte[] arrby = (byte[])this.meta.get((Object)string);
            return arrby;
        }
    }

    public long getMetaLong(String string) {
        void var5_2 = this;
        synchronized (var5_2) {
            long l2 = Long.parseLong((String)this.getMetaString(string));
            return l2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getMetaString(String string) {
        void var6_2 = this;
        synchronized (var6_2) {
            byte[] arrby = this.getMeta(string);
            if (arrby == null) {
                return null;
            }
            try {
                return new String(arrby, "UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new RuntimeException((Throwable)unsupportedEncodingException);
            }
        }
    }

    @Override
    public Schema getSchema() {
        return this.schema;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasNext() {
        block3 : {
            block2 : {
                if (this.peek != null || this.blockCount != 0L) break block2;
                this.peek = this.next();
                if (this.peek == null) break block3;
            }
            return true;
        }
        return false;
    }

    public Iterator<D> iterator() {
        return this;
    }

    public D next() {
        D d2;
        if (this.peek != null) {
            D d3 = this.peek;
            this.peek = null;
            return d3;
        }
        try {
            d2 = this.next(null);
        }
        catch (IOException iOException) {
            throw new RuntimeException((Throwable)iOException);
        }
        return d2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public D next(D d2) throws IOException {
        void var9_2 = this;
        synchronized (var9_2) {
            D d3;
            block6 : {
                while (this.blockCount == 0L) {
                    long l2;
                    long l3 = this.in.tell();
                    if (l3 == (l2 = this.in.length())) {
                        d3 = null;
                        break block6;
                    }
                    DataFileReader12.super.skipSync();
                    this.blockCount = this.vin.readLong();
                    if (this.blockCount != -1L) continue;
                    this.seek(this.vin.readLong() + this.in.tell());
                }
                --this.blockCount;
                D d4 = this.reader.read(d2, this.vin);
                d3 = d4;
            }
            return d3;
        }
    }

    @Override
    public boolean pastSync(long l2) throws IOException {
        return this.blockStart >= 16L + l2 || this.blockStart >= this.in.length();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void seek(long l2) throws IOException {
        void var4_2 = this;
        synchronized (var4_2) {
            this.in.seek(l2);
            this.blockCount = 0L;
            this.blockStart = l2;
            this.vin = DecoderFactory.get().binaryDecoder(this.in, this.vin);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void sync(long l2) throws IOException {
        void var6_2 = this;
        // MONITORENTER : var6_2
        if (16L + this.in.tell() >= this.in.length()) {
            this.seek(this.in.length());
            // MONITOREXIT : var6_2
            return;
        }
        this.in.seek(l2);
        this.vin.readFixed(this.syncBuffer);
        int n2 = 0;
        do {
            if (this.in.tell() >= this.in.length()) {
                this.seek(this.in.length());
                return;
            }
            int n3 = 0;
            do {
                if (n3 >= this.sync.length || this.sync[n3] != this.syncBuffer[(n2 + n3) % this.sync.length]) {
                    if (n3 != this.sync.length) break;
                    this.seek(this.in.tell() - 16L);
                    return;
                }
                ++n3;
            } while (true);
            this.syncBuffer[n2 % this.sync.length] = (byte)this.in.read();
            ++n2;
        } while (true);
    }

    @Override
    public long tell() throws IOException {
        return this.in.tell();
    }
}

