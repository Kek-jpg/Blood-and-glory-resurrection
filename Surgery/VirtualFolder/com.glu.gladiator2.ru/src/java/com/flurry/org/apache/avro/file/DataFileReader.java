/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.File
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.file.DataFileConstants;
import com.flurry.org.apache.avro.file.DataFileReader12;
import com.flurry.org.apache.avro.file.DataFileStream;
import com.flurry.org.apache.avro.file.FileReader;
import com.flurry.org.apache.avro.file.SeekableFileInput;
import com.flurry.org.apache.avro.file.SeekableInput;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.DatumReader;
import com.flurry.org.apache.avro.io.DecoderFactory;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class DataFileReader<D>
extends DataFileStream<D>
implements FileReader<D> {
    private long blockStart;
    private SeekableInputStream sin;

    public DataFileReader(SeekableInput seekableInput, DatumReader<D> datumReader) throws IOException {
        super(datumReader);
        this.sin = new SeekableInputStream(seekableInput);
        this.initialize(this.sin);
        this.blockFinished();
    }

    protected DataFileReader(SeekableInput seekableInput, DatumReader<D> datumReader, DataFileStream.Header header) throws IOException {
        super(datumReader);
        this.sin = new SeekableInputStream(seekableInput);
        this.initialize(this.sin, header);
    }

    public DataFileReader(File file, DatumReader<D> datumReader) throws IOException {
        super(new SeekableFileInput(file), datumReader);
    }

    public static <D> DataFileReader<D> openReader(SeekableInput seekableInput, DatumReader<D> datumReader, DataFileStream.Header header, boolean bl) throws IOException {
        DataFileReader<D> dataFileReader = new DataFileReader<D>(seekableInput, datumReader, header);
        if (bl) {
            dataFileReader.sync(seekableInput.tell());
            return dataFileReader;
        }
        dataFileReader.seek(seekableInput.tell());
        return dataFileReader;
    }

    public static <D> FileReader<D> openReader(SeekableInput seekableInput, DatumReader<D> datumReader) throws IOException {
        if (seekableInput.length() < (long)DataFileConstants.MAGIC.length) {
            throw new IOException("Not an Avro data file");
        }
        byte[] arrby = new byte[DataFileConstants.MAGIC.length];
        seekableInput.seek(0L);
        int n2 = 0;
        while (n2 < arrby.length) {
            n2 = seekableInput.read(arrby, n2, arrby.length - n2);
        }
        seekableInput.seek(0L);
        if (Arrays.equals((byte[])DataFileConstants.MAGIC, (byte[])arrby)) {
            return new DataFileReader<D>(seekableInput, datumReader);
        }
        if (Arrays.equals((byte[])DataFileReader12.MAGIC, (byte[])arrby)) {
            return new DataFileReader12<D>(seekableInput, datumReader);
        }
        throw new IOException("Not an Avro data file");
    }

    public static <D> FileReader<D> openReader(File file, DatumReader<D> datumReader) throws IOException {
        return DataFileReader.openReader(new SeekableFileInput(file), datumReader);
    }

    @Override
    protected void blockFinished() throws IOException {
        this.blockStart = this.sin.tell() - (long)this.vin.inputStream().available();
    }

    @Override
    public boolean pastSync(long l2) throws IOException {
        return this.blockStart >= 16L + l2 || this.blockStart >= this.sin.length();
    }

    public long previousSync() {
        return this.blockStart;
    }

    public void seek(long l2) throws IOException {
        this.sin.seek(l2);
        this.vin = DecoderFactory.get().binaryDecoder(this.sin, this.vin);
        this.datumIn = null;
        this.blockRemaining = 0L;
        this.blockStart = l2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void sync(long var1) throws IOException {
        block11 : {
            block12 : {
                this.seek(var1);
                if (var1 == 0L && this.getMeta("avro.sync") != null) {
                    this.initialize(this.sin);
                    return;
                }
                try {
                    var4_2 = this.vin.inputStream();
                    this.vin.readFixed(this.syncBuffer);
                    var5_3 = 0;
                    break block11;
lbl10: // 1 sources:
                    do {
                        if (var6_7 >= 16 || this.getHeader().sync[var6_7] != this.syncBuffer[(var5_3 + var6_7) % 16]) {
                            if (var6_7 == 16) {
                                this.blockStart = 16L + (var1 + (long)var5_3);
                                return;
                            }
                        } else {
                            ++var6_7;
                            continue;
                        }
                        var7_6 = var4_2.read();
                        var8_4 = this.syncBuffer;
                        break block12;
                        break;
                    } while (true);
                }
                catch (EOFException var3_8) {}
                ** GOTO lbl-1000
            }
            var9_5 = var5_3 + 1;
            try {
                var8_4[var5_3 % 16] = (byte)var7_6;
                if (var7_6 != -1) {
                    var5_3 = var9_5;
                    break block11;
                }
            }
            catch (EOFException var10_9) {}
lbl-1000: // 3 sources:
            {
                this.blockStart = this.sin.tell();
                return;
            }
        }
        var6_7 = 0;
        ** while (true)
    }

    @Override
    public long tell() throws IOException {
        return this.sin.tell();
    }

    static class SeekableInputStream
    extends InputStream
    implements SeekableInput {
        private SeekableInput in;
        private final byte[] oneByte = new byte[1];

        SeekableInputStream(SeekableInput seekableInput) throws IOException {
            this.in = seekableInput;
        }

        public int available() throws IOException {
            long l2 = this.in.length() - this.in.tell();
            if (l2 > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return (int)l2;
        }

        public void close() throws IOException {
            this.in.close();
            super.close();
        }

        @Override
        public long length() throws IOException {
            return this.in.length();
        }

        public int read() throws IOException {
            int n2 = this.read(this.oneByte, 0, 1);
            if (n2 == 1) {
                n2 = 255 & this.oneByte[0];
            }
            return n2;
        }

        public int read(byte[] arrby) throws IOException {
            return this.in.read(arrby, 0, arrby.length);
        }

        @Override
        public int read(byte[] arrby, int n2, int n3) throws IOException {
            return this.in.read(arrby, n2, n3);
        }

        @Override
        public void seek(long l2) throws IOException {
            if (l2 < 0L) {
                throw new IOException("Illegal seek: " + l2);
            }
            this.in.seek(l2);
        }

        public long skip(long l2) throws IOException {
            long l3 = this.in.tell();
            long l4 = this.in.length() - l3;
            if (l4 > l2) {
                this.in.seek(l2);
                return this.in.tell() - l3;
            }
            this.in.seek(l4);
            return this.in.tell() - l3;
        }

        @Override
        public long tell() throws IOException {
            return this.in.tell();
        }
    }

}

