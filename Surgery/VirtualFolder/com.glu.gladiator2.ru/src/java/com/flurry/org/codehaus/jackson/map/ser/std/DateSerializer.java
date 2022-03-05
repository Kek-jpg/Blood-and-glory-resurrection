/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.std.ScalarSerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

@JacksonStdImpl
public class DateSerializer
extends ScalarSerializerBase<Date> {
    public static DateSerializer instance = new DateSerializer();

    public DateSerializer() {
        super(Date.class);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        String string;
        if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
            string = "number";
            do {
                return this.createSchemaNode(string, true);
                break;
            } while (true);
        }
        string = "string";
        return this.createSchemaNode(string, true);
    }

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        serializerProvider.defaultSerializeDateValue(date, jsonGenerator);
    }
}

