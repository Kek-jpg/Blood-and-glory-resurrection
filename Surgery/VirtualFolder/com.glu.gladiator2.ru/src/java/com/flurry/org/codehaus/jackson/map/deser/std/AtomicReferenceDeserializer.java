/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.util.concurrent.atomic.AtomicReference
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.ResolvableDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDeserializer
extends StdScalarDeserializer<AtomicReference<?>>
implements ResolvableDeserializer {
    protected final BeanProperty _property;
    protected final JavaType _referencedType;
    protected JsonDeserializer<?> _valueDeserializer;

    public AtomicReferenceDeserializer(JavaType javaType, BeanProperty beanProperty) {
        super(AtomicReference.class);
        this._referencedType = javaType;
        this._property = beanProperty;
    }

    @Override
    public AtomicReference<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new AtomicReference(this._valueDeserializer.deserialize(jsonParser, deserializationContext));
    }

    @Override
    public void resolve(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider) throws JsonMappingException {
        this._valueDeserializer = deserializerProvider.findValueDeserializer(deserializationConfig, this._referencedType, this._property);
    }
}

