/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.InputStream
 *  java.io.ObjectInputStream
 *  java.io.ObjectOutputStream
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Date
 *  java.util.List
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.concurrent.ConcurrentHashMap
 *  org.apache.http.client.CookieStore
 *  org.apache.http.cookie.Cookie
 */
package com.kontagent.network.asynchttpclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.kontagent.network.asynchttpclient.SerializableCookie;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class PersistentCookieStore
implements CookieStore {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private final SharedPreferences cookiePrefs;
    private final ConcurrentHashMap<String, Cookie> cookies;

    public PersistentCookieStore(Context context) {
        this.cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        this.cookies = new ConcurrentHashMap();
        String string2 = this.cookiePrefs.getString(COOKIE_NAME_STORE, null);
        if (string2 != null) {
            for (String string3 : TextUtils.split((String)string2, (String)",")) {
                Cookie cookie;
                String string4 = this.cookiePrefs.getString(COOKIE_NAME_PREFIX + string3, null);
                if (string4 == null || (cookie = this.decodeCookie(string4)) == null) continue;
                this.cookies.put((Object)string3, (Object)cookie);
            }
            this.clearExpired(new Date());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addCookie(Cookie cookie) {
        String string2 = cookie.getName();
        if (!cookie.isExpired(new Date())) {
            this.cookies.put((Object)string2, (Object)cookie);
        } else {
            this.cookies.remove((Object)string2);
        }
        SharedPreferences.Editor editor = this.cookiePrefs.edit();
        editor.putString(COOKIE_NAME_STORE, TextUtils.join((CharSequence)",", (Iterable)this.cookies.keySet()));
        editor.putString(COOKIE_NAME_PREFIX + string2, this.encodeCookie(new SerializableCookie(cookie)));
        editor.commit();
    }

    protected String byteArrayToHexString(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer(2 * arrby.length);
        int n = arrby.length;
        for (int i2 = 0; i2 < n; ++i2) {
            int n2 = 255 & arrby[i2];
            if (n2 < 16) {
                stringBuffer.append('0');
            }
            stringBuffer.append(Integer.toHexString((int)n2));
        }
        return stringBuffer.toString().toUpperCase();
    }

    public void clear() {
        this.cookies.clear();
        SharedPreferences.Editor editor = this.cookiePrefs.edit();
        for (String string2 : this.cookies.keySet()) {
            editor.remove(COOKIE_NAME_PREFIX + string2);
        }
        editor.remove(COOKIE_NAME_STORE);
        editor.commit();
    }

    public boolean clearExpired(Date date) {
        boolean bl = false;
        SharedPreferences.Editor editor = this.cookiePrefs.edit();
        for (Map.Entry entry : this.cookies.entrySet()) {
            String string2 = (String)entry.getKey();
            if (!((Cookie)entry.getValue()).isExpired(date)) continue;
            this.cookies.remove((Object)string2);
            editor.remove(COOKIE_NAME_PREFIX + string2);
            bl = true;
        }
        if (bl) {
            editor.putString(COOKIE_NAME_STORE, TextUtils.join((CharSequence)",", (Iterable)this.cookies.keySet()));
        }
        editor.commit();
        return bl;
    }

    protected Cookie decodeCookie(String string2) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.hexStringToByteArray(string2));
        try {
            Cookie cookie = ((SerializableCookie)new ObjectInputStream((InputStream)byteArrayInputStream).readObject()).getCookie();
            return cookie;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    protected String encodeCookie(SerializableCookie serializableCookie) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream((OutputStream)byteArrayOutputStream).writeObject((Object)serializableCookie);
        }
        catch (Exception exception) {
            return null;
        }
        return this.byteArrayToHexString(byteArrayOutputStream.toByteArray());
    }

    public List<Cookie> getCookies() {
        return new ArrayList(this.cookies.values());
    }

    protected byte[] hexStringToByteArray(String string2) {
        int n = string2.length();
        byte[] arrby = new byte[n / 2];
        for (int i2 = 0; i2 < n; i2 += 2) {
            arrby[i2 / 2] = (byte)((Character.digit((char)string2.charAt(i2), (int)16) << 4) + Character.digit((char)string2.charAt(i2 + 1), (int)16));
        }
        return arrby;
    }
}

