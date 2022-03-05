/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.SerializerFactory
 *  com.flurry.org.codehaus.jackson.map.ser.FilterProvider
 *  com.flurry.org.codehaus.jackson.schema.JsonSchema
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerFactory;
import com.flurry.org.codehaus.jackson.map.ser.FilterProvider;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

public abstract class SerializerProvider {
    protected static final JavaType TYPE_OBJECT = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
    protected final SerializationConfig _config;
    protected final Class<?> _serializationView;

    /*
     * Enabled aggressive block sorting
     */
    protected SerializerProvider(SerializationConfig serializationConfig) {
        this._config = serializationConfig;
        Class<?> class_ = serializationConfig == null ? null : this._config.getSerializationView();
        this._serializationView = class_;
    }

    public abstract int cachedSerializersCount();

    public JavaType constructSpecializedType(JavaType javaType, Class<?> class_) {
        return this._config.constructSpecializedType(javaType, class_);
    }

    public JavaType constructType(Type type) {
        return this._config.getTypeFactory().constructType(type);
    }

    public abstract void defaultSerializeDateKey(long var1, JsonGenerator var3) throws IOException, JsonProcessingException;

    public abstract void defaultSerializeDateKey(Date var1, JsonGenerator var2) throws IOException, JsonProcessingException;

    public abstract void defaultSerializeDateValue(long var1, JsonGenerator var3) throws IOException, JsonProcessingException;

    public abstract void defaultSerializeDateValue(Date var1, JsonGenerator var2) throws IOException, JsonProcessingException;

    public final void defaultSerializeField(String string, Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        jsonGenerator.writeFieldName(string);
        if (object == null) {
            this.getNullValueSerializer().serialize(null, jsonGenerator, (SerializerProvider)this);
            return;
        }
        this.findTypedValueSerializer(object.getClass(), true, null).serialize(object, jsonGenerator, (SerializerProvider)this);
    }

    public final void defaultSerializeNull(JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        this.getNullValueSerializer().serialize(null, jsonGenerator, (SerializerProvider)this);
    }

    public final void defaultSerializeValue(Object object, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (object == null) {
            this.getNullValueSerializer().serialize(null, jsonGenerator, (SerializerProvider)this);
            return;
        }
        this.findTypedValueSerializer(object.getClass(), true, null).serialize(object, jsonGenerator, (SerializerProvider)this);
    }

    public abstract JsonSerializer<Object> findKeySerializer(JavaType var1, BeanProperty var2) throws JsonMappingException;

    @Deprecated
    public final JsonSerializer<Object> findTypedValueSerializer(JavaType javaType, boolean bl) throws JsonMappingException {
        return this.findTypedValueSerializer(javaType, bl, null);
    }

    public abstract JsonSerializer<Object> findTypedValueSerializer(JavaType var1, boolean var2, BeanProperty var3) throws JsonMappingException;

    @Deprecated
    public final JsonSerializer<Object> findTypedValueSerializer(Class<?> class_, boolean bl) throws JsonMappingException {
        return this.findTypedValueSerializer(class_, bl, null);
    }

    public abstract JsonSerializer<Object> findTypedValueSerializer(Class<?> var1, boolean var2, BeanProperty var3) throws JsonMappingException;

    @Deprecated
    public final JsonSerializer<Object> findValueSerializer(JavaType javaType) throws JsonMappingException {
        return this.findValueSerializer(javaType, null);
    }

    public abstract JsonSerializer<Object> findValueSerializer(JavaType var1, BeanProperty var2) throws JsonMappingException;

    @Deprecated
    public final JsonSerializer<Object> findValueSerializer(Class<?> class_) throws JsonMappingException {
        return this.findValueSerializer(class_, null);
    }

    public abstract JsonSerializer<Object> findValueSerializer(Class<?> var1, BeanProperty var2) throws JsonMappingException;

    public abstract void flushCachedSerializers();

    public abstract JsonSchema generateJsonSchema(Class<?> var1, SerializationConfig var2, SerializerFactory var3) throws JsonMappingException;

    public final SerializationConfig getConfig() {
        return this._config;
    }

    public final FilterProvider getFilterProvider() {
        return this._config.getFilterProvider();
    }

    @Deprecated
    public final JsonSerializer<Object> getKeySerializer() throws JsonMappingException {
        return this.findKeySerializer(TYPE_OBJECT, null);
    }

    @Deprecated
    public final JsonSerializer<Object> getKeySerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return this.findKeySerializer(javaType, beanProperty);
    }

    public abstract JsonSerializer<Object> getNullKeySerializer();

    public abstract JsonSerializer<Object> getNullValueSerializer();

    public final Class<?> getSerializationView() {
        return this._serializationView;
    }

    public abstract JsonSerializer<Object> getUnknownTypeSerializer(Class<?> var1);

    public abstract boolean hasSerializerFor(SerializationConfig var1, Class<?> var2, SerializerFactory var3);

    public final boolean isEnabled(SerializationConfig.Feature feature) {
        return this._config.isEnabled(feature);
    }

    public abstract void serializeValue(SerializationConfig var1, JsonGenerator var2, Object var3, SerializerFactory var4) throws IOException, JsonGenerationException;

    public abstract void serializeValue(SerializationConfig var1, JsonGenerator var2, Object var3, JavaType var4, SerializerFactory var5) throws IOException, JsonGenerationException;

    public abstract void setDefaultKeySerializer(JsonSerializer<Object> var1);

    public abstract void setNullKeySerializer(JsonSerializer<Object> var1);

    public abstract void setNullValueSerializer(JsonSerializer<Object> var1);
}

