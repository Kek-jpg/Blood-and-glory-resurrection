/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.Writer
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.io;

import com.flurry.org.codehaus.jackson.io.IOContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public abstract class OutputDecorator {
    public abstract OutputStream decorate(IOContext var1, OutputStream var2) throws IOException;

    public abstract Writer decorate(IOContext var1, Writer var2) throws IOException;
}

