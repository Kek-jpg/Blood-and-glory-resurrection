/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.Calendar
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import com.flurry.org.codehaus.jackson.map.util.ObjectBuffer;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public abstract class DeserializationContext {
    protected final DeserializationConfig _config;
    protected final int _featureFlags;

    protected DeserializationContext(DeserializationConfig deserializationConfig) {
        this._config = deserializationConfig;
        this._featureFlags = deserializationConfig._featureFlags;
    }

    public abstract Calendar constructCalendar(Date var1);

    public JavaType constructType(Class<?> class_) {
        return this._config.constructType(class_);
    }

    public abstract Object findInjectableValue(Object var1, BeanProperty var2, Object var3);

    public abstract ArrayBuilders getArrayBuilders();

    public Base64Variant getBase64Variant() {
        return this._config.getBase64Variant();
    }

    public DeserializationConfig getConfig() {
        return this._config;
    }

    public DeserializerProvider getDeserializerProvider() {
        return null;
    }

    public final JsonNodeFactory getNodeFactory() {
        return this._config.getNodeFactory();
    }

    public abstract JsonParser getParser();

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public abstract boolean handleUnknownProperty(JsonParser var1, JsonDeserializer<?> var2, Object var3, String var4) throws IOException, JsonProcessingException;

    public abstract JsonMappingException instantiationException(Class<?> var1, String var2);

    public abstract JsonMappingException instantiationException(Class<?> var1, Throwable var2);

    public boolean isEnabled(DeserializationConfig.Feature feature) {
        return (this._featureFlags & feature.getMask()) != 0;
    }

    public abstract ObjectBuffer leaseObjectBuffer();

    public abstract JsonMappingException mappingException(Class<?> var1);

    public abstract JsonMappingException mappingException(Class<?> var1, JsonToken var2);

    public JsonMappingException mappingException(String string) {
        return JsonMappingException.from(this.getParser(), string);
    }

    public abstract Date parseDate(String var1) throws IllegalArgumentException;

    public abstract void returnObjectBuffer(ObjectBuffer var1);

    public abstract JsonMappingException unknownFieldException(Object var1, String var2);

    public abstract JsonMappingException unknownTypeException(JavaType var1, String var2);

    public abstract JsonMappingException weirdKeyException(Class<?> var1, String var2, String var3);

    public abstract JsonMappingException weirdNumberException(Class<?> var1, String var2);

    public abstract JsonMappingException weirdStringException(Class<?> var1, String var2);

    public abstract JsonMappingException wrongTokenException(JsonParser var1, JsonToken var2, String var3);
}

