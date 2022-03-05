/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Type
 *  java.text.DateFormat
 *  java.util.Date
 */
package com.flurry.org.codehaus.jackson.map.ser;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonLocation;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.ContextualSerializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.JsonSerializer;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.ResolvableSerializer;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerFactory;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.TypeSerializer;
import com.flurry.org.codehaus.jackson.map.ser.impl.FailingSerializer;
import com.flurry.org.codehaus.jackson.map.ser.impl.ReadOnlyClassToSerializerMap;
import com.flurry.org.codehaus.jackson.map.ser.impl.SerializerCache;
import com.flurry.org.codehaus.jackson.map.ser.impl.UnknownSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.NullSerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.StdKeySerializer;
import com.flurry.org.codehaus.jackson.map.ser.std.StdKeySerializers;
import com.flurry.org.codehaus.jackson.map.util.ClassUtil;
import com.flurry.org.codehaus.jackson.map.util.RootNameLookup;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.schema.SchemaAware;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

public class StdSerializerProvider
extends SerializerProvider {
    static final boolean CACHE_UNKNOWN_MAPPINGS;
    @Deprecated
    public static final JsonSerializer<Object> DEFAULT_KEY_SERIALIZER;
    public static final JsonSerializer<Object> DEFAULT_NULL_KEY_SERIALIZER;
    public static final JsonSerializer<Object> DEFAULT_UNKNOWN_SERIALIZER;
    protected DateFormat _dateFormat;
    protected JsonSerializer<Object> _keySerializer;
    protected final ReadOnlyClassToSerializerMap _knownSerializers;
    protected JsonSerializer<Object> _nullKeySerializer;
    protected JsonSerializer<Object> _nullValueSerializer;
    protected final RootNameLookup _rootNames;
    protected final SerializerCache _serializerCache;
    protected final SerializerFactory _serializerFactory;
    protected JsonSerializer<Object> _unknownTypeSerializer;

    static {
        DEFAULT_NULL_KEY_SERIALIZER = new FailingSerializer("Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)");
        DEFAULT_KEY_SERIALIZER = new StdKeySerializer();
        DEFAULT_UNKNOWN_SERIALIZER = new UnknownSerializer();
    }

    public StdSerializerProvider() {
        super(null);
        this._unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
        this._nullValueSerializer = NullSerializer.instance;
        this._nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
        this._serializerFactory = null;
        this._serializerCache = new SerializerCache();
        this._knownSerializers = null;
        this._rootNames = new RootNameLookup();
    }

    protected StdSerializerProvider(SerializationConfig serializationConfig, StdSerializerProvider stdSerializerProvider, SerializerFactory serializerFactory) {
        super(serializationConfig);
        this._unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
        this._nullValueSerializer = NullSerializer.instance;
        this._nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
        if (serializationConfig == null) {
            throw new NullPointerException();
        }
        this._serializerFactory = serializerFactory;
        this._serializerCache = stdSerializerProvider._serializerCache;
        this._unknownTypeSerializer = stdSerializerProvider._unknownTypeSerializer;
        this._keySerializer = stdSerializerProvider._keySerializer;
        this._nullValueSerializer = stdSerializerProvider._nullValueSerializer;
        this._nullKeySerializer = stdSerializerProvider._nullKeySerializer;
        this._rootNames = stdSerializerProvider._rootNames;
        this._knownSerializers = this._serializerCache.getReadOnlyLookupMap();
    }

    protected JsonSerializer<Object> _createAndCacheUntypedSerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        block2 : {
            try {
                jsonSerializer = this._createUntypedSerializer(javaType, beanProperty);
                if (jsonSerializer == null) break block2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new JsonMappingException(illegalArgumentException.getMessage(), null, illegalArgumentException);
            }
            this._serializerCache.addAndResolveNonTypedSerializer(javaType, jsonSerializer, (SerializerProvider)this);
        }
        return jsonSerializer;
    }

    protected JsonSerializer<Object> _createAndCacheUntypedSerializer(Class<?> class_, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        block2 : {
            try {
                jsonSerializer = this._createUntypedSerializer(this._config.constructType(class_), beanProperty);
                if (jsonSerializer == null) break block2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new JsonMappingException(illegalArgumentException.getMessage(), null, illegalArgumentException);
            }
            this._serializerCache.addAndResolveNonTypedSerializer(class_, jsonSerializer, (SerializerProvider)this);
        }
        return jsonSerializer;
    }

    protected JsonSerializer<Object> _createUntypedSerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        return this._serializerFactory.createSerializer(this._config, javaType, beanProperty);
    }

    protected JsonSerializer<Object> _findExplicitUntypedSerializer(Class<?> class_, BeanProperty beanProperty) {
        JsonSerializer<Object> jsonSerializer = this._knownSerializers.untypedValueSerializer(class_);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        JsonSerializer<Object> jsonSerializer2 = this._serializerCache.untypedValueSerializer(class_);
        if (jsonSerializer2 != null) {
            return jsonSerializer2;
        }
        try {
            JsonSerializer<Object> jsonSerializer3 = this._createAndCacheUntypedSerializer(class_, beanProperty);
            return jsonSerializer3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    protected JsonSerializer<Object> _handleContextualResolvable(JsonSerializer<Object> jsonSerializer, BeanProperty beanProperty) throws JsonMappingException {
        if (!(jsonSerializer instanceof ContextualSerializer)) {
            return jsonSerializer;
        }
        JsonSerializer jsonSerializer2 = ((ContextualSerializer)((Object)jsonSerializer)).createContextual(this._config, beanProperty);
        if (jsonSerializer2 != jsonSerializer) {
            if (jsonSerializer2 instanceof ResolvableSerializer) {
                ((ResolvableSerializer)((Object)jsonSerializer2)).resolve((SerializerProvider)this);
            }
            jsonSerializer = jsonSerializer2;
        }
        return jsonSerializer;
    }

    protected void _reportIncompatibleRootType(Object object, JavaType javaType) throws IOException, JsonProcessingException {
        if (javaType.isPrimitive() && ClassUtil.wrapperType(javaType.getRawClass()).isAssignableFrom(object.getClass())) {
            return;
        }
        throw new JsonMappingException("Incompatible types: declared root type (" + javaType + ") vs " + object.getClass().getName());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void _serializeValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonProcessingException {
        boolean bl;
        JsonSerializer<Object> jsonSerializer;
        if (object == null) {
            jsonSerializer = this.getNullValueSerializer();
            bl = false;
        } else {
            jsonSerializer = this.findTypedValueSerializer(object.getClass(), true, null);
            bl = this._config.isEnabled(SerializationConfig.Feature.WRAP_ROOT_VALUE);
            if (bl) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName(this._rootNames.findRootName(object.getClass(), this._config));
            }
        }
        try {
            jsonSerializer.serialize(object, jsonGenerator, (SerializerProvider)this);
            if (bl) {
                jsonGenerator.writeEndObject();
            }
            return;
        }
        catch (IOException iOException) {
            throw iOException;
        }
        catch (Exception exception) {
            String string = exception.getMessage();
            if (string == null) {
                string = "[no message for " + exception.getClass().getName() + "]";
            }
            throw new JsonMappingException(string, exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void _serializeValue(JsonGenerator jsonGenerator, Object object, JavaType javaType) throws IOException, JsonProcessingException {
        boolean bl;
        JsonSerializer<Object> jsonSerializer;
        if (object == null) {
            jsonSerializer = this.getNullValueSerializer();
            bl = false;
        } else {
            if (!javaType.getRawClass().isAssignableFrom(object.getClass())) {
                this._reportIncompatibleRootType(object, javaType);
            }
            jsonSerializer = this.findTypedValueSerializer(javaType, true, null);
            bl = this._config.isEnabled(SerializationConfig.Feature.WRAP_ROOT_VALUE);
            if (bl) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeFieldName(this._rootNames.findRootName(javaType, this._config));
            }
        }
        try {
            jsonSerializer.serialize(object, jsonGenerator, (SerializerProvider)this);
            if (bl) {
                jsonGenerator.writeEndObject();
            }
            return;
        }
        catch (IOException iOException) {
            throw iOException;
        }
        catch (Exception exception) {
            String string = exception.getMessage();
            if (string == null) {
                string = "[no message for " + exception.getClass().getName() + "]";
            }
            throw new JsonMappingException(string, exception);
        }
    }

    @Override
    public int cachedSerializersCount() {
        return this._serializerCache.size();
    }

    protected StdSerializerProvider createInstance(SerializationConfig serializationConfig, SerializerFactory serializerFactory) {
        return new StdSerializerProvider(serializationConfig, (StdSerializerProvider)this, serializerFactory);
    }

    @Override
    public void defaultSerializeDateKey(long l, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
            jsonGenerator.writeFieldName(String.valueOf((long)l));
            return;
        }
        if (this._dateFormat == null) {
            this._dateFormat = (DateFormat)this._config.getDateFormat().clone();
        }
        jsonGenerator.writeFieldName(this._dateFormat.format(new Date(l)));
    }

    @Override
    public void defaultSerializeDateKey(Date date, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
            jsonGenerator.writeFieldName(String.valueOf((long)date.getTime()));
            return;
        }
        if (this._dateFormat == null) {
            this._dateFormat = (DateFormat)this._config.getDateFormat().clone();
        }
        jsonGenerator.writeFieldName(this._dateFormat.format(date));
    }

    @Override
    public final void defaultSerializeDateValue(long l, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
            jsonGenerator.writeNumber(l);
            return;
        }
        if (this._dateFormat == null) {
            this._dateFormat = (DateFormat)this._config.getDateFormat().clone();
        }
        jsonGenerator.writeString(this._dateFormat.format(new Date(l)));
    }

    @Override
    public final void defaultSerializeDateValue(Date date, JsonGenerator jsonGenerator) throws IOException, JsonProcessingException {
        if (this.isEnabled(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)) {
            jsonGenerator.writeNumber(date.getTime());
            return;
        }
        if (this._dateFormat == null) {
            this._dateFormat = (DateFormat)this._config.getDateFormat().clone();
        }
        jsonGenerator.writeString(this._dateFormat.format(date));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<Object> findKeySerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        void var3_7;
        void var3_5;
        JsonSerializer<Object> jsonSerializer = this._serializerFactory.createKeySerializer(this._config, javaType, beanProperty);
        if (jsonSerializer == null) {
            if (this._keySerializer == null) {
                JsonSerializer<Object> jsonSerializer2 = StdKeySerializers.getStdKeySerializer(javaType);
            } else {
                JsonSerializer<Object> jsonSerializer3 = this._keySerializer;
            }
        }
        if (var3_5 instanceof ContextualSerializer) {
            JsonSerializer jsonSerializer4 = ((ContextualSerializer)var3_5).createContextual(this._config, beanProperty);
        }
        return var3_7;
    }

    @Override
    public JsonSerializer<Object> findTypedValueSerializer(JavaType javaType, boolean bl, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = this._knownSerializers.typedValueSerializer(javaType);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        JsonSerializer<Object> jsonSerializer2 = this._serializerCache.typedValueSerializer(javaType);
        if (jsonSerializer2 != null) {
            return jsonSerializer2;
        }
        WrappedSerializer wrappedSerializer = this.findValueSerializer(javaType, beanProperty);
        TypeSerializer typeSerializer = this._serializerFactory.createTypeSerializer(this._config, javaType, beanProperty);
        if (typeSerializer != null) {
            wrappedSerializer = new WrappedSerializer(typeSerializer, wrappedSerializer);
        }
        if (bl) {
            this._serializerCache.addTypedSerializer(javaType, (JsonSerializer<Object>)wrappedSerializer);
        }
        return wrappedSerializer;
    }

    @Override
    public JsonSerializer<Object> findTypedValueSerializer(Class<?> class_, boolean bl, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = this._knownSerializers.typedValueSerializer(class_);
        if (jsonSerializer != null) {
            return jsonSerializer;
        }
        JsonSerializer<Object> jsonSerializer2 = this._serializerCache.typedValueSerializer(class_);
        if (jsonSerializer2 != null) {
            return jsonSerializer2;
        }
        WrappedSerializer wrappedSerializer = this.findValueSerializer(class_, beanProperty);
        TypeSerializer typeSerializer = this._serializerFactory.createTypeSerializer(this._config, this._config.constructType(class_), beanProperty);
        if (typeSerializer != null) {
            wrappedSerializer = new WrappedSerializer(typeSerializer, wrappedSerializer);
        }
        if (bl) {
            this._serializerCache.addTypedSerializer(class_, (JsonSerializer<Object>)wrappedSerializer);
        }
        return wrappedSerializer;
    }

    @Override
    public JsonSerializer<Object> findValueSerializer(JavaType javaType, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = this._knownSerializers.untypedValueSerializer(javaType);
        if (jsonSerializer == null && (jsonSerializer = this._serializerCache.untypedValueSerializer(javaType)) == null && (jsonSerializer = this._createAndCacheUntypedSerializer(javaType, beanProperty)) == null) {
            return this.getUnknownTypeSerializer(javaType.getRawClass());
        }
        return this._handleContextualResolvable(jsonSerializer, beanProperty);
    }

    @Override
    public JsonSerializer<Object> findValueSerializer(Class<?> class_, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer = this._knownSerializers.untypedValueSerializer(class_);
        if (jsonSerializer == null && (jsonSerializer = this._serializerCache.untypedValueSerializer(class_)) == null && (jsonSerializer = this._serializerCache.untypedValueSerializer(this._config.constructType(class_))) == null && (jsonSerializer = this._createAndCacheUntypedSerializer(class_, beanProperty)) == null) {
            return this.getUnknownTypeSerializer(class_);
        }
        return this._handleContextualResolvable(jsonSerializer, beanProperty);
    }

    @Override
    public void flushCachedSerializers() {
        this._serializerCache.flush();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSchema generateJsonSchema(Class<?> class_, SerializationConfig serializationConfig, SerializerFactory serializerFactory) throws JsonMappingException {
        if (class_ == null) {
            throw new IllegalArgumentException("A class must be provided");
        }
        StdSerializerProvider stdSerializerProvider = this.createInstance(serializationConfig, serializerFactory);
        if (stdSerializerProvider.getClass() != this.getClass()) {
            throw new IllegalStateException("Broken serializer provider: createInstance returned instance of type " + (Object)stdSerializerProvider.getClass() + "; blueprint of type " + (Object)this.getClass());
        }
        JsonSerializer<Object> jsonSerializer = stdSerializerProvider.findValueSerializer(class_, null);
        JsonNode jsonNode = jsonSerializer instanceof SchemaAware ? ((SchemaAware)((Object)jsonSerializer)).getSchema(stdSerializerProvider, null) : JsonSchema.getDefaultSchemaNode();
        if (!(jsonNode instanceof ObjectNode)) {
            throw new IllegalArgumentException("Class " + class_.getName() + " would not be serialized as a JSON object and therefore has no schema");
        }
        return new JsonSchema((ObjectNode)jsonNode);
    }

    @Override
    public JsonSerializer<Object> getNullKeySerializer() {
        return this._nullKeySerializer;
    }

    @Override
    public JsonSerializer<Object> getNullValueSerializer() {
        return this._nullValueSerializer;
    }

    @Override
    public JsonSerializer<Object> getUnknownTypeSerializer(Class<?> class_) {
        return this._unknownTypeSerializer;
    }

    @Override
    public boolean hasSerializerFor(SerializationConfig serializationConfig, Class<?> class_, SerializerFactory serializerFactory) {
        return this.createInstance(serializationConfig, serializerFactory)._findExplicitUntypedSerializer(class_, null) != null;
    }

    @Override
    public final void serializeValue(SerializationConfig serializationConfig, JsonGenerator jsonGenerator, Object object, SerializerFactory serializerFactory) throws IOException, JsonGenerationException {
        if (serializerFactory == null) {
            throw new IllegalArgumentException("Can not pass null serializerFactory");
        }
        StdSerializerProvider stdSerializerProvider = this.createInstance(serializationConfig, serializerFactory);
        if (stdSerializerProvider.getClass() != this.getClass()) {
            throw new IllegalStateException("Broken serializer provider: createInstance returned instance of type " + (Object)stdSerializerProvider.getClass() + "; blueprint of type " + (Object)this.getClass());
        }
        stdSerializerProvider._serializeValue(jsonGenerator, object);
    }

    @Override
    public final void serializeValue(SerializationConfig serializationConfig, JsonGenerator jsonGenerator, Object object, JavaType javaType, SerializerFactory serializerFactory) throws IOException, JsonGenerationException {
        if (serializerFactory == null) {
            throw new IllegalArgumentException("Can not pass null serializerFactory");
        }
        StdSerializerProvider stdSerializerProvider = this.createInstance(serializationConfig, serializerFactory);
        if (stdSerializerProvider.getClass() != this.getClass()) {
            throw new IllegalStateException("Broken serializer provider: createInstance returned instance of type " + (Object)stdSerializerProvider.getClass() + "; blueprint of type " + (Object)this.getClass());
        }
        stdSerializerProvider._serializeValue(jsonGenerator, object, javaType);
    }

    @Override
    public void setDefaultKeySerializer(JsonSerializer<Object> jsonSerializer) {
        if (jsonSerializer == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._keySerializer = jsonSerializer;
    }

    @Override
    public void setNullKeySerializer(JsonSerializer<Object> jsonSerializer) {
        if (jsonSerializer == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._nullKeySerializer = jsonSerializer;
    }

    @Override
    public void setNullValueSerializer(JsonSerializer<Object> jsonSerializer) {
        if (jsonSerializer == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._nullValueSerializer = jsonSerializer;
    }

    private static final class WrappedSerializer
    extends JsonSerializer<Object> {
        protected final JsonSerializer<Object> _serializer;
        protected final TypeSerializer _typeSerializer;

        public WrappedSerializer(TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
            this._typeSerializer = typeSerializer;
            this._serializer = jsonSerializer;
        }

        @Override
        public Class<Object> handledType() {
            return Object.class;
        }

        @Override
        public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            this._serializer.serializeWithType(object, jsonGenerator, serializerProvider, this._typeSerializer);
        }

        @Override
        public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
            this._serializer.serializeWithType(object, jsonGenerator, serializerProvider, typeSerializer);
        }
    }

}

