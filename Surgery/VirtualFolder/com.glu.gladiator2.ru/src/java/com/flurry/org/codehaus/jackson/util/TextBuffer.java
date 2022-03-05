/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.io.NumberInput
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.math.BigDecimal
 *  java.util.ArrayList
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.io.NumberInput;
import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import java.math.BigDecimal;
import java.util.ArrayList;

public final class TextBuffer {
    static final int MAX_SEGMENT_LEN = 262144;
    static final int MIN_SEGMENT_LEN = 1000;
    static final char[] NO_CHARS = new char[0];
    private final BufferRecycler _allocator;
    private char[] _currentSegment;
    private int _currentSize;
    private boolean _hasSegments = false;
    private char[] _inputBuffer;
    private int _inputLen;
    private int _inputStart;
    private char[] _resultArray;
    private String _resultString;
    private int _segmentSize;
    private ArrayList<char[]> _segments;

    public TextBuffer(BufferRecycler bufferRecycler) {
        this._allocator = bufferRecycler;
    }

    private final char[] _charArray(int n) {
        return new char[n];
    }

    private char[] buildResultArray() {
        if (this._resultString != null) {
            return this._resultString.toCharArray();
        }
        if (this._inputStart >= 0) {
            if (this._inputLen < 1) {
                return NO_CHARS;
            }
            char[] arrc = this._charArray(this._inputLen);
            System.arraycopy((Object)this._inputBuffer, (int)this._inputStart, (Object)arrc, (int)0, (int)this._inputLen);
            return arrc;
        }
        int n = this.size();
        if (n < 1) {
            return NO_CHARS;
        }
        char[] arrc = this._charArray(n);
        ArrayList<char[]> arrayList = this._segments;
        int n2 = 0;
        if (arrayList != null) {
            int n3 = this._segments.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                char[] arrc2 = (char[])this._segments.get(i2);
                int n4 = arrc2.length;
                System.arraycopy((Object)arrc2, (int)0, (Object)arrc, (int)n2, (int)n4);
                n2 += n4;
            }
        }
        System.arraycopy((Object)this._currentSegment, (int)0, (Object)arrc, (int)n2, (int)this._currentSize);
        return arrc;
    }

    private final void clearSegments() {
        this._hasSegments = false;
        this._segments.clear();
        this._segmentSize = 0;
        this._currentSize = 0;
    }

    private void expand(int n) {
        if (this._segments == null) {
            this._segments = new ArrayList();
        }
        char[] arrc = this._currentSegment;
        this._hasSegments = true;
        this._segments.add((Object)arrc);
        this._segmentSize += arrc.length;
        int n2 = arrc.length;
        int n3 = n2 >> 1;
        if (n3 < n) {
            n3 = n;
        }
        char[] arrc2 = TextBuffer.super._charArray(Math.min((int)262144, (int)(n2 + n3)));
        this._currentSize = 0;
        this._currentSegment = arrc2;
    }

    private final char[] findBuffer(int n) {
        if (this._allocator != null) {
            return this._allocator.allocCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, n);
        }
        return new char[Math.max((int)n, (int)1000)];
    }

    private void unshare(int n) {
        int n2 = this._inputLen;
        this._inputLen = 0;
        char[] arrc = this._inputBuffer;
        this._inputBuffer = null;
        int n3 = this._inputStart;
        this._inputStart = -1;
        int n4 = n2 + n;
        if (this._currentSegment == null || n4 > this._currentSegment.length) {
            this._currentSegment = TextBuffer.super.findBuffer(n4);
        }
        if (n2 > 0) {
            System.arraycopy((Object)arrc, (int)n3, (Object)this._currentSegment, (int)0, (int)n2);
        }
        this._segmentSize = 0;
        this._currentSize = n2;
    }

    public void append(char c2) {
        if (this._inputStart >= 0) {
            TextBuffer.super.unshare(16);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc = this._currentSegment;
        if (this._currentSize >= arrc.length) {
            TextBuffer.super.expand(1);
            arrc = this._currentSegment;
        }
        int n = this._currentSize;
        this._currentSize = n + 1;
        arrc[n] = c2;
    }

    public void append(String string2, int n, int n2) {
        if (this._inputStart >= 0) {
            TextBuffer.super.unshare(n2);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc = this._currentSegment;
        int n3 = arrc.length - this._currentSize;
        if (n3 >= n2) {
            string2.getChars(n, n + n2, arrc, this._currentSize);
            this._currentSize = n2 + this._currentSize;
            return;
        }
        if (n3 > 0) {
            string2.getChars(n, n + n3, arrc, this._currentSize);
            n2 -= n3;
            n += n3;
        }
        TextBuffer.super.expand(n2);
        string2.getChars(n, n + n2, this._currentSegment, 0);
        this._currentSize = n2;
    }

    public void append(char[] arrc, int n, int n2) {
        if (this._inputStart >= 0) {
            TextBuffer.super.unshare(n2);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc2 = this._currentSegment;
        int n3 = arrc2.length - this._currentSize;
        if (n3 >= n2) {
            System.arraycopy((Object)arrc, (int)n, (Object)arrc2, (int)this._currentSize, (int)n2);
            this._currentSize = n2 + this._currentSize;
            return;
        }
        if (n3 > 0) {
            System.arraycopy((Object)arrc, (int)n, (Object)arrc2, (int)this._currentSize, (int)n3);
            n += n3;
            n2 -= n3;
        }
        TextBuffer.super.expand(n2);
        System.arraycopy((Object)arrc, (int)n, (Object)this._currentSegment, (int)0, (int)n2);
        this._currentSize = n2;
    }

    public char[] contentsAsArray() {
        char[] arrc = this._resultArray;
        if (arrc == null) {
            this._resultArray = arrc = this.buildResultArray();
        }
        return arrc;
    }

    public BigDecimal contentsAsDecimal() throws NumberFormatException {
        if (this._resultArray != null) {
            return new BigDecimal(this._resultArray);
        }
        if (this._inputStart >= 0) {
            return new BigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
        }
        if (this._segmentSize == 0) {
            return new BigDecimal(this._currentSegment, 0, this._currentSize);
        }
        return new BigDecimal(this.contentsAsArray());
    }

    public double contentsAsDouble() throws NumberFormatException {
        return NumberInput.parseDouble((String)this.contentsAsString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String contentsAsString() {
        if (this._resultString != null) return this._resultString;
        if (this._resultArray != null) {
            this._resultString = new String(this._resultArray);
            return this._resultString;
        }
        if (this._inputStart >= 0) {
            if (this._inputLen < 1) {
                this._resultString = "";
                return "";
            }
            this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
            return this._resultString;
        }
        int n = this._segmentSize;
        int n2 = this._currentSize;
        if (n == 0) {
            String string2 = n2 == 0 ? "" : new String(this._currentSegment, 0, n2);
            this._resultString = string2;
            return this._resultString;
        }
        StringBuilder stringBuilder = new StringBuilder(n + n2);
        if (this._segments != null) {
            int n3 = this._segments.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                char[] arrc = (char[])this._segments.get(i2);
                stringBuilder.append(arrc, 0, arrc.length);
            }
        }
        stringBuilder.append(this._currentSegment, 0, this._currentSize);
        this._resultString = stringBuilder.toString();
        return this._resultString;
    }

    public final char[] emptyAndGetCurrentSegment() {
        char[] arrc;
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        if ((arrc = this._currentSegment) == null) {
            this._currentSegment = arrc = this.findBuffer(0);
        }
        return arrc;
    }

    public void ensureNotShared() {
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public char[] expandCurrentSegment() {
        char[] arrc = this._currentSegment;
        int n = arrc.length;
        int n2 = n == 262144 ? 262145 : Math.min((int)262144, (int)(n + (n >> 1)));
        this._currentSegment = this._charArray(n2);
        System.arraycopy((Object)arrc, (int)0, (Object)this._currentSegment, (int)0, (int)n);
        return this._currentSegment;
    }

    public char[] finishCurrentSegment() {
        if (this._segments == null) {
            this._segments = new ArrayList();
        }
        this._hasSegments = true;
        this._segments.add((Object)this._currentSegment);
        int n = this._currentSegment.length;
        this._segmentSize = n + this._segmentSize;
        char[] arrc = this._charArray(Math.min((int)(n + (n >> 1)), (int)262144));
        this._currentSize = 0;
        this._currentSegment = arrc;
        return arrc;
    }

    /*
     * Enabled aggressive block sorting
     */
    public char[] getCurrentSegment() {
        if (this._inputStart >= 0) {
            this.unshare(1);
            return this._currentSegment;
        }
        char[] arrc = this._currentSegment;
        if (arrc == null) {
            this._currentSegment = this.findBuffer(0);
            return this._currentSegment;
        }
        if (this._currentSize < arrc.length) return this._currentSegment;
        this.expand(1);
        return this._currentSegment;
    }

    public int getCurrentSegmentSize() {
        return this._currentSize;
    }

    public char[] getTextBuffer() {
        if (this._inputStart >= 0) {
            return this._inputBuffer;
        }
        if (this._resultArray != null) {
            return this._resultArray;
        }
        if (this._resultString != null) {
            char[] arrc = this._resultString.toCharArray();
            this._resultArray = arrc;
            return arrc;
        }
        if (!this._hasSegments) {
            return this._currentSegment;
        }
        return this.contentsAsArray();
    }

    public int getTextOffset() {
        if (this._inputStart >= 0) {
            return this._inputStart;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasTextAsCharacters() {
        return this._inputStart >= 0 || this._resultArray != null || this._resultString == null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void releaseBuffers() {
        if (this._allocator == null) {
            this.resetWithEmpty();
            return;
        } else {
            if (this._currentSegment == null) return;
            {
                this.resetWithEmpty();
                char[] arrc = this._currentSegment;
                this._currentSegment = null;
                this._allocator.releaseCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, arrc);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void resetWithCopy(char[] arrc, int n, int n2) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            TextBuffer.super.clearSegments();
        } else if (this._currentSegment == null) {
            this._currentSegment = TextBuffer.super.findBuffer(n2);
        }
        this._segmentSize = 0;
        this._currentSize = 0;
        this.append(arrc, n, n2);
    }

    public void resetWithEmpty() {
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
    }

    public void resetWithShared(char[] arrc, int n, int n2) {
        this._resultString = null;
        this._resultArray = null;
        this._inputBuffer = arrc;
        this._inputStart = n;
        this._inputLen = n2;
        if (this._hasSegments) {
            TextBuffer.super.clearSegments();
        }
    }

    public void resetWithString(String string2) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = string2;
        this._resultArray = null;
        if (this._hasSegments) {
            TextBuffer.super.clearSegments();
        }
        this._currentSize = 0;
    }

    public void setCurrentLength(int n) {
        this._currentSize = n;
    }

    public int size() {
        if (this._inputStart >= 0) {
            return this._inputLen;
        }
        if (this._resultArray != null) {
            return this._resultArray.length;
        }
        if (this._resultString != null) {
            return this._resultString.length();
        }
        return this._segmentSize + this._currentSize;
    }

    public String toString() {
        return this.contentsAsString();
    }
}

