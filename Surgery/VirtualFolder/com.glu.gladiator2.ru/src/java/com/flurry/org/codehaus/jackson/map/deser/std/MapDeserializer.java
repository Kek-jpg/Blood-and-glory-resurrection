/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.InvocationTargetException
 *  java.util.Collection
 *  java.util.HashSet
 *  java.util.Map
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
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.ResolvableDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.impl.CreatorProperty;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyBasedCreator;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyValueBuffer;
import com.flurry.org.codehaus.jackson.map.deser.std.ContainerDeserializerBase;
import com.flurry.org.codehaus.jackson.map.deser.std.StdValueInstantiator;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

@JacksonStdImpl
public class MapDeserializer
extends ContainerDeserializerBase<Map<Object, Object>>
implements ResolvableDeserializer {
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected final boolean _hasDefaultCreator;
    protected HashSet<String> _ignorableProperties;
    protected final KeyDeserializer _keyDeserializer;
    protected final JavaType _mapType;
    protected PropertyBasedCreator _propertyBasedCreator;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    protected MapDeserializer(MapDeserializer mapDeserializer) {
        super(mapDeserializer._valueClass);
        this._mapType = mapDeserializer._mapType;
        this._keyDeserializer = mapDeserializer._keyDeserializer;
        this._valueDeserializer = mapDeserializer._valueDeserializer;
        this._valueTypeDeserializer = mapDeserializer._valueTypeDeserializer;
        this._valueInstantiator = mapDeserializer._valueInstantiator;
        this._propertyBasedCreator = mapDeserializer._propertyBasedCreator;
        this._delegateDeserializer = mapDeserializer._delegateDeserializer;
        this._hasDefaultCreator = mapDeserializer._hasDefaultCreator;
        this._ignorableProperties = mapDeserializer._ignorableProperties;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MapDeserializer(JavaType javaType, ValueInstantiator valueInstantiator, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(Map.class);
        this._mapType = javaType;
        this._keyDeserializer = keyDeserializer;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._propertyBasedCreator = valueInstantiator.canCreateFromObjectWith() ? new PropertyBasedCreator(valueInstantiator) : null;
        this._hasDefaultCreator = valueInstantiator.canCreateUsingDefault();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    protected MapDeserializer(JavaType javaType, Constructor<Map<Object, Object>> constructor, KeyDeserializer keyDeserializer, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer) {
        super(Map.class);
        this._mapType = javaType;
        this._keyDeserializer = keyDeserializer;
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        StdValueInstantiator stdValueInstantiator = new StdValueInstantiator(null, javaType);
        if (constructor != null) {
            stdValueInstantiator.configureFromObjectSettings(new AnnotatedConstructor(constructor, null, null), null, null, null, null);
        }
        boolean bl = constructor != null;
        this._hasDefaultCreator = bl;
        this._valueInstantiator = stdValueInstantiator;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<Object, Object> _deserializeUsingCreator(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding(jsonParser, deserializationContext);
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while (jsonToken == JsonToken.FIELD_NAME) {
            String string = jsonParser.getCurrentName();
            JsonToken jsonToken2 = jsonParser.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains((Object)string)) {
                jsonParser.skipChildren();
            } else {
                SettableBeanProperty settableBeanProperty = propertyBasedCreator.findCreatorProperty(string);
                if (settableBeanProperty != null) {
                    Object object = settableBeanProperty.deserialize(jsonParser, deserializationContext);
                    if (propertyValueBuffer.assignParameter(settableBeanProperty.getPropertyIndex(), object)) {
                        Map map;
                        jsonParser.nextToken();
                        try {
                            map = (Map)propertyBasedCreator.build(propertyValueBuffer);
                        }
                        catch (Exception exception) {
                            this.wrapAndThrow(exception, this._mapType.getRawClass());
                            return null;
                        }
                        this._readAndBind(jsonParser, deserializationContext, (Map<Object, Object>)map);
                        return map;
                    }
                } else {
                    String string2 = jsonParser.getCurrentName();
                    Object object = this._keyDeserializer.deserializeKey(string2, deserializationContext);
                    Object object2 = jsonToken2 == JsonToken.VALUE_NULL ? null : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
                    propertyValueBuffer.bufferMapProperty(object, object2);
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        try {
            return (Map)propertyBasedCreator.build(propertyValueBuffer);
        }
        catch (Exception exception) {
            this.wrapAndThrow(exception, this._mapType.getRawClass());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void _readAndBind(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
        }
        KeyDeserializer keyDeserializer = this._keyDeserializer;
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while (jsonToken == JsonToken.FIELD_NAME) {
            String string = jsonParser.getCurrentName();
            Object object = keyDeserializer.deserializeKey(string, deserializationContext);
            JsonToken jsonToken2 = jsonParser.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains((Object)string)) {
                jsonParser.skipChildren();
            } else {
                Object object2 = jsonToken2 == JsonToken.VALUE_NULL ? null : (typeDeserializer == null ? jsonDeserializer.deserialize(jsonParser, deserializationContext) : jsonDeserializer.deserializeWithType(jsonParser, deserializationContext, typeDeserializer));
                map.put(object, object2);
            }
            jsonToken = jsonParser.nextToken();
        }
        return;
    }

    @Override
    public Map<Object, Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingCreator(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer != null) {
            return (Map)this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (!this._hasDefaultCreator) {
            throw deserializationContext.instantiationException(this.getMapClass(), "No default constructor found");
        }
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken != JsonToken.START_OBJECT && jsonToken != JsonToken.FIELD_NAME && jsonToken != JsonToken.END_OBJECT) {
            if (jsonToken == JsonToken.VALUE_STRING) {
                return (Map)this._valueInstantiator.createFromString(jsonParser.getText());
            }
            throw deserializationContext.mappingException(this.getMapClass());
        }
        Map map = (Map)this._valueInstantiator.createUsingDefault();
        this._readAndBind(jsonParser, deserializationContext, (Map<Object, Object>)map);
        return map;
    }

    @Override
    public Map<Object, Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken != JsonToken.START_OBJECT && jsonToken != JsonToken.FIELD_NAME) {
            throw deserializationContext.mappingException(this.getMapClass());
        }
        this._readAndBind(jsonParser, deserializationContext, map);
        return map;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    @Override
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    @Override
    public JavaType getContentType() {
        return this._mapType.getContentType();
    }

    public final Class<?> getMapClass() {
        return this._mapType.getRawClass();
    }

    @Override
    public JavaType getValueType() {
        return this._mapType;
    }

    @Override
    public void resolve(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider) throws JsonMappingException {
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            JavaType javaType = this._valueInstantiator.getDelegateType();
            if (javaType == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._mapType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            this._delegateDeserializer = this.findDeserializer(deserializationConfig, deserializerProvider, javaType, new BeanProperty.Std(null, javaType, null, this._valueInstantiator.getDelegateCreator()));
        }
        if (this._propertyBasedCreator != null) {
            for (SettableBeanProperty settableBeanProperty : this._propertyBasedCreator.getCreatorProperties()) {
                if (settableBeanProperty.hasValueDeserializer()) continue;
                this._propertyBasedCreator.assignDeserializer(settableBeanProperty, this.findDeserializer(deserializationConfig, deserializerProvider, settableBeanProperty.getType(), settableBeanProperty));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setIgnorableProperties(String[] arrstring) {
        HashSet<String> hashSet = arrstring == null || arrstring.length == 0 ? null : ArrayBuilders.arrayToSet(arrstring);
        this._ignorableProperties = hashSet;
    }

    protected void wrapAndThrow(Throwable throwable, Object object) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        if (throwable instanceof IOException && !(throwable instanceof JsonMappingException)) {
            throw (IOException)throwable;
        }
        throw JsonMappingException.wrapWithPath(throwable, object, null);
    }
}

