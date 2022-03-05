/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerFactory;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.type.JavaType;

public abstract class DeserializerProvider {
    protected DeserializerProvider() {
    }

    public abstract int cachedDeserializersCount();

    public abstract SerializedString findExpectedRootName(DeserializationConfig var1, JavaType var2) throws JsonMappingException;

    public abstract KeyDeserializer findKeyDeserializer(DeserializationConfig var1, JavaType var2, BeanProperty var3) throws JsonMappingException;

    public abstract JsonDeserializer<Object> findTypedValueDeserializer(DeserializationConfig var1, JavaType var2, BeanProperty var3) throws JsonMappingException;

    public abstract JsonDeserializer<Object> findValueDeserializer(DeserializationConfig var1, JavaType var2, BeanProperty var3) throws JsonMappingException;

    public abstract void flushCachedDeserializers();

    public abstract boolean hasValueDeserializerFor(DeserializationConfig var1, JavaType var2);

    public abstract JavaType mapAbstractType(DeserializationConfig var1, JavaType var2) throws JsonMappingException;

    public abstract DeserializerProvider withAbstractTypeResolver(AbstractTypeResolver var1);

    public abstract DeserializerProvider withAdditionalDeserializers(Deserializers var1);

    public abstract DeserializerProvider withAdditionalKeyDeserializers(KeyDeserializers var1);

    public abstract DeserializerProvider withDeserializerModifier(BeanDeserializerModifier var1);

    public abstract DeserializerProvider withFactory(DeserializerFactory var1);

    public abstract DeserializerProvider withValueInstantiators(ValueInstantiators var1);
}

