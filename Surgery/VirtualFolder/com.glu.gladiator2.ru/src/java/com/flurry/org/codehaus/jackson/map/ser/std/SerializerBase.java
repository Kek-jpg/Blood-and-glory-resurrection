/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Error
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.schema.SchemaAware;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public abstract class SerializerBase<T>
extends JsonSerializer<T>
implements SchemaAware {
    protected final Class<T> _handledType;

    protected SerializerBase(JavaType javaType) {
        this._handledType = javaType.getRawClass();
    }

    protected SerializerBase(Class<T> class_) {
        this._handledType = class_;
    }

    protected SerializerBase(Class<?> class_, boolean bl) {
        this._handledType = class_;
    }

    protected ObjectNode createObjectNode() {
        return JsonNodeFactory.instance.objectNode();
    }

    protected ObjectNode createSchemaNode(String string) {
        ObjectNode objectNode = this.createObjectNode();
        objectNode.put("type", string);
        return objectNode;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected ObjectNode createSchemaNode(String string, boolean bl) {
        ObjectNode objectNode = this.createSchemaNode(string);
        if (!bl) {
            boolean bl2 = !bl;
            objectNode.put("required", bl2);
        }
        return objectNode;
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        return this.createSchemaNode("string");
    }

    @Override
    public final Class<T> handledType() {
        return this._handledType;
    }

    protected boolean isDefaultSerializer(JsonSerializer<?> jsonSerializer) {
        return jsonSerializer != null && jsonSerializer.getClass().getAnnotation(JacksonStdImpl.class) != null;
    }

    @Override
    public abstract void serialize(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    /*
     * Enabled aggressive block sorting
     */
    public void wrapAndThrow(SerializerProvider serializerProvider, Throwable throwable, Object object, int n) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl = serializerProvider == null || serializerProvider.isEnabled(SerializationConfig.Feature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            if (bl && throwable instanceof JsonMappingException) throw JsonMappingException.wrapWithPath(throwable, object, n);
            {
                throw (IOException)throwable;
            }
        } else {
            if (bl || !(throwable instanceof RuntimeException)) throw JsonMappingException.wrapWithPath(throwable, object, n);
            {
                throw (RuntimeException)throwable;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void wrapAndThrow(SerializerProvider serializerProvider, Throwable throwable, Object object, String string) throws IOException {
        while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        boolean bl = serializerProvider == null || serializerProvider.isEnabled(SerializationConfig.Feature.WRAP_EXCEPTIONS);
        if (throwable instanceof IOException) {
            if (bl && throwable instanceof JsonMappingException) throw JsonMappingException.wrapWithPath(throwable, object, string);
            {
                throw (IOException)throwable;
            }
        } else {
            if (bl || !(throwable instanceof RuntimeException)) throw JsonMappingException.wrapWithPath(throwable, object, string);
            {
                throw (RuntimeException)throwable;
            }
        }
    }

    @Deprecated
    public void wrapAndThrow(Throwable throwable, Object object, int n) throws IOException {
        this.wrapAndThrow(null, throwable, object, n);
    }

    @Deprecated
    public void wrapAndThrow(Throwable throwable, Object object, String string) throws IOException {
        this.wrapAndThrow(null, throwable, object, string);
    }
}

