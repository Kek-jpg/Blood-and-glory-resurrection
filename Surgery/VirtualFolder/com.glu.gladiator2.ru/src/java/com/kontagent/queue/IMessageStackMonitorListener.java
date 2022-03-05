/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.kontagent.queue;

import com.kontagent.queue.Message;
import com.kontagent.queue.MessageStackMonitor;

public interface IMessageStackMonitorListener {
    public void onMessageAdded(MessageStackMonitor var1, Message var2);

    public void onMessageRemoved(MessageStackMonitor var1, Message var2);

    public void onMessageStatusChanged(MessageStackMonitor var1, Message var2);
}

