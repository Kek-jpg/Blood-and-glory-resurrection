/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.kontagent.session;

import com.kontagent.queue.IKTQueue;
import com.kontagent.queue.Message;
import com.kontagent.session.ISession;

public interface ISessionListener {
    public void sessionQueueDidReachabilityChanged(ISession var1, boolean var2);

    public void sessionQueueDidResume(ISession var1, IKTQueue var2);

    public void sessionQueueDidStart(ISession var1, IKTQueue var2);

    public void sessionQueueDidStop(ISession var1, IKTQueue var2);

    public void sessionQueueMessageAdded(ISession var1, Message var2);

    public void sessionQueueMessageRemoved(ISession var1, Message var2);

    public void sessionQueueMessageStatusChanged(ISession var1, Message var2);
}

