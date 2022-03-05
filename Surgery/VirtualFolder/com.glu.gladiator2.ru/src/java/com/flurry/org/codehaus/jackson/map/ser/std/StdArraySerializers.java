/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 *  java.lang.reflect.Type
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.annotate.JacksonStdImpl;
import com.flurry.org.codehaus.jackson.map.ser.std.ContainerSerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class StdArraySerializers {
    protected StdArraySerializers() {
    }

    public static abstract class ArraySerializerBase<T>
    extends ContainerSerializerBase<T> {
        protected final BeanProperty _property;
        protected final TypeSerializer _valueTypeSerializer;

        protected ArraySerializerBase(Class<T> class_, TypeSerializer typeSerializer, BeanProperty beanProperty) {
            super(class_);
            this._valueTypeSerializer = typeSerializer;
            this._property = beanProperty;
        }

        @Override
        public final void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeStartArray();
            this.serializeContents(t, jsonGenerator, serializerProvider);
            jsonGenerator.writeEndArray();
        }

        protected abstract void serializeContents(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

        @Override
        public final void serializeWithType(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
            typeSerializer.writeTypePrefixForArray(t, jsonGenerator);
            this.serializeContents(t, jsonGenerator, serializerProvider);
            typeSerializer.writeTypeSuffixForArray(t, jsonGenerator);
        }
    }

    @JacksonStdImpl
    public static final class BooleanArraySerializer
    extends ArraySerializerBase<boolean[]> {
        public BooleanArraySerializer() {
            super(boolean[].class, null, null);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("boolean"));
            return objectNode;
        }

        @Override
        public void serializeContents(boolean[] arrbl, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n = arrbl.length;
            for (int i = 0; i < n; ++i) {
                jsonGenerator.writeBoolean(arrbl[i]);
            }
        }
    }

    @JacksonStdImpl
    public static final class ByteArraySerializer
    extends SerializerBase<byte[]> {
        public ByteArraySerializer() {
            super(byte[].class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("string"));
            return objectNode;
        }

        @Override
        public void serialize(byte[] arrby, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeBinary(arrby);
        }

        @Override
        public void serializeWithType(byte[] arrby, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
            typeSerializer.writeTypePrefixForScalar(arrby, jsonGenerator);
            jsonGenerator.writeBinary(arrby);
            typeSerializer.writeTypeSuffixForScalar(arrby, jsonGenerator);
        }
    }

    @JacksonStdImpl
    public static final class CharArraySerializer
    extends SerializerBase<char[]> {
        public CharArraySerializer() {
            super(char[].class);
        }

        private final void _writeArrayContents(JsonGenerator jsonGenerator, char[] arrc) throws IOException, JsonGenerationException {
            int n = arrc.length;
            for (int i = 0; i < n; ++i) {
                jsonGenerator.writeString(arrc, i, 1);
            }
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            ObjectNode objectNode2 = this.createSchemaNode("string");
            objectNode2.put("type", "string");
            objectNode.put("items", objectNode2);
            return objectNode;
        }

        @Override
        public void serialize(char[] arrc, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
                jsonGenerator.writeStartArray();
                CharArraySerializer.super._writeArrayContents(jsonGenerator, arrc);
                jsonGenerator.writeEndArray();
                return;
            }
            jsonGenerator.writeString(arrc, 0, arrc.length);
        }

        @Override
        public void serializeWithType(char[] arrc, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
            if (serializerProvider.isEnabled(SerializationConfig.Feature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
                typeSerializer.writeTypePrefixForArray(arrc, jsonGenerator);
                CharArraySerializer.super._writeArrayContents(jsonGenerator, arrc);
                typeSerializer.writeTypeSuffixForArray(arrc, jsonGenerator);
                return;
            }
            typeSerializer.writeTypePrefixForScalar(arrc, jsonGenerator);
            jsonGenerator.writeString(arrc, 0, arrc.length);
            typeSerializer.writeTypeSuffixForScalar(arrc, jsonGenerator);
        }
    }

    @JacksonStdImpl
    public static final class DoubleArraySerializer
    extends ArraySerializerBase<double[]> {
        public DoubleArraySerializer() {
            super(double[].class, null, null);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("number"));
            return objectNode;
        }

        @Override
        public void serializeContents(double[] arrd, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n = arrd.length;
            for (int i = 0; i < n; ++i) {
                jsonGenerator.writeNumber(arrd[i]);
            }
        }
    }

    @JacksonStdImpl
    public static final class FloatArraySerializer
    extends ArraySerializerBase<float[]> {
        public FloatArraySerializer() {
            this(null);
        }

        public FloatArraySerializer(TypeSerializer typeSerializer) {
            super(float[].class, typeSerializer, null);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new FloatArraySerializer(typeSerializer);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("number"));
            return objectNode;
        }

        @Override
        public void serializeContents(float[] arrf, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n = arrf.length;
            for (int i = 0; i < n; ++i) {
                jsonGenerator.writeNumber(arrf[i]);
            }
        }
    }

    @JacksonStdImpl
    public static final class IntArraySerializer
    extends ArraySerializerBase<int[]> {
        public IntArraySerializer() {
            super(int[].class, null, null);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("integer"));
            return objectNode;
        }

        @Override
        public void serializeContents(int[] arrn, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                jsonGenerator.writeNumber(arrn[i]);
            }
        }
    }

    @JacksonStdImpl
    public static final class LongArraySerializer
    extends ArraySerializerBase<long[]> {
        public LongArraySerializer() {
            this(null);
        }

        public LongArraySerializer(TypeSerializer typeSerializer) {
            super(long[].class, typeSerializer, null);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new LongArraySerializer(typeSerializer);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("number", true));
            return objectNode;
        }

        @Override
        public void serializeContents(long[] arrl, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n = arrl.length;
            for (int i = 0; i < n; ++i) {
                jsonGenerator.writeNumber(arrl[i]);
            }
        }
    }

    @JacksonStdImpl
    public static final class ShortArraySerializer
    extends ArraySerializerBase<short[]> {
        public ShortArraySerializer() {
            this(null);
        }

        public ShortArraySerializer(TypeSerializer typeSerializer) {
            super(short[].class, typeSerializer, null);
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return new ShortArraySerializer(typeSerializer);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("integer"));
            return objectNode;
        }

        @Override
        public void serializeContents(short[] arrs, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n = arrs.length;
            for (int i = 0; i < n; ++i) {
                jsonGenerator.writeNumber(arrs[i]);
            }
        }
    }

    @JacksonStdImpl
    public static final class StringArraySerializer
    extends ArraySerializerBase<String[]>
    implements ResolvableSerializer {
        protected JsonSerializer<Object> _elementSerializer;

        public StringArraySerializer(BeanProperty beanProperty) {
            super(String[].class, null, beanProperty);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void serializeContentsSlow(String[] arrstring, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException, JsonGenerationException {
            int n = 0;
            int n2 = arrstring.length;
            while (n < n2) {
                if (arrstring[n] == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else {
                    jsonSerializer.serialize(arrstring[n], jsonGenerator, serializerProvider);
                }
                ++n;
            }
            return;
        }

        @Override
        public ContainerSerializerBase<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
            return this;
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            ObjectNode objectNode = this.createSchemaNode("array", true);
            objectNode.put("items", this.createSchemaNode("string"));
            return objectNode;
        }

        @Override
        public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
            JsonSerializer<Object> jsonSerializer = serializerProvider.findValueSerializer(String.class, this._property);
            if (jsonSerializer != null && jsonSerializer.getClass().getAnnotation(JacksonStdImpl.class) == null) {
                this._elementSerializer = jsonSerializer;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void serializeContents(String[] arrstring, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            int n = arrstring.length;
            if (n != 0) {
                if (this._elementSerializer != null) {
                    StringArraySerializer.super.serializeContentsSlow(arrstring, jsonGenerator, serializerProvider, this._elementSerializer);
                    return;
                }
                for (int i = 0; i < n; ++i) {
                    if (arrstring[i] == null) {
                        jsonGenerator.writeNull();
                        continue;
                    }
                    jsonGenerator.writeString(arrstring[i]);
                }
            }
        }
    }

}

