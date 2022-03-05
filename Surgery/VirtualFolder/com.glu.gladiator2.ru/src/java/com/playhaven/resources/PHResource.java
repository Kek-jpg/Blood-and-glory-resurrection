/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.String
 */
package com.playhaven.resources;

import android.util.Base64;
import java.io.UnsupportedEncodingException;

public class PHResource {
    private String data;
    private String key;

    public byte[] getData() {
        return Base64.decode((String)this.data, (int)1);
    }

    public String getResourceKey() {
        return this.key;
    }

    public void setDataByte(byte[] arrby) throws UnsupportedEncodingException {
        this.data = new String(arrby, "UTF-8");
    }

    public void setDataStr(String string2) {
        this.data = string2;
    }

    public void setResourceKey(String string2) {
        this.key = string2;
    }
}

