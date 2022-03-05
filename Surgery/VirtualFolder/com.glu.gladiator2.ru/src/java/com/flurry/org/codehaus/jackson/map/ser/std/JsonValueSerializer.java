/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Method
 *  java.lang.reflect.Modifier
 *  java.lang.reflect.Type
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
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.schema.SchemaAware;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

@JacksonStdImpl
public class JsonValueSerializer
extends SerializerBase<Object>
implements ResolvableSerializer,
SchemaAware {
    protected final Method _accessorMethod;
    protected boolean _forceTypeInformation;
    protected final BeanProperty _property;
    protected JsonSerializer<Object> _valueSerializer;

    public JsonValueSerializer(Method method, JsonSerializer<Object> jsonSerializer, BeanProperty beanProperty) {
        super(Object.class);
        this._accessorMethod = method;
        this._valueSerializer = jsonSerializer;
        this._property = beanProperty;
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        if (this._valueSerializer instanceof SchemaAware) {
            return ((SchemaAware)((Object)this._valueSerializer)).getSchema(serializerProvider, null);
        }
        return JsonSchema.getDefaultSchemaNode();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean isNaturalTypeWithStdHandling(JavaType javaType, JsonSerializer<?> jsonSerializer) {
        block3 : {
            block2 : {
                Class<?> class_ = javaType.getRawClass();
                if (!javaType.isPrimitive() ? class_ != String.class && class_ != Integer.class && class_ != Boolean.class && class_ != Double.class : class_ != Integer.TYPE && class_ != Boolean.TYPE && class_ != Double.TYPE) break block2;
                if (jsonSerializer.getClass().getAnnotation(JacksonStdImpl.class) != null) break block3;
            }
            return false;
        }
        return true;
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        if (this._valueSerializer == null && (serializerProvider.isEnabled(SerializationConfig.Feature.USE_STATIC_TYPING) || Modifier.isFinal((int)this._accessorMethod.getReturnType().getModifiers()))) {
            JavaType javaType = serializerProvider.constructType(this._accessorMethod.getGenericReturnType());
            this._valueSerializer = serializerProvider.findTypedValueSerializer(javaType, false, this._property);
            this._forceTypeInformation = this.isNaturalTypeWithStdHandling(javaType, this._valueSerializer);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        try {
            Object object2 = this._accessorMethod.invoke(object, new Object[0]);
            if (object2 == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                return;
            }
            JsonSerializer<Object> jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer = serializerProvider.findTypedValueSerializer(object2.getClass(), true, this._property);
            }
            jsonSerializer.serialize(object2, jsonGenerator, serializerProvider);
            return;
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
            throw JsonMappingException.wrapWithPath((Throwable)exception2, object, this._accessorMethod.getName() + "()");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        try {
            Object object2 = this._accessorMethod.invoke(object, new Object[0]);
            if (object2 == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                return;
            }
            JsonSerializer<Object> jsonSerializer = this._valueSerializer;
            if (jsonSerializer != null) {
                if (this._forceTypeInformation) {
                    typeSerializer.writeTypePrefixForScalar(object, jsonGenerator);
                }
                jsonSerializer.serializeWithType(object2, jsonGenerator, serializerProvider, typeSerializer);
                if (!this._forceTypeInformation) return;
                {
                    typeSerializer.writeTypeSuffixForScalar(object, jsonGenerator);
                    return;
                }
            }
            serializerProvider.findTypedValueSerializer(object2.getClass(), true, this._property).serialize(object2, jsonGenerator, serializerProvider);
            return;
        }
        catch (IOException iOException) {
            throw iOException;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            while (exception2 instanceof InvocationTargetException && exception2.getCause() != null) {
                exception2 = exception2.getCause();
            }
            if (!(exception2 instanceof Error)) throw JsonMappingException.wrapWithPath((Throwable)exception2, object, this._accessorMethod.getName() + "()");
            {
                throw (Error)exception2;
            }
        }
    }

    public String toString() {
        return "(@JsonValue serializer for method " + (Object)this._accessorMethod.getDeclaringClass() + "#" + this._accessorMethod.getName() + ")";
    }
}

