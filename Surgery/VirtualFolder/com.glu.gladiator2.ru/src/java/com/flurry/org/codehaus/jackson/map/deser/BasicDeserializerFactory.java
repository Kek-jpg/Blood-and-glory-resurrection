/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.EnumMap
 *  java.util.EnumSet
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.LinkedHashMap
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Map
 *  java.util.Queue
 *  java.util.Set
 *  java.util.SortedMap
 *  java.util.SortedSet
 *  java.util.TreeMap
 *  java.util.TreeSet
 *  java.util.concurrent.ConcurrentHashMap
 *  java.util.concurrent.ConcurrentMap
 *  java.util.concurrent.atomic.AtomicReference
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.ContextualDeserializer;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializerFactory;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.StdDeserializers;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.std.AtomicReferenceDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.CollectionDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.EnumDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.EnumMapDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.EnumSetDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.JsonNodeDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.MapDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.ObjectArrayDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.std.PrimitiveArrayDeserializers;
import com.flurry.org.codehaus.jackson.map.deser.std.StdKeyDeserializers;
import com.flurry.org.codehaus.jackson.map.deser.std.StringCollectionDeserializer;
import com.flurry.org.codehaus.jackson.map.ext.OptionalHandlerFactory;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.ClassKey;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.EnumResolver;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BasicDeserializerFactory
extends DeserializerFactory {
    protected static final HashMap<JavaType, JsonDeserializer<Object>> _arrayDeserializers;
    static final HashMap<String, Class<? extends Collection>> _collectionFallbacks;
    static final HashMap<JavaType, KeyDeserializer> _keyDeserializers;
    static final HashMap<String, Class<? extends Map>> _mapFallbacks;
    static final HashMap<ClassKey, JsonDeserializer<Object>> _simpleDeserializers;
    protected OptionalHandlerFactory optionalHandlers = OptionalHandlerFactory.instance;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        _simpleDeserializers = StdDeserializers.constructAll();
        _keyDeserializers = StdKeyDeserializers.constructAll();
        _mapFallbacks = new HashMap();
        _mapFallbacks.put((Object)Map.class.getName(), LinkedHashMap.class);
        _mapFallbacks.put((Object)ConcurrentMap.class.getName(), ConcurrentHashMap.class);
        _mapFallbacks.put((Object)SortedMap.class.getName(), TreeMap.class);
        _mapFallbacks.put((Object)"java.util.NavigableMap", TreeMap.class);
        try {
            Class class_ = Class.forName((String)"java.util.ConcurrentNavigableMap");
            Class class_2 = Class.forName((String)"java.util.ConcurrentSkipListMap");
            _mapFallbacks.put((Object)class_.getName(), (Object)class_2);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        _collectionFallbacks = new HashMap();
        _collectionFallbacks.put((Object)Collection.class.getName(), ArrayList.class);
        _collectionFallbacks.put((Object)List.class.getName(), ArrayList.class);
        _collectionFallbacks.put((Object)Set.class.getName(), HashSet.class);
        _collectionFallbacks.put((Object)SortedSet.class.getName(), TreeSet.class);
        _collectionFallbacks.put((Object)Queue.class.getName(), LinkedList.class);
        _collectionFallbacks.put((Object)"java.util.Deque", LinkedList.class);
        _collectionFallbacks.put((Object)"java.util.NavigableSet", TreeSet.class);
        _arrayDeserializers = PrimitiveArrayDeserializers.getAll();
    }

    protected BasicDeserializerFactory() {
    }

    /*
     * Enabled aggressive block sorting
     */
    JsonDeserializer<Object> _constructDeserializer(DeserializationConfig deserializationConfig, Annotated annotated, BeanProperty beanProperty, Object object) throws JsonMappingException {
        void var6_7;
        if (object instanceof JsonDeserializer) {
            JsonDeserializer jsonDeserializer = (JsonDeserializer)object;
            if (!(jsonDeserializer instanceof ContextualDeserializer)) return var6_7;
            {
                JsonDeserializer jsonDeserializer2 = ((ContextualDeserializer)((Object)jsonDeserializer)).createContextual(deserializationConfig, beanProperty);
                return var6_7;
            }
        } else {
            if (!(object instanceof Class)) {
                throw new IllegalStateException("AnnotationIntrospector returned deserializer definition of type " + object.getClass().getName() + "; expected type JsonDeserializer or Class<JsonDeserializer> instead");
            }
            Class class_ = (Class)object;
            if (!JsonDeserializer.class.isAssignableFrom(class_)) {
                throw new IllegalStateException("AnnotationIntrospector returned Class " + class_.getName() + "; expected Class<JsonDeserializer>");
            }
            JsonDeserializer<Object> jsonDeserializer = deserializationConfig.deserializerInstance(annotated, class_);
            if (!(jsonDeserializer instanceof ContextualDeserializer)) return var6_7;
            return ((ContextualDeserializer)((Object)jsonDeserializer)).createContextual(deserializationConfig, beanProperty);
        }
    }

    protected abstract JsonDeserializer<?> _findCustomArrayDeserializer(ArrayType var1, DeserializationConfig var2, DeserializerProvider var3, BeanProperty var4, TypeDeserializer var5, JsonDeserializer<?> var6) throws JsonMappingException;

    protected abstract JsonDeserializer<?> _findCustomCollectionDeserializer(CollectionType var1, DeserializationConfig var2, DeserializerProvider var3, BasicBeanDescription var4, BeanProperty var5, TypeDeserializer var6, JsonDeserializer<?> var7) throws JsonMappingException;

    protected abstract JsonDeserializer<?> _findCustomCollectionLikeDeserializer(CollectionLikeType var1, DeserializationConfig var2, DeserializerProvider var3, BasicBeanDescription var4, BeanProperty var5, TypeDeserializer var6, JsonDeserializer<?> var7) throws JsonMappingException;

    protected abstract JsonDeserializer<?> _findCustomEnumDeserializer(Class<?> var1, DeserializationConfig var2, BasicBeanDescription var3, BeanProperty var4) throws JsonMappingException;

    protected abstract JsonDeserializer<?> _findCustomMapDeserializer(MapType var1, DeserializationConfig var2, DeserializerProvider var3, BasicBeanDescription var4, BeanProperty var5, KeyDeserializer var6, TypeDeserializer var7, JsonDeserializer<?> var8) throws JsonMappingException;

    protected abstract JsonDeserializer<?> _findCustomMapLikeDeserializer(MapLikeType var1, DeserializationConfig var2, DeserializerProvider var3, BasicBeanDescription var4, BeanProperty var5, KeyDeserializer var6, TypeDeserializer var7, JsonDeserializer<?> var8) throws JsonMappingException;

    protected abstract JsonDeserializer<?> _findCustomTreeNodeDeserializer(Class<? extends JsonNode> var1, DeserializationConfig var2, BeanProperty var3) throws JsonMappingException;

    protected EnumResolver<?> constructEnumResolver(Class<?> class_, DeserializationConfig deserializationConfig) {
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING)) {
            return EnumResolver.constructUnsafeUsingToString(class_);
        }
        return EnumResolver.constructUnsafe(class_, deserializationConfig.getAnnotationIntrospector());
    }

    @Override
    public JsonDeserializer<?> createArrayDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, ArrayType arrayType, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<?> jsonDeserializer;
        TypeDeserializer typeDeserializer;
        JavaType javaType = arrayType.getContentType();
        JsonDeserializer<Object> jsonDeserializer2 = (JsonDeserializer<Object>)javaType.getValueHandler();
        if (jsonDeserializer2 == null) {
            JsonDeserializer<?> jsonDeserializer3 = (JsonDeserializer<?>)_arrayDeserializers.get((Object)javaType);
            if (jsonDeserializer3 != null) {
                JsonDeserializer<?> jsonDeserializer4 = this._findCustomArrayDeserializer(arrayType, deserializationConfig, deserializerProvider, beanProperty, null, null);
                if (jsonDeserializer4 != null) {
                    jsonDeserializer3 = jsonDeserializer4;
                }
                return jsonDeserializer3;
            }
            if (javaType.isPrimitive()) {
                throw new IllegalArgumentException("Internal error: primitive type (" + arrayType + ") passed, no array deserializer found");
            }
        }
        if ((typeDeserializer = (TypeDeserializer)javaType.getTypeHandler()) == null) {
            typeDeserializer = this.findTypeDeserializer(deserializationConfig, javaType, beanProperty);
        }
        if ((jsonDeserializer = this._findCustomArrayDeserializer(arrayType, deserializationConfig, deserializerProvider, beanProperty, typeDeserializer, jsonDeserializer2)) != null) {
            return jsonDeserializer;
        }
        if (jsonDeserializer2 == null) {
            jsonDeserializer2 = deserializerProvider.findValueDeserializer(deserializationConfig, javaType, beanProperty);
        }
        return new ObjectArrayDeserializer(arrayType, jsonDeserializer2, typeDeserializer);
    }

    @Override
    public JsonDeserializer<?> createCollectionDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, CollectionType collectionType, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<?> jsonDeserializer;
        CollectionType collectionType2 = (CollectionType)this.mapAbstractType(deserializationConfig, collectionType);
        Class<?> class_ = collectionType2.getRawClass();
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectForCreation(collectionType2);
        JsonDeserializer<Object> jsonDeserializer2 = this.findDeserializerFromAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
        if (jsonDeserializer2 != null) {
            return jsonDeserializer2;
        }
        CollectionType collectionType3 = this.modifyTypeByAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), collectionType2, null);
        JavaType javaType = collectionType3.getContentType();
        JsonDeserializer<Object> jsonDeserializer3 = (JsonDeserializer<Object>)javaType.getValueHandler();
        TypeDeserializer typeDeserializer = (TypeDeserializer)javaType.getTypeHandler();
        if (typeDeserializer == null) {
            typeDeserializer = this.findTypeDeserializer(deserializationConfig, javaType, beanProperty);
        }
        if ((jsonDeserializer = this._findCustomCollectionDeserializer(collectionType3, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, typeDeserializer, jsonDeserializer3)) != null) {
            return jsonDeserializer;
        }
        if (jsonDeserializer3 == null) {
            if (EnumSet.class.isAssignableFrom(class_)) {
                return new EnumSetDeserializer(javaType.getRawClass(), this.createEnumDeserializer(deserializationConfig, deserializerProvider, javaType, beanProperty));
            }
            jsonDeserializer3 = deserializerProvider.findValueDeserializer(deserializationConfig, javaType, beanProperty);
        }
        if (collectionType3.isInterface() || collectionType3.isAbstract()) {
            Class class_2 = (Class)_collectionFallbacks.get((Object)class_.getName());
            if (class_2 == null) {
                throw new IllegalArgumentException("Can not find a deserializer for non-concrete Collection type " + collectionType3);
            }
            collectionType3 = (CollectionType)deserializationConfig.constructSpecializedType(collectionType3, class_2);
            basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectForCreation(collectionType3);
        }
        ValueInstantiator valueInstantiator = this.findValueInstantiator(deserializationConfig, basicBeanDescription);
        if (javaType.getRawClass() == String.class) {
            return new StringCollectionDeserializer(collectionType3, jsonDeserializer3, valueInstantiator);
        }
        return new CollectionDeserializer((JavaType)collectionType3, jsonDeserializer3, typeDeserializer, valueInstantiator);
    }

    @Override
    public JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, CollectionLikeType collectionLikeType, BeanProperty beanProperty) throws JsonMappingException {
        CollectionLikeType collectionLikeType2 = (CollectionLikeType)this.mapAbstractType(deserializationConfig, collectionLikeType);
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectClassAnnotations(collectionLikeType2.getRawClass());
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        CollectionLikeType collectionLikeType3 = this.modifyTypeByAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), collectionLikeType2, null);
        JavaType javaType = collectionLikeType3.getContentType();
        JsonDeserializer jsonDeserializer2 = (JsonDeserializer)javaType.getValueHandler();
        TypeDeserializer typeDeserializer = (TypeDeserializer)javaType.getTypeHandler();
        if (typeDeserializer == null) {
            typeDeserializer = this.findTypeDeserializer(deserializationConfig, javaType, beanProperty);
        }
        return this._findCustomCollectionLikeDeserializer(collectionLikeType3, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, typeDeserializer, jsonDeserializer2);
    }

    @Override
    public JsonDeserializer<?> createEnumDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectForCreation(javaType);
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        Class<?> class_ = javaType.getRawClass();
        JsonDeserializer<?> jsonDeserializer2 = this._findCustomEnumDeserializer(class_, deserializationConfig, basicBeanDescription, beanProperty);
        if (jsonDeserializer2 != null) {
            return jsonDeserializer2;
        }
        for (AnnotatedMethod annotatedMethod : basicBeanDescription.getFactoryMethods()) {
            if (!deserializationConfig.getAnnotationIntrospector().hasCreatorAnnotation(annotatedMethod)) continue;
            if (annotatedMethod.getParameterCount() == 1 && annotatedMethod.getRawType().isAssignableFrom(class_)) {
                return EnumDeserializer.deserializerForCreator(deserializationConfig, class_, annotatedMethod);
            }
            throw new IllegalArgumentException("Unsuitable method (" + annotatedMethod + ") decorated with @JsonCreator (for Enum type " + class_.getName() + ")");
        }
        return new EnumDeserializer(this.constructEnumResolver(class_, deserializationConfig));
    }

    @Override
    public JsonDeserializer<?> createMapDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, MapType mapType, BeanProperty beanProperty) throws JsonMappingException {
        TypeDeserializer typeDeserializer;
        Class<?> class_;
        JsonDeserializer<?> jsonDeserializer;
        MapType mapType2 = (MapType)this.mapAbstractType(deserializationConfig, mapType);
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectForCreation(mapType2);
        JsonDeserializer<Object> jsonDeserializer2 = this.findDeserializerFromAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
        if (jsonDeserializer2 != null) {
            return jsonDeserializer2;
        }
        MapType mapType3 = this.modifyTypeByAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), mapType2, null);
        JavaType javaType = mapType3.getKeyType();
        JavaType javaType2 = mapType3.getContentType();
        JsonDeserializer<Object> jsonDeserializer3 = (JsonDeserializer<Object>)javaType2.getValueHandler();
        KeyDeserializer keyDeserializer = (KeyDeserializer)javaType.getValueHandler();
        if (keyDeserializer == null) {
            keyDeserializer = deserializerProvider.findKeyDeserializer(deserializationConfig, javaType, beanProperty);
        }
        if ((typeDeserializer = (TypeDeserializer)javaType2.getTypeHandler()) == null) {
            typeDeserializer = this.findTypeDeserializer(deserializationConfig, javaType2, beanProperty);
        }
        if ((jsonDeserializer = this._findCustomMapDeserializer(mapType3, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, keyDeserializer, typeDeserializer, jsonDeserializer3)) != null) {
            return jsonDeserializer;
        }
        if (jsonDeserializer3 == null) {
            jsonDeserializer3 = deserializerProvider.findValueDeserializer(deserializationConfig, javaType2, beanProperty);
        }
        if (EnumMap.class.isAssignableFrom(class_ = mapType3.getRawClass())) {
            Class<?> class_2 = javaType.getRawClass();
            if (class_2 == null || !class_2.isEnum()) {
                throw new IllegalArgumentException("Can not construct EnumMap; generic (key) type not available");
            }
            EnumMapDeserializer enumMapDeserializer = new EnumMapDeserializer(javaType.getRawClass(), this.createEnumDeserializer(deserializationConfig, deserializerProvider, javaType, beanProperty), jsonDeserializer3);
            return enumMapDeserializer;
        }
        if (mapType3.isInterface() || mapType3.isAbstract()) {
            Class class_3 = (Class)_mapFallbacks.get((Object)class_.getName());
            if (class_3 == null) {
                throw new IllegalArgumentException("Can not find a deserializer for non-concrete Map type " + mapType3);
            }
            mapType3 = (MapType)deserializationConfig.constructSpecializedType(mapType3, class_3);
            basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectForCreation(mapType3);
        }
        ValueInstantiator valueInstantiator = this.findValueInstantiator(deserializationConfig, basicBeanDescription);
        MapDeserializer mapDeserializer = new MapDeserializer((JavaType)mapType3, valueInstantiator, keyDeserializer, jsonDeserializer3, typeDeserializer);
        mapDeserializer.setIgnorableProperties(deserializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(basicBeanDescription.getClassInfo()));
        return mapDeserializer;
    }

    @Override
    public JsonDeserializer<?> createMapLikeDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, MapLikeType mapLikeType, BeanProperty beanProperty) throws JsonMappingException {
        TypeDeserializer typeDeserializer;
        MapLikeType mapLikeType2 = (MapLikeType)this.mapAbstractType(deserializationConfig, mapLikeType);
        BasicBeanDescription basicBeanDescription = (BasicBeanDescription)deserializationConfig.introspectForCreation(mapLikeType2);
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        MapLikeType mapLikeType3 = this.modifyTypeByAnnotation(deserializationConfig, basicBeanDescription.getClassInfo(), mapLikeType2, null);
        JavaType javaType = mapLikeType3.getKeyType();
        JavaType javaType2 = mapLikeType3.getContentType();
        JsonDeserializer jsonDeserializer2 = (JsonDeserializer)javaType2.getValueHandler();
        KeyDeserializer keyDeserializer = (KeyDeserializer)javaType.getValueHandler();
        if (keyDeserializer == null) {
            keyDeserializer = deserializerProvider.findKeyDeserializer(deserializationConfig, javaType, beanProperty);
        }
        if ((typeDeserializer = (TypeDeserializer)javaType2.getTypeHandler()) == null) {
            typeDeserializer = this.findTypeDeserializer(deserializationConfig, javaType2, beanProperty);
        }
        return this._findCustomMapLikeDeserializer(mapLikeType3, deserializationConfig, deserializerProvider, basicBeanDescription, beanProperty, keyDeserializer, typeDeserializer, jsonDeserializer2);
    }

    @Override
    public JsonDeserializer<?> createTreeDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        Class<?> class_ = javaType.getRawClass();
        JsonDeserializer<?> jsonDeserializer = this._findCustomTreeNodeDeserializer(class_, deserializationConfig, beanProperty);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        return JsonNodeDeserializer.getDeserializer(class_);
    }

    protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationConfig deserializationConfig, Annotated annotated, BeanProperty beanProperty) throws JsonMappingException {
        Object object = deserializationConfig.getAnnotationIntrospector().findDeserializer(annotated);
        if (object != null) {
            return this._constructDeserializer(deserializationConfig, annotated, beanProperty, object);
        }
        return null;
    }

    public TypeDeserializer findPropertyContentTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, AnnotatedMember annotatedMember, BeanProperty beanProperty) throws JsonMappingException {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> typeResolverBuilder = annotationIntrospector.findPropertyContentTypeResolver(deserializationConfig, annotatedMember, javaType);
        JavaType javaType2 = javaType.getContentType();
        if (typeResolverBuilder == null) {
            return this.findTypeDeserializer(deserializationConfig, javaType2, beanProperty);
        }
        return typeResolverBuilder.buildTypeDeserializer(deserializationConfig, javaType2, deserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, deserializationConfig, annotationIntrospector), beanProperty);
    }

    public TypeDeserializer findPropertyTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, AnnotatedMember annotatedMember, BeanProperty beanProperty) throws JsonMappingException {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> typeResolverBuilder = annotationIntrospector.findPropertyTypeResolver(deserializationConfig, annotatedMember, javaType);
        if (typeResolverBuilder == null) {
            return this.findTypeDeserializer(deserializationConfig, javaType, beanProperty);
        }
        return typeResolverBuilder.buildTypeDeserializer(deserializationConfig, javaType, deserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, deserializationConfig, annotationIntrospector), beanProperty);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonDeserializer<Object> findStdBeanDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        Class<?> class_ = javaType.getRawClass();
        JsonDeserializer jsonDeserializer = (JsonDeserializer)_simpleDeserializers.get((Object)new ClassKey(class_));
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        if (AtomicReference.class.isAssignableFrom(class_)) {
            JavaType javaType2;
            JavaType[] arrjavaType = deserializationConfig.getTypeFactory().findTypeParameters(javaType, AtomicReference.class);
            if (arrjavaType == null || arrjavaType.length < 1) {
                javaType2 = TypeFactory.unknownType();
                do {
                    return new AtomicReferenceDeserializer(javaType2, beanProperty);
                    break;
                } while (true);
            }
            javaType2 = arrjavaType[0];
            return new AtomicReferenceDeserializer(javaType2, beanProperty);
        }
        JsonDeserializer<Object> jsonDeserializer2 = this.optionalHandlers.findDeserializer(javaType, deserializationConfig, deserializerProvider);
        if (jsonDeserializer2 == null) return null;
        return jsonDeserializer2;
    }

    @Override
    public TypeDeserializer findTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        Collection<NamedType> collection;
        JavaType javaType2;
        AnnotatedClass annotatedClass = ((BasicBeanDescription)deserializationConfig.introspectClassAnnotations(javaType.getRawClass())).getClassInfo();
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> typeResolverBuilder = annotationIntrospector.findTypeResolver(deserializationConfig, annotatedClass, javaType);
        if (typeResolverBuilder == null) {
            typeResolverBuilder = deserializationConfig.getDefaultTyper(javaType);
            collection = null;
            if (typeResolverBuilder == null) {
                return null;
            }
        } else {
            collection = deserializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedClass, deserializationConfig, annotationIntrospector);
        }
        if (typeResolverBuilder.getDefaultImpl() == null && javaType.isAbstract() && (javaType2 = this.mapAbstractType(deserializationConfig, javaType)) != null && javaType2.getRawClass() != javaType.getRawClass()) {
            typeResolverBuilder = typeResolverBuilder.defaultImpl(javaType2.getRawClass());
        }
        return typeResolverBuilder.buildTypeDeserializer(deserializationConfig, javaType, collection, beanProperty);
    }

    @Override
    public abstract ValueInstantiator findValueInstantiator(DeserializationConfig var1, BasicBeanDescription var2) throws JsonMappingException;

    @Override
    public abstract JavaType mapAbstractType(DeserializationConfig var1, JavaType var2) throws JsonMappingException;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected <T extends JavaType> T modifyTypeByAnnotation(DeserializationConfig deserializationConfig, Annotated annotated, T object, String string) throws JsonMappingException {
        AnnotationIntrospector annotationIntrospector;
        JavaType javaType;
        Class<? extends KeyDeserializer> class_3;
        Class<?> class_;
        block10 : {
            Class<?> class_4;
            block11 : {
                annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
                Class<?> class_5 = annotationIntrospector.findDeserializationType(annotated, (JavaType)object, string);
                if (class_5 != null) {
                    JavaType javaType2 = ((JavaType)object).narrowBy(class_5);
                    object = javaType2;
                }
                if (!((JavaType)object).isContainerType()) return (T)object;
                class_4 = annotationIntrospector.findDeserializationKeyType(annotated, ((JavaType)object).getKeyType(), string);
                if (class_4 == null) break block10;
                if (!(object instanceof MapLikeType)) {
                    throw new JsonMappingException("Illegal key-type annotation: type " + object + " is not a Map(-like) type");
                }
                break block11;
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new JsonMappingException("Failed to narrow type " + object + " with concrete-type annotation (value " + class_5.getName() + "), method '" + annotated.getName() + "': " + illegalArgumentException.getMessage(), null, illegalArgumentException);
                }
            }
            try {
                JavaType javaType3 = ((MapLikeType)object).narrowKey(class_4);
                object = javaType3;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new JsonMappingException("Failed to narrow key type " + object + " with key-type annotation (" + class_4.getName() + "): " + illegalArgumentException.getMessage(), null, illegalArgumentException);
            }
        }
        if ((javaType = ((JavaType)object).getKeyType()) != null && javaType.getValueHandler() == null && (class_3 = annotationIntrospector.findKeyDeserializer(annotated)) != null && class_3 != KeyDeserializer.None.class) {
            javaType.setValueHandler(deserializationConfig.keyDeserializerInstance(annotated, class_3));
        }
        if ((class_ = annotationIntrospector.findDeserializationContentType(annotated, ((JavaType)object).getContentType(), string)) != null) {
            JavaType javaType4 = ((JavaType)object).narrowContentsBy(class_);
            object = javaType4;
        }
        if (((JavaType)object).getContentType().getValueHandler() != null) return (T)object;
        Class<? extends JsonDeserializer<?>> class_2 = annotationIntrospector.findContentDeserializer(annotated);
        if (class_2 == null) return (T)object;
        if (class_2 == JsonDeserializer.None.class) return (T)object;
        JsonDeserializer<Object> jsonDeserializer = deserializationConfig.deserializerInstance(annotated, class_2);
        ((JavaType)object).getContentType().setValueHandler(jsonDeserializer);
        return (T)object;
        catch (IllegalArgumentException illegalArgumentException) {
            throw new JsonMappingException("Failed to narrow content type " + object + " with content-type annotation (" + class_.getName() + "): " + illegalArgumentException.getMessage(), null, illegalArgumentException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType resolveType(DeserializationConfig deserializationConfig, BasicBeanDescription basicBeanDescription, JavaType javaType, AnnotatedMember annotatedMember, BeanProperty beanProperty) throws JsonMappingException {
        if (javaType.isContainerType()) {
            Class<? extends JsonDeserializer<?>> class_;
            Class<? extends KeyDeserializer> class_2;
            TypeDeserializer typeDeserializer;
            AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
            JavaType javaType2 = javaType.getKeyType();
            if (javaType2 != null && (class_2 = annotationIntrospector.findKeyDeserializer(annotatedMember)) != null && class_2 != KeyDeserializer.None.class) {
                javaType2.setValueHandler(deserializationConfig.keyDeserializerInstance(annotatedMember, class_2));
            }
            if ((class_ = annotationIntrospector.findContentDeserializer(annotatedMember)) != null && class_ != JsonDeserializer.None.class) {
                JsonDeserializer<Object> jsonDeserializer = deserializationConfig.deserializerInstance(annotatedMember, class_);
                javaType.getContentType().setValueHandler(jsonDeserializer);
            }
            if (annotatedMember instanceof AnnotatedMember && (typeDeserializer = this.findPropertyContentTypeDeserializer(deserializationConfig, javaType, annotatedMember, beanProperty)) != null) {
                javaType = javaType.withContentTypeHandler(typeDeserializer);
            }
        }
        TypeDeserializer typeDeserializer = annotatedMember instanceof AnnotatedMember ? this.findPropertyTypeDeserializer(deserializationConfig, javaType, annotatedMember, beanProperty) : this.findTypeDeserializer(deserializationConfig, javaType, null);
        if (typeDeserializer == null) return javaType;
        return javaType.withTypeHandler(typeDeserializer);
    }

    @Override
    public abstract DeserializerFactory withConfig(DeserializerFactory.Config var1);
}

