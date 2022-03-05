/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Constructor
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
import com.flurry.org.codehaus.jackson.map.deser.impl.CreatorProperty;
import com.flurry.org.codehaus.jackson.map.deser.std.ContainerDeserializerBase;
import com.flurry.org.codehaus.jackson.map.deser.std.StdValueInstantiator;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;

@JacksonStdImpl
public class CollectionDeserializer
extends ContainerDeserializerBase<Collection<Object>>
implements ResolvableDeserializer {
    protected final JavaType _collectionType;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    protected CollectionDeserializer(CollectionDeserializer collectionDeserializer) {
        super(collectionDeserializer._valueClass);
        this._collectionType = collectionDeserializer._collectionType;
        this._valueDeserializer = collectionDeserializer._valueDeserializer;
        this._valueTypeDeserializer = collectionDeserializer._valueTypeDeserializer;
        this._valueInstantiator = collectionDeserializer._valueInstantiator;
        this._delegateDeserializer = collectionDeserializer._delegateDeserializer;
    }

    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator) {
        super(javaType.getRawClass());
        this._collectionType = javaType;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = valueInstantiator;
    }

    @Deprecated
    protected CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, Constructor<Collection<Object>> constructor) {
        super(javaType.getRawClass());
        this._collectionType = javaType;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        StdValueInstantiator stdValueInstantiator = new StdValueInstantiator(null, javaType);
        if (constructor != null) {
            stdValueInstantiator.configureFromObjectSettings(new AnnotatedConstructor(constructor, null, null), null, null, null, null);
        }
        this._valueInstantiator = stdValueInstantiator;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Collection<Object> handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException, JsonProcessingException {
        if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            throw deserializationContext.mappingException(this._collectionType.getRawClass());
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        Object object = jsonParser.getCurrentToken() == JsonToken.VALUE_NULL ? null : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
        collection.add(object);
        return collection;
    }

    @Override
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String string;
        if (this._delegateDeserializer != null) {
            return (Collection)this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && (string = jsonParser.getText()).length() == 0) {
            return (Collection)this._valueInstantiator.createFromString(string);
        }
        return this.deserialize(jsonParser, deserializationContext, (Collection<Object>)((Collection)this._valueInstantiator.createUsingDefault()));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException, JsonProcessingException {
        JsonToken jsonToken;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return CollectionDeserializer.super.handleNonArray(jsonParser, deserializationContext, collection);
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while ((jsonToken = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
            Object object = jsonToken == JsonToken.VALUE_NULL ? null : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
            collection.add(object);
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
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            JavaType javaType = this._valueInstantiator.getDelegateType();
            if (javaType == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._collectionType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            this._delegateDeserializer = this.findDeserializer(deserializationConfig, deserializerProvider, javaType, new BeanProperty.Std(null, javaType, null, this._valueInstantiator.getDelegateCreator()));
        }
    }
}

