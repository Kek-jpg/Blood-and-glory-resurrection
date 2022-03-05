/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.ResolvableDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.std.ContainerDeserializerBase;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Collection;

@JacksonStdImpl
public final class StringCollectionDeserializer
extends ContainerDeserializerBase<Collection<String>>
implements ResolvableDeserializer {
    protected final JavaType _collectionType;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected final boolean _isDefaultDeserializer;
    protected final JsonDeserializer<String> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;

    protected StringCollectionDeserializer(StringCollectionDeserializer stringCollectionDeserializer) {
        super(stringCollectionDeserializer._valueClass);
        this._collectionType = stringCollectionDeserializer._collectionType;
        this._valueDeserializer = stringCollectionDeserializer._valueDeserializer;
        this._valueInstantiator = stringCollectionDeserializer._valueInstantiator;
        this._isDefaultDeserializer = stringCollectionDeserializer._isDefaultDeserializer;
    }

    public StringCollectionDeserializer(JavaType javaType, JsonDeserializer<?> jsonDeserializer, ValueInstantiator valueInstantiator) {
        super(javaType.getRawClass());
        this._collectionType = javaType;
        this._valueDeserializer = jsonDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._isDefaultDeserializer = this.isDefaultSerializer(jsonDeserializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Collection<String> deserializeUsingCustom(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection) throws IOException, JsonProcessingException {
        JsonDeserializer<String> jsonDeserializer = this._valueDeserializer;
        JsonToken jsonToken;
        while ((jsonToken = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            String string = jsonToken == JsonToken.VALUE_NULL ? null : jsonDeserializer.deserialize(jsonParser, deserializationContext);
            collection.add(string);
        }
        return collection;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Collection<String> handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection) throws IOException, JsonProcessingException {
        if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            throw deserializationContext.mappingException(this._collectionType.getRawClass());
        }
        JsonDeserializer<String> jsonDeserializer = this._valueDeserializer;
        String string = jsonParser.getCurrentToken() == JsonToken.VALUE_NULL ? null : (jsonDeserializer == null ? jsonParser.getText() : jsonDeserializer.deserialize(jsonParser, deserializationContext));
        collection.add(string);
        return collection;
    }

    @Override
    public Collection<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null) {
            return (Collection)this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        return this.deserialize(jsonParser, deserializationContext, (Collection<String>)((Collection)this._valueInstantiator.createUsingDefault()));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Collection<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection) throws IOException, JsonProcessingException {
        JsonToken jsonToken;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return StringCollectionDeserializer.super.handleNonArray(jsonParser, deserializationContext, collection);
        }
        if (!this._isDefaultDeserializer) {
            return StringCollectionDeserializer.super.deserializeUsingCustom(jsonParser, deserializationContext, collection);
        }
        while ((jsonToken = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            String string = jsonToken == JsonToken.VALUE_NULL ? null : jsonParser.getText();
            collection.add((Object)string);
        }
        return collection;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    @Override
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    @Override
    public JavaType getContentType() {
        return this._collectionType.getContentType();
    }

    @Override
    public void resolve(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider) throws JsonMappingException {
        AnnotatedWithParams annotatedWithParams = this._valueInstantiator.getDelegateCreator();
        if (annotatedWithParams != null) {
            JavaType javaType = this._valueInstantiator.getDelegateType();
            this._delegateDeserializer = this.findDeserializer(deserializationConfig, deserializerProvider, javaType, new BeanProperty.Std(null, javaType, null, annotatedWithParams));
        }
    }
}

