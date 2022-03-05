/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Observable
 */
package com.kontagent.queue;

import java.util.Observable;

public class Message
extends Observable {
    private int deliveryTrials;
    private Long id;
    private boolean isDelivered;
    private String name;
    private String sessionId;
    private String timestamp;
    private String url;

    public Message(String string2, String string3, String string4, String string5) {
        this.url = string2;
        this.name = string3;
        this.sessionId = string4;
        this.timestamp = string5;
    }

    public Message(String string2, String string3, String string4, String string5, Long l) {
        this.url = string2;
        this.name = string3;
        this.sessionId = string4;
        this.timestamp = string5;
        this.id = l;
    }

    private void changed() {
        this.setChanged();
        this.notifyObservers();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Message message = (Message)((Object)object);
        if (this.id != null) {
            if (!this.id.equals((Object)message.id)) {
                return false;
            }
        } else if (message.id != null) return false;
        if (this.name != null) {
            if (!this.name.equals((Object)message.name)) {
                return false;
            }
        } else if (message.name != null) return false;
        if (this.sessionId != null) {
            if (!this.sessionId.equals((Object)message.sessionId)) {
                return false;
            }
        } else if (message.sessionId != null) return false;
        if (this.timestamp != null) {
            if (!this.timestamp.equals((Object)message.timestamp)) {
                return false;
            }
        } else if (message.timestamp != null) return false;
        if (this.url != null) {
            if (this.url.equals((Object)message.url)) return true;
            return false;
        }
        if (message.url == null) return true;
        return false;
    }

    public int getDeliveryTrials() {
        return this.deliveryTrials;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getUrl() {
        return this.url;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = this.url != null ? this.url.hashCode() : 0;
        int n2 = n * 31;
        int n3 = this.id != null ? this.id.hashCode() : 0;
        int n4 = 31 * (n2 + n3);
        int n5 = this.name != null ? this.name.hashCode() : 0;
        int n6 = 31 * (n4 + n5);
        int n7 = this.sessionId != null ? this.sessionId.hashCode() : 0;
        int n8 = 31 * (n6 + n7);
        String string2 = this.timestamp;
        int n9 = 0;
        if (string2 != null) {
            n9 = this.timestamp.hashCode();
        }
        return n8 + n9;
    }

    public boolean isDelivered() {
        return this.isDelivered;
    }

    public void setDelivered(boolean bl) {
        this.isDelivered = bl;
        Message.super.changed();
    }

    public void setDeliveryTrials(int n) {
        this.deliveryTrials = n;
        Message.super.changed();
    }

    public void setId(Long l) {
        this.id = l;
    }

    public void setName(String string2) {
        this.name = string2;
    }

    public void setSessionId(String string2) {
        this.sessionId = string2;
    }

    public void setTimestamp(String string2) {
        this.timestamp = string2;
    }

    public void setUrl(String string2) {
        this.url = string2;
    }

    public String toString() {
        return "Message{url='" + this.url + '\'' + ", id=" + (Object)this.id + ", name='" + this.name + '\'' + ", sessionId='" + this.sessionId + '\'' + ", timestamp='" + this.timestamp + '\'' + ", isDelivered=" + this.isDelivered + ", deliveryTrials=" + this.deliveryTrials + '}';
    }
}

