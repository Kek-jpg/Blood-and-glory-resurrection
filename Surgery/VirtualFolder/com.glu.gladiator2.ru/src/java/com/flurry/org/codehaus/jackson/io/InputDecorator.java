/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.Reader
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public abstract class InputDecorator {
    public abstract InputStream decorate(IOContext var1, InputStream var2) throws IOException;

    public abstract InputStream decorate(IOContext var1, byte[] var2, int var3, int var4) throws IOException;

    public abstract Reader decorate(IOContext var1, Reader var2) throws IOException;
}

