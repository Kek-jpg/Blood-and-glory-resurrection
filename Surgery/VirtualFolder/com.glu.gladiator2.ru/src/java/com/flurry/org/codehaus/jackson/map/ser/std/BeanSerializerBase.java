/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.StackOverflowError
 *  java.lang.String
 *  java.lang.Throwable
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
import com.flurry.org.codehaus.jackson.map.ser.AnyGetterWriter;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyFilter;
import com.flurry.org.codehaus.jackson.map.ser.BeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.ser.FilterProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.schema.SchemaAware;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Type;

public abstract class BeanSerializerBase
extends SerializerBase<Object>
implements ResolvableSerializer,
SchemaAware {
    protected static final BeanPropertyWriter[] NO_PROPS = new BeanPropertyWriter[0];
    protected final AnyGetterWriter _anyGetterWriter;
    protected final BeanPropertyWriter[] _filteredProps;
    protected final Object _propertyFilterId;
    protected final BeanPropertyWriter[] _props;

    protected BeanSerializerBase(BeanSerializerBase beanSerializerBase) {
        super(beanSerializerBase._handledType, beanSerializerBase._props, beanSerializerBase._filteredProps, beanSerializerBase._anyGetterWriter, beanSerializerBase._propertyFilterId);
    }

    protected BeanSerializerBase(JavaType javaType, BeanPropertyWriter[] arrbeanPropertyWriter, BeanPropertyWriter[] arrbeanPropertyWriter2, AnyGetterWriter anyGetterWriter, Object object) {
        super(javaType);
        this._props = arrbeanPropertyWriter;
        this._filteredProps = arrbeanPropertyWriter2;
        this._anyGetterWriter = anyGetterWriter;
        this._propertyFilterId = object;
    }

    public BeanSerializerBase(Class<?> class_, BeanPropertyWriter[] arrbeanPropertyWriter, BeanPropertyWriter[] arrbeanPropertyWriter2, AnyGetterWriter anyGetterWriter, Object object) {
        super(class_);
        this._props = arrbeanPropertyWriter;
        this._filteredProps = arrbeanPropertyWriter2;
        this._anyGetterWriter = anyGetterWriter;
        this._propertyFilterId = object;
    }

    protected BeanPropertyFilter findFilter(SerializerProvider serializerProvider) throws JsonMappingException {
        Object object = this._propertyFilterId;
        FilterProvider filterProvider = serializerProvider.getFilterProvider();
        if (filterProvider == null) {
            throw new JsonMappingException("Can not resolve BeanPropertyFilter with id '" + object + "'; no FilterProvider configured");
        }
        return filterProvider.findFilter(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        ObjectNode objectNode = this.createSchemaNode("object", true);
        ObjectNode objectNode2 = objectNode.objectNode();
        int n = 0;
        do {
            if (n >= this._props.length) {
                objectNode.put("properties", objectNode2);
                return objectNode;
            }
            BeanPropertyWriter beanPropertyWriter = this._props[n];
            JavaType javaType = beanPropertyWriter.getSerializationType();
            Class<?> class_ = javaType == null ? beanPropertyWriter.getGenericPropertyType() : javaType.getRawClass();
            JsonSerializer<Object> jsonSerializer = beanPropertyWriter.getSerializer();
            if (jsonSerializer == null) {
                Class<?> class_2 = beanPropertyWriter.getRawSerializationType();
                if (class_2 == null) {
                    class_2 = beanPropertyWriter.getPropertyType();
                }
                jsonSerializer = serializerProvider.findValueSerializer(class_2, (BeanProperty)beanPropertyWriter);
            }
            JsonNode jsonNode = jsonSerializer instanceof SchemaAware ? ((SchemaAware)((Object)jsonSerializer)).getSchema(serializerProvider, (Type)class_) : JsonSchema.getDefaultSchemaNode();
            objectNode2.put(beanPropertyWriter.getName(), jsonNode);
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        int n = this._filteredProps == null ? 0 : this._filteredProps.length;
        int n2 = this._props.length;
        for (int i = 0; i < n2; ++i) {
            TypeSerializer typeSerializer;
            BeanPropertyWriter beanPropertyWriter;
            BeanPropertyWriter beanPropertyWriter2;
            BeanPropertyWriter beanPropertyWriter3 = this._props[i];
            if (beanPropertyWriter3.hasSerializer()) continue;
            JavaType javaType = beanPropertyWriter3.getSerializationType();
            if (javaType == null && !(javaType = serializerProvider.constructType(beanPropertyWriter3.getGenericPropertyType())).isFinal()) {
                if (!javaType.isContainerType() && javaType.containedTypeCount() <= 0) continue;
                beanPropertyWriter3.setNonTrivialBaseType(javaType);
                continue;
            }
            JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(javaType, (BeanProperty)beanPropertyWriter3);
            if (javaType.isContainerType() && (typeSerializer = (TypeSerializer)javaType.getContentType().getTypeHandler()) != null && jsonSerializer instanceof ContainerSerializerBase) {
                jsonSerializer = ((ContainerSerializerBase)jsonSerializer).withValueTypeSerializer(typeSerializer);
            }
            this._props[i] = beanPropertyWriter = beanPropertyWriter3.withSerializer(jsonSerializer);
            if (i >= n || (beanPropertyWriter2 = this._filteredProps[i]) == null) continue;
            this._filteredProps[i] = beanPropertyWriter2.withSerializer(jsonSerializer);
        }
        if (this._anyGetterWriter != null) {
            this._anyGetterWriter.resolve(serializerProvider);
        }
    }

    @Override
    public abstract void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void serializeFields(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        int n;
        BeanPropertyWriter[] arrbeanPropertyWriter = this._filteredProps != null && serializerProvider.getSerializationView() != null ? this._filteredProps : this._props;
        try {
            for (BeanPropertyWriter beanPropertyWriter : arrbeanPropertyWriter) {
                if (beanPropertyWriter == null) continue;
                beanPropertyWriter.serializeAsField(object, jsonGenerator, serializerProvider);
            }
            if (this._anyGetterWriter != null) {
                this._anyGetterWriter.getAndSerialize(object, jsonGenerator, serializerProvider);
            }
            return;
        }
        catch (Exception exception) {
            String string = n == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n].getName();
            this.wrapAndThrow(serializerProvider, (Throwable)exception, object, string);
            return;
        }
        catch (StackOverflowError stackOverflowError) {
            JsonMappingException jsonMappingException = new JsonMappingException("Infinite recursion (StackOverflowError)");
            String string = n == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n].getName();
            jsonMappingException.prependPath(new JsonMappingException.Reference(object, string));
            throw jsonMappingException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void serializeFieldsFiltered(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        int n;
        BeanPropertyWriter[] arrbeanPropertyWriter = this._filteredProps != null && serializerProvider.getSerializationView() != null ? this._filteredProps : this._props;
        BeanPropertyFilter beanPropertyFilter = this.findFilter(serializerProvider);
        if (beanPropertyFilter == null) {
            this.serializeFields(object, jsonGenerator, serializerProvider);
            return;
        }
        int n2 = 0;
        try {
            n = arrbeanPropertyWriter.length;
        }
        catch (Exception exception) {
            String string = n2 == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n2].getName();
            this.wrapAndThrow(serializerProvider, (Throwable)exception, object, string);
            return;
        }
        catch (StackOverflowError stackOverflowError) {
            JsonMappingException jsonMappingException = new JsonMappingException("Infinite recursion (StackOverflowError)");
            String string = n2 == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n2].getName();
            jsonMappingException.prependPath(new JsonMappingException.Reference(object, string));
            throw jsonMappingException;
        }
        do {
            if (n2 >= n) {
                if (this._anyGetterWriter == null) return;
                this._anyGetterWriter.getAndSerialize(object, jsonGenerator, serializerProvider);
                return;
            }
            BeanPropertyWriter beanPropertyWriter = arrbeanPropertyWriter[n2];
            if (beanPropertyWriter != null) {
                beanPropertyFilter.serializeAsField(object, jsonGenerator, serializerProvider, beanPropertyWriter);
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForObject(object, jsonGenerator);
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
        } else {
            this.serializeFields(object, jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForObject(object, jsonGenerator);
    }
}

