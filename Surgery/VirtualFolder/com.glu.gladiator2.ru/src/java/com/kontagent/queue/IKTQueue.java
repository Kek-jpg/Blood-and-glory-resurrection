/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.kontagent.queue;

import com.kontagent.queue.Message;

public interface IKTQueue {
    public void clear();

    public void enqueue(Message var1);

    public int queueSize();

    public void start();

    public void stop();
}

