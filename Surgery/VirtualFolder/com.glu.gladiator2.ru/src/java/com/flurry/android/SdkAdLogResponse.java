/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.android;

import com.flurry.org.apache.avro.Protocol;

public interface SdkAdLogResponse {
    public static final Protocol PROTOCOL = Protocol.parse("{\"protocol\":\"SdkAdLogResponse\",\"namespace\":\"com.flurry.android\",\"types\":[{\"type\":\"record\",\"name\":\"SdkLogResponse\",\"fields\":[{\"name\":\"result\",\"type\":\"string\"},{\"name\":\"errors\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}],\"messages\":{}}");
}

