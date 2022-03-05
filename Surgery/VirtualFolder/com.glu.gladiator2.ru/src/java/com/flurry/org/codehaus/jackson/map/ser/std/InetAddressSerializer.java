/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.net.InetAddress
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.ScalarSerializerBase;
import java.io.IOException;
import java.net.InetAddress;

public class InetAddressSerializer
extends ScalarSerializerBase<InetAddress> {
    public static final InetAddressSerializer instance = new InetAddressSerializer();

    public InetAddressSerializer() {
        super(InetAddress.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(InetAddress inetAddress, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        String string = inetAddress.toString().trim();
        int n = string.indexOf(47);
        if (n >= 0) {
            string = n == 0 ? string.substring(1) : string.substring(0, n);
        }
        jsonGenerator.writeString(string);
    }

    @Override
    public void serializeWithType(InetAddress inetAddress, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForScalar((Object)inetAddress, jsonGenerator, InetAddress.class);
        this.serialize(inetAddress, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar((Object)inetAddress, jsonGenerator);
    }
}

