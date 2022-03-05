/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Member
 *  java.lang.reflect.Method
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.annotate.JsonCachable;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.EnumResolver;
import java.io.IOException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@JsonCachable
public class EnumDeserializer
extends StdScalarDeserializer<Enum<?>> {
    protected final EnumResolver<?> _resolver;

    public EnumDeserializer(EnumResolver<?> enumResolver) {
        super(Enum.class);
        this._resolver = enumResolver;
    }

    public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig deserializationConfig, Class<?> class_, AnnotatedMethod annotatedMethod) {
        if (annotatedMethod.getParameterType(0) != String.class) {
            throw new IllegalArgumentException("Parameter #0 type for factory method (" + annotatedMethod + ") not suitable, must be java.lang.String");
        }
        if (deserializationConfig.isEnabled(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
            ClassUtil.checkAndFixAccess(annotatedMethod.getMember());
        }
        return new FactoryBasedDeserializer(class_, annotatedMethod);
    }

    @Override
    public Enum<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_STRING || jsonToken == JsonToken.FIELD_NAME) {
            String string = jsonParser.getText();
            Object obj = this._resolver.findEnum(string);
            if (obj == null) {
                throw deserializationContext.weirdStringException(this._resolver.getEnumClass(), "value not one of declared Enum instance names");
            }
            return obj;
        }
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            if (deserializationContext.isEnabled(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
                throw deserializationContext.mappingException("Not allowed to deserialize Enum value out of JSON number (disable DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS to allow)");
            }
            int n = jsonParser.getIntValue();
            Object obj = this._resolver.getEnum(n);
            if (obj == null) {
                throw deserializationContext.weirdNumberException(this._resolver.getEnumClass(), "index value outside legal index range [0.." + this._resolver.lastValidIndex() + "]");
            }
            return obj;
        }
        throw deserializationContext.mappingException(this._resolver.getEnumClass());
    }

    protected static class FactoryBasedDeserializer
    extends StdScalarDeserializer<Object> {
        protected final Class<?> _enumClass;
        protected final Method _factory;

        public FactoryBasedDeserializer(Class<?> class_, AnnotatedMethod annotatedMethod) {
            super(Enum.class);
            this._enumClass = class_;
            this._factory = annotatedMethod.getAnnotated();
        }

        @Override
        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken != JsonToken.VALUE_STRING && jsonToken != JsonToken.FIELD_NAME) {
                throw deserializationContext.mappingException(this._enumClass);
            }
            String string = jsonParser.getText();
            try {
                Object object = this._factory.invoke(this._enumClass, new Object[]{string});
                return object;
            }
            catch (Exception exception) {
                ClassUtil.unwrapAndThrowAsIAE(exception);
                return null;
            }
        }
    }

}

