/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializable;
import com.flurry.org.codehaus.jackson.map.JsonSerializableWithType;
import com.flurry.org.codehaus.jackson.map.ObjectMapper;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.SerializerBase;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.schema.JsonSerializableSchema;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@JacksonStdImpl
public class SerializableSerializer
extends SerializerBase<JsonSerializable> {
    public static final SerializableSerializer instance = new SerializableSerializer();

    protected SerializableSerializer() {
        super(JsonSerializable.class);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) throws JsonMappingException {
        ObjectNode objectNode = this.createObjectNode();
        String string = "any";
        String string2 = null;
        String string3 = null;
        if (type != null) {
            Class<?> class_ = TypeFactory.type(type).getRawClass();
            boolean bl = class_.isAnnotationPresent(JsonSerializableSchema.class);
            string2 = null;
            string3 = null;
            if (bl) {
                JsonSerializableSchema jsonSerializableSchema = (JsonSerializableSchema)class_.getAnnotation(JsonSerializableSchema.class);
                string = jsonSerializableSchema.schemaType();
                boolean bl2 = "##irrelevant".equals((Object)jsonSerializableSchema.schemaObjectPropertiesDefinition());
                string3 = null;
                if (!bl2) {
                    string3 = jsonSerializableSchema.schemaObjectPropertiesDefinition();
                }
                boolean bl3 = "##irrelevant".equals((Object)jsonSerializableSchema.schemaItemDefinition());
                string2 = null;
                if (!bl3) {
                    string2 = jsonSerializableSchema.schemaItemDefinition();
                }
            }
        }
        objectNode.put("type", string);
        if (string3 != null) {
            objectNode.put("properties", new ObjectMapper().readValue(string3, JsonNode.class));
        }
        if (string2 == null) return objectNode;
        try {
            objectNode.put("items", new ObjectMapper().readValue(string2, JsonNode.class));
            return objectNode;
        }
        catch (IOException iOException) {
            throw new IllegalStateException((Throwable)iOException);
        }
        catch (IOException iOException) {
            throw new IllegalStateException((Throwable)iOException);
        }
    }

    @Override
    public void serialize(JsonSerializable jsonSerializable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonSerializable.serialize(jsonGenerator, serializerProvider);
    }

    @Override
    public final void serializeWithType(JsonSerializable jsonSerializable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        if (jsonSerializable instanceof JsonSerializableWithType) {
            ((JsonSerializableWithType)jsonSerializable).serializeWithType(jsonGenerator, serializerProvider, typeSerializer);
            return;
        }
        this.serialize(jsonSerializable, jsonGenerator, serializerProvider);
    }
}

