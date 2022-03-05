/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.ThreadLocal
 *  java.lang.ref.SoftReference
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import com.flurry.org.codehaus.jackson.util.CharTypes;
import com.flurry.org.codehaus.jackson.util.TextBuffer;
import java.lang.ref.SoftReference;

public final class JsonStringEncoder {
    private static final byte[] HEX_BYTES;
    private static final char[] HEX_CHARS;
    private static final int INT_0 = 48;
    private static final int INT_BACKSLASH = 92;
    private static final int INT_U = 117;
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder;
    protected ByteArrayBuilder _byteBuilder;
    protected final char[] _quoteBuffer = new char[6];
    protected TextBuffer _textBuffer;

    static {
        HEX_CHARS = CharTypes.copyHexChars();
        HEX_BYTES = CharTypes.copyHexBytes();
        _threadEncoder = new ThreadLocal();
    }

    public JsonStringEncoder() {
        this._quoteBuffer[0] = 92;
        this._quoteBuffer[2] = 48;
        this._quoteBuffer[3] = 48;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int _appendByteEscape(int n, int n2, ByteArrayBuilder byteArrayBuilder, int n3) {
        byteArrayBuilder.setCurrentSegmentLength(n3);
        byteArrayBuilder.append(92);
        if (n2 >= 0) {
            byteArrayBuilder.append((byte)n2);
            return byteArrayBuilder.getCurrentSegmentLength();
        }
        byteArrayBuilder.append(117);
        if (n > 255) {
            int n4 = n >> 8;
            byteArrayBuilder.append(HEX_BYTES[n4 >> 4]);
            byteArrayBuilder.append(HEX_BYTES[n4 & 15]);
            n &= 255;
        } else {
            byteArrayBuilder.append(48);
            byteArrayBuilder.append(48);
        }
        byteArrayBuilder.append(HEX_BYTES[n >> 4]);
        byteArrayBuilder.append(HEX_BYTES[n & 15]);
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    private int _appendSingleEscape(int n, char[] arrc) {
        if (n < 0) {
            int n2 = -(n + 1);
            arrc[1] = 117;
            arrc[4] = HEX_CHARS[n2 >> 4];
            arrc[5] = HEX_CHARS[n2 & 15];
            return 6;
        }
        arrc[1] = (char)n;
        return 2;
    }

    private int _convertSurrogate(int n, int n2) {
        if (n2 < 56320 || n2 > 57343) {
            throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString((int)n) + ", second 0x" + Integer.toHexString((int)n2) + "; illegal combination");
        }
        return 65536 + (n - 55296 << 10) + (n2 - 56320);
    }

    private void _throwIllegalSurrogate(int n) {
        if (n > 1114111) {
            throw new IllegalArgumentException("Illegal character point (0x" + Integer.toHexString((int)n) + ") to output; max is 0x10FFFF as per RFC 4627");
        }
        if (n >= 55296) {
            if (n <= 56319) {
                throw new IllegalArgumentException("Unmatched first part of surrogate pair (0x" + Integer.toHexString((int)n) + ")");
            }
            throw new IllegalArgumentException("Unmatched second part of surrogate pair (0x" + Integer.toHexString((int)n) + ")");
        }
        throw new IllegalArgumentException("Illegal character point (0x" + Integer.toHexString((int)n) + ") to output");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JsonStringEncoder getInstance() {
        SoftReference softReference = (SoftReference)_threadEncoder.get();
        JsonStringEncoder jsonStringEncoder = softReference == null ? null : (JsonStringEncoder)softReference.get();
        if (jsonStringEncoder == null) {
            jsonStringEncoder = new JsonStringEncoder();
            _threadEncoder.set((Object)new SoftReference((Object)jsonStringEncoder));
        }
        return jsonStringEncoder;
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] encodeAsUTF8(String string) {
        ByteArrayBuilder byteArrayBuilder = this._byteBuilder;
        if (byteArrayBuilder == null) {
            this._byteBuilder = byteArrayBuilder = new ByteArrayBuilder(null);
        }
        int n = string.length();
        int n2 = 0;
        byte[] arrby = byteArrayBuilder.resetAndGetFirstSegment();
        int n3 = arrby.length;
        int n4 = 0;
        while (n4 < n) {
            int n5;
            int n6;
            int n7;
            int n8 = n4 + 1;
            int n9 = string.charAt(n4);
            int n10 = n8;
            while (n9 <= 127) {
                if (n2 >= n3) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n3 = arrby.length;
                    n2 = 0;
                }
                int n11 = n2 + 1;
                arrby[n2] = (byte)n9;
                if (n10 >= n) {
                    n2 = n11;
                    return this._byteBuilder.completeAndCoalesce(n2);
                }
                int n12 = n10 + 1;
                n9 = string.charAt(n10);
                n2 = n11;
                n10 = n12;
            }
            if (n2 >= n3) {
                arrby = byteArrayBuilder.finishCurrentSegment();
                n3 = arrby.length;
                n5 = 0;
            } else {
                n5 = n2;
            }
            if (n9 < 2048) {
                n6 = n5 + 1;
                arrby[n5] = (byte)(192 | n9 >> 6);
                n7 = n10;
            } else if (n9 < 55296 || n9 > 57343) {
                int n13 = n5 + 1;
                arrby[n5] = (byte)(224 | n9 >> 12);
                if (n13 >= n3) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n3 = arrby.length;
                    n13 = 0;
                }
                int n14 = n13 + 1;
                arrby[n13] = (byte)(128 | 63 & n9 >> 6);
                n6 = n14;
                n7 = n10;
            } else {
                int n15;
                if (n9 > 56319) {
                    JsonStringEncoder.super._throwIllegalSurrogate(n9);
                }
                if (n10 >= n) {
                    JsonStringEncoder.super._throwIllegalSurrogate(n9);
                }
                n7 = n10 + 1;
                if ((n9 = JsonStringEncoder.super._convertSurrogate(n9, string.charAt(n10))) > 1114111) {
                    JsonStringEncoder.super._throwIllegalSurrogate(n9);
                }
                int n16 = n5 + 1;
                arrby[n5] = (byte)(240 | n9 >> 18);
                if (n16 >= n3) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n3 = arrby.length;
                    n16 = 0;
                }
                int n17 = n16 + 1;
                arrby[n16] = (byte)(128 | 63 & n9 >> 12);
                if (n17 >= n3) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n3 = arrby.length;
                    n15 = 0;
                } else {
                    n15 = n17;
                }
                int n18 = n15 + 1;
                arrby[n15] = (byte)(128 | 63 & n9 >> 6);
                n6 = n18;
            }
            if (n6 >= n3) {
                arrby = byteArrayBuilder.finishCurrentSegment();
                n3 = arrby.length;
                n6 = 0;
            }
            int n19 = n6 + 1;
            arrby[n6] = (byte)(128 | n9 & 63);
            n2 = n19;
            n4 = n7;
        }
        return this._byteBuilder.completeAndCoalesce(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public char[] quoteAsString(String string) {
        TextBuffer textBuffer = this._textBuffer;
        if (textBuffer == null) {
            this._textBuffer = textBuffer = new TextBuffer(null);
        }
        char[] arrc = textBuffer.emptyAndGetCurrentSegment();
        int[] arrn = CharTypes.get7BitOutputEscapes();
        int n = arrn.length;
        int n2 = 0;
        int n3 = string.length();
        int n4 = 0;
        block0 : while (n2 < n3) {
            int n5;
            block7 : {
                int n6;
                do {
                    char c;
                    if ((c = string.charAt(n2)) < n && arrn[c] != 0) {
                        n5 = n2 + 1;
                        n6 = JsonStringEncoder.super._appendSingleEscape(arrn[string.charAt(n2)], this._quoteBuffer);
                        if (n4 + n6 <= arrc.length) break;
                        int n7 = arrc.length - n4;
                        if (n7 > 0) {
                            System.arraycopy((Object)this._quoteBuffer, (int)0, (Object)arrc, (int)n4, (int)n7);
                        }
                        arrc = textBuffer.finishCurrentSegment();
                        int n8 = n6 - n7;
                        System.arraycopy((Object)this._quoteBuffer, (int)n7, (Object)arrc, (int)n4, (int)n8);
                        n4 += n8;
                        break block7;
                    }
                    if (n4 >= arrc.length) {
                        arrc = textBuffer.finishCurrentSegment();
                        n4 = 0;
                    }
                    int n9 = n4 + 1;
                    arrc[n4] = c;
                    if (++n2 >= n3) {
                        n4 = n9;
                        break block0;
                    }
                    n4 = n9;
                } while (true);
                System.arraycopy((Object)this._quoteBuffer, (int)0, (Object)arrc, (int)n4, (int)n6);
                n4 += n6;
            }
            n2 = n5;
        }
        textBuffer.setCurrentLength(n4);
        return textBuffer.contentsAsArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] quoteAsUTF8(String string) {
        ByteArrayBuilder byteArrayBuilder = this._byteBuilder;
        if (byteArrayBuilder == null) {
            this._byteBuilder = byteArrayBuilder = new ByteArrayBuilder(null);
        }
        int n = 0;
        int n2 = string.length();
        int n3 = 0;
        byte[] arrby = byteArrayBuilder.resetAndGetFirstSegment();
        block0 : while (n < n2) {
            char c;
            int n4;
            int n5;
            int n6;
            int[] arrn = CharTypes.get7BitOutputEscapes();
            do {
                char c2;
                if ((c2 = string.charAt(n)) > '' || arrn[c2] != 0) {
                    if (n3 >= arrby.length) {
                        arrby = byteArrayBuilder.finishCurrentSegment();
                        n3 = 0;
                    }
                    n6 = n + 1;
                    c = string.charAt(n);
                    if (c > '') break;
                    n3 = JsonStringEncoder.super._appendByteEscape(c, arrn[c], byteArrayBuilder, n3);
                    arrby = byteArrayBuilder.getCurrentSegment();
                    n = n6;
                    continue block0;
                }
                if (n3 >= arrby.length) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n3 = 0;
                }
                int n7 = n3 + 1;
                arrby[n3] = (byte)c2;
                if (++n >= n2) {
                    n3 = n7;
                    return this._byteBuilder.completeAndCoalesce(n3);
                }
                n3 = n7;
            } while (true);
            if (c <= '\u07ff') {
                int n8 = n3 + 1;
                arrby[n3] = (byte)(192 | c >> 6);
                n5 = 128 | c & 63;
                n4 = n8;
                n = n6;
            } else if (c < '\ud800' || c > '\udfff') {
                int n9;
                int n10 = n3 + 1;
                arrby[n3] = (byte)(224 | c >> 12);
                if (n10 >= arrby.length) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n9 = 0;
                } else {
                    n9 = n10;
                }
                int n11 = n9 + 1;
                arrby[n9] = (byte)(128 | 63 & c >> 6);
                n5 = 128 | c & 63;
                n4 = n11;
                n = n6;
            } else {
                int n12;
                int n13;
                if (c > '\udbff') {
                    JsonStringEncoder.super._throwIllegalSurrogate(c);
                }
                if (n6 >= n2) {
                    JsonStringEncoder.super._throwIllegalSurrogate(c);
                }
                n = n6 + 1;
                int n14 = JsonStringEncoder.super._convertSurrogate(c, string.charAt(n6));
                if (n14 > 1114111) {
                    JsonStringEncoder.super._throwIllegalSurrogate(n14);
                }
                int n15 = n3 + 1;
                arrby[n3] = (byte)(240 | n14 >> 18);
                if (n15 >= arrby.length) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n12 = 0;
                } else {
                    n12 = n15;
                }
                int n16 = n12 + 1;
                arrby[n12] = (byte)(128 | 63 & n14 >> 12);
                if (n16 >= arrby.length) {
                    arrby = byteArrayBuilder.finishCurrentSegment();
                    n13 = 0;
                } else {
                    n13 = n16;
                }
                int n17 = n13 + 1;
                arrby[n13] = (byte)(128 | 63 & n14 >> 6);
                n5 = 128 | n14 & 63;
                n4 = n17;
            }
            if (n4 >= arrby.length) {
                arrby = byteArrayBuilder.finishCurrentSegment();
                n4 = 0;
            }
            int n18 = n4 + 1;
            arrby[n4] = (byte)n5;
            n3 = n18;
        }
        return this._byteBuilder.completeAndCoalesce(n3);
    }
}

