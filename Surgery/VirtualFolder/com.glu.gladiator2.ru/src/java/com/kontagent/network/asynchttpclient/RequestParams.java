/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.InputStream
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Iterator
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.concurrent.ConcurrentHashMap
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.message.BasicNameValuePair
 */
package com.kontagent.network.asynchttpclient;

import com.kontagent.network.asynchttpclient.SimpleMultipartEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class RequestParams {
    private static String ENCODING = "UTF-8";
    protected ConcurrentHashMap<String, FileWrapper> fileParams;
    protected ConcurrentHashMap<String, String> urlParams;

    public RequestParams() {
        this.init();
    }

    public RequestParams(String string2, String string3) {
        RequestParams.super.init();
        this.put(string2, string3);
    }

    public RequestParams(Map<String, String> map) {
        RequestParams.super.init();
        for (Map.Entry entry : map.entrySet()) {
            this.put((String)entry.getKey(), (String)entry.getValue());
        }
    }

    private void init() {
        this.urlParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    HttpEntity getEntity() {
        SimpleMultipartEntity simpleMultipartEntity;
        if (!this.fileParams.isEmpty()) {
            simpleMultipartEntity = new SimpleMultipartEntity();
            for (Map.Entry entry : this.urlParams.entrySet()) {
                simpleMultipartEntity.addPart((String)entry.getKey(), (String)entry.getValue());
            }
        } else {
            try {
                return new UrlEncodedFormEntity(this.getParamsList(), ENCODING);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                unsupportedEncodingException.printStackTrace();
                return null;
            }
        }
        int n = 0;
        int n2 = -1 + this.fileParams.entrySet().size();
        Iterator iterator = this.fileParams.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            FileWrapper fileWrapper = (FileWrapper)entry.getValue();
            if (fileWrapper.inputStream != null) {
                boolean bl = n == n2;
                if (fileWrapper.contentType != null) {
                    simpleMultipartEntity.addPart((String)entry.getKey(), fileWrapper.getFileName(), fileWrapper.inputStream, fileWrapper.contentType, bl);
                } else {
                    simpleMultipartEntity.addPart((String)entry.getKey(), fileWrapper.getFileName(), fileWrapper.inputStream, bl);
                }
            }
            ++n;
        }
        return simpleMultipartEntity;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(this.getParamsList(), (String)ENCODING);
    }

    protected List<BasicNameValuePair> getParamsList() {
        LinkedList linkedList = new LinkedList();
        for (Map.Entry entry : this.urlParams.entrySet()) {
            linkedList.add((Object)new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
        }
        return linkedList;
    }

    public void put(String string2, File file) throws FileNotFoundException {
        this.put(string2, (InputStream)new FileInputStream(file), file.getName());
    }

    public void put(String string2, InputStream inputStream) {
        this.put(string2, inputStream, null);
    }

    public void put(String string2, InputStream inputStream, String string3) {
        this.put(string2, inputStream, string3, null);
    }

    public void put(String string2, InputStream inputStream, String string3, String string4) {
        if (string2 != null && inputStream != null) {
            this.fileParams.put((Object)string2, (Object)new FileWrapper(inputStream, string3, string4));
        }
    }

    public void put(String string2, String string3) {
        if (string2 != null && string3 != null) {
            this.urlParams.put((Object)string2, (Object)string3);
        }
    }

    public void remove(String string2) {
        this.urlParams.remove((Object)string2);
        this.fileParams.remove((Object)string2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : this.urlParams.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append((String)entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String)entry.getValue());
        }
        for (Map.Entry entry : this.fileParams.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append((String)entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append("FILE");
        }
        return stringBuilder.toString();
    }

    private static class FileWrapper {
        public String contentType;
        public String fileName;
        public InputStream inputStream;

        public FileWrapper(InputStream inputStream, String string2, String string3) {
            this.inputStream = inputStream;
            this.fileName = string2;
            this.contentType = string3;
        }

        public String getFileName() {
            if (this.fileName != null) {
                return this.fileName;
            }
            return "nofilename";
        }
    }

}

