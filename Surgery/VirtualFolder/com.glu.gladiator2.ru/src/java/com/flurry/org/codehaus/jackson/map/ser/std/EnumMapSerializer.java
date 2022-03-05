/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 *  java.util.EnumMap
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
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.EnumSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.map.util.EnumValues;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.schema.SchemaAware;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@JacksonStdImpl
public class EnumMapSerializer
extends ContainerSerializerBase<EnumMap<? extends Enum<?>, ?>>
implements ResolvableSerializer {
    protected final EnumValues _keyEnums;
    protected final BeanProperty _property;
    protected final boolean _staticTyping;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final TypeSerializer _valueTypeSerializer;

    @Deprecated
    public EnumMapSerializer(JavaType javaType, boolean bl, EnumValues enumValues, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        this(javaType, bl, enumValues, typeSerializer, beanProperty, null);
    }

    public EnumMapSerializer(JavaType javaType, boolean bl, EnumValues enumValues, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
        boolean bl2;
        block3 : {
            block2 : {
                super(EnumMap.class, false);
                if (bl) break block2;
                bl2 = false;
                if (javaType == null) break block3;
                boolean bl3 = javaType.isFinal();
                bl2 = false;
                if (!bl3) break block3;
            }
            bl2 = true;
        }
        this._staticTyping = bl2;
        this._valueType = javaType;
        this._keyEnums = enumValues;
        this._valueTypeSerializer = typeSerializer;
        this._property = beanProperty;
        this._valueSerializer = jsonSerializer;
    }

    @Override
    public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new EnumMapSerializer(this._valueType, this._staticTyping, this._keyEnums, typeSerializer, this._property, this._valueSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        Type[] arrtype;
        ObjectNode objectNode = this.createSchemaNode("object", true);
        if (type instanceof ParameterizedType && (arrtype = ((ParameterizedType)type).getActualTypeArguments()).length == 2) {
            JavaType javaType = serializerProvider.constructType(arrtype[0]);
            JavaType javaType2 = serializerProvider.constructType(arrtype[1]);
            ObjectNode objectNode2 = JsonNodeFactory.instance.objectNode();
            for (Enum enum_ : (Enum[])javaType.getRawClass().getEnumConstants()) {
                JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(javaType2.getRawClass(), this._property);
                JsonNode jsonNode = jsonSerializer instanceof SchemaAware ? ((SchemaAware)((Object)jsonSerializer)).getSchema(serializerProvider, null) : JsonSchema.getDefaultSchemaNode();
                objectNode2.put(serializerProvider.getConfig().getAnnotationIntrospector().findEnumValue(enum_), jsonNode);
            }
            objectNode.put("properties", objectNode2);
        }
        return objectNode;
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        if (this._staticTyping && this._valueSerializer == null) {
            this._valueSerializer = serializerProvider.findValueSerializer(this._valueType, this._property);
        }
    }

    @Override
    public void serialize(EnumMap<? extends Enum<?>, ?> enumMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        if (!enumMap.isEmpty()) {
            this.serializeContents(enumMap, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndObject();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void serializeContents(EnumMap<? extends Enum<?>, ?> enumMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._valueSerializer != null) {
            this.serializeContentsUsing(enumMap, jsonGenerator, serializerProvider, this._valueSerializer);
            return;
        }
        JsonSerializer<Object> jsonSerializer = null;
        Class class_ = null;
        EnumValues enumValues = this._keyEnums;
        Iterator iterator = enumMap.entrySet().iterator();
        while (iterator.hasNext()) {
            JsonSerializer<Object> jsonSerializer2;
            Map.Entry entry = (Map.Entry)iterator.next();
            Enum enum_ = (Enum)entry.getKey();
            if (enumValues == null) {
                enumValues = ((EnumSerializer)((SerializerBase)serializerProvider.findValueSerializer(enum_.getDeclaringClass(), this._property))).getEnumValues();
            }
            jsonGenerator.writeFieldName(enumValues.serializedValueFor(enum_));
            Object object = entry.getValue();
            if (object == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                continue;
            }
            Class class_2 = object.getClass();
            if (class_2 == class_) {
                jsonSerializer2 = jsonSerializer;
            } else {
                jsonSerializer = jsonSerializer2 = serializerProvider.findValueSerializer(class_2, this._property);
                class_ = class_2;
            }
            try {
                jsonSerializer2.serialize(object, jsonGenerator, serializerProvider);
            }
            catch (Exception exception) {
                this.wrapAndThrow(serializerProvider, (Throwable)exception, (Object)enumMap, ((Enum)entry.getKey()).name());
                continue;
            }
            break;
        }
        return;
    }

    protected void serializeContentsUsing(EnumMap<? extends Enum<?>, ?> enumMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException, JsonGenerationException {
        EnumValues enumValues = this._keyEnums;
        for (Map.Entry entry : enumMap.entrySet()) {
            Enum enum_ = (Enum)entry.getKey();
            if (enumValues == null) {
                enumValues = ((EnumSerializer)((SerializerBase)serializerProvider.findValueSerializer(enum_.getDeclaringClass(), this._property))).getEnumValues();
            }
            jsonGenerator.writeFieldName(enumValues.serializedValueFor(enum_));
            Object object = entry.getValue();
            if (object == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                continue;
            }
            try {
                jsonSerializer.serialize(object, jsonGenerator, serializerProvider);
            }
            catch (Exception exception) {
                this.wrapAndThrow(serializerProvider, (Throwable)exception, (Object)enumMap, ((Enum)entry.getKey()).name());
            }
        }
    }

    @Override
    public void serializeWithType(EnumMap<? extends Enum<?>, ?> enumMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForObject(enumMap, jsonGenerator);
        if (!enumMap.isEmpty()) {
            this.serializeContents(enumMap, jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForObject(enumMap, jsonGenerator);
    }
}

