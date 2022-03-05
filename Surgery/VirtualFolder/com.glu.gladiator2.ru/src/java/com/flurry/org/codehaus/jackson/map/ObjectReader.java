/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.EOFException
 *  java.io.File
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.Reader
 *  java.lang.Class
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.lang.reflect.Type
 *  java.net.URL
 *  java.util.Iterator
 *  java.util.concurrent.ConcurrentHashMap
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.FormatSchema;
import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.Versioned;
import com.flurry.org.codehaus.jackson.io.SerializedString;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.DeserializationConfig;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.DeserializerProvider;
import com.flurry.org.codehaus.jackson.map.InjectableValues;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.MappingIterator;
import com.flurry.org.codehaus.jackson.map.ObjectMapper;
import com.flurry.org.codehaus.jackson.map.deser.StdDeserializationContext;
import com.flurry.org.codehaus.jackson.map.type.SimpleType;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.node.ArrayNode;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.NullNode;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import com.flurry.org.codehaus.jackson.node.TreeTraversingParser;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.type.TypeReference;
import com.flurry.org.codehaus.jackson.util.VersionUtil;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectReader
extends ObjectCodec
implements Versioned {
    private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
    protected final DeserializationConfig _config;
    protected final InjectableValues _injectableValues;
    protected final JsonFactory _jsonFactory;
    protected final DeserializerProvider _provider;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
    protected final FormatSchema _schema;
    protected final boolean _unwrapRoot;
    protected final Object _valueToUpdate;
    protected final JavaType _valueType;

    protected ObjectReader(ObjectMapper objectMapper, DeserializationConfig deserializationConfig) {
        super(objectMapper, deserializationConfig, null, null, null, null);
    }

    protected ObjectReader(ObjectMapper objectMapper, DeserializationConfig deserializationConfig, JavaType javaType, Object object, FormatSchema formatSchema, InjectableValues injectableValues) {
        this._config = deserializationConfig;
        this._rootDeserializers = objectMapper._rootDeserializers;
        this._provider = objectMapper._deserializerProvider;
        this._jsonFactory = objectMapper._jsonFactory;
        this._valueType = javaType;
        this._valueToUpdate = object;
        if (object != null && javaType.isArrayType()) {
            throw new IllegalArgumentException("Can not update an array value");
        }
        this._schema = formatSchema;
        this._injectableValues = injectableValues;
        this._unwrapRoot = deserializationConfig.isEnabled(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE);
    }

    protected ObjectReader(ObjectReader objectReader, DeserializationConfig deserializationConfig, JavaType javaType, Object object, FormatSchema formatSchema, InjectableValues injectableValues) {
        this._config = deserializationConfig;
        this._rootDeserializers = objectReader._rootDeserializers;
        this._provider = objectReader._provider;
        this._jsonFactory = objectReader._jsonFactory;
        this._valueType = javaType;
        this._valueToUpdate = object;
        if (object != null && javaType.isArrayType()) {
            throw new IllegalArgumentException("Can not update an array value");
        }
        this._schema = formatSchema;
        this._injectableValues = injectableValues;
        this._unwrapRoot = deserializationConfig.isEnabled(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE);
    }

    protected static JsonToken _initForReading(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == null && (jsonToken = jsonParser.nextToken()) == null) {
            throw new EOFException("No content to map to Object due to end of input");
        }
        return jsonToken;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _bind(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        Object object;
        JsonToken jsonToken = ObjectReader._initForReading(jsonParser);
        if (jsonToken == JsonToken.VALUE_NULL) {
            object = this._valueToUpdate == null ? this._findRootDeserializer(this._config, this._valueType).getNullValue() : this._valueToUpdate;
        } else if (jsonToken == JsonToken.END_ARRAY || jsonToken == JsonToken.END_OBJECT) {
            object = this._valueToUpdate;
        } else {
            DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
            JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer(this._config, this._valueType);
            if (this._unwrapRoot) {
                object = this._unwrapAndDeserialize(jsonParser, deserializationContext, this._valueType, jsonDeserializer);
            } else if (this._valueToUpdate == null) {
                object = jsonDeserializer.deserialize(jsonParser, deserializationContext);
            } else {
                jsonDeserializer.deserialize(jsonParser, deserializationContext, this._valueToUpdate);
                object = this._valueToUpdate;
            }
        }
        jsonParser.clearCurrentToken();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object _bindAndClose(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        JsonToken jsonToken = ObjectReader._initForReading(jsonParser);
        if (jsonToken == JsonToken.VALUE_NULL) {
            if (this._valueToUpdate != null) return this._valueToUpdate;
            Object object = this._findRootDeserializer(this._config, this._valueType).getNullValue();
            return object;
        }
        if (jsonToken == JsonToken.END_ARRAY) return this._valueToUpdate;
        if (jsonToken == JsonToken.END_OBJECT) {
            return this._valueToUpdate;
        }
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer(this._config, this._valueType);
        if (this._unwrapRoot) {
            return this._unwrapAndDeserialize(jsonParser, deserializationContext, this._valueType, jsonDeserializer);
        }
        if (this._valueToUpdate == null) {
            return jsonDeserializer.deserialize(jsonParser, deserializationContext);
        }
        jsonDeserializer.deserialize(jsonParser, deserializationContext, this._valueToUpdate);
        return this._valueToUpdate;
    }

    protected JsonNode _bindAndCloseAsTree(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        try {
            JsonNode jsonNode = this._bindAsTree(jsonParser);
            return jsonNode;
        }
        finally {
            jsonParser.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonNode _bindAsTree(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        void var3_4;
        JsonToken jsonToken = ObjectReader._initForReading(jsonParser);
        if (jsonToken == JsonToken.VALUE_NULL || jsonToken == JsonToken.END_ARRAY || jsonToken == JsonToken.END_OBJECT) {
            NullNode nullNode = NullNode.instance;
        } else {
            DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
            JsonDeserializer<Object> jsonDeserializer = this._findRootDeserializer(this._config, JSON_NODE_TYPE);
            if (this._unwrapRoot) {
                JsonNode jsonNode = (JsonNode)this._unwrapAndDeserialize(jsonParser, deserializationContext, JSON_NODE_TYPE, jsonDeserializer);
            } else {
                JsonNode jsonNode = (JsonNode)jsonDeserializer.deserialize(jsonParser, deserializationContext);
            }
        }
        jsonParser.clearCurrentToken();
        return var3_4;
    }

    protected DeserializationContext _createDeserializationContext(JsonParser jsonParser, DeserializationConfig deserializationConfig) {
        return new StdDeserializationContext(deserializationConfig, jsonParser, this._provider, this._injectableValues);
    }

    protected JsonDeserializer<Object> _findRootDeserializer(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        if (javaType == null) {
            throw new JsonMappingException("No value type configured for ObjectReader");
        }
        JsonDeserializer jsonDeserializer = (JsonDeserializer)this._rootDeserializers.get((Object)javaType);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        JsonDeserializer<Object> jsonDeserializer2 = this._provider.findTypedValueDeserializer(deserializationConfig, javaType, null);
        if (jsonDeserializer2 == null) {
            throw new JsonMappingException("Can not find a deserializer for type " + javaType);
        }
        this._rootDeserializers.put((Object)javaType, jsonDeserializer2);
        return jsonDeserializer2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _unwrapAndDeserialize(JsonParser jsonParser, DeserializationContext deserializationContext, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) throws IOException, JsonParseException, JsonMappingException {
        Object object;
        SerializedString serializedString = this._provider.findExpectedRootName(deserializationContext.getConfig(), javaType);
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
        if (this._valueToUpdate == null) {
            object = jsonDeserializer.deserialize(jsonParser, deserializationContext);
        } else {
            jsonDeserializer.deserialize(jsonParser, deserializationContext, this._valueToUpdate);
            object = this._valueToUpdate;
        }
        if (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + serializedString + "'), but " + (Object)((Object)jsonParser.getCurrentToken()));
        }
        return object;
    }

    @Override
    public JsonNode createArrayNode() {
        return this._config.getNodeFactory().arrayNode();
    }

    @Override
    public JsonNode createObjectNode() {
        return this._config.getNodeFactory().objectNode();
    }

    @Override
    public JsonNode readTree(JsonParser jsonParser) throws IOException, JsonProcessingException {
        return this._bindAsTree(jsonParser);
    }

    public JsonNode readTree(InputStream inputStream) throws IOException, JsonProcessingException {
        return this._bindAndCloseAsTree(this._jsonFactory.createJsonParser(inputStream));
    }

    public JsonNode readTree(Reader reader) throws IOException, JsonProcessingException {
        return this._bindAndCloseAsTree(this._jsonFactory.createJsonParser(reader));
    }

    public JsonNode readTree(String string) throws IOException, JsonProcessingException {
        return this._bindAndCloseAsTree(this._jsonFactory.createJsonParser(string));
    }

    public <T> T readValue(JsonNode jsonNode) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this.treeAsTokens(jsonNode));
    }

    public <T> T readValue(JsonParser jsonParser) throws IOException, JsonProcessingException {
        return (T)this._bind(jsonParser);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, JavaType javaType) throws IOException, JsonProcessingException {
        return this.withType(javaType).readValue(jsonParser);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.withType(typeReference).readValue(jsonParser);
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, Class<T> class_) throws IOException, JsonProcessingException {
        return this.withType(class_).readValue(jsonParser);
    }

    public <T> T readValue(File file) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this._jsonFactory.createJsonParser(file));
    }

    public <T> T readValue(InputStream inputStream) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this._jsonFactory.createJsonParser(inputStream));
    }

    public <T> T readValue(Reader reader) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this._jsonFactory.createJsonParser(reader));
    }

    public <T> T readValue(String string) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this._jsonFactory.createJsonParser(string));
    }

    public <T> T readValue(URL uRL) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this._jsonFactory.createJsonParser(uRL));
    }

    public <T> T readValue(byte[] arrby) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this._jsonFactory.createJsonParser(arrby));
    }

    public <T> T readValue(byte[] arrby, int n, int n2) throws IOException, JsonProcessingException {
        return (T)this._bindAndClose(this._jsonFactory.createJsonParser(arrby, n, n2));
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser) throws IOException, JsonProcessingException {
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, deserializationContext, this._findRootDeserializer(this._config, this._valueType), false, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(File file) throws IOException, JsonProcessingException {
        JsonParser jsonParser = this._jsonFactory.createJsonParser(file);
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, deserializationContext, this._findRootDeserializer(this._config, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(InputStream inputStream) throws IOException, JsonProcessingException {
        JsonParser jsonParser = this._jsonFactory.createJsonParser(inputStream);
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, deserializationContext, this._findRootDeserializer(this._config, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(Reader reader) throws IOException, JsonProcessingException {
        JsonParser jsonParser = this._jsonFactory.createJsonParser(reader);
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, deserializationContext, this._findRootDeserializer(this._config, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(String string) throws IOException, JsonProcessingException {
        JsonParser jsonParser = this._jsonFactory.createJsonParser(string);
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, deserializationContext, this._findRootDeserializer(this._config, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(URL uRL) throws IOException, JsonProcessingException {
        JsonParser jsonParser = this._jsonFactory.createJsonParser(uRL);
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, deserializationContext, this._findRootDeserializer(this._config, this._valueType), true, this._valueToUpdate);
    }

    public final <T> MappingIterator<T> readValues(byte[] arrby) throws IOException, JsonProcessingException {
        return this.readValues(arrby, 0, arrby.length);
    }

    public <T> MappingIterator<T> readValues(byte[] arrby, int n, int n2) throws IOException, JsonProcessingException {
        JsonParser jsonParser = this._jsonFactory.createJsonParser(arrby, n, n2);
        if (this._schema != null) {
            jsonParser.setSchema(this._schema);
        }
        DeserializationContext deserializationContext = this._createDeserializationContext(jsonParser, this._config);
        return new MappingIterator(this._valueType, jsonParser, deserializationContext, this._findRootDeserializer(this._config, this._valueType), true, this._valueToUpdate);
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, JavaType javaType) throws IOException, JsonProcessingException {
        return this.withType(javaType).readValues(jsonParser);
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return this.withType(typeReference).readValues(jsonParser);
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, Class<T> class_) throws IOException, JsonProcessingException {
        return this.withType(class_).readValues(jsonParser);
    }

    @Override
    public JsonParser treeAsTokens(JsonNode jsonNode) {
        return new TreeTraversingParser(jsonNode, (ObjectCodec)this);
    }

    @Override
    public <T> T treeToValue(JsonNode jsonNode, Class<T> class_) throws IOException, JsonProcessingException {
        return this.readValue(this.treeAsTokens(jsonNode), class_);
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }

    public ObjectReader withInjectableValues(InjectableValues injectableValues) {
        if (this._injectableValues == injectableValues) {
            return this;
        }
        return new ObjectReader((ObjectReader)this, this._config, this._valueType, this._valueToUpdate, this._schema, injectableValues);
    }

    public ObjectReader withNodeFactory(JsonNodeFactory jsonNodeFactory) {
        if (jsonNodeFactory == this._config.getNodeFactory()) {
            return this;
        }
        return new ObjectReader((ObjectReader)this, this._config.withNodeFactory(jsonNodeFactory), this._valueType, this._valueToUpdate, this._schema, this._injectableValues);
    }

    public ObjectReader withSchema(FormatSchema formatSchema) {
        if (this._schema == formatSchema) {
            return this;
        }
        return new ObjectReader((ObjectReader)this, this._config, this._valueType, this._valueToUpdate, formatSchema, this._injectableValues);
    }

    public ObjectReader withType(JavaType javaType) {
        if (javaType == this._valueType) {
            return this;
        }
        return new ObjectReader((ObjectReader)this, this._config, javaType, this._valueToUpdate, this._schema, this._injectableValues);
    }

    public ObjectReader withType(TypeReference<?> typeReference) {
        return this.withType(this._config.getTypeFactory().constructType(typeReference.getType()));
    }

    public ObjectReader withType(Class<?> class_) {
        return this.withType(this._config.constructType(class_));
    }

    public ObjectReader withType(Type type) {
        return this.withType(this._config.getTypeFactory().constructType(type));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectReader withValueToUpdate(Object object) {
        JavaType javaType;
        if (object == this._valueToUpdate) {
            return this;
        }
        if (object == null) {
            throw new IllegalArgumentException("cat not update null value");
        }
        if (this._valueType == null) {
            javaType = this._config.constructType(object.getClass());
            do {
                return new ObjectReader((ObjectReader)this, this._config, javaType, object, this._schema, this._injectableValues);
                break;
            } while (true);
        }
        javaType = this._valueType;
        return new ObjectReader((ObjectReader)this, this._config, javaType, object, this._schema, this._injectableValues);
    }

    @Override
    public void writeTree(JsonGenerator jsonGenerator, JsonNode jsonNode) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Not implemented for ObjectReader");
    }

    @Override
    public void writeValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Not implemented for ObjectReader");
    }
}

