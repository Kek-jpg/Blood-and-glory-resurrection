/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.Writer
 *  java.lang.Appendable
 *  java.lang.Integer
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public final class UTF8Writer
extends Writer {
    static final int SURR1_FIRST = 55296;
    static final int SURR1_LAST = 56319;
    static final int SURR2_FIRST = 56320;
    static final int SURR2_LAST = 57343;
    protected final IOContext _context;
    OutputStream _out;
    byte[] _outBuffer;
    final int _outBufferEnd;
    int _outPtr;
    int _surrogate = 0;

    public UTF8Writer(IOContext iOContext, OutputStream outputStream) {
        this._context = iOContext;
        this._out = outputStream;
        this._outBuffer = iOContext.allocWriteEncodingBuffer();
        this._outBufferEnd = -4 + this._outBuffer.length;
        this._outPtr = 0;
    }

    private int convertSurrogate(int n) throws IOException {
        int n2 = this._surrogate;
        this._surrogate = 0;
        if (n < 56320 || n > 57343) {
            throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString((int)n2) + ", second 0x" + Integer.toHexString((int)n) + "; illegal combination");
        }
        return 65536 + (n2 - 55296 << 10) + (n - 56320);
    }

    private void throwIllegal(int n) throws IOException {
        if (n > 1114111) {
            throw new IOException("Illegal character point (0x" + Integer.toHexString((int)n) + ") to output; max is 0x10FFFF as per RFC 4627");
        }
        if (n >= 55296) {
            if (n <= 56319) {
                throw new IOException("Unmatched first part of surrogate pair (0x" + Integer.toHexString((int)n) + ")");
            }
            throw new IOException("Unmatched second part of surrogate pair (0x" + Integer.toHexString((int)n) + ")");
        }
        throw new IOException("Illegal character point (0x" + Integer.toHexString((int)n) + ") to output");
    }

    public Writer append(char c) throws IOException {
        this.write(c);
        return this;
    }

    public void close() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            OutputStream outputStream = this._out;
            this._out = null;
            byte[] arrby = this._outBuffer;
            if (arrby != null) {
                this._outBuffer = null;
                this._context.releaseWriteEncodingBuffer(arrby);
            }
            outputStream.close();
            int n = this._surrogate;
            this._surrogate = 0;
            if (n > 0) {
                this.throwIllegal(n);
            }
        }
    }

    public void flush() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            this._out.flush();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void write(int n) throws IOException {
        int n2;
        if (this._surrogate > 0) {
            n = UTF8Writer.super.convertSurrogate(n);
        } else if (n >= 55296 && n <= 57343) {
            if (n > 56319) {
                UTF8Writer.super.throwIllegal(n);
            }
            this._surrogate = n;
            return;
        }
        if (this._outPtr >= this._outBufferEnd) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
        }
        if (n < 128) {
            byte[] arrby = this._outBuffer;
            int n3 = this._outPtr;
            this._outPtr = n3 + 1;
            arrby[n3] = (byte)n;
            return;
        }
        int n4 = this._outPtr;
        if (n < 2048) {
            byte[] arrby = this._outBuffer;
            int n5 = n4 + 1;
            arrby[n4] = (byte)(192 | n >> 6);
            byte[] arrby2 = this._outBuffer;
            n2 = n5 + 1;
            arrby2[n5] = (byte)(128 | n & 63);
        } else if (n <= 65535) {
            byte[] arrby = this._outBuffer;
            int n6 = n4 + 1;
            arrby[n4] = (byte)(224 | n >> 12);
            byte[] arrby3 = this._outBuffer;
            int n7 = n6 + 1;
            arrby3[n6] = (byte)(128 | 63 & n >> 6);
            byte[] arrby4 = this._outBuffer;
            int n8 = n7 + 1;
            arrby4[n7] = (byte)(128 | n & 63);
            n2 = n8;
        } else {
            if (n > 1114111) {
                UTF8Writer.super.throwIllegal(n);
            }
            byte[] arrby = this._outBuffer;
            int n9 = n4 + 1;
            arrby[n4] = (byte)(240 | n >> 18);
            byte[] arrby5 = this._outBuffer;
            int n10 = n9 + 1;
            arrby5[n9] = (byte)(128 | 63 & n >> 12);
            byte[] arrby6 = this._outBuffer;
            int n11 = n10 + 1;
            arrby6[n10] = (byte)(128 | 63 & n >> 6);
            byte[] arrby7 = this._outBuffer;
            n2 = n11 + 1;
            arrby7[n11] = (byte)(128 | n & 63);
        }
        this._outPtr = n2;
    }

    public void write(String string) throws IOException {
        this.write(string, 0, string.length());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void write(String var1, int var2_3, int var3_2) throws IOException {
        if (var3_2 < 2) {
            if (var3_2 != 1) return;
            this.write(var1.charAt(var2_3));
            return;
        }
        if (this._surrogate > 0) {
            var29_4 = var2_3 + 1;
            var30_5 = var1.charAt(var2_3);
            --var3_2;
            this.write(UTF8Writer.super.convertSurrogate(var30_5));
            var2_3 = var29_4;
        }
        var4_6 = this._outPtr;
        var5_7 = this._outBuffer;
        var6_8 = this._outBufferEnd;
        var7_9 = var3_2 + var2_3;
        var8_10 = var2_3;
        block0 : do {
            block15 : {
                block19 : {
                    block16 : {
                        block17 : {
                            if (var8_10 >= var7_9) break block16;
                            if (var4_6 >= var6_8) {
                                this._out.write(var5_7, 0, var4_6);
                                var4_6 = 0;
                            }
                            var10_28 = var8_10 + 1;
                            var11_11 = var1.charAt(var8_10);
                            if (var11_11 < '') break block17;
                            var12_27 = var4_6;
                            var8_10 = var10_28;
                            ** GOTO lbl44
                        }
                        var12_27 = var4_6 + 1;
                        var5_7[var4_6] = (byte)var11_11;
                        var24_26 = var7_9 - var10_28;
                        var25_16 = var6_8 - var12_27;
                        if (var24_26 > var25_16) {
                            var24_26 = var25_16;
                        }
                        var26_15 = var24_26 + var10_28;
                        var8_10 = var10_28;
                        do {
                            block18 : {
                                if (var8_10 >= var26_15) {
                                    var4_6 = var12_27;
                                    continue block0;
                                }
                                var27_24 = var8_10 + 1;
                                var11_11 = var1.charAt(var8_10);
                                if (var11_11 < '') break block18;
                                var8_10 = var27_24;
lbl44: // 2 sources:
                                if (var11_11 >= '\u0800') break;
                                var22_23 = var12_27 + 1;
                                var5_7[var12_27] = (byte)(192 | var11_11 >> 6);
                                var23_14 = var22_23 + 1;
                                var5_7[var22_23] = (byte)(128 | var11_11 & 63);
                                var4_6 = var23_14;
                                var15_13 = var8_10;
                                break block15;
                            }
                            var28_12 = var12_27 + 1;
                            var5_7[var12_27] = (byte)var11_11;
                            var12_27 = var28_12;
                            var8_10 = var27_24;
                        } while (true);
                        if (var11_11 < '\ud800' || var11_11 > '\udfff') {
                            var13_21 = var12_27 + 1;
                            var5_7[var12_27] = (byte)(224 | var11_11 >> 12);
                            var14_18 = var13_21 + 1;
                            var5_7[var13_21] = (byte)(128 | 63 & var11_11 >> 6);
                            var4_6 = var14_18 + 1;
                            var5_7[var14_18] = (byte)(128 | var11_11 & 63);
                            continue;
                        }
                        if (var11_11 > '\udbff') {
                            this._outPtr = var12_27;
                            UTF8Writer.super.throwIllegal(var11_11);
                        }
                        this._surrogate = var11_11;
                        if (var8_10 < var7_9) break block19;
                        var4_6 = var12_27;
                    }
                    this._outPtr = var4_6;
                    return;
                }
                var15_13 = var8_10 + 1;
                var16_19 = UTF8Writer.super.convertSurrogate(var1.charAt(var8_10));
                if (var16_19 > 1114111) {
                    this._outPtr = var12_27;
                    UTF8Writer.super.throwIllegal(var16_19);
                }
                var17_17 = var12_27 + 1;
                var5_7[var12_27] = (byte)(240 | var16_19 >> 18);
                var18_25 = var17_17 + 1;
                var5_7[var17_17] = (byte)(128 | 63 & var16_19 >> 12);
                var19_22 = var18_25 + 1;
                var5_7[var18_25] = (byte)(128 | 63 & var16_19 >> 6);
                var20_20 = var19_22 + 1;
                var5_7[var19_22] = (byte)(128 | var16_19 & 63);
                var4_6 = var20_20;
            }
            var8_10 = var15_13;
        } while (true);
    }

    public void write(char[] arrc) throws IOException {
        this.write(arrc, 0, arrc.length);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void write(char[] var1, int var2_3, int var3_2) throws IOException {
        if (var3_2 < 2) {
            if (var3_2 != 1) return;
            this.write(var1[var2_3]);
            return;
        }
        if (this._surrogate > 0) {
            var29_4 = var2_3 + 1;
            var30_5 = var1[var2_3];
            --var3_2;
            this.write(UTF8Writer.super.convertSurrogate(var30_5));
            var2_3 = var29_4;
        }
        var4_6 = this._outPtr;
        var5_7 = this._outBuffer;
        var6_8 = this._outBufferEnd;
        var7_9 = var3_2 + var2_3;
        var8_10 = var2_3;
        block0 : do {
            block15 : {
                block19 : {
                    block16 : {
                        block17 : {
                            if (var8_10 >= var7_9) break block16;
                            if (var4_6 >= var6_8) {
                                this._out.write(var5_7, 0, var4_6);
                                var4_6 = 0;
                            }
                            var10_28 = var8_10 + 1;
                            var11_11 = var1[var8_10];
                            if (var11_11 < 128) break block17;
                            var12_27 = var4_6;
                            var8_10 = var10_28;
                            ** GOTO lbl44
                        }
                        var12_27 = var4_6 + 1;
                        var5_7[var4_6] = (byte)var11_11;
                        var24_26 = var7_9 - var10_28;
                        var25_16 = var6_8 - var12_27;
                        if (var24_26 > var25_16) {
                            var24_26 = var25_16;
                        }
                        var26_15 = var24_26 + var10_28;
                        var8_10 = var10_28;
                        do {
                            block18 : {
                                if (var8_10 >= var26_15) {
                                    var4_6 = var12_27;
                                    continue block0;
                                }
                                var27_24 = var8_10 + 1;
                                var11_11 = var1[var8_10];
                                if (var11_11 < 128) break block18;
                                var8_10 = var27_24;
lbl44: // 2 sources:
                                if (var11_11 >= 2048) break;
                                var22_23 = var12_27 + 1;
                                var5_7[var12_27] = (byte)(192 | var11_11 >> 6);
                                var23_14 = var22_23 + 1;
                                var5_7[var22_23] = (byte)(128 | var11_11 & 63);
                                var4_6 = var23_14;
                                var15_13 = var8_10;
                                break block15;
                            }
                            var28_12 = var12_27 + 1;
                            var5_7[var12_27] = (byte)var11_11;
                            var12_27 = var28_12;
                            var8_10 = var27_24;
                        } while (true);
                        if (var11_11 < 55296 || var11_11 > 57343) {
                            var13_21 = var12_27 + 1;
                            var5_7[var12_27] = (byte)(224 | var11_11 >> 12);
                            var14_18 = var13_21 + 1;
                            var5_7[var13_21] = (byte)(128 | 63 & var11_11 >> 6);
                            var4_6 = var14_18 + 1;
                            var5_7[var14_18] = (byte)(128 | var11_11 & 63);
                            continue;
                        }
                        if (var11_11 > 56319) {
                            this._outPtr = var12_27;
                            UTF8Writer.super.throwIllegal(var11_11);
                        }
                        this._surrogate = var11_11;
                        if (var8_10 < var7_9) break block19;
                        var4_6 = var12_27;
                    }
                    this._outPtr = var4_6;
                    return;
                }
                var15_13 = var8_10 + 1;
                var16_19 = UTF8Writer.super.convertSurrogate(var1[var8_10]);
                if (var16_19 > 1114111) {
                    this._outPtr = var12_27;
                    UTF8Writer.super.throwIllegal(var16_19);
                }
                var17_17 = var12_27 + 1;
                var5_7[var12_27] = (byte)(240 | var16_19 >> 18);
                var18_25 = var17_17 + 1;
                var5_7[var17_17] = (byte)(128 | 63 & var16_19 >> 12);
                var19_22 = var18_25 + 1;
                var5_7[var18_25] = (byte)(128 | 63 & var16_19 >> 6);
                var20_20 = var19_22 + 1;
                var5_7[var19_22] = (byte)(128 | var16_19 & 63);
                var4_6 = var20_20;
            }
            var8_10 = var15_13;
        } while (true);
    }
}

