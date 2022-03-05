/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.Void
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import java.io.IOException;

@JacksonStdImpl
public class ClassDeserializer
extends StdScalarDeserializer<Class<?>> {
    public ClassDeserializer() {
        super(Class.class);
    }

    @Override
    public Class<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText();
            if (string.indexOf(46) < 0) {
                if ("int".equals((Object)string)) {
                    return Integer.TYPE;
                }
                if ("long".equals((Object)string)) {
                    return Long.TYPE;
                }
                if ("float".equals((Object)string)) {
                    return Float.TYPE;
                }
                if ("double".equals((Object)string)) {
                    return Double.TYPE;
                }
                if ("boolean".equals((Object)string)) {
                    return Boolean.TYPE;
                }
                if ("byte".equals((Object)string)) {
                    return Byte.TYPE;
                }
                if ("char".equals((Object)string)) {
                    return Character.TYPE;
                }
                if ("short".equals((Object)string)) {
                    return Short.TYPE;
                }
                if ("void".equals((Object)string)) {
                    return Void.TYPE;
                }
            }
            try {
                Class class_ = Class.forName((String)jsonParser.getText());
                return class_;
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw deserializationContext.instantiationException(this._valueClass, classNotFoundException);
            }
        }
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }
}

