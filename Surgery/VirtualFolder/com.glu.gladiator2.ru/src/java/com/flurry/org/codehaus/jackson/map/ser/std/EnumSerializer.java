/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Type
 *  java.util.Collection
 *  java.util.Iterator
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.SerializableString;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.introspect.BasicBeanDescription;
import com.flurry.org.codehaus.jackson.map.ser.std.ScalarSerializerBase;
import com.flurry.org.codehaus.jackson.map.util.EnumValues;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

@JacksonStdImpl
public class EnumSerializer
extends ScalarSerializerBase<Enum<?>> {
    protected final EnumValues _values;

    public EnumSerializer(EnumValues enumValues) {
        super(Enum.class, false);
        this._values = enumValues;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static EnumSerializer construct(Class<Enum<?>> class_, SerializationConfig serializationConfig, BasicBeanDescription basicBeanDescription) {
        EnumValues enumValues;
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        if (serializationConfig.isEnabled(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING)) {
            enumValues = EnumValues.constructFromToString(class_, annotationIntrospector);
            do {
                return new EnumSerializer(enumValues);
                break;
            } while (true);
        }
        enumValues = EnumValues.constructFromName(class_, annotationIntrospector);
        return new EnumSerializer(enumValues);
    }

    public EnumValues getEnumValues() {
        return this._values;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_ENUMS_USING_INDEX)) {
            return this.createSchemaNode("integer", true);
        }
        ObjectNode objectNode = this.createSchemaNode("string", true);
        if (type == null) return objectNode;
        if (!serializerProvider.constructType(type).isEnumType()) return objectNode;
        ArrayNode arrayNode = objectNode.putArray("enum");
        Iterator iterator = this._values.values().iterator();
        while (iterator.hasNext()) {
            arrayNode.add(((SerializedString)iterator.next()).getValue());
        }
        return objectNode;
    }

    @Override
    public final void serialize(Enum<?> enum_, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_ENUMS_USING_INDEX)) {
            jsonGenerator.writeNumber(enum_.ordinal());
            return;
        }
        jsonGenerator.writeString(this._values.serializedValueFor(enum_));
    }
}

