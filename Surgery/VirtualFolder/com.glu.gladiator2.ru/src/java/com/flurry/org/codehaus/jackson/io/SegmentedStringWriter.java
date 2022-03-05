/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.Appendable
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import com.flurry.org.codehaus.jackson.util.TextBuffer;
import java.io.IOException;
import java.io.Writer;

public final class SegmentedStringWriter
extends Writer {
    protected final TextBuffer _buffer;

    public SegmentedStringWriter(BufferRecycler bufferRecycler) {
        this._buffer = new TextBuffer(bufferRecycler);
    }

    public Writer append(char c) {
        this.write(c);
        return this;
    }

    public Writer append(CharSequence charSequence) {
        String string = charSequence.toString();
        this._buffer.append(string, 0, string.length());
        return this;
    }

    public Writer append(CharSequence charSequence, int n, int n2) {
        String string = charSequence.subSequence(n, n2).toString();
        this._buffer.append(string, 0, string.length());
        return this;
    }

    public void close() {
    }

    public void flush() {
    }

    public String getAndClear() {
        String string = this._buffer.contentsAsString();
        this._buffer.releaseBuffers();
        return string;
    }

    public void write(int n) {
        this._buffer.append((char)n);
    }

    public void write(String string) {
        this._buffer.append(string, 0, string.length());
    }

    public void write(String string, int n, int n2) {
        this._buffer.append(string, 0, string.length());
    }

    public void write(char[] arrc) {
        this._buffer.append(arrc, 0, arrc.length);
    }

    public void write(char[] arrc, int n, int n2) {
        this._buffer.append(arrc, n, n2);
    }
}

