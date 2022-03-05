/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Closeable
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.IllegalStateException
 *  java.lang.Iterable
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.nio.ByteBuffer
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.NoSuchElementException
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.file.Codec;
import com.flurry.org.apache.avro.file.CodecFactory;
import com.flurry.org.apache.avro.file.DataFileConstants;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DecoderFactory;
import com.flurry.org.apache.avro.util.Utf8;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class DataFileStream<D>
implements Iterator<D>,
Iterable<D>,
Closeable {
    private boolean availableBlock = false;
    private DataBlock block = null;
    ByteBuffer blockBuffer;
    long blockCount;
    long blockRemaining;
    private long blockSize;
    private Codec codec;
    BinaryDecoder datumIn = null;
    private Header header;
    private DatumReader<D> reader;
    byte[] syncBuffer = new byte[16];
    BinaryDecoder vin;

    protected DataFileStream(DatumReader<D> datumReader) throws IOException {
        this.reader = datumReader;
    }

    public DataFileStream(InputStream inputStream, DatumReader<D> datumReader) throws IOException {
        this.reader = datumReader;
        this.initialize(inputStream);
    }

    protected void blockFinished() throws IOException {
    }

    public void close() throws IOException {
        this.vin.inputStream().close();
    }

    public long getBlockCount() {
        return this.blockCount;
    }

    public Header getHeader() {
        return this.header;
    }

    public byte[] getMeta(String string) {
        return (byte[])this.header.meta.get((Object)string);
    }

    public List<String> getMetaKeys() {
        return this.header.metaKeyList;
    }

    public long getMetaLong(String string) {
        return Long.parseLong((String)this.getMetaString(string));
    }

    public String getMetaString(String string) {
        byte[] arrby = this.getMeta(string);
        if (arrby == null) {
            return null;
        }
        try {
            String string2 = new String(arrby, "UTF-8");
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException((Throwable)unsupportedEncodingException);
        }
    }

    public Schema getSchema() {
        return this.header.schema;
    }

    public boolean hasNext() {
        try {
            long l2;
            if (this.blockRemaining == 0L) {
                if (this.datumIn != null && !this.datumIn.isEnd()) {
                    throw new IOException("Block read partially, the data may be corrupt");
                }
                if (this.hasNextBlock()) {
                    this.block = this.nextRawBlock(this.block);
                    this.block.decompressUsing(this.codec);
                    this.blockBuffer = this.block.getAsByteBuffer();
                    this.datumIn = DecoderFactory.get().binaryDecoder(this.blockBuffer.array(), this.blockBuffer.arrayOffset() + this.blockBuffer.position(), this.blockBuffer.remaining(), this.datumIn);
                }
            }
            if ((l2 = this.blockRemaining) != 0L) {
                return true;
            }
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
        catch (EOFException eOFException) {
            // empty catch block
        }
        return false;
    }

    boolean hasNextBlock() {
        block7 : {
            block6 : {
                if (!this.availableBlock) break block6;
                return true;
            }
            if (!this.vin.isEnd()) break block7;
            return false;
        }
        try {
            this.blockRemaining = this.vin.readLong();
            this.blockSize = this.vin.readLong();
            if (this.blockSize > Integer.MAX_VALUE || this.blockSize < 0L) {
                throw new IOException("Block size invalid or too large for this implementation: " + this.blockSize);
            }
            this.blockCount = this.blockRemaining;
            this.availableBlock = true;
            return true;
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
        catch (EOFException eOFException) {
            return false;
        }
    }

    void initialize(InputStream inputStream) throws IOException {
        this.header = new Header(null);
        this.vin = DecoderFactory.get().binaryDecoder(inputStream, this.vin);
        byte[] arrby = new byte[DataFileConstants.MAGIC.length];
        try {
            this.vin.readFixed(arrby);
        }
        catch (IOException iOException) {
            throw new IOException("Not a data file.");
        }
        if (!Arrays.equals((byte[])DataFileConstants.MAGIC, (byte[])arrby)) {
            throw new IOException("Not a data file.");
        }
        long l2 = this.vin.readMapStart();
        if (l2 > 0L) {
            do {
                for (long i2 = 0L; i2 < l2; ++i2) {
                    String string = this.vin.readString(null).toString();
                    ByteBuffer byteBuffer = this.vin.readBytes(null);
                    byte[] arrby2 = new byte[byteBuffer.remaining()];
                    byteBuffer.get(arrby2);
                    this.header.meta.put((Object)string, (Object)arrby2);
                    this.header.metaKeyList.add((Object)string);
                }
            } while ((l2 = this.vin.mapNext()) != 0L);
        }
        this.vin.readFixed(this.header.sync);
        this.header.metaKeyList = Collections.unmodifiableList((List)this.header.metaKeyList);
        this.header.schema = Schema.parse(this.getMetaString("avro.schema"), false);
        this.codec = this.resolveCodec();
        this.reader.setSchema(this.header.schema);
    }

    void initialize(InputStream inputStream, Header header) throws IOException {
        this.header = header;
        this.codec = this.resolveCodec();
        this.reader.setSchema(header.schema);
    }

    public Iterator<D> iterator() {
        return this;
    }

    public D next() {
        D d2;
        try {
            d2 = this.next(null);
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
        return d2;
    }

    public D next(D d2) throws IOException {
        long l2;
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        D d3 = this.reader.read(d2, this.datumIn);
        this.blockRemaining = l2 = this.blockRemaining - 1L;
        if (0L == l2) {
            this.blockFinished();
        }
        return d3;
    }

    public ByteBuffer nextBlock() throws IOException {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        if (this.blockRemaining != this.blockCount) {
            throw new IllegalStateException("Not at block start.");
        }
        this.blockRemaining = 0L;
        this.datumIn = null;
        return this.blockBuffer;
    }

    /*
     * Enabled aggressive block sorting
     */
    DataBlock nextRawBlock(DataBlock dataBlock) throws IOException {
        if (!this.hasNextBlock()) {
            throw new NoSuchElementException();
        }
        if (dataBlock == null || dataBlock.data.length < (int)this.blockSize) {
            dataBlock = new DataBlock(this.blockRemaining, (int)this.blockSize, null);
        } else {
            dataBlock.numEntries = this.blockRemaining;
            dataBlock.blockSize = (int)this.blockSize;
        }
        this.vin.readFixed(dataBlock.data, 0, dataBlock.blockSize);
        this.vin.readFixed(this.syncBuffer);
        if (!Arrays.equals((byte[])this.syncBuffer, (byte[])this.header.sync)) {
            throw new IOException("Invalid sync!");
        }
        this.availableBlock = false;
        return dataBlock;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    Codec resolveCodec() {
        String string = this.getMetaString("avro.codec");
        if (string != null) {
            return CodecFactory.fromString(string).createInstance();
        }
        return CodecFactory.nullCodec().createInstance();
    }

    static class DataBlock {
        private int blockSize;
        private byte[] data;
        private long numEntries;
        private int offset;

        private DataBlock(long l2, int n2) {
            this.offset = 0;
            this.data = new byte[n2];
            this.numEntries = l2;
            this.blockSize = n2;
        }

        /* synthetic */ DataBlock(long l2, int n2, 1 var4) {
            super(l2, n2);
        }

        DataBlock(ByteBuffer byteBuffer, long l2) {
            this.offset = 0;
            this.data = byteBuffer.array();
            this.blockSize = byteBuffer.remaining();
            this.offset = byteBuffer.arrayOffset() + byteBuffer.position();
            this.numEntries = l2;
        }

        void compressUsing(Codec codec) throws IOException {
            ByteBuffer byteBuffer = codec.compress(this.getAsByteBuffer());
            this.data = byteBuffer.array();
            this.blockSize = byteBuffer.remaining();
        }

        void decompressUsing(Codec codec) throws IOException {
            ByteBuffer byteBuffer = codec.decompress(this.getAsByteBuffer());
            this.data = byteBuffer.array();
            this.blockSize = byteBuffer.remaining();
        }

        ByteBuffer getAsByteBuffer() {
            return ByteBuffer.wrap((byte[])this.data, (int)this.offset, (int)this.blockSize);
        }

        int getBlockSize() {
            return this.blockSize;
        }

        byte[] getData() {
            return this.data;
        }

        long getNumEntries() {
            return this.numEntries;
        }

        void writeBlockTo(BinaryEncoder binaryEncoder, byte[] arrby) throws IOException {
            binaryEncoder.writeLong(this.numEntries);
            binaryEncoder.writeLong(this.blockSize);
            binaryEncoder.writeFixed(this.data, this.offset, this.blockSize);
            binaryEncoder.writeFixed(arrby);
            binaryEncoder.flush();
        }
    }

    public static final class Header {
        Map<String, byte[]> meta;
        private transient List<String> metaKeyList;
        Schema schema;
        byte[] sync;

        private Header() {
            this.meta = new HashMap();
            this.metaKeyList = new ArrayList();
            this.sync = new byte[16];
        }

        /* synthetic */ Header(1 var1) {
        }
    }

}

