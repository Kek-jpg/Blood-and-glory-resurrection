/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Byte
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.reflect.Type
 *  java.math.BigDecimal
 *  java.math.BigInteger
 *  java.sql.Date
 *  java.sql.Time
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.std.DateSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.NonTypedScalarSerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.ScalarSerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;

public class StdSerializers {
    protected StdSerializers() {
    }

    @JacksonStdImpl
    public static final class BooleanSerializer
    extends NonTypedScalarSerializerBase<Boolean> {
        final boolean _forPrimitive;

        public BooleanSerializer(boolean bl) {
            super(Boolean.class);
            this._forPrimitive = bl;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            boolean bl;
            if (!this._forPrimitive) {
                bl = true;
                do {
                    return this.createSchemaNode("boolean", bl);
                    break;
                } while (true);
            }
            bl = false;
            return this.createSchemaNode("boolean", bl);
        }

        @Override
        public void serialize(Boolean bl, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeBoolean(bl);
        }
    }

    @JacksonStdImpl
    @Deprecated
    public static final class CalendarSerializer
    extends com.flurry.org.codehaus.jackson.map.ser.std.CalendarSerializer {
    }

    @JacksonStdImpl
    public static final class DoubleSerializer
    extends NonTypedScalarSerializerBase<Double> {
        static final DoubleSerializer instance = new DoubleSerializer();

        public DoubleSerializer() {
            super(Double.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("number", true);
        }

        @Override
        public void serialize(Double d, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeNumber(d);
        }
    }

    @JacksonStdImpl
    public static final class FloatSerializer
    extends ScalarSerializerBase<Float> {
        static final FloatSerializer instance = new FloatSerializer();

        public FloatSerializer() {
            super(Float.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("number", true);
        }

        @Override
        public void serialize(Float f, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeNumber(f.floatValue());
        }
    }

    @JacksonStdImpl
    public static final class IntLikeSerializer
    extends ScalarSerializerBase<Number> {
        static final IntLikeSerializer instance = new IntLikeSerializer();

        public IntLikeSerializer() {
            super(Number.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("integer", true);
        }

        @Override
        public void serialize(Number number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeNumber(number.intValue());
        }
    }

    @JacksonStdImpl
    public static final class IntegerSerializer
    extends NonTypedScalarSerializerBase<Integer> {
        public IntegerSerializer() {
            super(Integer.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("integer", true);
        }

        @Override
        public void serialize(Integer n, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeNumber(n);
        }
    }

    @JacksonStdImpl
    public static final class LongSerializer
    extends ScalarSerializerBase<Long> {
        static final LongSerializer instance = new LongSerializer();

        public LongSerializer() {
            super(Long.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("number", true);
        }

        @Override
        public void serialize(Long l, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeNumber(l);
        }
    }

    @JacksonStdImpl
    public static final class NumberSerializer
    extends ScalarSerializerBase<Number> {
        public static final NumberSerializer instance = new NumberSerializer();

        public NumberSerializer() {
            super(Number.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("number", true);
        }

        @Override
        public void serialize(Number number, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (number instanceof BigDecimal) {
                jsonGenerator.writeNumber((BigDecimal)number);
                return;
            }
            if (number instanceof BigInteger) {
                jsonGenerator.writeNumber((BigInteger)number);
                return;
            }
            if (number instanceof Integer) {
                jsonGenerator.writeNumber(number.intValue());
                return;
            }
            if (number instanceof Long) {
                jsonGenerator.writeNumber(number.longValue());
                return;
            }
            if (number instanceof Double) {
                jsonGenerator.writeNumber(number.doubleValue());
                return;
            }
            if (number instanceof Float) {
                jsonGenerator.writeNumber(number.floatValue());
                return;
            }
            if (number instanceof Byte || number instanceof Short) {
                jsonGenerator.writeNumber(number.intValue());
                return;
            }
            jsonGenerator.writeNumber(number.toString());
        }
    }

    @JacksonStdImpl
    @Deprecated
    public static final class SerializableSerializer
    extends com.flurry.org.codehaus.jackson.map.ser.std.SerializableSerializer {
    }

    @JacksonStdImpl
    @Deprecated
    public static final class SerializableWithTypeSerializer
    extends com.flurry.org.codehaus.jackson.map.ser.std.SerializableWithTypeSerializer {
    }

    @JacksonStdImpl
    public static final class SqlDateSerializer
    extends ScalarSerializerBase<Date> {
        public SqlDateSerializer() {
            super(Date.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("string", true);
        }

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeString(date.toString());
        }
    }

    @JacksonStdImpl
    public static final class SqlTimeSerializer
    extends ScalarSerializerBase<Time> {
        public SqlTimeSerializer() {
            super(Time.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("string", true);
        }

        @Override
        public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeString(time.toString());
        }
    }

    @JacksonStdImpl
    @Deprecated
    public static final class StringSerializer
    extends NonTypedScalarSerializerBase<String> {
        public StringSerializer() {
            super(String.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("string", true);
        }

        @Override
        public void serialize(String string, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeString(string);
        }
    }

    @JacksonStdImpl
    @Deprecated
    public static final class UtilDateSerializer
    extends DateSerializer {
    }

}

