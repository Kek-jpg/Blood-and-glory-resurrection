/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileDescriptor
 *  java.io.FileInputStream
 *  java.io.IOException
 *  java.nio.channels.FileChannel
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.file.SeekableInput;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class SeekableFileInput
extends FileInputStream
implements SeekableInput {
    public SeekableFileInput(File file) throws IOException {
        super(file);
    }

    public SeekableFileInput(FileDescriptor fileDescriptor) throws IOException {
        super(fileDescriptor);
    }

    @Override
    public long length() throws IOException {
        return this.getChannel().size();
    }

    @Override
    public void seek(long l2) throws IOException {
        this.getChannel().position(l2);
    }

    @Override
    public long tell() throws IOException {
        return this.getChannel().position();
    }
}

