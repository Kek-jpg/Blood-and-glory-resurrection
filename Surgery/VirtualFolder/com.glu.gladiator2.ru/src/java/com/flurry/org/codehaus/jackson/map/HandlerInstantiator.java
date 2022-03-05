/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;

public abstract class HandlerInstantiator {
    public abstract JsonDeserializer<?> deserializerInstance(DeserializationConfig var1, Annotated var2, Class<? extends JsonDeserializer<?>> var3);

    public abstract KeyDeserializer keyDeserializerInstance(DeserializationConfig var1, Annotated var2, Class<? extends KeyDeserializer> var3);

    public abstract JsonSerializer<?> serializerInstance(SerializationConfig var1, Annotated var2, Class<? extends JsonSerializer<?>> var3);

    public abstract TypeIdResolver typeIdResolverInstance(MapperConfig<?> var1, Annotated var2, Class<? extends TypeIdResolver> var3);

    public abstract TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> var1, Annotated var2, Class<? extends TypeResolverBuilder<?>> var3);

    public ValueInstantiator valueInstantiatorInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<? extends ValueInstantiator> class_) {
        return null;
    }
}

