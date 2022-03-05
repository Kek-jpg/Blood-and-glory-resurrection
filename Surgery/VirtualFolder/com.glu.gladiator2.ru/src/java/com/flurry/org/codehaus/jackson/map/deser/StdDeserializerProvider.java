/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.HashMap
 *  java.util.concurrent.ConcurrentHashMap
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.ContextualDeserializer;
import com.flurry.org.codehaus.jackson.map.ContextualKeyDeserializer;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerFactory;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.ResolvableDeserializer;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerFactory;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.RootNameLookup;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class StdDeserializerProvider
extends DeserializerProvider {
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _cachedDeserializers = new ConcurrentHashMap(64, 0.75f, 2);
    protected DeserializerFactory _factory;
    protected final HashMap<JavaType, JsonDeserializer<Object>> _incompleteDeserializers = new HashMap(8);
    protected final RootNameLookup _rootNames;

    public StdDeserializerProvider() {
        this(BeanDeserializerFactory.instance);
    }

    public StdDeserializerProvider(DeserializerFactory deserializerFactory) {
        this._factory = deserializerFactory;
        this._rootNames = new RootNameLookup();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected JsonDeserializer<Object> _createAndCache2(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        Boolean bl;
        AnnotationIntrospector annotationIntrospector;
        JsonDeserializer<Object> jsonDeserializer;
        try {
            JsonDeserializer<Object> jsonDeserializer2;
            jsonDeserializer = jsonDeserializer2 = this._createDeserializer(deserializationConfig, javaType, beanProperty);
            if (jsonDeserializer == null) {
                return null;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new JsonMappingException(illegalArgumentException.getMessage(), null, illegalArgumentException);
        }
        boolean bl2 = jsonDeserializer instanceof ResolvableDeserializer;
        boolean bl3 = jsonDeserializer.getClass() == BeanDeserializer.class;
        if (!bl3 && deserializationConfig.isEnabled(DeserializationConfig.Feature.USE_ANNOTATIONS) && (bl = (annotationIntrospector = deserializationConfig.getAnnotationIntrospector()).findCachability(AnnotatedClass.construct(jsonDeserializer.getClass(), annotationIntrospector, null))) != null) {
            bl3 = bl;
        }
        if (bl2) {
            this._incompleteDeserializers.put((Object)javaType, jsonDeserializer);
            this._resolveDeserializer(deserializationConfig, (ResolvableDeserializer)((Object)jsonDeserializer));
            this._incompleteDeserializers.remove((Object)javaType);
        }
        if (!bl3) return jsonDeserializer;
        this._cachedDeserializers.put((Object)javaType, jsonDeserializer);
        return jsonDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected JsonDeserializer<Object> _createAndCacheValueDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        HashMap<JavaType, JsonDeserializer<Object>> hashMap;
        HashMap<JavaType, JsonDeserializer<Object>> hashMap2 = hashMap = this._incompleteDeserializers;
        synchronized (hashMap2) {
            JsonDeserializer jsonDeserializer;
            JsonDeserializer<Object> jsonDeserializer2 = this._findCachedDeserializer(javaType);
            if (jsonDeserializer2 != null) {
                return jsonDeserializer2;
            }
            int n = this._incompleteDeserializers.size();
            if (n > 0 && (jsonDeserializer = (JsonDeserializer)this._incompleteDeserializers.get((Object)javaType)) != null) {
                return jsonDeserializer;
            }
            try {
                JsonDeserializer<Object> jsonDeserializer3 = this._createAndCache2(deserializationConfig, javaType, beanProperty);
                return jsonDeserializer3;
            }
            finally {
                if (n == 0 && this._incompleteDeserializers.size() > 0) {
                    this._incompleteDeserializers.clear();
                }
            }
        }
    }

    protected JsonDeserializer<Object> _createDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        if (javaType.isEnumType()) {
            return this._factory.createEnumDeserializer(deserializationConfig, (DeserializerProvider)this, javaType, beanProperty);
        }
        if (javaType.isContainerType()) {
            if (javaType.isArrayType()) {
                return this._factory.createArrayDeserializer(deserializationConfig, (DeserializerProvider)this, (ArrayType)javaType, beanProperty);
            }
            if (javaType.isMapLikeType()) {
                MapLikeType mapLikeType = (MapLikeType)javaType;
                if (mapLikeType.isTrueMapType()) {
                    return this._factory.createMapDeserializer(deserializationConfig, (DeserializerProvider)this, (MapType)mapLikeType, beanProperty);
                }
                return this._factory.createMapLikeDeserializer(deserializationConfig, (DeserializerProvider)this, mapLikeType, beanProperty);
            }
            if (javaType.isCollectionLikeType()) {
                CollectionLikeType collectionLikeType = (CollectionLikeType)javaType;
                if (collectionLikeType.isTrueCollectionType()) {
                    return this._factory.createCollectionDeserializer(deserializationConfig, (DeserializerProvider)this, (CollectionType)collectionLikeType, beanProperty);
                }
                return this._factory.createCollectionLikeDeserializer(deserializationConfig, (DeserializerProvider)this, collectionLikeType, beanProperty);
            }
        }
        if (JsonNode.class.isAssignableFrom(javaType.getRawClass())) {
            return this._factory.createTreeDeserializer(deserializationConfig, (DeserializerProvider)this, javaType, beanProperty);
        }
        return this._factory.createBeanDeserializer(deserializationConfig, (DeserializerProvider)this, javaType, beanProperty);
    }

    protected JsonDeserializer<Object> _findCachedDeserializer(JavaType javaType) {
        if (javaType == null) {
            throw new IllegalArgumentException();
        }
        return (JsonDeserializer)this._cachedDeserializers.get((Object)javaType);
    }

    protected KeyDeserializer _handleUnknownKeyDeserializer(JavaType javaType) throws JsonMappingException {
        throw new JsonMappingException("Can not find a (Map) Key deserializer for type " + javaType);
    }

    protected JsonDeserializer<Object> _handleUnknownValueDeserializer(JavaType javaType) throws JsonMappingException {
        if (!ClassUtil.isConcrete(javaType.getRawClass())) {
            throw new JsonMappingException("Can not find a Value deserializer for abstract type " + javaType);
        }
        throw new JsonMappingException("Can not find a Value deserializer for type " + javaType);
    }

    protected void _resolveDeserializer(DeserializationConfig deserializationConfig, ResolvableDeserializer resolvableDeserializer) throws JsonMappingException {
        resolvableDeserializer.resolve(deserializationConfig, (DeserializerProvider)this);
    }

    @Override
    public int cachedDeserializersCount() {
        return this._cachedDeserializers.size();
    }

    @Override
    public SerializedString findExpectedRootName(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        return this._rootNames.findRootName(javaType, deserializationConfig);
    }

    @Override
    public KeyDeserializer findKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        KeyDeserializer keyDeserializer = this._factory.createKeyDeserializer(deserializationConfig, javaType, beanProperty);
        if (keyDeserializer instanceof ContextualKeyDeserializer) {
            keyDeserializer = ((ContextualKeyDeserializer)((Object)keyDeserializer)).createContextual(deserializationConfig, beanProperty);
        }
        if (keyDeserializer == null) {
            keyDeserializer = this._handleUnknownKeyDeserializer(javaType);
        }
        return keyDeserializer;
    }

    @Override
    public JsonDeserializer<Object> findTypedValueDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        WrappedDeserializer wrappedDeserializer = this.findValueDeserializer(deserializationConfig, javaType, beanProperty);
        TypeDeserializer typeDeserializer = this._factory.findTypeDeserializer(deserializationConfig, javaType, beanProperty);
        if (typeDeserializer != null) {
            wrappedDeserializer = new WrappedDeserializer(typeDeserializer, wrappedDeserializer);
        }
        return wrappedDeserializer;
    }

    @Override
    public JsonDeserializer<Object> findValueDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer = this._findCachedDeserializer(javaType);
        if (jsonDeserializer != null) {
            if (jsonDeserializer instanceof ContextualDeserializer) {
                jsonDeserializer = ((ContextualDeserializer)((Object)jsonDeserializer)).createContextual(deserializationConfig, beanProperty);
            }
            return jsonDeserializer;
        }
        JsonDeserializer<Object> jsonDeserializer2 = this._createAndCacheValueDeserializer(deserializationConfig, javaType, beanProperty);
        if (jsonDeserializer2 == null) {
            jsonDeserializer2 = this._handleUnknownValueDeserializer(javaType);
        }
        if (jsonDeserializer2 instanceof ContextualDeserializer) {
            jsonDeserializer2 = ((ContextualDeserializer)((Object)jsonDeserializer2)).createContextual(deserializationConfig, beanProperty);
        }
        return jsonDeserializer2;
    }

    @Override
    public void flushCachedDeserializers() {
        this._cachedDeserializers.clear();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean hasValueDeserializerFor(DeserializationConfig deserializationConfig, JavaType javaType) {
        JsonDeserializer<Object> jsonDeserializer = this._findCachedDeserializer(javaType);
        if (jsonDeserializer == null) {
            JsonDeserializer<Object> jsonDeserializer2 = this._createAndCacheValueDeserializer(deserializationConfig, javaType, null);
            jsonDeserializer = jsonDeserializer2;
        }
        boolean bl = false;
        if (jsonDeserializer == null) return bl;
        return true;
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public JavaType mapAbstractType(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        return this._factory.mapAbstractType(deserializationConfig, javaType);
    }

    @Override
    public DeserializerProvider withAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver) {
        return this.withFactory(this._factory.withAbstractTypeResolver(abstractTypeResolver));
    }

    @Override
    public DeserializerProvider withAdditionalDeserializers(Deserializers deserializers) {
        return this.withFactory(this._factory.withAdditionalDeserializers(deserializers));
    }

    @Override
    public DeserializerProvider withAdditionalKeyDeserializers(KeyDeserializers keyDeserializers) {
        return this.withFactory(this._factory.withAdditionalKeyDeserializers(keyDeserializers));
    }

    @Override
    public DeserializerProvider withDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
        return this.withFactory(this._factory.withDeserializerModifier(beanDeserializerModifier));
    }

    @Override
    public StdDeserializerProvider withFactory(DeserializerFactory deserializerFactory) {
        if (this.getClass() != StdDeserializerProvider.class) {
            throw new IllegalStateException("DeserializerProvider of type " + this.getClass().getName() + " does not override 'withFactory()' method");
        }
        return new StdDeserializerProvider(deserializerFactory);
    }

    @Override
    public DeserializerProvider withValueInstantiators(ValueInstantiators valueInstantiators) {
        return this.withFactory(this._factory.withValueInstantiators(valueInstantiators));
    }

    protected static final class WrappedDeserializer
    extends JsonDeserializer<Object> {
        final JsonDeserializer<Object> _deserializer;
        final TypeDeserializer _typeDeserializer;

        public WrappedDeserializer(TypeDeserializer typeDeserializer, JsonDeserializer<Object> jsonDeserializer) {
            this._typeDeserializer = typeDeserializer;
            this._deserializer = jsonDeserializer;
        }

        @Override
        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._deserializer.deserializeWithType(jsonParser, deserializationContext, this._typeDeserializer);
        }

        @Override
        public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            throw new IllegalStateException("Type-wrapped deserializer's deserializeWithType should never get called");
        }
    }

}

