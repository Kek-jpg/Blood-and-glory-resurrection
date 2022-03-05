/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.ObjectInputStream
 *  java.io.ObjectOutputStream
 *  java.io.Serializable
 *  java.lang.ClassNotFoundException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Date
 *  org.apache.http.cookie.Cookie
 *  org.apache.http.impl.cookie.BasicClientCookie
 */
package com.kontagent.network.asynchttpclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class SerializableCookie
implements Serializable {
    private static final long serialVersionUID = 6374381828722046732L;
    private transient BasicClientCookie clientCookie;
    private final transient Cookie cookie;

    public SerializableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.clientCookie = new BasicClientCookie((String)objectInputStream.readObject(), (String)objectInputStream.readObject());
        this.clientCookie.setComment((String)objectInputStream.readObject());
        this.clientCookie.setDomain((String)objectInputStream.readObject());
        this.clientCookie.setExpiryDate((Date)objectInputStream.readObject());
        this.clientCookie.setPath((String)objectInputStream.readObject());
        this.clientCookie.setVersion(objectInputStream.readInt());
        this.clientCookie.setSecure(objectInputStream.readBoolean());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject((Object)this.cookie.getName());
        objectOutputStream.writeObject((Object)this.cookie.getValue());
        objectOutputStream.writeObject((Object)this.cookie.getComment());
        objectOutputStream.writeObject((Object)this.cookie.getDomain());
        objectOutputStream.writeObject((Object)this.cookie.getExpiryDate());
        objectOutputStream.writeObject((Object)this.cookie.getPath());
        objectOutputStream.writeInt(this.cookie.getVersion());
        objectOutputStream.writeBoolean(this.cookie.isSecure());
    }

    public Cookie getCookie() {
        Cookie cookie = this.cookie;
        if (this.clientCookie != null) {
            cookie = this.clientCookie;
        }
        return cookie;
    }
}

