/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.Field
 *  java.lang.reflect.Method
 *  java.lang.reflect.Type
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.ser.impl.PropertySerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.impl.UnwrappingBeanPropertyWriter;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BeanPropertyWriter
implements BeanProperty {
    protected final Method _accessorMethod;
    protected final JavaType _cfgSerializationType;
    protected final Annotations _contextAnnotations;
    protected final JavaType _declaredType;
    protected PropertySerializerMap _dynamicSerializers;
    protected final Field _field;
    protected Class<?>[] _includeInViews;
    protected HashMap<Object, Object> _internalSettings;
    protected final AnnotatedMember _member;
    protected final SerializedString _name;
    protected JavaType _nonTrivialBaseType;
    protected final JsonSerializer<Object> _serializer;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected TypeSerializer _typeSerializer;

    /*
     * Enabled aggressive block sorting
     */
    public BeanPropertyWriter(AnnotatedMember annotatedMember, Annotations annotations, SerializedString serializedString, JavaType javaType, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JavaType javaType2, Method method, Field field, boolean bl, Object object) {
        this._member = annotatedMember;
        this._contextAnnotations = annotations;
        this._name = serializedString;
        this._declaredType = javaType;
        this._serializer = jsonSerializer;
        PropertySerializerMap propertySerializerMap = jsonSerializer == null ? PropertySerializerMap.emptyMap() : null;
        this._dynamicSerializers = propertySerializerMap;
        this._typeSerializer = typeSerializer;
        this._cfgSerializationType = javaType2;
        this._accessorMethod = method;
        this._field = field;
        this._suppressNulls = bl;
        this._suppressableValue = object;
    }

    public BeanPropertyWriter(AnnotatedMember annotatedMember, Annotations annotations, String string, JavaType javaType, JsonSerializer<Object> jsonSerializer, TypeSerializer typeSerializer, JavaType javaType2, Method method, Field field, boolean bl, Object object) {
        super(annotatedMember, annotations, new SerializedString(string), javaType, jsonSerializer, typeSerializer, javaType2, method, field, bl, object);
    }

    protected BeanPropertyWriter(BeanPropertyWriter beanPropertyWriter) {
        super(beanPropertyWriter, beanPropertyWriter._serializer);
    }

    protected BeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, JsonSerializer<Object> jsonSerializer) {
        this._serializer = jsonSerializer;
        this._member = beanPropertyWriter._member;
        this._contextAnnotations = beanPropertyWriter._contextAnnotations;
        this._declaredType = beanPropertyWriter._declaredType;
        this._accessorMethod = beanPropertyWriter._accessorMethod;
        this._field = beanPropertyWriter._field;
        if (beanPropertyWriter._internalSettings != null) {
            this._internalSettings = new HashMap(beanPropertyWriter._internalSettings);
        }
        this._name = beanPropertyWriter._name;
        this._cfgSerializationType = beanPropertyWriter._cfgSerializationType;
        this._dynamicSerializers = beanPropertyWriter._dynamicSerializers;
        this._suppressNulls = beanPropertyWriter._suppressNulls;
        this._suppressableValue = beanPropertyWriter._suppressableValue;
        this._includeInViews = beanPropertyWriter._includeInViews;
        this._typeSerializer = beanPropertyWriter._typeSerializer;
        this._nonTrivialBaseType = beanPropertyWriter._nonTrivialBaseType;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> class_, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult = this._nonTrivialBaseType != null ? propertySerializerMap.findAndAddSerializer(serializerProvider.constructSpecializedType(this._nonTrivialBaseType, class_), serializerProvider, (BeanProperty)this) : propertySerializerMap.findAndAddSerializer(class_, serializerProvider, (BeanProperty)this);
        if (propertySerializerMap != serializerAndMapResult.map) {
            this._dynamicSerializers = serializerAndMapResult.map;
        }
        return serializerAndMapResult.serializer;
    }

    protected void _reportSelfReference(Object object) throws JsonMappingException {
        throw new JsonMappingException("Direct self-reference leading to cycle");
    }

    public final Object get(Object object) throws Exception {
        if (this._accessorMethod != null) {
            return this._accessorMethod.invoke(object, new Object[0]);
        }
        return this._field.get(object);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._member.getAnnotation(class_);
    }

    @Override
    public <A extends Annotation> A getContextAnnotation(Class<A> class_) {
        return this._contextAnnotations.get(class_);
    }

    public Type getGenericPropertyType() {
        if (this._accessorMethod != null) {
            return this._accessorMethod.getGenericReturnType();
        }
        return this._field.getGenericType();
    }

    public Object getInternalSetting(Object object) {
        if (this._internalSettings == null) {
            return null;
        }
        return this._internalSettings.get(object);
    }

    @Override
    public AnnotatedMember getMember() {
        return this._member;
    }

    @Override
    public String getName() {
        return this._name.getValue();
    }

    public Class<?> getPropertyType() {
        if (this._accessorMethod != null) {
            return this._accessorMethod.getReturnType();
        }
        return this._field.getType();
    }

    public Class<?> getRawSerializationType() {
        if (this._cfgSerializationType == null) {
            return null;
        }
        return this._cfgSerializationType.getRawClass();
    }

    public JavaType getSerializationType() {
        return this._cfgSerializationType;
    }

    public SerializedString getSerializedName() {
        return this._name;
    }

    public JsonSerializer<Object> getSerializer() {
        return this._serializer;
    }

    @Override
    public JavaType getType() {
        return this._declaredType;
    }

    public Class<?>[] getViews() {
        return this._includeInViews;
    }

    public boolean hasSerializer() {
        return this._serializer != null;
    }

    public Object removeInternalSetting(Object object) {
        HashMap<Object, Object> hashMap = this._internalSettings;
        Object object2 = null;
        if (hashMap != null) {
            object2 = this._internalSettings.remove(object);
            if (this._internalSettings.size() == 0) {
                this._internalSettings = null;
            }
        }
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        JsonSerializer<Object> jsonSerializer;
        Object object2 = this.get(object);
        if (object2 == null) {
            if (this._suppressNulls) return;
            {
                jsonGenerator.writeFieldName(this._name);
                serializerProvider.defaultSerializeNull(jsonGenerator);
            }
            return;
        }
        if (object2 == object) {
            this._reportSelfReference(object);
        }
        if (this._suppressableValue != null && this._suppressableValue.equals(object2)) return;
        {
            Class class_;
            PropertySerializerMap propertySerializerMap;
            jsonSerializer = this._serializer;
            if (jsonSerializer == null && (jsonSerializer = (propertySerializerMap = this._dynamicSerializers).serializerFor(class_ = object2.getClass())) == null) {
                jsonSerializer = this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
            }
            jsonGenerator.writeFieldName(this._name);
            if (this._typeSerializer == null) {
                jsonSerializer.serialize(object2, jsonGenerator, serializerProvider);
                return;
            }
        }
        jsonSerializer.serializeWithType(object2, jsonGenerator, serializerProvider, this._typeSerializer);
    }

    public Object setInternalSetting(Object object, Object object2) {
        if (this._internalSettings == null) {
            this._internalSettings = new HashMap();
        }
        return this._internalSettings.put(object, object2);
    }

    public void setNonTrivialBaseType(JavaType javaType) {
        this._nonTrivialBaseType = javaType;
    }

    public void setViews(Class<?>[] arrclass) {
        this._includeInViews = arrclass;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder.append("property '").append(this.getName()).append("' (");
        if (this._accessorMethod != null) {
            stringBuilder.append("via method ").append(this._accessorMethod.getDeclaringClass().getName()).append("#").append(this._accessorMethod.getName());
        } else {
            stringBuilder.append("field \"").append(this._field.getDeclaringClass().getName()).append("#").append(this._field.getName());
        }
        if (this._serializer == null) {
            stringBuilder.append(", no static serializer");
        } else {
            stringBuilder.append(", static serializer of type " + this._serializer.getClass().getName());
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public BeanPropertyWriter unwrappingWriter() {
        return new UnwrappingBeanPropertyWriter(this);
    }

    public BeanPropertyWriter withSerializer(JsonSerializer<Object> jsonSerializer) {
        if (this.getClass() != BeanPropertyWriter.class) {
            throw new IllegalStateException("BeanPropertyWriter sub-class does not override 'withSerializer()'; needs to!");
        }
        return new BeanPropertyWriter((BeanPropertyWriter)this, jsonSerializer);
    }
}

