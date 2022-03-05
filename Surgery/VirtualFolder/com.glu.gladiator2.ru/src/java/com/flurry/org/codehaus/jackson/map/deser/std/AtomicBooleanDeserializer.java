/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.concurrent.atomic.AtomicBoolean
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanDeserializer
extends StdScalarDeserializer<AtomicBoolean> {
    public AtomicBooleanDeserializer() {
        super(AtomicBoolean.class);
    }

    @Override
    public AtomicBoolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new AtomicBoolean(this._parseBooleanPrimitive(jsonParser, deserializationContext));
    }
}

