/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Long
 *  java.lang.Object
 */
package com.kontagent.queue;

import com.kontagent.queue.IKTQueue;
import com.kontagent.queue.Message;

public interface ITransferQueueListener {
    public void queueDidAddMessage(IKTQueue var1, Message var2);

    public void queueDidFinishedProcessing(IKTQueue var1);

    public void queueDidReachabilityChanged(boolean var1);

    public void queueDidRemoveMessage(IKTQueue var1, Long var2);

    public void queueDidResume(IKTQueue var1);

    public void queueDidStart(IKTQueue var1);

    public void queueDidStop(IKTQueue var1);

    public void queueDidTransferElementFailed(IKTQueue var1, Long var2);
}

