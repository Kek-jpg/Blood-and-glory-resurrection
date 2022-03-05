/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.reflect.Constructor
 *  java.lang.reflect.Method
 *  java.util.Calendar
 *  java.util.Date
 *  java.util.UUID
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.io.NumberInput;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializer;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.EnumResolver;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public abstract class StdKeyDeserializer
extends KeyDeserializer {
    protected final Class<?> _keyClass;

    protected StdKeyDeserializer(Class<?> class_) {
        this._keyClass = class_;
    }

    protected abstract Object _parse(String var1, DeserializationContext var2) throws Exception;

    protected double _parseDouble(String string) throws IllegalArgumentException {
        return NumberInput.parseDouble(string);
    }

    protected int _parseInt(String string) throws IllegalArgumentException {
        return Integer.parseInt((String)string);
    }

    protected long _parseLong(String string) throws IllegalArgumentException {
        return Long.parseLong((String)string);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final Object deserializeKey(String string, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (string == null) {
            return null;
        }
        try {
            Object object = this._parse(string, deserializationContext);
            Object object2 = object;
            if (object2 != null) return object2;
        }
        catch (Exception exception) {
            throw deserializationContext.weirdKeyException(this._keyClass, string, "not a valid representation: " + exception.getMessage());
        }
        throw deserializationContext.weirdKeyException(this._keyClass, string, "not a valid representation");
    }

    public Class<?> getKeyClass() {
        return this._keyClass;
    }

    static final class BoolKD
    extends StdKeyDeserializer {
        BoolKD() {
            super(Boolean.class);
        }

        public Boolean _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            if ("true".equals((Object)string)) {
                return Boolean.TRUE;
            }
            if ("false".equals((Object)string)) {
                return Boolean.FALSE;
            }
            throw deserializationContext.weirdKeyException(this._keyClass, string, "value not 'true' or 'false'");
        }
    }

    static final class ByteKD
    extends StdKeyDeserializer {
        ByteKD() {
            super(Byte.class);
        }

        public Byte _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            int n = this._parseInt(string);
            if (n < -128 || n > 127) {
                throw deserializationContext.weirdKeyException(this._keyClass, string, "overflow, value can not be represented as 8-bit value");
            }
            return (byte)n;
        }
    }

    static final class CalendarKD
    extends StdKeyDeserializer {
        protected CalendarKD() {
            super(Calendar.class);
        }

        public Calendar _parse(String string, DeserializationContext deserializationContext) throws IllegalArgumentException, JsonMappingException {
            Date date = deserializationContext.parseDate(string);
            if (date == null) {
                return null;
            }
            return deserializationContext.constructCalendar(date);
        }
    }

    static final class CharKD
    extends StdKeyDeserializer {
        CharKD() {
            super(Character.class);
        }

        public Character _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            if (string.length() == 1) {
                return Character.valueOf((char)string.charAt(0));
            }
            throw deserializationContext.weirdKeyException(this._keyClass, string, "can only convert 1-character Strings");
        }
    }

    static final class DateKD
    extends StdKeyDeserializer {
        protected DateKD() {
            super(Date.class);
        }

        public Date _parse(String string, DeserializationContext deserializationContext) throws IllegalArgumentException, JsonMappingException {
            return deserializationContext.parseDate(string);
        }
    }

    static final class DoubleKD
    extends StdKeyDeserializer {
        DoubleKD() {
            super(Double.class);
        }

        public Double _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            return this._parseDouble(string);
        }
    }

    static final class EnumKD
    extends StdKeyDeserializer {
        protected final AnnotatedMethod _factory;
        protected final EnumResolver<?> _resolver;

        protected EnumKD(EnumResolver<?> enumResolver, AnnotatedMethod annotatedMethod) {
            super(enumResolver.getEnumClass());
            this._resolver = enumResolver;
            this._factory = annotatedMethod;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Object _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            Object obj;
            void var3_5;
            if (this._factory != null) {
                try {
                    Object object;
                    Object object2 = object = this._factory.call1(string);
                    return var3_5;
                }
                catch (Exception exception) {
                    ClassUtil.unwrapAndThrowAsIAE(exception);
                }
            }
            if ((obj = this._resolver.findEnum(string)) != null) return var3_5;
            {
                throw deserializationContext.weirdKeyException(this._keyClass, string, "not one of values for Enum class");
            }
        }
    }

    static final class FloatKD
    extends StdKeyDeserializer {
        FloatKD() {
            super(Float.class);
        }

        public Float _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            return Float.valueOf((float)((float)this._parseDouble(string)));
        }
    }

    static final class IntKD
    extends StdKeyDeserializer {
        IntKD() {
            super(Integer.class);
        }

        public Integer _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            return this._parseInt(string);
        }
    }

    static final class LongKD
    extends StdKeyDeserializer {
        LongKD() {
            super(Long.class);
        }

        public Long _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            return this._parseLong(string);
        }
    }

    static final class ShortKD
    extends StdKeyDeserializer {
        ShortKD() {
            super(Integer.class);
        }

        public Short _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            int n = this._parseInt(string);
            if (n < -32768 || n > 32767) {
                throw deserializationContext.weirdKeyException(this._keyClass, string, "overflow, value can not be represented as 16-bit value");
            }
            return (short)n;
        }
    }

    static final class StringCtorKeyDeserializer
    extends StdKeyDeserializer {
        protected final Constructor<?> _ctor;

        public StringCtorKeyDeserializer(Constructor<?> constructor) {
            super(constructor.getDeclaringClass());
            this._ctor = constructor;
        }

        @Override
        public Object _parse(String string, DeserializationContext deserializationContext) throws Exception {
            return this._ctor.newInstance(new Object[]{string});
        }
    }

    static final class StringFactoryKeyDeserializer
    extends StdKeyDeserializer {
        final Method _factoryMethod;

        public StringFactoryKeyDeserializer(Method method) {
            super(method.getDeclaringClass());
            this._factoryMethod = method;
        }

        @Override
        public Object _parse(String string, DeserializationContext deserializationContext) throws Exception {
            return this._factoryMethod.invoke(null, new Object[]{string});
        }
    }

    static final class StringKD
    extends StdKeyDeserializer {
        private static final StringKD sObject;
        private static final StringKD sString;

        static {
            sString = new StringKD(String.class);
            sObject = new StringKD(Object.class);
        }

        private StringKD(Class<?> class_) {
            super(class_);
        }

        public static StringKD forType(Class<?> class_) {
            if (class_ == String.class) {
                return sString;
            }
            if (class_ == Object.class) {
                return sObject;
            }
            return new StringKD(class_);
        }

        @Override
        public String _parse(String string, DeserializationContext deserializationContext) throws JsonMappingException {
            return string;
        }
    }

    static final class UuidKD
    extends StdKeyDeserializer {
        protected UuidKD() {
            super(UUID.class);
        }

        public UUID _parse(String string, DeserializationContext deserializationContext) throws IllegalArgumentException, JsonMappingException {
            return UUID.fromString((String)string);
        }
    }

}

