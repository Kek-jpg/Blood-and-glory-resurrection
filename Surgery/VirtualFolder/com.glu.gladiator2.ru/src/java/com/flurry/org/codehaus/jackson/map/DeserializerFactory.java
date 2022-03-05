/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Iterable
 *  java.lang.Object
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.type.JavaType;

public abstract class DeserializerFactory {
    protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];

    public abstract JsonDeserializer<?> createArrayDeserializer(DeserializationConfig var1, DeserializerProvider var2, ArrayType var3, BeanProperty var4) throws JsonMappingException;

    public abstract JsonDeserializer<Object> createBeanDeserializer(DeserializationConfig var1, DeserializerProvider var2, JavaType var3, BeanProperty var4) throws JsonMappingException;

    public abstract JsonDeserializer<?> createCollectionDeserializer(DeserializationConfig var1, DeserializerProvider var2, CollectionType var3, BeanProperty var4) throws JsonMappingException;

    public abstract JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationConfig var1, DeserializerProvider var2, CollectionLikeType var3, BeanProperty var4) throws JsonMappingException;

    public abstract JsonDeserializer<?> createEnumDeserializer(DeserializationConfig var1, DeserializerProvider var2, JavaType var3, BeanProperty var4) throws JsonMappingException;

    public KeyDeserializer createKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return null;
    }

    public abstract JsonDeserializer<?> createMapDeserializer(DeserializationConfig var1, DeserializerProvider var2, MapType var3, BeanProperty var4) throws JsonMappingException;

    public abstract JsonDeserializer<?> createMapLikeDeserializer(DeserializationConfig var1, DeserializerProvider var2, MapLikeType var3, BeanProperty var4) throws JsonMappingException;

    public abstract JsonDeserializer<?> createTreeDeserializer(DeserializationConfig var1, DeserializerProvider var2, JavaType var3, BeanProperty var4) throws JsonMappingException;

    public TypeDeserializer findTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return null;
    }

    public abstract ValueInstantiator findValueInstantiator(DeserializationConfig var1, BasicBeanDescription var2) throws JsonMappingException;

    public abstract Config getConfig();

    public abstract JavaType mapAbstractType(DeserializationConfig var1, JavaType var2) throws JsonMappingException;

    public final DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver) {
        return this.withConfig(this.getConfig().withAbstractTypeResolver(abstractTypeResolver));
    }

    public final DeserializerFactory withAdditionalDeserializers(Deserializers deserializers) {
        return this.withConfig(this.getConfig().withAdditionalDeserializers(deserializers));
    }

    public final DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers keyDeserializers) {
        return this.withConfig(this.getConfig().withAdditionalKeyDeserializers(keyDeserializers));
    }

    public abstract DeserializerFactory withConfig(Config var1);

    public final DeserializerFactory withDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
        return this.withConfig(this.getConfig().withDeserializerModifier(beanDeserializerModifier));
    }

    public final DeserializerFactory withValueInstantiators(ValueInstantiators valueInstantiators) {
        return this.withConfig(this.getConfig().withValueInstantiators(valueInstantiators));
    }

    public static abstract class Config {
        public abstract Iterable<AbstractTypeResolver> abstractTypeResolvers();

        public abstract Iterable<BeanDeserializerModifier> deserializerModifiers();

        public abstract Iterable<Deserializers> deserializers();

        public abstract boolean hasAbstractTypeResolvers();

        public abstract boolean hasDeserializerModifiers();

        public abstract boolean hasDeserializers();

        public abstract boolean hasKeyDeserializers();

        public abstract boolean hasValueInstantiators();

        public abstract Iterable<KeyDeserializers> keyDeserializers();

        public abstract Iterable<ValueInstantiators> valueInstantiators();

        public abstract Config withAbstractTypeResolver(AbstractTypeResolver var1);

        public abstract Config withAdditionalDeserializers(Deserializers var1);

        public abstract Config withAdditionalKeyDeserializers(KeyDeserializers var1);

        public abstract Config withDeserializerModifier(BeanDeserializerModifier var1);

        public abstract Config withValueInstantiators(ValueInstantiators var1);
    }

}

