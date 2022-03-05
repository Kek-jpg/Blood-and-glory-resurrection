/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.TimeZone
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.ScalarSerializerBase;
import java.io.IOException;
import java.util.TimeZone;

public class TimeZoneSerializer
extends ScalarSerializerBase<TimeZone> {
    public static final TimeZoneSerializer instance = new TimeZoneSerializer();

    public TimeZoneSerializer() {
        super(TimeZone.class);
    }

    @Override
    public void serialize(TimeZone timeZone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeString(timeZone.getID());
    }

    @Override
    public void serializeWithType(TimeZone timeZone, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForScalar((Object)timeZone, jsonGenerator, TimeZone.class);
        this.serialize(timeZone, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForScalar((Object)timeZone, jsonGenerator);
    }
}

