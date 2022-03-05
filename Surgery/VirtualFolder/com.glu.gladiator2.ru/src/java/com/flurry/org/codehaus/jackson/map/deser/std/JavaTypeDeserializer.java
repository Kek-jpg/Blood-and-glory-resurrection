/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public class JavaTypeDeserializer
extends StdScalarDeserializer<JavaType> {
    public JavaTypeDeserializer() {
        super(JavaType.class);
    }

    @Override
    public JavaType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            if (string.length() == 0) {
                return (JavaType)this.getEmptyValue();
            }
            return deserializationContext.getTypeFactory().constructFromCanonical(string);
        }
        if (jsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
            return (JavaType)jsonParser.getEmbeddedObject();
        }
        throw deserializationContext.mappingException(this._valueClass);
    }
}

