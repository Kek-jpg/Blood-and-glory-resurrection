/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Observable
 *  java.util.Observer
 */
package com.kontagent.queue;

import com.kontagent.KontagentLog;
import com.kontagent.queue.IMessageStackMonitorListener;
import com.kontagent.queue.Message;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MessageStackMonitor
implements Observer {
    private final Map<Long, Message> messages = new HashMap();
    private IMessageStackMonitorListener monitorListener;
    private String sessionId;

    public MessageStackMonitor(String string2) {
        this.sessionId = string2;
    }

    public void addMessage(Message message) {
        if (message != null) {
            Object[] arrobject = new Object[]{message.toString()};
            KontagentLog.d(String.format((String)"Adding message: %s", (Object[])arrobject));
            message.addObserver((Observer)this);
            this.messages.put((Object)message.getId(), (Object)message);
            if (this.monitorListener != null) {
                this.monitorListener.onMessageAdded((MessageStackMonitor)this, message);
            }
        }
    }

    public Message getMessageById(Long l) {
        return (Message)((Object)this.messages.get((Object)l));
    }

    public void removeMessage(Long l) {
        Message message = (Message)((Object)this.messages.remove((Object)l));
        if (this.monitorListener != null && message != null) {
            Object[] arrobject = new Object[]{message.toString()};
            KontagentLog.d(String.format((String)"Removing message: %s", (Object[])arrobject));
            this.monitorListener.onMessageRemoved((MessageStackMonitor)this, message);
            message.deleteObserver((Observer)this);
        }
    }

    public MessageStackMonitor setMonitorListener(IMessageStackMonitorListener iMessageStackMonitorListener) {
        this.monitorListener = iMessageStackMonitorListener;
        return this;
    }

    public void syncMessagesWithInternalDatabaseOnStartup(List<Message> list) {
        if (list != null) {
            for (Message message : list) {
                this.messages.put((Object)message.getId(), (Object)message);
            }
        }
    }

    public void update(Observable observable, Object object) {
        Message message = (Message)observable;
        if (this.monitorListener != null && message != null) {
            Object[] arrobject = new Object[]{message.toString()};
            KontagentLog.d(String.format((String)"Updating message: %s", (Object[])arrobject));
            this.monitorListener.onMessageStatusChanged((MessageStackMonitor)this, message);
        }
    }
}

