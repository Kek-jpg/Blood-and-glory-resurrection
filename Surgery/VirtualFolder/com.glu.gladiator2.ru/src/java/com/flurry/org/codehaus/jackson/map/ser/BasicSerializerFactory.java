/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Iterable
 *  java.lang.Long
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.reflect.Member
 *  java.lang.reflect.Method
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.net.InetAddress
 *  java.nio.charset.Charset
 *  java.sql.Date
 *  java.sql.Time
 *  java.sql.Timestamp
 *  java.util.Calendar
 *  java.util.Collection
 *  java.util.Date
 *  java.util.EnumMap
 *  java.util.EnumSet
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.RandomAccess
 *  java.util.TimeZone
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.ContextualSerializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializable;
import com.flurry.org.codehaus.jackson.map.JsonSerializableWithType;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerFactory;
import com.flurry.org.codehaus.jackson.map.Serializers;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.ext.OptionalHandlerFactory;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedClass;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.ser.StdSerializers;
import com.flurry.org.codehaus.jackson.map.ser.std.CalendarSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.DateSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.EnumMapSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.EnumSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.IndexedStringListSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.InetAddressSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.JsonValueSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.MapSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.NullSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.ObjectArraySerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializableSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializableWithTypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.StdArraySerializers;
import com.flurry.org.codehaus.jackson.map.ser.std.StdContainerSerializers;
import com.flurry.org.codehaus.jackson.map.ser.std.StdJdkSerializers;
import com.flurry.org.codehaus.jackson.map.ser.std.StringCollectionSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.StringSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.TimeZoneSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.ToStringSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.TokenBufferSerializer;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.map.type.CollectionLikeType;
import com.flurry.org.codehaus.jackson.map.type.CollectionType;
import com.flurry.org.codehaus.jackson.map.type.MapLikeType;
import com.flurry.org.codehaus.jackson.map.type.MapType;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.EnumValues;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.util.TokenBuffer;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.TimeZone;

