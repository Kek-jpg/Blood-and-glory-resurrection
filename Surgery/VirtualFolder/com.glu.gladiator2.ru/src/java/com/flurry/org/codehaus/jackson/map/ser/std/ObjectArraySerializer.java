/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.InvocationTargetException
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
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.StdArraySerializers;
import com.flurry.org.codehaus.jackson.map.type.ArrayType;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.schema.SchemaAware;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class ObjectArraySerializer
extends StdArraySerializers.ArraySerializerBase<Object[]>
implements ResolvableSerializer {
    protected PropertySerializerMap _dynamicSerializers;
    protected JsonSerializer<Object> _elementSerializer;
    protected final JavaType _elementType;
    protected final boolean _staticTyping;

    @Deprecated
    public ObjectArraySerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        super(javaType, bl, typeSerializer, beanProperty, null);
    }

    public ObjectArraySerializer(JavaType javaType, boolean bl, TypeSerializer typeSerializer, BeanProperty beanProperty, JsonSerializer<Object> jsonSerializer) {
        super(Object[].class, typeSerializer, beanProperty);
        this._elementType = javaType;
        this._staticTyping = bl;
        this._dynamicSerializers = PropertySerializerMap.emptyMap();
        this._elementSerializer = jsonSerializer;
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
    public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new ObjectArraySerializer(this._elementType, this._staticTyping, typeSerializer, this._property, this._elementSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        Class<?> class_;
        ObjectNode objectNode;
        block3 : {
            block2 : {
                JavaType javaType;
                objectNode = this.createSchemaNode("array", true);
                if (type == null || !(javaType = serializerProvider.constructType(type)).isArrayType()) break block2;
                class_ = ((ArrayType)javaType).getContentType().getRawClass();
                if (class_ != Object.class) break block3;
                objectNode.put("items", JsonSchema.getDefaultSchemaNode());
            }
            return objectNode;
        }
        JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(class_, this._property);
        JsonNode jsonNode = jsonSerializer instanceof SchemaAware ? ((SchemaAware)((Object)jsonSerializer)).getSchema(serializerProvider, null) : JsonSchema.getDefaultSchemaNode();
        objectNode.put("items", jsonNode);
        return objectNode;
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        if (this._staticTyping && this._elementSerializer == null) {
            this._elementSerializer = serializerProvider.findValueSerializer(this._elementType, this._property);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void serializeContents(Object[] var1, JsonGenerator var2_3, SerializerProvider var3_2) throws IOException, JsonGenerationException {
        var4_4 = var1.length;
        if (var4_4 == 0) {
            return;
        }
        if (this._elementSerializer != null) {
            this.serializeContentsUsing(var1, var2_3, var3_2, this._elementSerializer);
            return;
        }
        if (this._valueTypeSerializer != null) {
            this.serializeTypedContents(var1, var2_3, var3_2);
            return;
        }
        var5_5 = 0;
        var6_6 = null;
        try {
            var10_7 = this._dynamicSerializers;
lbl14: // 2 sources:
            do {
                if (var5_5 >= var4_4) return;
                var6_6 = var1[var5_5];
                if (var6_6 == null) {
                    var3_2.defaultSerializeNull(var2_3);
                } else {
                    var11_8 = var6_6.getClass();
                    var12_10 = var10_7.serializerFor(var11_8);
                    if (var12_10 == null) {
                        if (this._elementType.hasGenericTypes()) {
                            var12_10 = this._findAndAddDynamic(var10_7, var3_2.constructSpecializedType(this._elementType, var11_8), var3_2);
                        } else {
                            var13_9 = this._findAndAddDynamic(var10_7, var11_8, var3_2);
                            var12_10 = var13_9;
                        }
                    }
                    var12_10.serialize(var6_6, var2_3, var3_2);
                }
                break;
            } while (true);
        }
        catch (IOException var9_11) {
            throw var9_11;
        }
        catch (Exception var7_12) {
            var8_13 = var7_12;
            while (var8_13 instanceof InvocationTargetException && var8_13.getCause() != null) {
                var8_13 = var8_13.getCause();
            }
            if (var8_13 instanceof Error == false) throw JsonMappingException.wrapWithPath((Throwable)var8_13, var6_6, var5_5);
            throw (Error)var8_13;
        }
        ++var5_5;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeContentsUsing(Object[] arrobject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException, JsonGenerationException {
        int n = arrobject.length;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        int n2 = 0;
        Object object = null;
        while (n2 < n) {
            try {
                object = arrobject[n2];
                if (object == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else if (typeSerializer == null) {
                    jsonSerializer.serialize(object, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
                }
            }
            catch (IOException iOException) {
                throw iOException;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                while (exception2 instanceof InvocationTargetException && exception2.getCause() != null) {
                    exception2 = exception2.getCause();
                }
                if (exception2 instanceof Error) {
                    throw (Error)exception2;
                }
                throw JsonMappingException.wrapWithPath((Throwable)exception2, object, n2);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void serializeTypedContents(Object[] arrobject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        PropertySerializerMap propertySerializerMap;
        int n = arrobject.length;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        int n2 = 0;
        Object object = null;
        try {
            propertySerializerMap = this._dynamicSerializers;
        }
        catch (IOException iOException) {
            throw iOException;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            while (exception2 instanceof InvocationTargetException && exception2.getCause() != null) {
                exception2 = exception2.getCause();
            }
            if (exception2 instanceof Error) {
                throw (Error)exception2;
            }
            throw JsonMappingException.wrapWithPath((Throwable)exception2, object, n2);
        }
        do {
            if (n2 < n) {
                object = arrobject[n2];
                if (object == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else {
                    Class class_ = object.getClass();
                    JsonSerializer<Object> jsonSerializer = propertySerializerMap.serializerFor(class_);
                    if (jsonSerializer == null) {
                        jsonSerializer = this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
                    }
                    jsonSerializer.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
                }
            } else {
                return;
            }
            ++n2;
        } while (true);
    }
}

