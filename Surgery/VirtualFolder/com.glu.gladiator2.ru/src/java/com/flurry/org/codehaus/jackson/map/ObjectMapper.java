/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.FormatSchema
 *  com.flurry.org.codehaus.jackson.map.ObjectMapper$DefaultTypeResolverBuilder
 *  com.flurry.org.codehaus.jackson.map.ObjectMapper$DefaultTyping
 *  com.flurry.org.codehaus.jackson.map.ObjectReader
 *  com.flurry.org.codehaus.jackson.map.ObjectWriter
 *  com.flurry.org.codehaus.jackson.map.SerializerFactory
 *  com.flurry.org.codehaus.jackson.map.deser.StdDeserializationContext
 *  com.flurry.org.codehaus.jackson.map.deser.StdDeserializerProvider
 *  com.flurry.org.codehaus.jackson.map.introspect.BasicClassIntrospector
 *  com.flurry.org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector
 *  com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker$Std
 *  com.flurry.org.codehaus.jackson.map.ser.BeanSerializerFactory
 *  com.flurry.org.codehaus.jackson.map.ser.FilterProvider
 *  com.flurry.org.codehaus.jackson.map.ser.StdSerializerProvider
 *  com.flurry.org.codehaus.jackson.map.type.SimpleType
 *  com.flurry.org.codehaus.jackson.node.ArrayNode
 *  com.flurry.org.codehaus.jackson.node.NullNode
 *  com.flurry.org.codehaus.jackson.node.ObjectNode
 *  com.flurry.org.codehaus.jackson.node.TreeTraversingParser
 *  com.flurry.org.codehaus.jackson.schema.JsonSchema
 *  com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter
 *  com.flurry.org.codehaus.jackson.util.TokenBuffer
 *  com.flurry.org.codehaus.jackson.util.VersionUtil
 *  java.io.Closeable
 *  java.io.EOFException
 *  java.io.File
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.Reader
 *  java.io.Writer
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalArgumentException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Type
 *  java.net.URL
 *  java.text.DateFormat
 *  java.util.Iterator
 *  java.util.concurrent.ConcurrentHashMap
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.FormatSchema;
import com.flurry.org.codehaus.jackson.JsonEncoding;
import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.Versioned;
import com.flurry.org.codehaus.jackson.annotate.JsonAutoDetect;
import com.flurry.org.codehaus.jackson.annotate.JsonMethod;
import com.flurry.org.codehaus.jackson.annotate.JsonTypeInfo;
import com.flurry.org.codehaus.jackson.io.SegmentedStringWriter;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.AbstractTypeResolver;
import com.flurry.org.codehaus.jackson.map.AnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.BeanDescription;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.ClassIntrospector;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.Deserializers;
import com.flurry.org.codehaus.jackson.map.HandlerInstantiator;
import com.flurry.org.codehaus.jackson.map.InjectableValues;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.KeyDeserializers;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.MappingIterator;
import com.flurry.org.codehaus.jackson.map.MappingJsonFactory;
import com.flurry.org.codehaus.jackson.map.Module;
import com.flurry.org.codehaus.jackson.map.ObjectMapper;
import com.flurry.org.codehaus.jackson.map.ObjectReader;
import com.flurry.org.codehaus.jackson.map.ObjectWriter;
import com.flurry.org.codehaus.jackson.map.PropertyNamingStrategy;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerFactory;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.Serializers;
import com.flurry.org.codehaus.jackson.map.annotate.JsonSerialize;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializerModifier;
import com.flurry.org.codehaus.jackson.map.deser.StdDeserializationContext;
import com.flurry.org.codehaus.jackson.map.deser.StdDeserializerProvider;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiators;
import com.flurry.org.codehaus.jackson.map.introspect.BasicClassIntrospector;
import com.flurry.org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import com.flurry.org.codehaus.jackson.map.introspect.VisibilityChecker;
import com.flurry.org.codehaus.jackson.map.jsontype.NamedType;
import com.flurry.org.codehaus.jackson.map.jsontype.SubtypeResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeIdResolver;
import com.flurry.org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import com.flurry.org.codehaus.jackson.map.jsontype.impl.StdSubtypeResolver;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerFactory;
import com.flurry.org.codehaus.jackson.map.ser.BeanSerializerModifier;
import com.flurry.org.codehaus.jackson.map.ser.FilterProvider;
import com.flurry.org.codehaus.jackson.map.ser.StdSerializerProvider;
import com.flurry.org.codehaus.jackson.map.type.SimpleType;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.map.type.TypeModifier;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.TreeTraversingParser;
import com.flurry.org.codehaus.jackson.schema.JsonSchema;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.type.TypeReference;
import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter;
import com.flurry.org.codehaus.jackson.util.TokenBuffer;
import com.flurry.org.codehaus.jackson.util.VersionUtil;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectMapper
extends ObjectCodec
implements Versioned {
    protected static final AnnotationIntrospector DEFAULT_ANNOTATION_INTROSPECTOR;
    protected static final ClassIntrospector<? extends BeanDescription> DEFAULT_INTROSPECTOR;
    private static final JavaType JSON_NODE_TYPE;
    protected static final VisibilityChecker<?> STD_VISIBILITY_CHECKER;
    protected DeserializationConfig _deserializationConfig;
    protected DeserializerProvider _deserializerProvider;
    protected InjectableValues _injectableValues;
    protected final JsonFactory _jsonFactory;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
    protected SerializationConfig _serializationConfig;
    protected SerializerFactory _serializerFactory;
    protected SerializerProvider _serializerProvider;
    protected SubtypeResolver _subtypeResolver;
    protected TypeFactory _typeFactory;

    static {
        JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
        DEFAULT_INTROSPECTOR = BasicClassIntrospector.instance;
        DEFAULT_ANNOTATION_INTROSPECTOR = new JacksonAnnotationIntrospector();
        STD_VISIBILITY_CHECKER = VisibilityChecker.Std.defaultInstance();
    }

    public ObjectMapper() {
        this(null, null, null);
    }

    public ObjectMapper(JsonFactory jsonFactory) {
        super(jsonFactory, null, null);
    }

    public ObjectMapper(JsonFactory jsonFactory, SerializerProvider serializerProvider, DeserializerProvider deserializerProvider) {
        super(jsonFactory, serializerProvider, deserializerProvider, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ObjectMapper(JsonFactory jsonFactory, SerializerProvider serializerProvider, DeserializerProvider deserializerProvider, SerializationConfig serializationConfig, DeserializationConfig deserializationConfig) {
        this._rootDeserializers = new ConcurrentHashMap(64, 0.6f, 2);
        if (jsonFactory == null) {
            this._jsonFactory = new MappingJsonFactory(this);
        } else {
            this._jsonFactory = jsonFactory;
            if (jsonFactory.getCodec() == null) {
                this._jsonFactory.setCodec(this);
            }
        }
        this._typeFactory = TypeFactory.defaultInstance();
        if (serializationConfig == null) {
            serializationConfig = new SerializationConfig(DEFAULT_INTROSPECTOR, DEFAULT_ANNOTATION_INTROSPECTOR, STD_VISIBILITY_CHECKER, null, null, this._typeFactory, null);
        }
        this._serializationConfig = serializationConfig;
        if (deserializationConfig == null) {
            deserializationConfig = new DeserializationConfig(DEFAULT_INTROSPECTOR, DEFAULT_ANNOTATION_INTROSPECTOR, STD_VISIBILITY_CHECKER, null, null, this._typeFactory, null);
        }
        this._deserializationConfig = deserializationConfig;
        if (serializerProvider == null) {
            serializerProvider = new StdSerializerProvider();
        }
        this._serializerProvider = serializerProvider;
        if (deserializerProvider == null) {
            deserializerProvider = new StdDeserializerProvider();
        }
        this._deserializerProvider = deserializerProvider;
        this._serializerFactory = BeanSerializerFactory.instance;
    }

    @Deprecated
    public ObjectMapper(SerializerFactory serializerFactory) {
        super(null, null, null);
        this.setSerializerFactory(serializerFactory);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void _configAndWriteCloseable(JsonGenerator var1, Object var2_3, SerializationConfig var3_2) throws IOException, JsonGenerationException, JsonMappingException {
        var4_4 = (Closeable)var2_3;
        try {
            this._serializerProvider.serializeValue(var3_2, var1, var2_3, this._serializerFactory);
            var8_5 = var1;
            var1 = null;
            var8_5.close();
            var9_6 = var4_4;
            var4_4 = null;
            var9_6.close();
            ** if (!false) goto lbl13
        }
        catch (Throwable var5_7) {
            block12 : {
                if (var1 != null) {
                    var1.close();
                }
                break block12;
                catch (IOException var11_8) {}
lbl20: // 2 sources:
                if (false == false) return;
                try {
                    null.close();
                    return;
                }
                catch (IOException var10_9) {
                    return;
                }
                catch (IOException var7_10) {}
            }
            if (var4_4 == null) throw var5_7;
            try {
                var4_4.close();
            }
            catch (IOException var6_11) {
                throw var5_7;
            }
            throw var5_7;
        }
lbl-1000: // 1 sources:
        {
            null.close();
        }
lbl13: // 2 sources:
        ** GOTO lbl20
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private final void _writeCloseableValue(JsonGenerator jsonGenerator, Object object, SerializationConfig serializationConfig) throws IOException, JsonGenerationException, JsonMappingException {
        closeable2 = (Closeable)object;
        this._serializerProvider.serializeValue(serializationConfig, jsonGenerator, object, this._serializerFactory);
        if (serializationConfig.isEnabled(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE)) {
            jsonGenerator.flush();
        }
        closeable = closeable2;
        closeable2 = null;
        closeable.close();
        if (!false) ** GOTO lbl12
        null.close();
lbl12: // 2 sources:
        return;
        catch (Throwable throwable) {
            if (closeable2 != null) {
                closeable2.close();
            }
lbl17: // 4 sources:
            do {
                throw throwable;
                break;
            } while (true);
        }
        catch (IOException iOException) {
            return;
        }
        {
            catch (IOException iOException) {
                ** continue;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _configAndWriteValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig serializationConfig = this.copySerializationConfig();
        if (serializationConfig.isEnabled(SerializationConfig.Feature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (serializationConfig.isEnabled(SerializationConfig.Feature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            ObjectMapper.super._configAndWriteCloseable(jsonGenerator, object, serializationConfig);
            return;
        }
        boolean bl = false;
        try {
            this._serializerProvider.serializeValue(serializationConfig, jsonGenerator, object, this._serializerFactory);
            bl = true;
            jsonGenerator.close();
            if (bl) return;
        }
        catch (Throwable throwable) {
            if (bl) throw throwable;
            try {
                jsonGenerator.close();
            }
            catch (IOException iOException) {
                throw throwable;
            }
            throw throwable;
        }
        try {
            jsonGenerator.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _configAndWriteValue(JsonGenerator jsonGenerator, Object object, Class<?> class_) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig serializationConfig = this.copySerializationConfig().withView(class_);
        if (serializationConfig.isEnabled(SerializationConfig.Feature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (serializationConfig.isEnabled(SerializationConfig.Feature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            ObjectMapper.super._configAndWriteCloseable(jsonGenerator, object, serializationConfig);
            return;
        }
        boolean bl = false;
        try {
            this._serializerProvider.serializeValue(serializationConfig, jsonGenerator, object, this._serializerFactory);
            bl = true;
            jsonGenerator.close();
            if (bl) return;
        }
        catch (Throwable throwable) {
            if (bl) throw throwable;
            try {
                jsonGenerator.close();
            }
            catch (IOException iOException) {
                throw throwable;
            }
            throw throwable;
        }
        try {
            jsonGenerator.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    protected Object _convert(Object object, JavaType javaType) throws IllegalArgumentException {
        if (object == null) {
            return null;
        }
        TokenBuffer tokenBuffer = new TokenBuffer((ObjectCodec)this);
        try {
            this.writeValue((JsonGenerator)tokenBuffer, object);
            JsonParser jsonParser = tokenBuffer.asParser();
            T t = this.readValue(jsonParser, javaType);
            jsonParser.close();
            return t;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException(iOException.getMessage(), (Throwable)iOException);
        }
    }

    protected DeserializationContext _createDeserializationContext(JsonParser jsonParser, DeserializationConfig deserializationConfig) {
        return new StdDeserializationContext(deserializationConfig, jsonParser, this._deserializerProvider, this._injectableValues);
    }

    protected PrettyPrinter _defaultPrettyPrinter() {
        return new DefaultPrettyPrinter();
    }

    protected JsonDeserializer<Object> _findRootDeserializer(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        JsonDeserializer jsonDeserializer = (JsonDeserializer)this._rootDeserializers.get((Object)javaType);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        JsonDeserializer<Object> jsonDeserializer2 = this._deserializerProvider.findTypedValueDeserializer(deserializationConfig, javaType, null);
        if (jsonDeserializer2 == null) {
            throw new JsonMappingException("Can not find a deserializer for type " + javaType);
        }
        this._rootDeserializers.put((Object)javaType, jsonDeserializer2);
        return jsonDeserializer2;
    }

    protected JsonToken _initForReading(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == null && (jsonToken = jsonParser.nextToken()) == null) {
            throw new EOFException("No content to map to Object due to end of input");
        }
        return jsonToken;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object _readMapAndClose(JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        Object object;
        block8 : {
            block9 : {
                try {
                    Object object2;
                    JsonToken jsonToken = this._initForReading(jsonParser);
                    if (jsonToken == JsonToken.VALUE_NULL) {
                        object = this._findRootDeserializer(this._deserializationConfig, javaType).getNullValue();
                        break block8;
                    }
                    if (jsonToken == JsonToken.END_ARRAY || jsonToken == JsonToken.END_OBJECT) break block9;
                    DeserializationConfig deserializationConfig = this.copyDeserializationConfig();
                    DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, deserializationConfig);
                    JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer(deserializationConfig, javaType);
                    object = deserializationConfig.isEnabled(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE) ? this._unwrapAndDeserialize(jsonParser, javaType, deserializationContext, jsonDeserializer) : (object2 = jsonDeserializer.deserialize(jsonParser, deserializationContext));
                    break block8;
                }
                catch (Throwable throwable) {
                    try {
                        jsonParser.close();
                    }
                    catch (IOException iOException) {
                        throw throwable;
                    }
                    throw throwable;
                }
            }
            object = null;
        }
        jsonParser.clearCurrentToken();
        try {
            jsonParser.close();
            return object;
        }
        catch (IOException iOException) {
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _readValue(DeserializationConfig deserializationConfig, JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        Object object;
        JsonToken jsonToken = this._initForReading(jsonParser);
        if (jsonToken == JsonToken.VALUE_NULL) {
            object = this._findRootDeserializer(deserializationConfig, javaType).getNullValue();
        } else if (jsonToken == JsonToken.END_ARRAY || jsonToken == JsonToken.END_OBJECT) {
            object = null;
        } else {
            DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, deserializationConfig);
            JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer(deserializationConfig, javaType);
            object = deserializationConfig.isEnabled(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE) ? this._unwrapAndDeserialize(jsonParser, javaType, deserializationContext, jsonDeserializer) : jsonDeserializer.deserialize(jsonParser, deserializationContext);
        }
        jsonParser.clearCurrentToken();
        return object;
    }

    protected Object _unwrapAndDeserialize(JsonParser jsonParser, JavaType javaType, DeserializationContext deserializationContext, JsonDeserializer<Object> jsonDeserializer) throws IOException, JsonParseException, JsonMappingException {
        SerializedString serializedString = this._deserializerProvider.findExpectedRootName(deserializationContext.getConfig(), javaType);
        if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not START_OBJECT (needed to unwrap root name '" + serializedString + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        if (jsonParser.nextToken() != JsonToken.FIELD_NAME) {
            throw JsonMappingException.from(jsonParser, "Current token not FIELD_NAME (to contain expected root name '" + serializedString + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        String string = jsonParser.getCurrentName();
        if (!serializedString.getValue().equals((Object)string)) {
            throw JsonMappingException.from(jsonParser, "Root name '" + string + "' does not match expected ('" + serializedString + "') for type " + javaType);
        }
        jsonParser.nextToken();
        Object object = jsonDeserializer.deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + serializedString + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        return object;
    }

    public boolean canDeserialize(JavaType javaType) {
        return this._deserializerProvider.hasValueDeserializerFor(this.copyDeserializationConfig(), javaType);
    }

    public boolean canSerialize(Class<?> class_) {
        return this._serializerProvider.hasSerializerFor(this.copySerializationConfig(), class_, this._serializerFactory);
    }

    public ObjectMapper configure(JsonGenerator.Feature feature, boolean bl) {
        this._jsonFactory.configure(feature, bl);
        return this;
    }

    public ObjectMapper configure(JsonParser.Feature feature, boolean bl) {
        this._jsonFactory.configure(feature, bl);
        return this;
    }

    public ObjectMapper configure(DeserializationConfig.Feature feature, boolean bl) {
        this._deserializationConfig.set(feature, bl);
        return this;
    }

    public ObjectMapper configure(SerializationConfig.Feature feature, boolean bl) {
        this._serializationConfig.set(feature, bl);
        return this;
    }

    public JavaType constructType(Type type) {
        return this._typeFactory.constructType(type);
    }

    public <T> T convertValue(Object object, JavaType javaType) throws IllegalArgumentException {
        return (T)this._convert(object, javaType);
    }

    public <T> T convertValue(Object object, TypeReference typeReference) throws IllegalArgumentException {
        return (T)this._convert(object, this._typeFactory.constructType(typeReference));
    }

    public <T> T convertValue(Object object, Class<T> class_) throws IllegalArgumentException {
        return (T)this._convert(object, this._typeFactory.constructType((Type)class_));
    }

    public DeserializationConfig copyDeserializationConfig() {
        return this._deserializationConfig.createUnshared(this._subtypeResolver).passSerializationFeatures(this._serializationConfig._featureFlags);
    }

    public SerializationConfig copySerializationConfig() {
        return this._serializationConfig.createUnshared(this._subtypeResolver);
    }

    public ArrayNode createArrayNode() {
        return this._deserializationConfig.getNodeFactory().arrayNode();
    }

    public ObjectNode createObjectNode() {
        return this._deserializationConfig.getNodeFactory().objectNode();
    }

    @Deprecated
    public ObjectWriter defaultPrettyPrintingWriter() {
        return this.writerWithDefaultPrettyPrinter();
    }

    public /* varargs */ ObjectMapper disable(DeserializationConfig.Feature ... arrfeature) {
        this._deserializationConfig = this._deserializationConfig.without(arrfeature);
        return this;
    }

    public /* varargs */ ObjectMapper disable(SerializationConfig.Feature ... arrfeature) {
        this._serializationConfig = this._serializationConfig.without(arrfeature);
        return this;
    }

    public ObjectMapper disableDefaultTyping() {
        return this.setDefaultTyping(null);
    }

    public /* varargs */ ObjectMapper enable(DeserializationConfig.Feature ... arrfeature) {
        this._deserializationConfig = this._deserializationConfig.with(arrfeature);
        return this;
    }

    public /* varargs */ ObjectMapper enable(SerializationConfig.Feature ... arrfeature) {
        this._serializationConfig = this._serializationConfig.with(arrfeature);
        return this;
    }

    public ObjectMapper enableDefaultTyping() {
        return this.enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping defaultTyping) {
        return this.enableDefaultTyping(defaultTyping, JsonTypeInfo.As.WRAPPER_ARRAY);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping defaultTyping, JsonTypeInfo.As as) {
        return this.setDefaultTyping((TypeResolverBuilder<?>)new DefaultTypeResolverBuilder(defaultTyping).init(JsonTypeInfo.Id.CLASS, null).inclusion(as));
    }

    public ObjectMapper enableDefaultTypingAsProperty(DefaultTyping defaultTyping, String string) {
        return this.setDefaultTyping((TypeResolverBuilder<?>)new DefaultTypeResolverBuilder(defaultTyping).init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY).typeProperty(string));
    }

    @Deprecated
    public ObjectWriter filteredWriter(FilterProvider filterProvider) {
        return this.writer(filterProvider);
    }

    public JsonSchema generateJsonSchema(Class<?> class_) throws JsonMappingException {
        return this.generateJsonSchema(class_, this.copySerializationConfig());
    }

    public JsonSchema generateJsonSchema(Class<?> class_, SerializationConfig serializationConfig) throws JsonMappingException {
        return this._serializerProvider.generateJsonSchema(class_, serializationConfig, this._serializerFactory);
    }

    public DeserializationConfig getDeserializationConfig() {
        return this._deserializationConfig;
    }

    public DeserializerProvider getDeserializerProvider() {
        return this._deserializerProvider;
    }

    public JsonFactory getJsonFactory() {
        return this._jsonFactory;
    }

    public JsonNodeFactory getNodeFactory() {
        return this._deserializationConfig.getNodeFactory();
    }

    public SerializationConfig getSerializationConfig() {
        return this._serializationConfig;
    }

    public SerializerProvider getSerializerProvider() {
        return this._serializerProvider;
    }

    public SubtypeResolver getSubtypeResolver() {
        if (this._subtypeResolver == null) {
            this._subtypeResolver = new StdSubtypeResolver();
        }
        return this._subtypeResolver;
    }

    public TypeFactory getTypeFactory() {
        return this._typeFactory;
    }

    public VisibilityChecker<?> getVisibilityChecker() {
        return this._serializationConfig.getDefaultVisibilityChecker();
    }

    public boolean isEnabled(JsonGenerator.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public boolean isEnabled(JsonParser.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public boolean isEnabled(DeserializationConfig.Feature feature) {
        return this._deserializationConfig.isEnabled(feature);
    }

    public boolean isEnabled(SerializationConfig.Feature feature) {
        return this._serializationConfig.isEnabled(feature);
    }

    @Deprecated
    public ObjectWriter prettyPrintingWriter(PrettyPrinter prettyPrinter) {
        return this.writer(prettyPrinter);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode readTree(JsonParser jsonParser) throws IOException, JsonProcessingException {
        DeserializationConfig deserializationConfig = this.copyDeserializationConfig();
        if (jsonParser.getCurrentToken() == null && jsonParser.nextToken() == null) {
            return null;
        }
        JsonNode jsonNode = (JsonNode)this._readValue(deserializationConfig, jsonParser, JSON_NODE_TYPE);
        if (jsonNode != null) return jsonNode;
        return this.getNodeFactory().nullNode();
    }

    public JsonNode readTree(JsonParser jsonParser, DeserializationConfig deserializationConfig) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readValue(deserializationConfig, jsonParser, JSON_NODE_TYPE);
        if (jsonNode == null) {
            jsonNode = NullNode.instance;
        }
        return jsonNode;
    }

    public JsonNode readTree(File file) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createJsonParser(file), JSON_NODE_TYPE);
        if (jsonNode == null) {
            jsonNode = NullNode.instance;
        }
        return jsonNode;
    }

    public JsonNode readTree(InputStream inputStream) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createJsonParser(inputStream), JSON_NODE_TYPE);
        if (jsonNode == null) {
            jsonNode = NullNode.instance;
        }
        return jsonNode;
    }

    public JsonNode readTree(Reader reader) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createJsonParser(reader), JSON_NODE_TYPE);
        if (jsonNode == null) {
            jsonNode = NullNode.instance;
        }
        return jsonNode;
    }

    public JsonNode readTree(String string) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createJsonParser(string), JSON_NODE_TYPE);
        if (jsonNode == null) {
            jsonNode = NullNode.instance;
        }
        return jsonNode;
    }

    public JsonNode readTree(URL uRL) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createJsonParser(uRL), JSON_NODE_TYPE);
        if (jsonNode == null) {
            jsonNode = NullNode.instance;
        }
        return jsonNode;
    }

    public JsonNode readTree(byte[] arrby) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode)this._readMapAndClose(this._jsonFactory.createJsonParser(arrby), JSON_NODE_TYPE);
        if (jsonNode == null) {
            jsonNode = NullNode.instance;
        }
        return jsonNode;
    }

    public <T> T readValue(JsonNode jsonNode, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.copyDeserializationConfig(), this.treeAsTokens(jsonNode), javaType);
    }

    public <T> T readValue(JsonNode jsonNode, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.copyDeserializationConfig(), this.treeAsTokens(jsonNode), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(JsonNode jsonNode, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.copyDeserializationConfig(), this.treeAsTokens(jsonNode), this._typeFactory.constructType((Type)class_));
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.copyDeserializationConfig(), jsonParser, javaType);
    }

    public <T> T readValue(JsonParser jsonParser, JavaType javaType, DeserializationConfig deserializationConfig) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(deserializationConfig, jsonParser, javaType);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.copyDeserializationConfig(), jsonParser, this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference, DeserializationConfig deserializationConfig) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(deserializationConfig, jsonParser, this._typeFactory.constructType(typeReference));
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(this.copyDeserializationConfig(), jsonParser, this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(JsonParser jsonParser, Class<T> class_, DeserializationConfig deserializationConfig) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readValue(deserializationConfig, jsonParser, this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(File file, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(file), javaType);
    }

    public <T> T readValue(File file, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(file), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(File file, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(file), this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(InputStream inputStream, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(inputStream), javaType);
    }

    public <T> T readValue(InputStream inputStream, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(inputStream), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(InputStream inputStream, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(inputStream), this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(Reader reader, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(reader), javaType);
    }

    public <T> T readValue(Reader reader, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(reader), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(Reader reader, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(reader), this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(String string, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(string), javaType);
    }

    public <T> T readValue(String string, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(string), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(String string, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(string), this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(URL uRL, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(uRL), javaType);
    }

    public <T> T readValue(URL uRL, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(uRL), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(URL uRL, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(uRL), this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(byte[] arrby, int n, int n2, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(arrby, n, n2), javaType);
    }

    public <T> T readValue(byte[] arrby, int n, int n2, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(arrby, n, n2), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(byte[] arrby, int n, int n2, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(arrby, n, n2), this._typeFactory.constructType((Type)class_));
    }

    public <T> T readValue(byte[] arrby, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(arrby), javaType);
    }

    public <T> T readValue(byte[] arrby, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(arrby), this._typeFactory.constructType(typeReference));
    }

    public <T> T readValue(byte[] arrby, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return (T)this._readMapAndClose(this._jsonFactory.createJsonParser(arrby), this._typeFactory.constructType((Type)class_));
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, JavaType javaType) throws IOException, JsonProcessingException {
        DeserializationConfig deserializationConfig = this.copyDeserializationConfig();
        return new MappingIterator(javaType, jsonParser, this._createDeserializationContext(jsonParser, deserializationConfig), this._findRootDeserializer(deserializationConfig, javaType), false, null);
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.readValues(jsonParser, this._typeFactory.constructType(typeReference));
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, Class<T> class_) throws IOException, JsonProcessingException {
        return this.readValues(jsonParser, this._typeFactory.constructType((Type)class_));
    }

    public ObjectReader reader() {
        return new ObjectReader(this, this.copyDeserializationConfig()).withInjectableValues(this._injectableValues);
    }

    public ObjectReader reader(FormatSchema formatSchema) {
        return new ObjectReader((ObjectMapper)this, this.copyDeserializationConfig(), null, null, formatSchema, this._injectableValues);
    }

    public ObjectReader reader(InjectableValues injectableValues) {
        return new ObjectReader((ObjectMapper)this, this.copyDeserializationConfig(), null, null, null, injectableValues);
    }

    public ObjectReader reader(JsonNodeFactory jsonNodeFactory) {
        return new ObjectReader((ObjectMapper)this, this.copyDeserializationConfig()).withNodeFactory(jsonNodeFactory);
    }

    public ObjectReader reader(JavaType javaType) {
        return new ObjectReader((ObjectMapper)this, this.copyDeserializationConfig(), javaType, null, null, this._injectableValues);
    }

    public ObjectReader reader(TypeReference<?> typeReference) {
        return this.reader(this._typeFactory.constructType(typeReference));
    }

    public ObjectReader reader(Class<?> class_) {
        return this.reader(this._typeFactory.constructType((Type)class_));
    }

    public ObjectReader readerForUpdating(Object object) {
        JavaType javaType = this._typeFactory.constructType((Type)object.getClass());
        return new ObjectReader((ObjectMapper)this, this.copyDeserializationConfig(), javaType, object, null, this._injectableValues);
    }

    public void registerModule(Module module) {
        if (module.getModuleName() == null) {
            throw new IllegalArgumentException("Module without defined name");
        }
        if (module.version() == null) {
            throw new IllegalArgumentException("Module without defined version");
        }
        module.setupModule(new Module.SetupContext((ObjectMapper)this){
            final /* synthetic */ ObjectMapper val$mapper;
            {
                this.val$mapper = objectMapper2;
            }

            @Override
            public void addAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver) {
                this.val$mapper._deserializerProvider = this.val$mapper._deserializerProvider.withAbstractTypeResolver(abstractTypeResolver);
            }

            @Override
            public void addBeanDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
                this.val$mapper._deserializerProvider = this.val$mapper._deserializerProvider.withDeserializerModifier(beanDeserializerModifier);
            }

            @Override
            public void addBeanSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
                this.val$mapper._serializerFactory = this.val$mapper._serializerFactory.withSerializerModifier(beanSerializerModifier);
            }

            @Override
            public void addDeserializers(Deserializers deserializers) {
                this.val$mapper._deserializerProvider = this.val$mapper._deserializerProvider.withAdditionalDeserializers(deserializers);
            }

            @Override
            public void addKeyDeserializers(KeyDeserializers keyDeserializers) {
                this.val$mapper._deserializerProvider = this.val$mapper._deserializerProvider.withAdditionalKeyDeserializers(keyDeserializers);
            }

            @Override
            public void addKeySerializers(Serializers serializers) {
                this.val$mapper._serializerFactory = this.val$mapper._serializerFactory.withAdditionalKeySerializers(serializers);
            }

            @Override
            public void addSerializers(Serializers serializers) {
                this.val$mapper._serializerFactory = this.val$mapper._serializerFactory.withAdditionalSerializers(serializers);
            }

            @Override
            public void addTypeModifier(TypeModifier typeModifier) {
                TypeFactory typeFactory = this.val$mapper._typeFactory.withModifier(typeModifier);
                this.val$mapper.setTypeFactory(typeFactory);
            }

            @Override
            public void addValueInstantiators(ValueInstantiators valueInstantiators) {
                this.val$mapper._deserializerProvider = this.val$mapper._deserializerProvider.withValueInstantiators(valueInstantiators);
            }

            @Override
            public void appendAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
                this.val$mapper._deserializationConfig = this.val$mapper._deserializationConfig.withAppendedAnnotationIntrospector(annotationIntrospector);
                this.val$mapper._serializationConfig = this.val$mapper._serializationConfig.withAppendedAnnotationIntrospector(annotationIntrospector);
            }

            @Override
            public DeserializationConfig getDeserializationConfig() {
                return this.val$mapper.getDeserializationConfig();
            }

            @Override
            public Version getMapperVersion() {
                return ObjectMapper.this.version();
            }

            @Override
            public SerializationConfig getSerializationConfig() {
                return this.val$mapper.getSerializationConfig();
            }

            @Override
            public void insertAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
                this.val$mapper._deserializationConfig = this.val$mapper._deserializationConfig.withInsertedAnnotationIntrospector(annotationIntrospector);
                this.val$mapper._serializationConfig = this.val$mapper._serializationConfig.withInsertedAnnotationIntrospector(annotationIntrospector);
            }

            @Override
            public boolean isEnabled(JsonGenerator.Feature feature) {
                return this.val$mapper.isEnabled(feature);
            }

            @Override
            public boolean isEnabled(JsonParser.Feature feature) {
                return this.val$mapper.isEnabled(feature);
            }

            @Override
            public boolean isEnabled(DeserializationConfig.Feature feature) {
                return this.val$mapper.isEnabled(feature);
            }

            @Override
            public boolean isEnabled(SerializationConfig.Feature feature) {
                return this.val$mapper.isEnabled(feature);
            }

            @Override
            public void setMixInAnnotations(Class<?> class_, Class<?> class_2) {
                this.val$mapper._deserializationConfig.addMixInAnnotations(class_, class_2);
                this.val$mapper._serializationConfig.addMixInAnnotations(class_, class_2);
            }
        });
    }

    public /* varargs */ void registerSubtypes(NamedType ... arrnamedType) {
        this.getSubtypeResolver().registerSubtypes(arrnamedType);
    }

    public /* varargs */ void registerSubtypes(Class<?> ... arrclass) {
        this.getSubtypeResolver().registerSubtypes(arrclass);
    }

    @Deprecated
    public ObjectReader schemaBasedReader(FormatSchema formatSchema) {
        return this.reader(formatSchema);
    }

    @Deprecated
    public ObjectWriter schemaBasedWriter(FormatSchema formatSchema) {
        return this.writer(formatSchema);
    }

    public ObjectMapper setAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        this._serializationConfig = this._serializationConfig.withAnnotationIntrospector(annotationIntrospector);
        this._deserializationConfig = this._deserializationConfig.withAnnotationIntrospector(annotationIntrospector);
        return this;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this._deserializationConfig = this._deserializationConfig.withDateFormat(dateFormat);
        this._serializationConfig = this._serializationConfig.withDateFormat(dateFormat);
    }

    public ObjectMapper setDefaultTyping(TypeResolverBuilder<?> typeResolverBuilder) {
        this._deserializationConfig = this._deserializationConfig.withTypeResolverBuilder((TypeResolverBuilder)typeResolverBuilder);
        this._serializationConfig = this._serializationConfig.withTypeResolverBuilder((TypeResolverBuilder)typeResolverBuilder);
        return this;
    }

    public ObjectMapper setDeserializationConfig(DeserializationConfig deserializationConfig) {
        this._deserializationConfig = deserializationConfig;
        return this;
    }

    public ObjectMapper setDeserializerProvider(DeserializerProvider deserializerProvider) {
        this._deserializerProvider = deserializerProvider;
        return this;
    }

    public void setFilters(FilterProvider filterProvider) {
        this._serializationConfig = this._serializationConfig.withFilters(filterProvider);
    }

    public void setHandlerInstantiator(HandlerInstantiator handlerInstantiator) {
        this._deserializationConfig = this._deserializationConfig.withHandlerInstantiator(handlerInstantiator);
        this._serializationConfig = this._serializationConfig.withHandlerInstantiator(handlerInstantiator);
    }

    public ObjectMapper setInjectableValues(InjectableValues injectableValues) {
        this._injectableValues = injectableValues;
        return this;
    }

    public ObjectMapper setNodeFactory(JsonNodeFactory jsonNodeFactory) {
        this._deserializationConfig = this._deserializationConfig.withNodeFactory(jsonNodeFactory);
        return this;
    }

    public ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this._serializationConfig = this._serializationConfig.withPropertyNamingStrategy(propertyNamingStrategy);
        this._deserializationConfig = this._deserializationConfig.withPropertyNamingStrategy(propertyNamingStrategy);
        return this;
    }

    public ObjectMapper setSerializationConfig(SerializationConfig serializationConfig) {
        this._serializationConfig = serializationConfig;
        return this;
    }

    public ObjectMapper setSerializationInclusion(JsonSerialize.Inclusion inclusion) {
        this._serializationConfig = this._serializationConfig.withSerializationInclusion(inclusion);
        return this;
    }

    public ObjectMapper setSerializerFactory(SerializerFactory serializerFactory) {
        this._serializerFactory = serializerFactory;
        return this;
    }

    public ObjectMapper setSerializerProvider(SerializerProvider serializerProvider) {
        this._serializerProvider = serializerProvider;
        return this;
    }

    public void setSubtypeResolver(SubtypeResolver subtypeResolver) {
        this._subtypeResolver = subtypeResolver;
    }

    public ObjectMapper setTypeFactory(TypeFactory typeFactory) {
        this._typeFactory = typeFactory;
        this._deserializationConfig = this._deserializationConfig.withTypeFactory(typeFactory);
        this._serializationConfig = this._serializationConfig.withTypeFactory(typeFactory);
        return this;
    }

    public ObjectMapper setVisibility(JsonMethod jsonMethod, JsonAutoDetect.Visibility visibility) {
        this._deserializationConfig = this._deserializationConfig.withVisibility(jsonMethod, visibility);
        this._serializationConfig = this._serializationConfig.withVisibility(jsonMethod, visibility);
        return this;
    }

    public void setVisibilityChecker(VisibilityChecker<?> visibilityChecker) {
        this._deserializationConfig = this._deserializationConfig.withVisibilityChecker((VisibilityChecker)visibilityChecker);
        this._serializationConfig = this._serializationConfig.withVisibilityChecker((VisibilityChecker)visibilityChecker);
    }

    @Override
    public JsonParser treeAsTokens(JsonNode jsonNode) {
        return new TreeTraversingParser(jsonNode, (ObjectCodec)this);
    }

    @Override
    public <T> T treeToValue(JsonNode jsonNode, Class<T> class_) throws IOException, JsonParseException, JsonMappingException {
        return this.readValue(this.treeAsTokens(jsonNode), class_);
    }

    @Deprecated
    public ObjectWriter typedWriter(JavaType javaType) {
        return this.writerWithType(javaType);
    }

    @Deprecated
    public ObjectWriter typedWriter(TypeReference<?> typeReference) {
        return this.writerWithType(typeReference);
    }

    @Deprecated
    public ObjectWriter typedWriter(Class<?> class_) {
        return this.writerWithType(class_);
    }

    @Deprecated
    public ObjectReader updatingReader(Object object) {
        return this.readerForUpdating(object);
    }

    public <T extends JsonNode> T valueToTree(Object object) throws IllegalArgumentException {
        JsonNode jsonNode;
        if (object == null) {
            return null;
        }
        TokenBuffer tokenBuffer = new TokenBuffer((ObjectCodec)this);
        try {
            this.writeValue((JsonGenerator)tokenBuffer, object);
            JsonParser jsonParser = tokenBuffer.asParser();
            jsonNode = this.readTree(jsonParser);
            jsonParser.close();
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException(iOException.getMessage(), (Throwable)iOException);
        }
        return (T)jsonNode;
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor((Class)this.getClass());
    }

    @Deprecated
    public ObjectWriter viewWriter(Class<?> class_) {
        return this.writerWithView(class_);
    }

    public ObjectMapper withModule(Module module) {
        this.registerModule(module);
        return this;
    }

    @Override
    public void writeTree(JsonGenerator jsonGenerator, JsonNode jsonNode) throws IOException, JsonProcessingException {
        SerializationConfig serializationConfig = this.copySerializationConfig();
        this._serializerProvider.serializeValue(serializationConfig, jsonGenerator, jsonNode, this._serializerFactory);
        if (serializationConfig.isEnabled(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE)) {
            jsonGenerator.flush();
        }
    }

    public void writeTree(JsonGenerator jsonGenerator, JsonNode jsonNode, SerializationConfig serializationConfig) throws IOException, JsonProcessingException {
        this._serializerProvider.serializeValue(serializationConfig, jsonGenerator, jsonNode, this._serializerFactory);
        if (serializationConfig.isEnabled(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE)) {
            jsonGenerator.flush();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig serializationConfig = this.copySerializationConfig();
        if (serializationConfig.isEnabled(SerializationConfig.Feature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            ObjectMapper.super._writeCloseableValue(jsonGenerator, object, serializationConfig);
            return;
        } else {
            this._serializerProvider.serializeValue(serializationConfig, jsonGenerator, object, this._serializerFactory);
            if (!serializationConfig.isEnabled(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE)) return;
            {
                jsonGenerator.flush();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeValue(JsonGenerator jsonGenerator, Object object, SerializationConfig serializationConfig) throws IOException, JsonGenerationException, JsonMappingException {
        if (serializationConfig.isEnabled(SerializationConfig.Feature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            ObjectMapper.super._writeCloseableValue(jsonGenerator, object, serializationConfig);
            return;
        } else {
            this._serializerProvider.serializeValue(serializationConfig, jsonGenerator, object, this._serializerFactory);
            if (!serializationConfig.isEnabled(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE)) return;
            {
                jsonGenerator.flush();
                return;
            }
        }
    }

    public void writeValue(File file, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(file, JsonEncoding.UTF8), object);
    }

    public void writeValue(OutputStream outputStream, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(outputStream, JsonEncoding.UTF8), object);
    }

    public void writeValue(Writer writer, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(writer), object);
    }

    public byte[] writeValueAsBytes(Object object) throws IOException, JsonGenerationException, JsonMappingException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(byteArrayBuilder, JsonEncoding.UTF8), object);
        byte[] arrby = byteArrayBuilder.toByteArray();
        byteArrayBuilder.release();
        return arrby;
    }

    public String writeValueAsString(Object object) throws IOException, JsonGenerationException, JsonMappingException {
        SegmentedStringWriter segmentedStringWriter = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(segmentedStringWriter), object);
        return segmentedStringWriter.getAndClear();
    }

    public ObjectWriter writer() {
        return new ObjectWriter(this, this.copySerializationConfig());
    }

    public ObjectWriter writer(FormatSchema formatSchema) {
        return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig(), formatSchema);
    }

    public ObjectWriter writer(PrettyPrinter prettyPrinter) {
        if (prettyPrinter == null) {
            prettyPrinter = ObjectWriter.NULL_PRETTY_PRINTER;
        }
        return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig(), null, prettyPrinter);
    }

    public ObjectWriter writer(FilterProvider filterProvider) {
        return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig().withFilters(filterProvider));
    }

    public ObjectWriter writer(DateFormat dateFormat) {
        return new ObjectWriter((ObjectMapper)this, (SerializationConfig)this.copySerializationConfig().withDateFormat(dateFormat));
    }

    public ObjectWriter writerWithDefaultPrettyPrinter() {
        return new ObjectWriter(this, this.copySerializationConfig(), null, this._defaultPrettyPrinter());
    }

    public ObjectWriter writerWithType(JavaType javaType) {
        return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig(), javaType, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectWriter writerWithType(TypeReference<?> typeReference) {
        JavaType javaType;
        if (typeReference == null) {
            javaType = null;
            do {
                return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig(), javaType, null);
                break;
            } while (true);
        }
        javaType = this._typeFactory.constructType(typeReference);
        return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig(), javaType, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectWriter writerWithType(Class<?> class_) {
        JavaType javaType;
        if (class_ == null) {
            javaType = null;
            do {
                return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig(), javaType, null);
                break;
            } while (true);
        }
        javaType = this._typeFactory.constructType((Type)class_);
        return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig(), javaType, null);
    }

    public ObjectWriter writerWithView(Class<?> class_) {
        return new ObjectWriter((ObjectMapper)this, this.copySerializationConfig().withView(class_));
    }

}

