/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Type
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@JacksonStdImpl
public class MapSerializer
extends ContainerSerializerBase<Map<?, ?>>
implements ResolvableSerializer {
    protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final HashSet<String> _ignoredEntries;
    protected JsonSerializer<Object> _keySerializer;
    protected final JavaType _keyType;
    protected final BeanProperty _property;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final boolean _valueTypeIsStatic;
    protected final TypeSerializer _valueTypeSerializer;

    protected MapSerializer() {
        this(null, null, null, false, null, null, null, null);
    }

    protected MapSerializer(HashSet<String> hashSet, JavaType javaType, JavaType javaType2, boolean bl, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, BeanProperty beanProperty) {
        super(Map.class, false);
        this._property = beanProperty;
        this._ignoredEntries = hashSet;
        this._keyType = javaType;
        this._valueType = javaType2;
        this._valueTypeIsStatic = bl;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyMap();
    }

    @Deprecated
    public static MapSerializer construct(String[] arrstring, JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        return MapSerializer.construct(arrstring, javaType, bl, typeSerializer, beanProperty, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static MapSerializer construct(String[] arrstring, JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2) {
        JavaType javaType2;
        JavaType javaType3;
        HashSet<String> hashSet = MapSerializer.toSet(arrstring);
        if (javaType == null) {
            javaType3 = javaType2 = UNSPECIFIED_TYPE;
        } else {
            javaType3 = javaType.getKeyType();
            javaType2 = javaType.getContentType();
        }
        if (bl) return new MapSerializer(hashSet, javaType3, javaType2, bl, typeSerializer, jsonSerializer, jsonSerializer2, beanProperty);
        if (javaType2 != null && javaType2.isFinal()) {
            bl = true;
            return new MapSerializer(hashSet, javaType3, javaType2, bl, typeSerializer, jsonSerializer, jsonSerializer2, beanProperty);
        }
        bl = false;
        return new MapSerializer(hashSet, javaType3, javaType2, bl, typeSerializer, jsonSerializer, jsonSerializer2, beanProperty);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static HashSet<String> toSet(String[] arrstring) {
        if (arrstring == null) return null;
        if (arrstring.length == 0) {
            return null;
        }
        HashSet hashSet = new HashSet(arrstring.length);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            hashSet.add((Object)arrstring[n2]);
            ++n2;
        }
        return hashSet;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, JavaType javaType, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult = propertySerializerMap.findAndAddSerializer(javaType, serializerProvider, this._property);
        if (propertySerializerMap != serializerAndMapResult.map) {
            this._dynamicValueSerializers = serializerAndMapResult.map;
        }
        return serializerAndMapResult.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> class_, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult = propertySerializerMap.findAndAddSerializer(class_, serializerProvider, this._property);
        if (propertySerializerMap != serializerAndMapResult.map) {
            this._dynamicValueSerializers = serializerAndMapResult.map;
        }
        return serializerAndMapResult.serializer;
    }

    @Override
    public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        MapSerializer mapSerializer = new MapSerializer(this._ignoredEntries, this._keyType, this._valueType, this._valueTypeIsStatic, typeSerializer, this._keySerializer, this._valueSerializer, this._property);
        if (this._valueSerializer != null) {
            mapSerializer._valueSerializer = this._valueSerializer;
        }
        return mapSerializer;
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("object", true);
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        if (this._valueTypeIsStatic && this._valueSerializer == null) {
            this._valueSerializer = serializerProvider.findValueSerializer(this._valueType, this._property);
        }
        if (this._keySerializer == null) {
            this._keySerializer = serializerProvider.findKeySerializer(this._keyType, this._property);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serialize(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        if (!map.isEmpty()) {
            if (this._valueSerializer != null) {
                this.serializeFieldsUsing(map, jsonGenerator, serializerProvider, this._valueSerializer);
            } else {
                this.serializeFields(map, jsonGenerator, serializerProvider);
            }
        }
        jsonGenerator.writeEndObject();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._valueTypeSerializer != null) {
            this.serializeTypedFields(map, jsonGenerator, serializerProvider);
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._keySerializer;
        HashSet<String> hashSet = this._ignoredEntries;
        boolean bl = !serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
        PropertySerializerMap propertySerializerMap = this._dynamicValueSerializers;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            Object object = entry.getValue();
            Object object2 = entry.getKey();
            if (object2 == null) {
                serializerProvider.getNullKeySerializer().serialize(null, jsonGenerator, serializerProvider);
            } else {
                if (bl && object == null || hashSet != null && hashSet.contains(object2)) continue;
                jsonSerializer.serialize(object2, jsonGenerator, serializerProvider);
            }
            if (object == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                continue;
            }
            Class class_ = object.getClass();
            JsonSerializer<Object> jsonSerializer2 = propertySerializerMap.serializerFor(class_);
            if (jsonSerializer2 == null) {
                jsonSerializer2 = this._valueType.hasGenericTypes() ? this._findAndAddDynamic(propertySerializerMap, serializerProvider.constructSpecializedType(this._valueType, class_), serializerProvider) : this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
                propertySerializerMap = this._dynamicValueSerializers;
            }
            try {
                jsonSerializer2.serialize(object, jsonGenerator, serializerProvider);
            }
            catch (Exception exception) {
                this.wrapAndThrow(serializerProvider, (Throwable)exception, (Object)map, "" + object2);
                continue;
            }
            break;
        }
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void serializeFieldsUsing(Map<?, ?> var1_1, JsonGenerator var2_4, SerializerProvider var3_3, JsonSerializer<Object> var4) throws IOException, JsonGenerationException {
        var5_5 = this._keySerializer;
        var6_6 = this._ignoredEntries;
        var7_7 = this._valueTypeSerializer;
        var8_8 = var3_3.isEnabled(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES) == false;
        var9_9 = var1_1.entrySet().iterator();
        while (var9_9.hasNext() != false) {
            var10_13 = (Map.Entry)var9_9.next();
            var11_10 = var10_13.getValue();
            var12_12 = var10_13.getKey();
            if (var12_12 == null) {
                var3_3.getNullKeySerializer().serialize(null, var2_4, var3_3);
            } else {
                if (var8_8 && var11_10 == null || var6_6 != null && var6_6.contains(var12_12)) continue;
                var5_5.serialize(var12_12, var2_4, var3_3);
            }
            if (var11_10 == null) {
                var3_3.defaultSerializeNull(var2_4);
                continue;
            }
            if (var7_7 != null) ** GOTO lbl22
            try {
                var4.serialize(var11_10, var2_4, var3_3);
lbl22: // 1 sources:
                var4.serializeWithType(var11_10, var2_4, var3_3, var7_7);
            }
            catch (Exception var13_11) {
                this.wrapAndThrow(var3_3, (Throwable)var13_11, (Object)var1_1, "" + var12_12);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void serializeTypedFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        JsonSerializer<Object> jsonSerializer = this._keySerializer;
        JsonSerializer<Object> jsonSerializer2 = null;
        Class class_ = null;
        HashSet<String> hashSet = this._ignoredEntries;
        boolean bl = !serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<Object> jsonSerializer3;
            Map.Entry entry = (Map.Entry)iterator.next();
            Object object = entry.getValue();
            Object object2 = entry.getKey();
            if (object2 == null) {
                serializerProvider.getNullKeySerializer().serialize(null, jsonGenerator, serializerProvider);
            } else {
                if (bl && object == null || hashSet != null && hashSet.contains(object2)) continue;
                jsonSerializer.serialize(object2, jsonGenerator, serializerProvider);
            }
            if (object == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                continue;
            }
            Class class_2 = object.getClass();
            if (class_2 == class_) {
                jsonSerializer3 = jsonSerializer2;
            } else {
                jsonSerializer2 = jsonSerializer3 = serializerProvider.findValueSerializer(class_2, this._property);
                class_ = class_2;
            }
            try {
                jsonSerializer3.serializeWithType(object, jsonGenerator, serializerProvider, this._valueTypeSerializer);
            }
            catch (Exception exception) {
                this.wrapAndThrow(serializerProvider, (Throwable)exception, (Object)map, "" + object2);
                continue;
            }
            break;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForObject(map, jsonGenerator);
        if (!map.isEmpty()) {
            if (this._valueSerializer != null) {
                this.serializeFieldsUsing(map, jsonGenerator, serializerProvider, this._valueSerializer);
            } else {
                this.serializeFields(map, jsonGenerator, serializerProvider);
            }
        }
        typeSerializer.writeTypeSuffixForObject(map, jsonGenerator);
    }
}