public abstract class BasicSerializerFactory
extends SerializerFactory {
    protected static final HashMap<String, JsonSerializer<?>> _arraySerializers;
    protected static final HashMap<String, JsonSerializer<?>> _concrete;
    protected static final HashMap<String, Class<? extends JsonSerializer<?>>> _concreteLazy;
    protected OptionalHandlerFactory optionalHandlers = OptionalHandlerFactory.instance;

    static {
        _concrete = new HashMap();
        _concreteLazy = new HashMap();
        _concrete.put((Object)String.class.getName(), (Object)new StringSerializer());
        ToStringSerializer toStringSerializer = ToStringSerializer.instance;
        _concrete.put((Object)StringBuffer.class.getName(), (Object)toStringSerializer);
        _concrete.put((Object)StringBuilder.class.getName(), (Object)toStringSerializer);
        _concrete.put((Object)Character.class.getName(), (Object)toStringSerializer);
        _concrete.put((Object)Character.TYPE.getName(), (Object)toStringSerializer);
        _concrete.put((Object)Boolean.TYPE.getName(), (Object)new StdSerializers.BooleanSerializer(true));
        _concrete.put((Object)Boolean.class.getName(), (Object)new StdSerializers.BooleanSerializer(false));
        StdSerializers.IntegerSerializer integerSerializer = new StdSerializers.IntegerSerializer();
        _concrete.put((Object)Integer.class.getName(), (Object)integerSerializer);
        _concrete.put((Object)Integer.TYPE.getName(), (Object)integerSerializer);
        _concrete.put((Object)Long.class.getName(), (Object)StdSerializers.LongSerializer.instance);
        _concrete.put((Object)Long.TYPE.getName(), (Object)StdSerializers.LongSerializer.instance);
        _concrete.put((Object)Byte.class.getName(), (Object)StdSerializers.IntLikeSerializer.instance);
        _concrete.put((Object)Byte.TYPE.getName(), (Object)StdSerializers.IntLikeSerializer.instance);
        _concrete.put((Object)Short.class.getName(), (Object)StdSerializers.IntLikeSerializer.instance);
        _concrete.put((Object)Short.TYPE.getName(), (Object)StdSerializers.IntLikeSerializer.instance);
        _concrete.put((Object)Float.class.getName(), (Object)StdSerializers.FloatSerializer.instance);
        _concrete.put((Object)Float.TYPE.getName(), (Object)StdSerializers.FloatSerializer.instance);
        _concrete.put((Object)Double.class.getName(), (Object)StdSerializers.DoubleSerializer.instance);
        _concrete.put((Object)Double.TYPE.getName(), (Object)StdSerializers.DoubleSerializer.instance);
        StdSerializers.NumberSerializer numberSerializer = new StdSerializers.NumberSerializer();
        _concrete.put((Object)BigInteger.class.getName(), (Object)numberSerializer);
        _concrete.put((Object)BigDecimal.class.getName(), (Object)numberSerializer);
        _concrete.put((Object)Calendar.class.getName(), (Object)CalendarSerializer.instance);
        DateSerializer dateSerializer = DateSerializer.instance;
        _concrete.put((Object)Date.class.getName(), (Object)dateSerializer);
        _concrete.put((Object)Timestamp.class.getName(), (Object)dateSerializer);
        _concrete.put((Object)java.sql.Date.class.getName(), (Object)new StdSerializers.SqlDateSerializer());
        _concrete.put((Object)Time.class.getName(), (Object)new StdSerializers.SqlTimeSerializer());
        for (Map.Entry entry : new StdJdkSerializers().provide()) {
            Object object = entry.getValue();
            if (object instanceof JsonSerializer) {
                _concrete.put((Object)((Class)entry.getKey()).getName(), (Object)((JsonSerializer)object));
                continue;
            }
            if (object instanceof Class) {
                Class class_ = (Class)object;
                _concreteLazy.put((Object)((Class)entry.getKey()).getName(), (Object)class_);
                continue;
            }
            throw new IllegalStateException("Internal error: unrecognized value of type " + entry.getClass().getName());
        }
        _concreteLazy.put((Object)TokenBuffer.class.getName(), TokenBufferSerializer.class);
        _arraySerializers = new HashMap();
        _arraySerializers.put((Object)boolean[].class.getName(), (Object)new StdArraySerializers.BooleanArraySerializer());
        _arraySerializers.put((Object)byte[].class.getName(), (Object)new StdArraySerializers.ByteArraySerializer());
        _arraySerializers.put((Object)char[].class.getName(), (Object)new StdArraySerializers.CharArraySerializer());
        _arraySerializers.put((Object)short[].class.getName(), (Object)new StdArraySerializers.ShortArraySerializer());
        _arraySerializers.put((Object)int[].class.getName(), (Object)new StdArraySerializers.IntArraySerializer());
        _arraySerializers.put((Object)long[].class.getName(), (Object)new StdArraySerializers.LongArraySerializer());
        _arraySerializers.put((Object)float[].class.getName(), (Object)new StdArraySerializers.FloatArraySerializer());
        _arraySerializers.put((Object)double[].class.getName(), (Object)new StdArraySerializers.DoubleArraySerializer());
    }

    protected BasicSerializerFactory() {
    }

    protected static JsonSerializer<Object> findContentSerializer(SerializationConfig serializationConfig, Annotated annotated, BeanProperty beanProperty) {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        Class<? extends JsonSerializer<?>> class_ = annotationIntrospector.findContentSerializer(annotated);
        if ((class_ == null || class_ == JsonSerializer.None.class) && beanProperty != null) {
            class_ = annotationIntrospector.findContentSerializer(beanProperty.getMember());
        }
        if (class_ != null && class_ != JsonSerializer.None.class) {
            return serializationConfig.serializerInstance(annotated, class_);
        }
        return null;
    }

    protected static JsonSerializer<Object> findKeySerializer(SerializationConfig serializationConfig, Annotated annotated, BeanProperty beanProperty) {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        Class<? extends JsonSerializer<?>> class_ = annotationIntrospector.findKeySerializer(annotated);
        if ((class_ == null || class_ == JsonSerializer.None.class) && beanProperty != null) {
            class_ = annotationIntrospector.findKeySerializer(beanProperty.getMember());
        }
        if (class_ != null && class_ != JsonSerializer.None.class) {
            return serializationConfig.serializerInstance(annotated, class_);
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static <T extends JavaType> T modifySecondaryTypesByAnnotation(SerializationConfig serializationConfig, Annotated annotated, T object) {
        Class<?> class_2;
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        if (!((JavaType)object).isContainerType()) return (T)object;
        Class<?> class_ = annotationIntrospector.findSerializationKeyType(annotated, ((JavaType)object).getKeyType());
        if (class_ != null) {
            if (!(object instanceof MapType)) {
                throw new IllegalArgumentException("Illegal key-type annotation: type " + object + " is not a Map type");
            }
            JavaType javaType = ((MapType)object).widenKey(class_);
            object = javaType;
        }
        if ((class_2 = annotationIntrospector.findSerializationContentType(annotated, ((JavaType)object).getContentType())) == null) return (T)object;
        try {
            JavaType javaType = ((JavaType)object).widenContentsBy(class_2);
            object = javaType;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Failed to narrow content type " + object + " with content-type annotation (" + class_2.getName() + "): " + illegalArgumentException.getMessage());
        }
        return (T)object;
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Failed to narrow key type " + object + " with key-type annotation (" + class_.getName() + "): " + illegalArgumentException.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<?> buildArraySerializer(SerializationConfig serializationConfig, ArrayType arrayType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        void var9_10;
        Class<?> class_ = arrayType.getRawClass();
        if (String[].class == class_) {
            StdArraySerializers.StringArraySerializer stringArraySerializer = new StdArraySerializers.StringArraySerializer(beanProperty);
            return var9_10;
        } else {
            JsonSerializer jsonSerializer2 = (JsonSerializer)_arraySerializers.get((Object)class_.getName());
            if (jsonSerializer2 != null) return var9_10;
            return new ObjectArraySerializer(arrayType.getContentType(), bl, typeSerializer, beanProperty, jsonSerializer);
        }
    }

    protected JsonSerializer<?> buildCollectionLikeSerializer(SerializationConfig serializationConfig, CollectionLikeType collectionLikeType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        Iterator iterator = this.customSerializers().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<?> jsonSerializer2 = ((Serializers)iterator.next()).findCollectionLikeSerializer(serializationConfig, collectionLikeType, basicBeanDescription, beanProperty, typeSerializer, jsonSerializer);
            if (jsonSerializer2 == null) continue;
            return jsonSerializer2;
        }
        return null;
    }

    protected JsonSerializer<?> buildCollectionSerializer(SerializationConfig serializationConfig, CollectionType collectionType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        Iterator iterator = this.customSerializers().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<?> jsonSerializer2 = ((Serializers)iterator.next()).findCollectionSerializer(serializationConfig, collectionType, basicBeanDescription, beanProperty, typeSerializer, jsonSerializer);
            if (jsonSerializer2 == null) continue;
            return jsonSerializer2;
        }
        Class<?> class_ = collectionType.getRawClass();
        if (EnumSet.class.isAssignableFrom(class_)) {
            return this.buildEnumSetSerializer(serializationConfig, collectionType, basicBeanDescription, beanProperty, bl, typeSerializer, jsonSerializer);
        }
        Class<?> class_2 = collectionType.getContentType().getRawClass();
        if (this.isIndexedList(class_)) {
            if (class_2 == String.class) {
                return new IndexedStringListSerializer(beanProperty);
            }
            return StdContainerSerializers.indexedListSerializer(collectionType.getContentType(), bl, typeSerializer, beanProperty, jsonSerializer);
        }
        if (class_2 == String.class) {
            return new StringCollectionSerializer(beanProperty);
        }
        return StdContainerSerializers.collectionSerializer(collectionType.getContentType(), bl, typeSerializer, beanProperty, jsonSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonSerializer<?> buildContainerSerializer(SerializationConfig serializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl) {
        TypeSerializer typeSerializer = this.createTypeSerializer(serializationConfig, javaType.getContentType(), beanProperty);
        if (typeSerializer != null) {
            bl = false;
        } else if (!bl) {
            bl = this.usesStaticTyping(serializationConfig, basicBeanDescription, typeSerializer, beanProperty);
        }
        JsonSerializer<Object> jsonSerializer = BasicSerializerFactory.findContentSerializer(serializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
        if (javaType.isMapLikeType()) {
            MapLikeType mapLikeType = (MapLikeType)javaType;
            JsonSerializer<Object> jsonSerializer2 = BasicSerializerFactory.findKeySerializer(serializationConfig, basicBeanDescription.getClassInfo(), beanProperty);
            if (mapLikeType.isTrueMapType()) {
                return this.buildMapSerializer(serializationConfig, (MapType)mapLikeType, basicBeanDescription, beanProperty, bl, jsonSerializer2, typeSerializer, jsonSerializer);
            }
            return this.buildMapLikeSerializer(serializationConfig, mapLikeType, basicBeanDescription, beanProperty, bl, jsonSerializer2, typeSerializer, jsonSerializer);
        }
        if (javaType.isCollectionLikeType()) {
            CollectionLikeType collectionLikeType = (CollectionLikeType)javaType;
            if (collectionLikeType.isTrueCollectionType()) {
                return this.buildCollectionSerializer(serializationConfig, (CollectionType)collectionLikeType, basicBeanDescription, beanProperty, bl, typeSerializer, jsonSerializer);
            }
            return this.buildCollectionLikeSerializer(serializationConfig, collectionLikeType, basicBeanDescription, beanProperty, bl, typeSerializer, jsonSerializer);
        }
        if (javaType.isArrayType()) {
            return this.buildArraySerializer(serializationConfig, (ArrayType)javaType, basicBeanDescription, beanProperty, bl, typeSerializer, jsonSerializer);
        }
        return null;
    }

    protected JsonSerializer<?> buildEnumMapSerializer(SerializationConfig serializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        JavaType javaType2 = javaType.getKeyType();
        boolean bl2 = javaType2.isEnumType();
        EnumValues enumValues = null;
        if (bl2) {
            enumValues = EnumValues.construct(javaType2.getRawClass(), serializationConfig.getAnnotationIntrospector());
        }
        return new EnumMapSerializer(javaType.getContentType(), bl, enumValues, typeSerializer, beanProperty, jsonSerializer);
    }

    protected JsonSerializer<?> buildEnumSetSerializer(SerializationConfig serializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        JavaType javaType2 = javaType.getContentType();
        if (!javaType2.isEnumType()) {
            javaType2 = null;
        }
        return StdContainerSerializers.enumSetSerializer(javaType2, beanProperty);
    }

    protected JsonSerializer<?> buildIterableSerializer(SerializationConfig serializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl) {
        JavaType javaType2 = javaType.containedType(0);
        if (javaType2 == null) {
            javaType2 = TypeFactory.unknownType();
        }
        TypeSerializer typeSerializer = this.createTypeSerializer(serializationConfig, javaType2, beanProperty);
        return StdContainerSerializers.iterableSerializer(javaType2, this.usesStaticTyping(serializationConfig, basicBeanDescription, typeSerializer, beanProperty), typeSerializer, beanProperty);
    }

    protected JsonSerializer<?> buildIteratorSerializer(SerializationConfig serializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl) {
        JavaType javaType2 = javaType.containedType(0);
        if (javaType2 == null) {
            javaType2 = TypeFactory.unknownType();
        }
        TypeSerializer typeSerializer = this.createTypeSerializer(serializationConfig, javaType2, beanProperty);
        return StdContainerSerializers.iteratorSerializer(javaType2, this.usesStaticTyping(serializationConfig, basicBeanDescription, typeSerializer, beanProperty), typeSerializer, beanProperty);
    }

    protected JsonSerializer<?> buildMapLikeSerializer(SerializationConfig serializationConfig, MapLikeType mapLikeType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
        Iterator iterator = this.customSerializers().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<?> jsonSerializer3 = ((Serializers)iterator.next()).findMapLikeSerializer(serializationConfig, mapLikeType, basicBeanDescription, beanProperty, jsonSerializer, typeSerializer, jsonSerializer2);
            if (jsonSerializer3 == null) continue;
            return jsonSerializer3;
        }
        return null;
    }

    protected JsonSerializer<?> buildMapSerializer(SerializationConfig serializationConfig, MapType mapType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer2) {
        Iterator iterator = this.customSerializers().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<?> jsonSerializer3 = ((Serializers)iterator.next()).findMapSerializer(serializationConfig, mapType, basicBeanDescription, beanProperty, jsonSerializer, typeSerializer, jsonSerializer2);
            if (jsonSerializer3 == null) continue;
            return jsonSerializer3;
        }
        if (EnumMap.class.isAssignableFrom(mapType.getRawClass())) {
            return this.buildEnumMapSerializer(serializationConfig, mapType, basicBeanDescription, beanProperty, bl, typeSerializer, jsonSerializer2);
        }
        return MapSerializer.construct(serializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(basicBeanDescription.getClassInfo()), mapType, bl, typeSerializer, beanProperty, jsonSerializer, jsonSerializer2);
    }

    @Override
    public abstract JsonSerializer<Object> createSerializer(SerializationConfig var1, JavaType var2, BeanProperty var3) throws JsonMappingException;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public TypeSerializer createTypeSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanProperty beanProperty) {
        AnnotatedClass annotatedClass = ((BasicBeanDescription)serializationConfig.introspectClassAnnotations(javaType.getRawClass())).getClassInfo();
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> typeResolverBuilder = annotationIntrospector.findTypeResolver(serializationConfig, annotatedClass, javaType);
        Collection<NamedType> collection = null;
        if (typeResolverBuilder == null) {
            typeResolverBuilder = serializationConfig.getDefaultTyper(javaType);
        } else {
            collection = serializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedClass, serializationConfig, annotationIntrospector);
        }
        if (typeResolverBuilder == null) {
            return null;
        }
        return typeResolverBuilder.buildTypeSerializer(serializationConfig, javaType, collection, beanProperty);
    }

    protected abstract Iterable<Serializers> customSerializers();

    public final JsonSerializer<?> findSerializerByAddonType(SerializationConfig serializationConfig, JavaType javaType, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl) throws JsonMappingException {
        Class<?> class_ = javaType.getRawClass();
        if (Iterator.class.isAssignableFrom(class_)) {
            return this.buildIteratorSerializer(serializationConfig, javaType, basicBeanDescription, beanProperty, bl);
        }
        if (Iterable.class.isAssignableFrom(class_)) {
            return this.buildIterableSerializer(serializationConfig, javaType, basicBeanDescription, beanProperty, bl);
        }
        if (CharSequence.class.isAssignableFrom(class_)) {
            return ToStringSerializer.instance;
        }
        return null;
    }

    public final JsonSerializer<?> findSerializerByLookup(JavaType javaType, SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl) {
        String string = javaType.getRawClass().getName();
        JsonSerializer jsonSerializer = (JsonSerializer)_concrete.get((Object)string);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        Class class_ = (Class)_concreteLazy.get((Object)string);
        if (class_ != null) {
            try {
                JsonSerializer jsonSerializer2 = (JsonSerializer)class_.newInstance();
                return jsonSerializer2;
            }
            catch (Exception exception) {
                throw new IllegalStateException("Failed to instantiate standard serializer (of type " + class_.getName() + "): " + exception.getMessage(), (Throwable)exception);
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final JsonSerializer<?> findSerializerByPrimaryType(JavaType javaType, SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, BeanProperty beanProperty, boolean bl) throws JsonMappingException {
        void var8_8;
        Class<?> class_ = javaType.getRawClass();
        if (JsonSerializable.class.isAssignableFrom(class_)) {
            if (!JsonSerializableWithType.class.isAssignableFrom(class_)) {
                return SerializableSerializer.instance;
            }
            SerializableWithTypeSerializer serializableWithTypeSerializer = SerializableWithTypeSerializer.instance;
            return var8_8;
        }
        AnnotatedMethod annotatedMethod = basicBeanDescription.findJsonValueMethod();
        if (annotatedMethod != null) {
            Method method = annotatedMethod.getAnnotated();
            if (!serializationConfig.isEnabled(SerializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) return new JsonValueSerializer(method, this.findSerializerFromAnnotation(serializationConfig, annotatedMethod, beanProperty), beanProperty);
            {
                ClassUtil.checkAndFixAccess((Member)method);
            }
            return new JsonValueSerializer(method, this.findSerializerFromAnnotation(serializationConfig, annotatedMethod, beanProperty), beanProperty);
        }
        if (InetAddress.class.isAssignableFrom(class_)) {
            return InetAddressSerializer.instance;
        }
        if (TimeZone.class.isAssignableFrom(class_)) {
            return TimeZoneSerializer.instance;
        }
        if (Charset.class.isAssignableFrom(class_)) {
            return ToStringSerializer.instance;
        }
        JsonSerializer<?> jsonSerializer = this.optionalHandlers.findSerializer(serializationConfig, javaType);
        if (jsonSerializer != null) return var8_8;
        {
            if (Number.class.isAssignableFrom(class_)) {
                return StdSerializers.NumberSerializer.instance;
            }
        }
        if (Enum.class.isAssignableFrom(class_)) {
            return EnumSerializer.construct(class_, serializationConfig, basicBeanDescription);
        }
        if (Calendar.class.isAssignableFrom(class_)) {
            return CalendarSerializer.instance;
        }
        if (!Date.class.isAssignableFrom(class_)) return null;
        return DateSerializer.instance;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonSerializer<Object> findSerializerFromAnnotation(SerializationConfig serializationConfig, Annotated annotated, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        Object object = serializationConfig.getAnnotationIntrospector().findSerializer(annotated);
        if (object == null) {
            return null;
        }
        if (object instanceof JsonSerializer) {
            jsonSerializer = (JsonSerializer<Object>)object;
            if (!(jsonSerializer instanceof ContextualSerializer)) return jsonSerializer;
            return ((ContextualSerializer)((Object)jsonSerializer)).createContextual(serializationConfig, beanProperty);
        }
        if (!(object instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned value of type " + object.getClass().getName() + "; expected type JsonSerializer or Class<JsonSerializer> instead");
        }
        Class class_ = (Class)object;
        if (!JsonSerializer.class.isAssignableFrom(class_)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + class_.getName() + "; expected Class<JsonSerializer>");
        }
        jsonSerializer = serializationConfig.serializerInstance(annotated, class_);
        if (!(jsonSerializer instanceof ContextualSerializer)) return jsonSerializer;
        return ((ContextualSerializer)((Object)jsonSerializer)).createContextual(serializationConfig, beanProperty);
    }

    public final JsonSerializer<?> getNullSerializer() {
        return NullSerializer.instance;
    }

    protected boolean isIndexedList(Class<?> class_) {
        return RandomAccess.class.isAssignableFrom(class_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected <T extends JavaType> T modifyTypeByAnnotation(SerializationConfig serializationConfig, Annotated annotated, T object) {
        Class<?> class_ = serializationConfig.getAnnotationIntrospector().findSerializationType(annotated);
        if (class_ == null) return (T)BasicSerializerFactory.modifySecondaryTypesByAnnotation(serializationConfig, annotated, object);
        try {
            JavaType javaType = ((JavaType)object).widenBy(class_);
            object = javaType;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Failed to widen type " + object + " with concrete-type annotation (value " + class_.getName() + "), method '" + annotated.getName() + "': " + illegalArgumentException.getMessage());
        }
        return (T)BasicSerializerFactory.modifySecondaryTypesByAnnotation(serializationConfig, annotated, object);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean usesStaticTyping(SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        block6 : {
            block5 : {
                JavaType javaType;
                if (typeSerializer != null) break block5;
                AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
                JsonSerialize.Typing typing = annotationIntrospector.findSerializationTyping(basicBeanDescription.getClassInfo());
                if (typing != null ? typing == JsonSerialize.Typing.STATIC : serializationConfig.isEnabled(SerializationConfig.Feature.USE_STATIC_TYPING)) {
                    return true;
                }
                if (beanProperty == null || !(javaType = beanProperty.getType()).isContainerType()) break block5;
                if (annotationIntrospector.findSerializationContentType(beanProperty.getMember(), beanProperty.getType()) != null) {
                    return true;
                }
                if (javaType instanceof MapType && annotationIntrospector.findSerializationKeyType(beanProperty.getMember(), beanProperty.getType()) != null) break block6;
            }
            return false;
        }
        return true;
    }
}

