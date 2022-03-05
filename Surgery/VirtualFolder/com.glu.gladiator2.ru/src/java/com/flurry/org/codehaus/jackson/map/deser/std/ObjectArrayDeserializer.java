/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Byte
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Array
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.std.ContainerDeserializerBase;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.util.ObjectBuffer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Array;

@JacksonStdImpl
public class ObjectArrayDeserializer
extends ContainerDeserializerBase<Object[]> {
    protected final JavaType _arrayType;
    protected final Class<?> _elementClass;
    protected final JsonDeserializer<Object> _elementDeserializer;
    protected final TypeDeserializer _elementTypeDeserializer;
    protected final boolean _untyped;

    /*
     * Enabled aggressive block sorting
     */
    public ObjectArrayDeserializer(ArrayType arrayType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(Object[].class);
        this._arrayType = arrayType;
        this._elementClass = arrayType.getContentType().getRawClass();
        boolean bl = this._elementClass == Object.class;
        this._untyped = bl;
        this._elementDeserializer = jsonDeserializer;
        this._elementTypeDeserializer = typeDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Object[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
            return null;
        }
        if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && this._elementClass == Byte.class) {
                return this.deserializeFromBase64(jsonParser, deserializationContext);
            }
            throw deserializationContext.mappingException(this._arrayType.getRawClass());
        }
        Object object = jsonParser.getCurrentToken() == JsonToken.VALUE_NULL ? null : (this._elementTypeDeserializer == null ? this._elementDeserializer.deserialize(jsonParser, deserializationContext) : this._elementDeserializer.deserializeWithType(jsonParser, deserializationContext, this._elementTypeDeserializer));
        Object[] arrobject = this._untyped ? new Object[1] : (Object[])Array.newInstance(this._elementClass, (int)1);
        arrobject[0] = object;
        return arrobject;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return ObjectArrayDeserializer.super.handleNonArray(jsonParser, deserializationContext);
        }
        ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] arrobject = objectBuffer.resetAndStart();
        int n = 0;
        TypeDeserializer typeDeserializer = this._elementTypeDeserializer;
        while ((jsonToken = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            Object object = jsonToken == JsonToken.VALUE_NULL ? null : (typeDeserializer == null ? this._elementDeserializer.deserialize(jsonParser, deserializationContext) : this._elementDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
            if (n >= arrobject.length) {
                arrobject = objectBuffer.appendCompletedChunk(arrobject);
                n = 0;
            }
            int n2 = n + 1;
            arrobject[n] = object;
            n = n2;
        }
        Object[] arrobject2 = this._untyped ? objectBuffer.completeAndClearBuffer(arrobject, n) : objectBuffer.completeAndClearBuffer(arrobject, n, this._elementClass);
        deserializationContext.returnObjectBuffer(objectBuffer);
        return arrobject2;
    }

    protected Byte[] deserializeFromBase64(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        byte[] arrby = jsonParser.getBinaryValue(deserializationContext.getBase64Variant());
        Byte[] arrbyte = new Byte[arrby.length];
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            arrbyte[i] = arrby[i];
        }
        return arrbyte;
    }

    public Object[] deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return (Object[])typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @Override
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._elementDeserializer;
    }

    @Override
    public JavaType getContentType() {
        return this._arrayType.getContentType();
    }
}

