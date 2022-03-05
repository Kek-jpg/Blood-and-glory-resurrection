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
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.reflect.Type
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.Base64Variant;
import com.flurry.org.codehaus.jackson.Base64Variants;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.deser.std.StdDeserializer;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.util.ArrayBuilders;
import com.flurry.org.codehaus.jackson.map.util.ObjectBuffer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class PrimitiveArrayDeserializers {
    static final PrimitiveArrayDeserializers instance = new PrimitiveArrayDeserializers();
    HashMap<JavaType, JsonDeserializer<Object>> _allDeserializers = new HashMap();

    protected PrimitiveArrayDeserializers() {
        this.add(Boolean.TYPE, new BooleanDeser());
        this.add(Byte.TYPE, new ByteDeser());
        this.add(Short.TYPE, new ShortDeser());
        this.add(Integer.TYPE, new IntDeser());
        this.add(Long.TYPE, new LongDeser());
        this.add(Float.TYPE, new FloatDeser());
        this.add(Double.TYPE, new DoubleDeser());
        this.add(String.class, new StringDeser());
        this.add(Character.TYPE, new CharDeser());
    }

    private void add(Class<?> class_, JsonDeserializer<?> jsonDeserializer) {
        this._allDeserializers.put((Object)TypeFactory.defaultInstance().constructType((Type)class_), jsonDeserializer);
    }

    public static HashMap<JavaType, JsonDeserializer<Object>> getAll() {
        return PrimitiveArrayDeserializers.instance._allDeserializers;
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    static abstract class Base<T>
    extends StdDeserializer<T> {
        protected Base(Class<T> class_) {
            super(class_);
        }

        @Override
        public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
            return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
        }
    }

    @JacksonStdImpl
    static final class BooleanDeser
    extends Base<boolean[]> {
        public BooleanDeser() {
            super(boolean[].class);
        }

        private final boolean[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            boolean[] arrbl = new boolean[]{this._parseBooleanPrimitive(jsonParser, deserializationContext)};
            return arrbl;
        }

        @Override
        public boolean[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return BooleanDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.BooleanBuilder booleanBuilder = deserializationContext.getArrayBuilders().getBooleanBuilder();
            boolean[] arrbl = (boolean[])booleanBuilder.resetAndStart();
            int n = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                boolean bl = this._parseBooleanPrimitive(jsonParser, deserializationContext);
                if (n >= arrbl.length) {
                    arrbl = booleanBuilder.appendCompletedChunk(arrbl, n);
                    n = 0;
                }
                int n2 = n + 1;
                arrbl[n] = bl;
                n = n2;
            }
            return booleanBuilder.completeAndClearBuffer(arrbl, n);
        }
    }

    @JacksonStdImpl
    static final class ByteDeser
    extends Base<byte[]> {
        public ByteDeser() {
            super(byte[].class);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private final byte[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            byte by;
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_NUMBER_INT || jsonToken == JsonToken.VALUE_NUMBER_FLOAT) {
                by = jsonParser.getByteValue();
                do {
                    return new byte[]{by};
                    break;
                } while (true);
            }
            if (jsonToken != JsonToken.VALUE_NULL) {
                throw deserializationContext.mappingException(this._valueClass.getComponentType());
            }
            by = 0;
            return new byte[]{by};
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public byte[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_STRING) {
                return jsonParser.getBinaryValue(deserializationContext.getBase64Variant());
            }
            if (jsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                Object object = jsonParser.getEmbeddedObject();
                if (object == null) {
                    return null;
                }
                if (object instanceof byte[]) {
                    return (byte[])object;
                }
            }
            if (!jsonParser.isExpectedStartArrayToken()) {
                return ByteDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.ByteBuilder byteBuilder = deserializationContext.getArrayBuilders().getByteBuilder();
            byte[] arrby = (byte[])byteBuilder.resetAndStart();
            int n = 0;
            JsonToken jsonToken2;
            while ((jsonToken2 = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
                byte by;
                if (jsonToken2 == JsonToken.VALUE_NUMBER_INT || jsonToken2 == JsonToken.VALUE_NUMBER_FLOAT) {
                    by = jsonParser.getByteValue();
                } else {
                    if (jsonToken2 != JsonToken.VALUE_NULL) {
                        throw deserializationContext.mappingException(this._valueClass.getComponentType());
                    }
                    by = 0;
                }
                if (n >= arrby.length) {
                    arrby = byteBuilder.appendCompletedChunk(arrby, n);
                    n = 0;
                }
                int n2 = n + 1;
                arrby[n] = by;
                n = n2;
            }
            return byteBuilder.completeAndClearBuffer(arrby, n);
        }
    }

    @JacksonStdImpl
    static final class CharDeser
    extends Base<char[]> {
        public CharDeser() {
            super(char[].class);
        }

        @Override
        public char[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            JsonToken jsonToken = jsonParser.getCurrentToken();
            if (jsonToken == JsonToken.VALUE_STRING) {
                char[] arrc = jsonParser.getTextCharacters();
                int n = jsonParser.getTextOffset();
                int n2 = jsonParser.getTextLength();
                char[] arrc2 = new char[n2];
                System.arraycopy((Object)arrc, (int)n, (Object)arrc2, (int)0, (int)n2);
                return arrc2;
            }
            if (jsonParser.isExpectedStartArrayToken()) {
                JsonToken jsonToken2;
                StringBuilder stringBuilder = new StringBuilder(64);
                while ((jsonToken2 = jsonParser.nextToken()) != JsonToken.END_ARRAY) {
                    if (jsonToken2 != JsonToken.VALUE_STRING) {
                        throw deserializationContext.mappingException(Character.TYPE);
                    }
                    String string = jsonParser.getText();
                    if (string.length() != 1) {
                        throw JsonMappingException.from(jsonParser, "Can not convert a JSON String of length " + string.length() + " into a char element of char array");
                    }
                    stringBuilder.append(string.charAt(0));
                }
                return stringBuilder.toString().toCharArray();
            }
            if (jsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                Object object = jsonParser.getEmbeddedObject();
                if (object == null) {
                    return null;
                }
                if (object instanceof char[]) {
                    return (char[])object;
                }
                if (object instanceof String) {
                    return ((String)object).toCharArray();
                }
                if (object instanceof byte[]) {
                    return Base64Variants.getDefaultVariant().encode((byte[])object, false).toCharArray();
                }
            }
            throw deserializationContext.mappingException(this._valueClass);
        }
    }

    @JacksonStdImpl
    static final class DoubleDeser
    extends Base<double[]> {
        public DoubleDeser() {
            super(double[].class);
        }

        private final double[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            double[] arrd = new double[]{this._parseDoublePrimitive(jsonParser, deserializationContext)};
            return arrd;
        }

        @Override
        public double[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return DoubleDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.DoubleBuilder doubleBuilder = deserializationContext.getArrayBuilders().getDoubleBuilder();
            double[] arrd = (double[])doubleBuilder.resetAndStart();
            int n = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                double d = this._parseDoublePrimitive(jsonParser, deserializationContext);
                if (n >= arrd.length) {
                    arrd = doubleBuilder.appendCompletedChunk(arrd, n);
                    n = 0;
                }
                int n2 = n + 1;
                arrd[n] = d;
                n = n2;
            }
            return doubleBuilder.completeAndClearBuffer(arrd, n);
        }
    }

    @JacksonStdImpl
    static final class FloatDeser
    extends Base<float[]> {
        public FloatDeser() {
            super(float[].class);
        }

        private final float[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            float[] arrf = new float[]{this._parseFloatPrimitive(jsonParser, deserializationContext)};
            return arrf;
        }

        @Override
        public float[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return FloatDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.FloatBuilder floatBuilder = deserializationContext.getArrayBuilders().getFloatBuilder();
            float[] arrf = (float[])floatBuilder.resetAndStart();
            int n = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                float f = this._parseFloatPrimitive(jsonParser, deserializationContext);
                if (n >= arrf.length) {
                    arrf = floatBuilder.appendCompletedChunk(arrf, n);
                    n = 0;
                }
                int n2 = n + 1;
                arrf[n] = f;
                n = n2;
            }
            return floatBuilder.completeAndClearBuffer(arrf, n);
        }
    }

    @JacksonStdImpl
    static final class IntDeser
    extends Base<int[]> {
        public IntDeser() {
            super(int[].class);
        }

        private final int[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            int[] arrn = new int[]{this._parseIntPrimitive(jsonParser, deserializationContext)};
            return arrn;
        }

        @Override
        public int[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return IntDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.IntBuilder intBuilder = deserializationContext.getArrayBuilders().getIntBuilder();
            int[] arrn = (int[])intBuilder.resetAndStart();
            int n = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                int n2 = this._parseIntPrimitive(jsonParser, deserializationContext);
                if (n >= arrn.length) {
                    arrn = intBuilder.appendCompletedChunk(arrn, n);
                    n = 0;
                }
                int n3 = n + 1;
                arrn[n] = n2;
                n = n3;
            }
            return intBuilder.completeAndClearBuffer(arrn, n);
        }
    }

    @JacksonStdImpl
    static final class LongDeser
    extends Base<long[]> {
        public LongDeser() {
            super(long[].class);
        }

        private final long[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            long[] arrl = new long[]{this._parseLongPrimitive(jsonParser, deserializationContext)};
            return arrl;
        }

        @Override
        public long[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return LongDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.LongBuilder longBuilder = deserializationContext.getArrayBuilders().getLongBuilder();
            long[] arrl = (long[])longBuilder.resetAndStart();
            int n = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                long l = this._parseLongPrimitive(jsonParser, deserializationContext);
                if (n >= arrl.length) {
                    arrl = longBuilder.appendCompletedChunk(arrl, n);
                    n = 0;
                }
                int n2 = n + 1;
                arrl[n] = l;
                n = n2;
            }
            return longBuilder.completeAndClearBuffer(arrl, n);
        }
    }

    @JacksonStdImpl
    static final class ShortDeser
    extends Base<short[]> {
        public ShortDeser() {
            super(short[].class);
        }

        private final short[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                return null;
            }
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                throw deserializationContext.mappingException(this._valueClass);
            }
            short[] arrs = new short[]{this._parseShortPrimitive(jsonParser, deserializationContext)};
            return arrs;
        }

        @Override
        public short[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return ShortDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ArrayBuilders.ShortBuilder shortBuilder = deserializationContext.getArrayBuilders().getShortBuilder();
            short[] arrs = (short[])shortBuilder.resetAndStart();
            int n = 0;
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                short s = this._parseShortPrimitive(jsonParser, deserializationContext);
                if (n >= arrs.length) {
                    arrs = shortBuilder.appendCompletedChunk(arrs, n);
                    n = 0;
                }
                int n2 = n + 1;
                arrs[n] = s;
                n = n2;
            }
            return shortBuilder.completeAndClearBuffer(arrs, n);
        }
    }

    @JacksonStdImpl
    static final class StringDeser
    extends Base<String[]> {
        public StringDeser() {
            super(String[].class);
        }

        /*
         * Enabled aggressive block sorting
         */
        private final String[] handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING && deserializationContext.isEnabled(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) && jsonParser.getText().length() == 0) {
                    return null;
                }
                throw deserializationContext.mappingException(this._valueClass);
            }
            String[] arrstring = new String[1];
            JsonToken jsonToken = jsonParser.getCurrentToken();
            JsonToken jsonToken2 = JsonToken.VALUE_NULL;
            String string = null;
            if (jsonToken != jsonToken2) {
                string = jsonParser.getText();
            }
            arrstring[0] = string;
            return arrstring;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public String[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (!jsonParser.isExpectedStartArrayToken()) {
                return StringDeser.super.handleNonArray(jsonParser, deserializationContext);
            }
            ObjectBuffer objectBuffer = deserializationContext.leaseObjectBuffer();
            Object[] arrobject = objectBuffer.resetAndStart();
            int n = 0;
            do {
                JsonToken jsonToken;
                if ((jsonToken = jsonParser.nextToken()) == JsonToken.END_ARRAY) {
                    String[] arrstring = objectBuffer.completeAndClearBuffer(arrobject, n, String.class);
                    deserializationContext.returnObjectBuffer(objectBuffer);
                    return arrstring;
                }
                String string = jsonToken == JsonToken.VALUE_NULL ? null : jsonParser.getText();
                if (n >= arrobject.length) {
                    arrobject = objectBuffer.appendCompletedChunk(arrobject);
                    n = 0;
                }
                int n2 = n + 1;
                arrobject[n] = string;
                n = n2;
            } while (true);
        }
    }

}

