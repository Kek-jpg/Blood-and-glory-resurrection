/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsArrayTypeDeserializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsArrayTypeSerializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsExternalTypeDeserializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsExternalTypeSerializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsPropertyTypeDeserializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsPropertyTypeSerializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsWrapperTypeDeserializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.AsWrapperTypeSerializer
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.ClassNameIdResolver
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.MinimalClassNameIdResolver
 *  com.flurry.org.codehaus.jackson.map.jsontype.impl.TypeNameIdResolver
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.map.jsontype.impl;

import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsArrayTypeDeserializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsArrayTypeSerializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsExternalTypeDeserializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsExternalTypeSerializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsPropertyTypeDeserializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsPropertyTypeSerializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsWrapperTypeDeserializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.AsWrapperTypeSerializer;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.ClassNameIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.MinimalClassNameIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.TypeNameIdResolver;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.Collection;

public class StdTypeResolverBuilder
implements TypeResolverBuilder<StdTypeResolverBuilder> {
    protected TypeIdResolver _customIdResolver;
    protected Class<?> _defaultImpl;
    protected JsonTypeInfo.Id _idType;
    protected JsonTypeInfo.As _includeAs;
    protected String _typeProperty;

    public static StdTypeResolverBuilder noTypeInfoBuilder() {
        return new StdTypeResolverBuilder().init(JsonTypeInfo.Id.NONE, null);
    }

    @Override
    public TypeDeserializer buildTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, Collection<NamedType> collection, BeanProperty beanProperty) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        TypeIdResolver typeIdResolver = this.idResolver(deserializationConfig, javaType, collection, false, true);
        switch (1.$SwitchMap$org$codehaus$jackson$annotate$JsonTypeInfo$As[this._includeAs.ordinal()]) {
            default: {
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + (Object)((Object)this._includeAs));
            }
            case 1: {
                return new AsArrayTypeDeserializer(javaType, typeIdResolver, beanProperty, this._defaultImpl);
            }
            case 2: {
                return new AsPropertyTypeDeserializer(javaType, typeIdResolver, beanProperty, this._defaultImpl, this._typeProperty);
            }
            case 3: {
                return new AsWrapperTypeDeserializer(javaType, typeIdResolver, beanProperty, this._defaultImpl);
            }
            case 4: 
        }
        return new AsExternalTypeDeserializer(javaType, typeIdResolver, beanProperty, this._defaultImpl, this._typeProperty);
    }

    @Override
    public TypeSerializer buildTypeSerializer(SerializationConfig serializationConfig, JavaType javaType, Collection<NamedType> collection, BeanProperty beanProperty) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        TypeIdResolver typeIdResolver = this.idResolver(serializationConfig, javaType, collection, true, false);
        switch (1.$SwitchMap$org$codehaus$jackson$annotate$JsonTypeInfo$As[this._includeAs.ordinal()]) {
            default: {
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + (Object)((Object)this._includeAs));
            }
            case 1: {
                return new AsArrayTypeSerializer(typeIdResolver, beanProperty);
            }
            case 2: {
                return new AsPropertyTypeSerializer(typeIdResolver, beanProperty, this._typeProperty);
            }
            case 3: {
                return new AsWrapperTypeSerializer(typeIdResolver, beanProperty);
            }
            case 4: 
        }
        return new AsExternalTypeSerializer(typeIdResolver, beanProperty, this._typeProperty);
    }

    @Override
    public StdTypeResolverBuilder defaultImpl(Class<?> class_) {
        this._defaultImpl = class_;
        return this;
    }

    @Override
    public Class<?> getDefaultImpl() {
        return this._defaultImpl;
    }

    public String getTypeProperty() {
        return this._typeProperty;
    }

    protected TypeIdResolver idResolver(MapperConfig<?> mapperConfig, JavaType javaType, Collection<NamedType> collection, boolean bl, boolean bl2) {
        if (this._customIdResolver != null) {
            return this._customIdResolver;
        }
        if (this._idType == null) {
            throw new IllegalStateException("Can not build, 'init()' not yet called");
        }
        switch (this._idType) {
            default: {
                throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + (Object)((Object)this._idType));
            }
            case CLASS: {
                return new ClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
            }
            case MINIMAL_CLASS: {
                return new MinimalClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
            }
            case NAME: {
                return TypeNameIdResolver.construct(mapperConfig, (JavaType)javaType, collection, (boolean)bl, (boolean)bl2);
            }
            case NONE: 
        }
        return null;
    }

    @Override
    public StdTypeResolverBuilder inclusion(JsonTypeInfo.As as) {
        if (as == null) {
            throw new IllegalArgumentException("includeAs can not be null");
        }
        this._includeAs = as;
        return this;
    }

    @Override
    public StdTypeResolverBuilder init(JsonTypeInfo.Id id, TypeIdResolver typeIdResolver) {
        if (id == null) {
            throw new IllegalArgumentException("idType can not be null");
        }
        this._idType = id;
        this._customIdResolver = typeIdResolver;
        this._typeProperty = id.getDefaultPropertyName();
        return this;
    }

    @Override
    public StdTypeResolverBuilder typeProperty(String string) {
        if (string == null || string.length() == 0) {
            string = this._idType.getDefaultPropertyName();
        }
        this._typeProperty = string;
        return this;
    }

}

