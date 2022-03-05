/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.UnsupportedOperationException
 *  java.util.Iterator
 *  java.util.NoSuchElementException
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonStreamContext;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.RuntimeJsonMappingException;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MappingIterator<T>
implements Iterator<T> {
    protected static final MappingIterator<?> EMPTY_ITERATOR = new MappingIterator<T>(null, null, null, null, false, null);
    protected final boolean _closeParser;
    protected final DeserializationContext _context;
    protected final JsonDeserializer<T> _deserializer;
    protected boolean _hasNextChecked;
    protected JsonParser _parser;
    protected final JavaType _type;
    protected final T _updatedValue;

    protected MappingIterator(JavaType javaType, JsonParser jsonParser, DeserializationContext deserializationContext, JsonDeserializer<?> jsonDeserializer) {
        super(javaType, jsonParser, deserializationContext, jsonDeserializer, true, null);
    }

    protected MappingIterator(JavaType javaType, JsonParser jsonParser, DeserializationContext deserializationContext, JsonDeserializer<?> jsonDeserializer, boolean bl, Object object) {
        this._type = javaType;
        this._parser = jsonParser;
        this._context = deserializationContext;
        this._deserializer = jsonDeserializer;
        if (jsonParser != null && jsonParser.getCurrentToken() == JsonToken.START_ARRAY && !jsonParser.getParsingContext().inRoot()) {
            jsonParser.clearCurrentToken();
        }
        this._closeParser = bl;
        if (object == null) {
            this._updatedValue = null;
            return;
        }
        this._updatedValue = object;
    }

    protected static <T> MappingIterator<T> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    public boolean hasNext() {
        try {
            boolean bl = this.hasNextValue();
            return bl;
        }
        catch (JsonMappingException jsonMappingException) {
            throw new RuntimeJsonMappingException(jsonMappingException.getMessage(), jsonMappingException);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException.getMessage(), (Throwable)iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasNextValue() throws IOException {
        if (this._parser == null) return false;
        if (this._hasNextChecked) return true;
        JsonToken jsonToken = this._parser.getCurrentToken();
        this._hasNextChecked = true;
        if (jsonToken != null) return true;
        JsonToken jsonToken2 = this._parser.nextToken();
        if (jsonToken2 == null) {
            JsonParser jsonParser = this._parser;
            this._parser = null;
            if (!this._closeParser) return false;
            {
                jsonParser.close();
                return false;
            }
        }
        if (jsonToken2 != JsonToken.END_ARRAY) return true;
        return false;
    }

    public T next() {
        T t;
        try {
            t = this.nextValue();
        }
        catch (JsonMappingException jsonMappingException) {
            throw new RuntimeJsonMappingException(jsonMappingException.getMessage(), jsonMappingException);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException.getMessage(), (Throwable)iOException);
        }
        return t;
    }

    /*
     * Enabled aggressive block sorting
     */
    public T nextValue() throws IOException {
        T t;
        if (!this._hasNextChecked && !this.hasNextValue()) {
            throw new NoSuchElementException();
        }
        if (this._parser == null) {
            throw new NoSuchElementException();
        }
        this._hasNextChecked = false;
        if (this._updatedValue == null) {
            t = this._deserializer.deserialize(this._parser, this._context);
        } else {
            this._deserializer.deserialize(this._parser, this._context, this._updatedValue);
            t = this._updatedValue;
        }
        this._parser.clearCurrentToken();
        return t;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

