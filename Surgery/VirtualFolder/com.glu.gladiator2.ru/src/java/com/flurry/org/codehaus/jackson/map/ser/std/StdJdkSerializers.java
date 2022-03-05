/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Void
 *  java.lang.reflect.Type
 *  java.net.URI
 *  java.net.URL
 *  java.util.Collection
 *  java.util.Currency
 *  java.util.HashMap
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.UUID
 *  java.util.concurrent.atomic.AtomicBoolean
 *  java.util.concurrent.atomic.AtomicInteger
 *  java.util.concurrent.atomic.AtomicLong
 *  java.util.concurrent.atomic.AtomicReference
 *  java.util.regex.Pattern
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.NullSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.ScalarSerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.map.ser.std.ToStringSerializer;
import com.flurry.org.codehaus.jackson.map.util.Provider;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class StdJdkSerializers
implements Provider<Map.Entry<Class<?>, Object>> {
    @Override
    public Collection<Map.Entry<Class<?>, Object>> provide() {
        HashMap hashMap = new HashMap();
        ToStringSerializer toStringSerializer = ToStringSerializer.instance;
        hashMap.put(URL.class, (Object)toStringSerializer);
        hashMap.put(URI.class, (Object)toStringSerializer);
        hashMap.put(Currency.class, (Object)toStringSerializer);
        hashMap.put(UUID.class, (Object)toStringSerializer);
        hashMap.put(Pattern.class, (Object)toStringSerializer);
        hashMap.put(Locale.class, (Object)toStringSerializer);
        hashMap.put(Locale.class, (Object)toStringSerializer);
        hashMap.put(AtomicReference.class, AtomicReferenceSerializer.class);
        hashMap.put(AtomicBoolean.class, AtomicBooleanSerializer.class);
        hashMap.put(AtomicInteger.class, AtomicIntegerSerializer.class);
        hashMap.put(AtomicLong.class, AtomicLongSerializer.class);
        hashMap.put(File.class, FileSerializer.class);
        hashMap.put(Class.class, ClassSerializer.class);
        hashMap.put((Object)Void.TYPE, NullSerializer.class);
        return hashMap.entrySet();
    }

    public static final class AtomicBooleanSerializer
    extends ScalarSerializerBase<AtomicBoolean> {
        public AtomicBooleanSerializer() {
            super(AtomicBoolean.class, false);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("boolean", true);
        }

        @Override
        public void serialize(AtomicBoolean atomicBoolean, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeBoolean(atomicBoolean.get());
        }
    }

    public static final class AtomicIntegerSerializer
    extends ScalarSerializerBase<AtomicInteger> {
        public AtomicIntegerSerializer() {
            super(AtomicInteger.class, false);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("integer", true);
        }

        @Override
        public void serialize(AtomicInteger atomicInteger, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeNumber(atomicInteger.get());
        }
    }

    public static final class AtomicLongSerializer
    extends ScalarSerializerBase<AtomicLong> {
        public AtomicLongSerializer() {
            super(AtomicLong.class, false);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("integer", true);
        }

        @Override
        public void serialize(AtomicLong atomicLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeNumber(atomicLong.get());
        }
    }

    public static final class AtomicReferenceSerializer
    extends SerializerBase<AtomicReference<?>> {
        public AtomicReferenceSerializer() {
            super(AtomicReference.class, false);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("any", true);
        }

        @Override
        public void serialize(AtomicReference<?> atomicReference, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            serializerProvider.defaultSerializeValue(atomicReference.get(), jsonGenerator);
        }
    }

    public static final class ClassSerializer
    extends ScalarSerializerBase<Class<?>> {
        public ClassSerializer() {
            super(Class.class, false);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("string", true);
        }

        @Override
        public void serialize(Class<?> class_, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeString(class_.getName());
        }
    }

    public static final class FileSerializer
    extends ScalarSerializerBase<File> {
        public FileSerializer() {
            super(File.class);
        }

        @Override
        public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
            return this.createSchemaNode("string", true);
        }

        @Override
        public void serialize(File file, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
            jsonGenerator.writeString(file.getAbsolutePath());
        }
    }

}

