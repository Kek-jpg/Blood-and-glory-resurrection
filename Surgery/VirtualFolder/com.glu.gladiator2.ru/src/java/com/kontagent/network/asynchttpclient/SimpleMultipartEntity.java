/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.util.Random
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.message.BasicHeader
 */
package com.kontagent.network.asynchttpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

class SimpleMultipartEntity
implements HttpEntity {
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String boundary = null;
    boolean isSetFirst = false;
    boolean isSetLast = false;
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    public SimpleMultipartEntity() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i2 = 0; i2 < 30; ++i2) {
            stringBuffer.append(MULTIPART_CHARS[random.nextInt(MULTIPART_CHARS.length)]);
        }
        this.boundary = stringBuffer.toString();
    }

    public void addPart(String string2, File file, boolean bl) {
        try {
            this.addPart(string2, file.getName(), (InputStream)new FileInputStream(file), bl);
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return;
        }
    }

    public void addPart(String string2, String string3) {
        this.writeFirstBoundaryIfNeeds();
        try {
            this.out.write(("Content-Disposition: form-data; name=\"" + string2 + "\"\r\n\r\n").getBytes());
            this.out.write(string3.getBytes());
            this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void addPart(String var1_4, String var2_5, InputStream var3_2, String var4_3, boolean var5_1) {
        this.writeFirstBoundaryIfNeeds();
        try {
            try {
                var10_6 = "Content-Type: " + var4_3 + "\r\n";
                this.out.write(("Content-Disposition: form-data; name=\"" + var1_4 + "\"; filename=\"" + var2_5 + "\"\r\n").getBytes());
                this.out.write(var10_6.getBytes());
                this.out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());
                var11_7 = new byte[4096];
                while ((var12_8 = var3_2.read(var11_7)) != -1) {
                    this.out.write(var11_7, 0, var12_8);
                }
                ** if (var5_1) goto lbl14
            }
            catch (IOException var8_9) {
                var8_9.printStackTrace();
                try {
                    var3_2.close();
                    return;
                }
                catch (IOException var9_11) {
                    var9_11.printStackTrace();
                    return;
                }
lbl23: // 1 sources:
                this.out.flush();
                try {
                    var3_2.close();
                    return;
                }
                catch (IOException var13_10) {
                    var13_10.printStackTrace();
                    return;
                }
            }
lbl-1000: // 1 sources:
            {
                this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
            }
lbl14: // 2 sources:
            ** GOTO lbl23
        }
        catch (Throwable var6_12) {
            try {
                var3_2.close();
            }
            catch (IOException var7_13) {
                var7_13.printStackTrace();
                throw var6_12;
            }
            throw var6_12;
        }
    }

    public void addPart(String string2, String string3, InputStream inputStream, boolean bl) {
        this.addPart(string2, string3, inputStream, "application/octet-stream", bl);
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (this.isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        return new ByteArrayInputStream(this.out.toByteArray());
    }

    public Header getContentEncoding() {
        return null;
    }

    public long getContentLength() {
        this.writeLastBoundaryIfNeeds();
        return this.out.toByteArray().length;
    }

    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isRepeatable() {
        return false;
    }

    public boolean isStreaming() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeFirstBoundaryIfNeeds() {
        if (!this.isSetFirst) {
            try {
                this.out.write(("--" + this.boundary + "\r\n").getBytes());
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        this.isSetFirst = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeLastBoundaryIfNeeds() {
        if (this.isSetLast) {
            return;
        }
        try {
            this.out.write(("\r\n--" + this.boundary + "--\r\n").getBytes());
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.isSetLast = true;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.out.toByteArray());
    }
}

