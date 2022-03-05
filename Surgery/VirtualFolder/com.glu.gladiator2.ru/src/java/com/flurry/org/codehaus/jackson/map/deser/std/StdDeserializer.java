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
 *  java.lang.Float
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.NoSuchFieldError
 *  java.lang.Number
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.StackTraceElement
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.sql.Date
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.io.NumberInput;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.std.StdScalarDeserializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public abstract class StdDeserializer<T>
extends JsonDeserializer<T> {
    protected final Class<?> _valueClass;

    /*
     * Enabled aggressive block sorting
     */
    protected StdDeserializer(JavaType javaType) {
        Class<?> class_ = javaType == null ? null : javaType.getRawClass();
        this._valueClass = class_;
    }

    protected StdDeserializer(Class<?> class_) {
        this._valueClass = class_;
    }

    protected static final double parseDouble(String string) throws NumberFormatException {
        if ("2.2250738585072012e-308".equals((Object)string)) {
            return Double.MIN_NORMAL;
        }
        return Double.parseDouble((String)string);
    }

    protected final Boolean _parseBoolean(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (jsonToken == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            if (jsonParser.getNumberType() == JsonParser.NumberType.INT) {
                if (jsonParser.getIntValue() == 0) {
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
            return this._parseBooleanFromNumber(jsonParser, deserializationContext);
        }
        if (jsonToken == JsonToken.VALUE_NULL) {
            return (Boolean)this.getNullValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            if ("true".equals((Object)string)) {
                return Boolean.TRUE;
            }
            if ("false".equals((Object)string)) {
                return Boolean.FALSE;
            }
            if (string.length() == 0) {
                return (Boolean)this.getEmptyValue();
            }
            throw deserializationContext.weirdStringException(this._valueClass, "only \"true\" or \"false\" recognized");
        }
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final boolean _parseBooleanFromNumber(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getNumberType() == JsonParser.NumberType.LONG) {
            Boolean bl;
            if (jsonParser.getLongValue() == 0L) {
                bl = Boolean.FALSE;
                do {
                    return bl;
                    break;
                } while (true);
            }
            bl = Boolean.TRUE;
            return bl;
        }
        String string = jsonParser.getText();
        if (!"0.0".equals((Object)string) && !"0".equals((Object)string)) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final boolean _parseBooleanPrimitive(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_TRUE) return true;
        if (jsonToken == JsonToken.VALUE_FALSE) {
            return false;
        }
        if (jsonToken == JsonToken.VALUE_NULL) {
            return false;
        }
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            if (jsonParser.getNumberType() != JsonParser.NumberType.INT) {
                return this._parseBooleanFromNumber(jsonParser, deserializationContext);
            }
            if (jsonParser.getIntValue() != 0) return true;
            return false;
        }
        if (jsonToken != JsonToken.VALUE_STRING) {
            throw deserializationContext.mappingException(this._valueClass, jsonToken);
        }
        String string = jsonParser.getText().trim();
        if ("true".equals((Object)string)) {
            return true;
        }
        if ("false".equals((Object)string) || string.length() == 0) return Boolean.FALSE;
        {
            throw deserializationContext.weirdStringException(this._valueClass, "only \"true\" or \"false\" recognized");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Byte _parseByte(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT || jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getByteValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            try {
                if (string.length() == 0) {
                    return (Byte)this.getEmptyValue();
                }
                int n = NumberInput.parseInt(string);
                if (n >= -128 && n <= 127) return (byte)n;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Byte value");
            }
            throw deserializationContext.weirdStringException(this._valueClass, "overflow, value can not be represented as 8-bit value");
        }
        if (jsonToken != JsonToken.VALUE_NULL) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        return (Byte)this.getNullValue();
    }

    protected Date _parseDate(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            return new Date(jsonParser.getLongValue());
        }
        if (jsonToken == JsonToken.VALUE_NULL) {
            return (Date)this.getNullValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            try {
                String string = jsonParser.getText().trim();
                if (string.length() == 0) {
                    return (Date)this.getEmptyValue();
                }
                Date date = deserializationContext.parseDate(string);
                return date;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid representation (error: " + illegalArgumentException.getMessage() + ")");
            }
        }
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Double _parseDouble(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return jsonParser.getDoubleValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getDoubleValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            if (string.length() == 0) {
                return (Double)this.getEmptyValue();
            }
            switch (string.charAt(0)) {
                case 'I': {
                    if ("Infinity".equals((Object)string)) return Double.POSITIVE_INFINITY;
                    if (!"INF".equals((Object)string)) break;
                    return Double.POSITIVE_INFINITY;
                }
                case 'N': {
                    if (!"NaN".equals((Object)string)) break;
                    return Double.NaN;
                }
                case '-': {
                    if ("-Infinity".equals((Object)string)) return Double.NEGATIVE_INFINITY;
                    if (!"-INF".equals((Object)string)) break;
                    return Double.NEGATIVE_INFINITY;
                }
            }
            try {
                return StdDeserializer.parseDouble(string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Double value");
            }
        }
        if (jsonToken != JsonToken.VALUE_NULL) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        return (Double)this.getNullValue();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final double _parseDoublePrimitive(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        double d = 0.0;
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return jsonParser.getDoubleValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getDoubleValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            if (string.length() == 0) return d;
            switch (string.charAt(0)) {
                case 'I': {
                    if ("Infinity".equals((Object)string)) return Double.POSITIVE_INFINITY;
                    if (!"INF".equals((Object)string)) break;
                    return Double.POSITIVE_INFINITY;
                }
                case 'N': {
                    if (!"NaN".equals((Object)string)) break;
                    return Double.NaN;
                }
                case '-': {
                    if ("-Infinity".equals((Object)string)) return Double.NEGATIVE_INFINITY;
                    if (!"-INF".equals((Object)string)) break;
                    return Double.NEGATIVE_INFINITY;
                }
            }
            try {
                return StdDeserializer.parseDouble(string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid double value");
            }
        }
        if (jsonToken == JsonToken.VALUE_NULL) return d;
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Float _parseFloat(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return Float.valueOf((float)jsonParser.getFloatValue());
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return Float.valueOf((float)jsonParser.getFloatValue());
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            if (string.length() == 0) {
                return (Float)this.getEmptyValue();
            }
            switch (string.charAt(0)) {
                case 'I': {
                    if ("Infinity".equals((Object)string)) return Float.valueOf((float)Float.POSITIVE_INFINITY);
                    if (!"INF".equals((Object)string)) break;
                    return Float.valueOf((float)Float.POSITIVE_INFINITY);
                }
                case 'N': {
                    if (!"NaN".equals((Object)string)) break;
                    return Float.valueOf((float)Float.NaN);
                }
                case '-': {
                    if ("-Infinity".equals((Object)string)) return Float.valueOf((float)Float.NEGATIVE_INFINITY);
                    if (!"-INF".equals((Object)string)) break;
                    return Float.valueOf((float)Float.NEGATIVE_INFINITY);
                }
            }
            try {
                return Float.valueOf((float)Float.parseFloat((String)string));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Float value");
            }
        }
        if (jsonToken != JsonToken.VALUE_NULL) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        return (Float)this.getNullValue();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final float _parseFloatPrimitive(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        float f;
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return jsonParser.getFloatValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getFloatValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            int n = string.length();
            f = 0.0f;
            if (n == 0) return f;
            switch (string.charAt(0)) {
                case 'I': {
                    if ("Infinity".equals((Object)string)) return Float.POSITIVE_INFINITY;
                    if (!"INF".equals((Object)string)) break;
                    return Float.POSITIVE_INFINITY;
                }
                case 'N': {
                    if (!"NaN".equals((Object)string)) break;
                    return Float.NaN;
                }
                case '-': {
                    if ("-Infinity".equals((Object)string)) return Float.NEGATIVE_INFINITY;
                    if (!"-INF".equals((Object)string)) break;
                    return Float.NEGATIVE_INFINITY;
                }
            }
            try {
                return Float.parseFloat((String)string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid float value");
            }
        }
        JsonToken jsonToken2 = JsonToken.VALUE_NULL;
        f = 0.0f;
        if (jsonToken == jsonToken2) return f;
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final int _parseIntPrimitive(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        int n;
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return jsonParser.getIntValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getIntValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string;
            int n2;
            block5 : {
                string = jsonParser.getText().trim();
                try {
                    n2 = string.length();
                    if (n2 <= 9) break block5;
                    long l = Long.parseLong((String)string);
                    if (l < Integer.MIN_VALUE) throw deserializationContext.weirdStringException(this._valueClass, "Overflow: numeric value (" + string + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
                    if (l <= Integer.MAX_VALUE) return (int)l;
                    throw deserializationContext.weirdStringException(this._valueClass, "Overflow: numeric value (" + string + ") out of range of int (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw deserializationContext.weirdStringException(this._valueClass, "not a valid int value");
                }
            }
            n = 0;
            if (n2 == 0) return n;
            return NumberInput.parseInt(string);
        }
        JsonToken jsonToken2 = JsonToken.VALUE_NULL;
        n = 0;
        if (jsonToken == jsonToken2) return n;
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Integer _parseInteger(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return jsonParser.getIntValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getIntValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string;
            int n;
            block5 : {
                long l;
                block6 : {
                    string = jsonParser.getText().trim();
                    try {
                        n = string.length();
                        if (n <= 9) break block5;
                        l = Long.parseLong((String)string);
                        if (l < Integer.MIN_VALUE) throw deserializationContext.weirdStringException(this._valueClass, "Overflow: numeric value (" + string + ") out of range of Integer (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
                        if (l <= Integer.MAX_VALUE) break block6;
                        throw deserializationContext.weirdStringException(this._valueClass, "Overflow: numeric value (" + string + ") out of range of Integer (" + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE + ")");
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        throw deserializationContext.weirdStringException(this._valueClass, "not a valid Integer value");
                    }
                }
                int n2 = (int)l;
                return n2;
            }
            if (n != 0) return NumberInput.parseInt(string);
            return (Integer)this.getEmptyValue();
        }
        if (jsonToken != JsonToken.VALUE_NULL) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        return (Integer)this.getNullValue();
    }

    protected final Long _parseLong(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT || jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getLongValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            if (string.length() == 0) {
                return (Long)this.getEmptyValue();
            }
            try {
                Long l = NumberInput.parseLong(string);
                return l;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Long value");
            }
        }
        if (jsonToken == JsonToken.VALUE_NULL) {
            return (Long)this.getNullValue();
        }
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final long _parseLongPrimitive(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        long l = 0L;
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT) return jsonParser.getLongValue();
        if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getLongValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            if (string.length() == 0) return l;
            try {
                return NumberInput.parseLong(string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid long value");
            }
        }
        if (jsonToken == JsonToken.VALUE_NULL) return l;
        throw deserializationContext.mappingException(this._valueClass, jsonToken);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Short _parseShort(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.VALUE_NUMBER_INT || jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return jsonParser.getShortValue();
        }
        if (jsonToken == JsonToken.VALUE_STRING) {
            String string = jsonParser.getText().trim();
            try {
                if (string.length() == 0) {
                    return (Short)this.getEmptyValue();
                }
                int n = NumberInput.parseInt(string);
                if (n >= -32768 && n <= 32767) return (short)n;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Short value");
            }
            throw deserializationContext.weirdStringException(this._valueClass, "overflow, value can not be represented as 16-bit value");
        }
        if (jsonToken != JsonToken.VALUE_NULL) throw deserializationContext.mappingException(this._valueClass, jsonToken);
        return (Short)this.getNullValue();
    }

    protected final short _parseShortPrimitive(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        int n = this._parseIntPrimitive(jsonParser, deserializationContext);
        if (n < -32768 || n > 32767) {
            throw deserializationContext.weirdStringException(this._valueClass, "overflow, value can not be represented as 16-bit value");
        }
        return (short)n;
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    protected JsonDeserializer<Object> findDeserializer(DeserializationConfig deserializationConfig, DeserializerProvider deserializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return deserializerProvider.findValueDeserializer(deserializationConfig, javaType, beanProperty);
    }

    public Class<?> getValueClass() {
        return this._valueClass;
    }

    public JavaType getValueType() {
        return null;
    }

    protected void handleUnknownProperty(JsonParser jsonParser, DeserializationContext deserializationContext, Object class_, String string) throws IOException, JsonProcessingException {
        if (class_ == null) {
            class_ = this.getValueClass();
        }
        if (deserializationContext.handleUnknownProperty(jsonParser, (JsonDeserializer<?>)this, class_, string)) {
            return;
        }
        this.reportUnknownProperty(deserializationContext, class_, string);
        jsonParser.skipChildren();
    }

    protected boolean isDefaultSerializer(JsonDeserializer<?> jsonDeserializer) {
        return jsonDeserializer != null && jsonDeserializer.getClass().getAnnotation(JacksonStdImpl.class) != null;
    }

    protected void reportUnknownProperty(DeserializationContext deserializationContext, Object object, String string) throws IOException, JsonProcessingException {
        if (deserializationContext.isEnabled(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES)) {
            throw deserializationContext.unknownFieldException(object, string);
        }
    }

    @JacksonStdImpl
    public static class BigDecimalDeserializer
    extends StdScalarDeserializer<BigDecimal> {
        public BigDecimalDeserializer() {
            super(BigDecimal.class);
        }

        @Override
        public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_NUMBER_INT || jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
                return jsonParser.getDecimalValue();
            }
            if (jsonToken == JsonToken.VALUE_STRING) {
                String string = jsonParser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                try {
                    BigDecimal bigDecimal = new BigDecimal(string);
                    return bigDecimal;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw deserializationContext.weirdStringException(this._valueClass, "not a valid representation");
                }
            }
            throw deserializationContext.mappingException(this._valueClass, jsonToken);
        }
    }

    @JacksonStdImpl
    public static class BigIntegerDeserializer
    extends StdScalarDeserializer<BigInteger> {
        public BigIntegerDeserializer() {
            super(BigInteger.class);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public BigInteger deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String string;
            block9 : {
                JsonToken jsonToken;
                block8 : {
                    jsonToken = jsonParser.getCurrentToken();
                    if (jsonToken != JsonToken.VALUE_NUMBER_INT) break block8;
                    switch (jsonParser.getNumberType()) {
                        default: {
                            break block9;
                        }
                        case INT: 
                        case LONG: {
                            return BigInteger.valueOf((long)jsonParser.getLongValue());
                        }
                    }
                }
                if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
                    return jsonParser.getDecimalValue().toBigInteger();
                }
                if (jsonToken != JsonToken.VALUE_STRING) {
                    throw deserializationContext.mappingException(this._valueClass, jsonToken);
                }
            }
            if ((string = jsonParser.getText().trim()).length() == 0) {
                return null;
            }
            try {
                return new BigInteger(string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid representation");
            }
        }
    }

    @JacksonStdImpl
    public static final class BooleanDeserializer
    extends PrimitiveOrWrapperDeserializer<Boolean> {
        public BooleanDeserializer(Class<Boolean> class_, Boolean bl) {
            super(class_, bl);
        }

        @Override
        public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseBoolean(jsonParser, deserializationContext);
        }

        public Boolean deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return this._parseBoolean(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class ByteDeserializer
    extends PrimitiveOrWrapperDeserializer<Byte> {
        public ByteDeserializer(Class<Byte> class_, Byte by) {
            super(class_, by);
        }

        @Override
        public Byte deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseByte(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class CharacterDeserializer
    extends PrimitiveOrWrapperDeserializer<Character> {
        public CharacterDeserializer(Class<Character> class_, Character c) {
            super(class_, c);
        }

        @Override
        public Character deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                int n = jsonParser.getIntValue();
                if (n >= 0 && n <= 65535) {
                    return Character.valueOf((char)((char)n));
                }
            } else if (jsonToken == JsonToken.VALUE_STRING) {
                String string = jsonParser.getText();
                if (string.length() == 1) {
                    return Character.valueOf((char)string.charAt(0));
                }
                if (string.length() == 0) {
                    return (Character)this.getEmptyValue();
                }
            }
            throw deserializationContext.mappingException(this._valueClass, jsonToken);
        }
    }

    @JacksonStdImpl
    public static final class DoubleDeserializer
    extends PrimitiveOrWrapperDeserializer<Double> {
        public DoubleDeserializer(Class<Double> class_, Double d) {
            super(class_, d);
        }

        @Override
        public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseDouble(jsonParser, deserializationContext);
        }

        public Double deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return this._parseDouble(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class FloatDeserializer
    extends PrimitiveOrWrapperDeserializer<Float> {
        public FloatDeserializer(Class<Float> class_, Float f) {
            super(class_, f);
        }

        @Override
        public Float deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseFloat(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class IntegerDeserializer
    extends PrimitiveOrWrapperDeserializer<Integer> {
        public IntegerDeserializer(Class<Integer> class_, Integer n) {
            super(class_, n);
        }

        @Override
        public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseInteger(jsonParser, deserializationContext);
        }

        public Integer deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return this._parseInteger(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class LongDeserializer
    extends PrimitiveOrWrapperDeserializer<Long> {
        public LongDeserializer(Class<Long> class_, Long l) {
            super(class_, l);
        }

        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseLong(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    public static final class NumberDeserializer
    extends StdScalarDeserializer<Number> {
        public NumberDeserializer() {
            super(Number.class);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Number deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                if (!deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS)) return jsonParser.getNumberValue();
                return jsonParser.getBigIntegerValue();
            }
            if (jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
                if (!deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS)) return jsonParser.getDoubleValue();
                return jsonParser.getDecimalValue();
            }
            if (jsonToken != JsonToken.VALUE_STRING) {
                throw deserializationContext.mappingException(this._valueClass, jsonToken);
            }
            String string = jsonParser.getText().trim();
            try {
                if (string.indexOf(46) >= 0) {
                    if (!deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS)) return new Double(string);
                    return new BigDecimal(string);
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid number");
            }
            if (deserializationContext.isEnabled(DeserializationConfig.Feature.USE_BIG_INTEGER_FOR_INTS)) {
                return new BigInteger(string);
            }
            long l = Long.parseLong((String)string);
            if (l > Integer.MAX_VALUE) return l;
            if (l < Integer.MIN_VALUE) return l;
            return (int)l;
        }

        @Override
        public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
                default: {
                    return typeDeserializer.deserializeTypedFromScalar(jsonParser, deserializationContext);
                }
                case 1: 
                case 2: 
                case 3: 
            }
            return this.deserialize(jsonParser, deserializationContext);
        }
    }

    protected static abstract class PrimitiveOrWrapperDeserializer<T>
    extends StdScalarDeserializer<T> {
        final T _nullValue;

        protected PrimitiveOrWrapperDeserializer(Class<T> class_, T t) {
            super(class_);
            this._nullValue = t;
        }

        @Override
        public final T getNullValue() {
            return this._nullValue;
        }
    }

    @JacksonStdImpl
    public static final class ShortDeserializer
    extends PrimitiveOrWrapperDeserializer<Short> {
        public ShortDeserializer(Class<Short> class_, Short s) {
            super(class_, s);
        }

        @Override
        public Short deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return this._parseShort(jsonParser, deserializationContext);
        }
    }

    public static class SqlDateDeserializer
    extends StdScalarDeserializer<java.sql.Date> {
        public SqlDateDeserializer() {
            super(java.sql.Date.class);
        }

        @Override
        public java.sql.Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Date date = this._parseDate(jsonParser, deserializationContext);
            if (date == null) {
                return null;
            }
            return new java.sql.Date(date.getTime());
        }
    }

    public static class StackTraceElementDeserializer
    extends StdScalarDeserializer<StackTraceElement> {
        public StackTraceElementDeserializer() {
            super(StackTraceElement.class);
        }

        @Override
        public StackTraceElement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.START_OBJECT) {
                JsonToken jsonToken2;
                String string = "";
                String string2 = "";
                String string3 = "";
                int n = -1;
                while ((jsonToken2 = jsonParser.nextValue()) != JsonToken.END_OBJECT) {
                    String string4 = jsonParser.getCurrentName();
                    if ("className".equals((Object)string4)) {
                        string = jsonParser.getText();
                        continue;
                    }
                    if ("fileName".equals((Object)string4)) {
                        string3 = jsonParser.getText();
                        continue;
                    }
                    if ("lineNumber".equals((Object)string4)) {
                        if (jsonToken2.isNumeric()) {
                            n = jsonParser.getIntValue();
                            continue;
                        }
                        throw JsonMappingException.from(jsonParser, "Non-numeric token (" + (Object)((Object)jsonToken2) + ") for property 'lineNumber'");
                    }
                    if ("methodName".equals((Object)string4)) {
                        string2 = jsonParser.getText();
                        continue;
                    }
                    if ("nativeMethod".equals((Object)string4)) continue;
                    this.handleUnknownProperty(jsonParser, deserializationContext, (Object)this._valueClass, string4);
                }
                return new StackTraceElement(string, string2, string3, n);
            }
            throw deserializationContext.mappingException(this._valueClass, jsonToken);
        }
    }

}

