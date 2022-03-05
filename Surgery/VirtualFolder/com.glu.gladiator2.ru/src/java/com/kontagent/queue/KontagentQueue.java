/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 */
package com.kontagent.queue;

import com.kontagent.queue.Message;
import java.util.Collection;

public interface KontagentQueue {
    public Message dequeue();

    public boolean enqueue(String var1);

    public boolean enqueue(Collection<String> var1);

    public Message peek();
}

