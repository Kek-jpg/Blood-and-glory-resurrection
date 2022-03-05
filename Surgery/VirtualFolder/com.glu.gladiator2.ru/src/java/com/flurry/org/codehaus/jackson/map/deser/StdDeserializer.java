/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.Base64Variants;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

@Deprecated
public abstract class StdDeserializer<T>
extends com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer<T> {
    protected StdDeserializer(JavaType javaType) {
        super(javaType);
    }

    protected StdDeserializer(Class<?> class_) {
        super(class_);
    }

    @JacksonStdImpl
    @Deprecated
    public class CalendarDeserializer
    extends com.flurry.org.codehaus.jackson.map.deser.std.CalendarDeserializer {
    }

    @JacksonStdImpl
    @Deprecated
    public class ClassDeserializer
    extends com.flurry.org.codehaus.jackson.map.deser.std.ClassDeserializer {
    }

    @JacksonStdImpl
    @Deprecated
    public static final class StringDeserializer
    extends StdScalarDeserializer<String> {
        public StringDeserializer() {
            super(String.class);
        }

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_STRING) {
                return jsonParser.getText();
            }
            if (jsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                Object object = jsonParser.getEmbeddedObject();
                if (object == null) {
                    return null;
                }
                if (object instanceof byte[]) {
                    return Base64Variants.getDefaultVariant().encode((byte[])object, false);
                }
                return object.toString();
            }
            if (jsonToken.isScalarValue()) {
                return jsonParser.getText();
            }
            throw deserializationContext.mappingException(this._valueClass, jsonToken);
        }

        @Override
        public String deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return this.deserialize(jsonParser, deserializationContext);
        }
    }

}

