/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.ParameterizedType
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.schema.SchemaAware;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AsArraySerializerBase<T>
extends ContainerSerializerBase<T>
implements ResolvableSerializer {
    protected PropertySerializerMap _dynamicSerializers;
    protected JsonSerializer<Object> _elementSerializer;
    protected final JavaType _elementType;
    protected final BeanProperty _property;
    protected final boolean _staticTyping;
    protected final TypeSerializer _valueTypeSerializer;

    @Deprecated
    protected AsArraySerializerBase(Class<?> class_, JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        this(class_, javaType, bl, typeSerializer, beanProperty, null);
    }

    protected AsArraySerializerBase(Class<?> class_, JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
        boolean bl2;
        block3 : {
            block2 : {
                super(class_, false);
                this._elementType = javaType;
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
        this._valueTypeSerializer = typeSerializer;
        this._property = beanProperty;
        this._elementSerializer = jsonSerializer;
        this._dynamicSerializers = PropertySerializerMap.emptyMap();
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, JavaType javaType, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult = propertySerializerMap.findAndAddSerializer(javaType, serializerProvider, this._property);
        if (propertySerializerMap != serializerAndMapResult.map) {
            this._dynamicSerializers = serializerAndMapResult.map;
        }
        return serializerAndMapResult.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> class_, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult = propertySerializerMap.findAndAddSerializer(class_, serializerProvider, this._property);
        if (propertySerializerMap != serializerAndMapResult.map) {
            this._dynamicSerializers = serializerAndMapResult.map;
        }
        return serializerAndMapResult.serializer;
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        Type[] arrtype;
        ObjectNode objectNode = this.createSchemaNode("array", true);
        JavaType javaType = null;
        if (type != null && (javaType = serializerProvider.constructType(type).getContentType()) == null && type instanceof ParameterizedType && (arrtype = ((ParameterizedType)type).getActualTypeArguments()).length == 1) {
            javaType = serializerProvider.constructType(arrtype[0]);
        }
        if (javaType == null && this._elementType != null) {
            javaType = this._elementType;
        }
        if (javaType != null) {
            Class<?> class_ = javaType.getRawClass();
            JsonNode jsonNode = null;
            if (class_ != Object.class) {
                JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(javaType, this._property);
                boolean bl = jsonSerializer instanceof SchemaAware;
                jsonNode = null;
                if (bl) {
                    jsonNode = ((SchemaAware)((Object)jsonSerializer)).getSchema(serializerProvider, null);
                }
            }
            if (jsonNode == null) {
                jsonNode = JsonSchema.getDefaultSchemaNode();
            }
            objectNode.put("items", jsonNode);
        }
        return objectNode;
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        if (this._staticTyping && this._elementType != null && this._elementSerializer == null) {
            this._elementSerializer = serializerProvider.findValueSerializer(this._elementType, this._property);
        }
    }

    @Override
    public final void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartArray();
        this.serializeContents(t, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }

    protected abstract void serializeContents(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    @Override
    public final void serializeWithType(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForArray(t, jsonGenerator);
        this.serializeContents(t, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForArray(t, jsonGenerator);
    }
}

