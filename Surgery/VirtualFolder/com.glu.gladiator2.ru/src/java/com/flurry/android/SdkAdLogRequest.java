/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.android;

import com.flurry.org.apache.avro.Protocol;

public interface SdkAdLogRequest {
    public static final Protocol PROTOCOL = Protocol.parse("{\"protocol\":\"SdkAdLogRequest\",\"namespace\":\"com.flurry.android\",\"types\":[{\"type\":\"record\",\"name\":\"AdReportedId\",\"fields\":[{\"name\":\"type\",\"type\":\"int\"},{\"name\":\"id\",\"type\":\"bytes\"}]},{\"type\":\"record\",\"name\":\"SdkAdEvent\",\"fields\":[{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"params\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"timeOffset\",\"type\":\"long\"}]},{\"type\":\"record\",\"name\":\"SdkAdLog\",\"fields\":[{\"name\":\"sessionId\",\"type\":\"long\"},{\"name\":\"adLogGUID\",\"type\":\"string\"},{\"name\":\"sdkAdEvents\",\"type\":{\"type\":\"array\",\"items\":\"SdkAdEvent\"}}]},{\"type\":\"record\",\"name\":\"SdkLogRequest\",\"fields\":[{\"name\":\"apiKey\",\"type\":\"string\"},{\"name\":\"adReportedIds\",\"type\":{\"type\":\"array\",\"items\":\"AdReportedId\"}},{\"name\":\"sdkAdLogs\",\"type\":{\"type\":\"array\",\"items\":\"SdkAdLog\"}},{\"name\":\"agentTimestamp\",\"type\":\"long\"},{\"name\":\"testDevice\",\"type\":\"boolean\",\"default\":false}]}],\"messages\":{}}");
}

