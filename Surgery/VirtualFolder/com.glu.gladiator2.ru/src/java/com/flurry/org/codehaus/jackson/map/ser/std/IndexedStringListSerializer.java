/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.List
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.std.StaticListSerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;
import java.util.List;

@JacksonStdImpl
public final class IndexedStringListSerializer
extends StaticListSerializerBase<List<String>>
implements ResolvableSerializer {
    protected JsonSerializer<String> _serializer;

    public IndexedStringListSerializer(BeanProperty beanProperty) {
        super(List.class, beanProperty);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void serializeContents(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        int n;
        int n2 = 0;
        try {
            n = list.size();
        }
        catch (Exception exception) {
            this.wrapAndThrow(serializerProvider, (Throwable)exception, (Object)list, n2);
            return;
        }
        while (n2 < n) {
            {
                String string = (String)list.get(n2);
                if (string == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else {
                    jsonGenerator.writeString(string);
                }
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void serializeUsingCustom(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        JsonSerializer<String> jsonSerializer;
        int n;
        int n2 = 0;
        try {
            n = list.size();
            jsonSerializer = this._serializer;
            n2 = 0;
        }
        catch (Exception exception) {
            this.wrapAndThrow(serializerProvider, (Throwable)exception, (Object)list, n2);
            return;
        }
        while (n2 < n) {
            {
                String string = (String)list.get(n2);
                if (string == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else {
                    jsonSerializer.serialize(string, jsonGenerator, serializerProvider);
                }
            }
            ++n2;
        }
        return;
    }

    @Override
    protected JsonNode contentSchema() {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(String.class, this._property);
        if (!this.isDefaultSerializer(jsonSerializer)) {
            this._serializer = jsonSerializer;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartArray();
        if (this._serializer == null) {
            IndexedStringListSerializer.super.serializeContents(list, jsonGenerator, serializerProvider);
        } else {
            IndexedStringListSerializer.super.serializeUsingCustom(list, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(List<String> list, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForArray(list, jsonGenerator);
        if (this._serializer == null) {
            IndexedStringListSerializer.super.serializeContents(list, jsonGenerator, serializerProvider);
        } else {
            IndexedStringListSerializer.super.serializeUsingCustom(list, jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForArray(list, jsonGenerator);
    }
}

