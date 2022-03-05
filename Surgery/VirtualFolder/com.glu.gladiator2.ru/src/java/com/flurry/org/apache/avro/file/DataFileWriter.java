/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedOutputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.Closeable
 *  java.io.File
 *  java.io.FileDescriptor
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.FilterOutputStream
 *  java.io.Flushable
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.RandomAccessFile
 *  java.io.UnsupportedEncodingException
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.nio.ByteBuffer
 *  java.rmi.server.UID
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.file.Codec;
import com.flurry.org.apache.avro.file.CodecFactory;
import com.flurry.org.apache.avro.file.DataFileConstants;
import com.flurry.org.apache.avro.file.DataFileReader;
import com.flurry.org.apache.avro.file.DataFileStream;
import com.flurry.org.apache.avro.file.SeekableFileInput;
import com.flurry.org.apache.avro.file.SeekableInput;
import com.flurry.org.apache.avro.generic.GenericDatumReader;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.io.DatumWriter;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.io.EncoderFactory;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.rmi.server.UID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataFileWriter<D>
implements Closeable,
Flushable {
    private long blockCount;
    private BinaryEncoder bufOut;
    private NonCopyingByteArrayOutputStream buffer;
    private Codec codec;
    private DatumWriter<D> dout;
    private boolean isOpen;
    private final Map<String, byte[]> meta = new HashMap();
    private DataFileWriter<D> out;
    private Schema schema;
    private byte[] sync;
    private int syncInterval = 16000;
    private BinaryEncoder vout;

    public DataFileWriter(DatumWriter<D> datumWriter) {
        this.dout = datumWriter;
    }

    private void assertNotOpen() {
        if (this.isOpen) {
            throw new AvroRuntimeException("already open");
        }
    }

    private void assertOpen() {
        if (!this.isOpen) {
            throw new AvroRuntimeException("not open");
        }
    }

    private int bufferInUse() {
        return this.buffer.size() + this.bufOut.bytesBuffered();
    }

    private static byte[] generateSync() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance((String)"MD5");
            long l2 = System.currentTimeMillis();
            messageDigest.update(((Object)new UID() + "@" + l2).getBytes());
            byte[] arrby = messageDigest.digest();
            return arrby;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException((Throwable)noSuchAlgorithmException);
        }
    }

    private void init(OutputStream outputStream) throws IOException {
        this.out = (DataFileWriter)this.new BufferedFileOutputStream(outputStream);
        EncoderFactory encoderFactory = new EncoderFactory();
        this.vout = encoderFactory.binaryEncoder((OutputStream)this.out, null);
        this.dout.setSchema(this.schema);
        this.buffer = new NonCopyingByteArrayOutputStream(Math.min((int)((int)(1.25 * (double)this.syncInterval)), (int)1073741822));
        this.bufOut = encoderFactory.binaryEncoder((OutputStream)this.buffer, null);
        if (this.codec == null) {
            this.codec = CodecFactory.nullCodec().createInstance();
        }
        this.isOpen = true;
    }

    public static boolean isReservedMeta(String string) {
        return string.startsWith("avro.");
    }

    private void resetBufferTo(int n2) throws IOException {
        this.bufOut.flush();
        byte[] arrby = this.buffer.toByteArray();
        this.buffer.reset();
        this.buffer.write(arrby, 0, n2);
    }

    private DataFileWriter<D> setMetaInternal(String string, String string2) {
        try {
            DataFileWriter<D> dataFileWriter = DataFileWriter.super.setMetaInternal(string, string2.getBytes("UTF-8"));
            return dataFileWriter;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException((Throwable)unsupportedEncodingException);
        }
    }

    private DataFileWriter<D> setMetaInternal(String string, byte[] arrby) {
        DataFileWriter.super.assertNotOpen();
        this.meta.put((Object)string, (Object)arrby);
        return this;
    }

    private void writeBlock() throws IOException {
        if (this.blockCount > 0L) {
            this.bufOut.flush();
            DataFileStream.DataBlock dataBlock = new DataFileStream.DataBlock(this.buffer.getByteArrayAsByteBuffer(), this.blockCount);
            dataBlock.compressUsing(this.codec);
            dataBlock.writeBlockTo(this.vout, this.sync);
            this.buffer.reset();
            this.blockCount = 0L;
        }
    }

    private void writeIfBlockFull() throws IOException {
        if (this.bufferInUse() >= this.syncInterval) {
            this.writeBlock();
        }
    }

    public void append(D d2) throws IOException {
        DataFileWriter.super.assertOpen();
        int n2 = DataFileWriter.super.bufferInUse();
        try {
            this.dout.write(d2, this.bufOut);
        }
        catch (IOException iOException) {
            DataFileWriter.super.resetBufferTo(n2);
            throw new AppendWriteException((Exception)((Object)iOException));
        }
        catch (RuntimeException runtimeException) {
            DataFileWriter.super.resetBufferTo(n2);
            throw new AppendWriteException((Exception)((Object)runtimeException));
        }
        this.blockCount = 1L + this.blockCount;
        DataFileWriter.super.writeIfBlockFull();
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void appendAllFrom(DataFileStream<D> dataFileStream, boolean bl) throws IOException {
        DataFileWriter.super.assertOpen();
        Schema schema = dataFileStream.getSchema();
        if (!this.schema.equals(schema)) {
            throw new IOException("Schema from file " + dataFileStream + " does not match");
        }
        DataFileWriter.super.writeBlock();
        Codec codec = dataFileStream.resolveCodec();
        boolean bl2 = this.codec.equals(codec);
        DataFileStream.DataBlock dataBlock = null;
        if (bl2) {
            dataBlock = null;
            if (!bl) {
                while (dataFileStream.hasNextBlock()) {
                    dataBlock = dataFileStream.nextRawBlock(dataBlock);
                    dataBlock.writeBlockTo(this.vout, this.sync);
                }
                return;
            }
        }
        while (dataFileStream.hasNextBlock()) {
            dataBlock = dataFileStream.nextRawBlock(dataBlock);
            dataBlock.decompressUsing(codec);
            dataBlock.compressUsing(this.codec);
            dataBlock.writeBlockTo(this.vout, this.sync);
        }
    }

    public void appendEncoded(ByteBuffer byteBuffer) throws IOException {
        DataFileWriter.super.assertOpen();
        int n2 = byteBuffer.position();
        this.bufOut.writeFixed(byteBuffer.array(), n2, byteBuffer.limit() - n2);
        this.blockCount = 1L + this.blockCount;
        DataFileWriter.super.writeIfBlockFull();
    }

    /*
     * Enabled aggressive block sorting
     */
    public DataFileWriter<D> appendTo(File file) throws IOException {
        DataFileWriter.super.assertNotOpen();
        if (!file.exists()) {
            throw new FileNotFoundException("Not found: " + (Object)file);
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        DataFileReader dataFileReader = new DataFileReader(new SeekableFileInput(randomAccessFile.getFD()), new GenericDatumReader());
        this.schema = dataFileReader.getSchema();
        this.sync = dataFileReader.getHeader().sync;
        this.meta.putAll(dataFileReader.getHeader().meta);
        byte[] arrby = (byte[])this.meta.get((Object)"avro.codec");
        this.codec = arrby != null ? CodecFactory.fromString(new String(arrby, "UTF-8")).createInstance() : CodecFactory.nullCodec().createInstance();
        randomAccessFile.close();
        DataFileWriter.super.init((OutputStream)new FileOutputStream(file, true));
        return this;
    }

    public void close() throws IOException {
        this.flush();
        this.out.close();
        this.isOpen = false;
    }

    public DataFileWriter<D> create(Schema schema, File file) throws IOException {
        return this.create(schema, (OutputStream)new FileOutputStream(file));
    }

    public DataFileWriter<D> create(Schema schema, OutputStream outputStream) throws IOException {
        DataFileWriter.super.assertNotOpen();
        this.schema = schema;
        DataFileWriter.super.setMetaInternal("avro.schema", schema.toString());
        this.sync = DataFileWriter.generateSync();
        DataFileWriter.super.init(outputStream);
        this.vout.writeFixed(DataFileConstants.MAGIC);
        this.vout.writeMapStart();
        this.vout.setItemCount(this.meta.size());
        for (Map.Entry entry : this.meta.entrySet()) {
            this.vout.startItem();
            this.vout.writeString((String)entry.getKey());
            this.vout.writeBytes((byte[])entry.getValue());
        }
        this.vout.writeMapEnd();
        this.vout.writeFixed(this.sync);
        this.vout.flush();
        return this;
    }

    public void flush() throws IOException {
        this.sync();
        this.vout.flush();
    }

    public DataFileWriter<D> setCodec(CodecFactory codecFactory) {
        DataFileWriter.super.assertNotOpen();
        this.codec = codecFactory.createInstance();
        DataFileWriter.super.setMetaInternal("avro.codec", this.codec.getName());
        return this;
    }

    public DataFileWriter<D> setMeta(String string, long l2) {
        return this.setMeta(string, Long.toString((long)l2));
    }

    public DataFileWriter<D> setMeta(String string, String string2) {
        try {
            DataFileWriter<D> dataFileWriter = this.setMeta(string, string2.getBytes("UTF-8"));
            return dataFileWriter;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException((Throwable)unsupportedEncodingException);
        }
    }

    public DataFileWriter<D> setMeta(String string, byte[] arrby) {
        if (DataFileWriter.isReservedMeta(string)) {
            throw new AvroRuntimeException("Cannot set reserved meta key: " + string);
        }
        return DataFileWriter.super.setMetaInternal(string, arrby);
    }

    public DataFileWriter<D> setSyncInterval(int n2) {
        if (n2 < 32 || n2 > 1073741824) {
            throw new IllegalArgumentException("Invalid syncInterval value: " + n2);
        }
        this.syncInterval = n2;
        return this;
    }

    public long sync() throws IOException {
        this.assertOpen();
        this.writeBlock();
        return ((BufferedFileOutputStream)((Object)this.out)).tell();
    }

    public static class AppendWriteException
    extends RuntimeException {
        public AppendWriteException(Exception exception) {
            super((Throwable)exception);
        }
    }

    private class BufferedFileOutputStream
    extends BufferedOutputStream {
        private long position;

        public BufferedFileOutputStream(OutputStream outputStream) throws IOException {
            super(null);
            this.out = (BufferedFileOutputStream)this.new PositionFilter(outputStream);
        }

        static /* synthetic */ long access$014(BufferedFileOutputStream bufferedFileOutputStream, long l2) {
            long l3;
            bufferedFileOutputStream.position = l3 = l2 + bufferedFileOutputStream.position;
            return l3;
        }

        public long tell() {
            return this.position + (long)this.count;
        }

        private class PositionFilter
        extends FilterOutputStream {
            public PositionFilter(OutputStream outputStream) throws IOException {
                super(outputStream);
            }

            public void write(byte[] arrby, int n2, int n3) throws IOException {
                this.out.write(arrby, n2, n3);
                BufferedFileOutputStream.access$014(BufferedFileOutputStream.this, n3);
            }
        }

    }

    private static class NonCopyingByteArrayOutputStream
    extends ByteArrayOutputStream {
        NonCopyingByteArrayOutputStream(int n2) {
            super(n2);
        }

        ByteBuffer getByteArrayAsByteBuffer() {
            return ByteBuffer.wrap((byte[])this.buf, (int)0, (int)this.count);
        }
    }

}

