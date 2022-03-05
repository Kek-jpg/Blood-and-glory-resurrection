/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExternalTypeHandler {
    private final HashMap<String, Integer> _nameToPropertyIndex;
    private final ExtTypedProperty[] _properties;
    private final TokenBuffer[] _tokens;
    private final String[] _typeIds;

    protected ExternalTypeHandler(ExternalTypeHandler externalTypeHandler) {
        this._properties = externalTypeHandler._properties;
        this._nameToPropertyIndex = externalTypeHandler._nameToPropertyIndex;
        int n = this._properties.length;
        this._typeIds = new String[n];
        this._tokens = new TokenBuffer[n];
    }

    protected ExternalTypeHandler(ExtTypedProperty[] arrextTypedProperty, HashMap<String, Integer> hashMap, String[] arrstring, TokenBuffer[] arrtokenBuffer) {
        this._properties = arrextTypedProperty;
        this._nameToPropertyIndex = hashMap;
        this._typeIds = arrstring;
        this._tokens = arrtokenBuffer;
    }

    protected final void _deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, int n) throws IOException, JsonProcessingException {
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser.getCodec());
        tokenBuffer.writeStartArray();
        tokenBuffer.writeString(this._typeIds[n]);
        JsonParser jsonParser2 = this._tokens[n].asParser(jsonParser);
        jsonParser2.nextToken();
        tokenBuffer.copyCurrentStructure(jsonParser2);
        tokenBuffer.writeEndArray();
        JsonParser jsonParser3 = tokenBuffer.asParser(jsonParser);
        jsonParser3.nextToken();
        this._properties[n].getProperty().deserializeAndSet(jsonParser3, deserializationContext, object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object complete(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        int n = 0;
        int n2 = this._properties.length;
        while (n < n2) {
            if (this._typeIds[n] == null) {
                if (this._tokens[n] != null) {
                    throw deserializationContext.mappingException("Missing external type id property '" + this._properties[n].getTypePropertyName());
                }
            } else {
                if (this._tokens[n] == null) {
                    SettableBeanProperty settableBeanProperty = this._properties[n].getProperty();
                    throw deserializationContext.mappingException("Missing property '" + settableBeanProperty.getName() + "' for external type id '" + this._properties[n].getTypePropertyName());
                }
                this._deserialize(jsonParser, deserializationContext, object, n);
            }
            ++n;
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean handleToken(JsonParser jsonParser, DeserializationContext deserializationContext, String string, Object object) throws IOException, JsonProcessingException {
        boolean bl;
        Integer n = (Integer)this._nameToPropertyIndex.get((Object)string);
        if (n == null) {
            return false;
        }
        int n2 = n;
        if (this._properties[n2].hasTypePropertyName(string)) {
            this._typeIds[n2] = jsonParser.getText();
            jsonParser.skipChildren();
            bl = false;
            if (object != null) {
                TokenBuffer tokenBuffer = this._tokens[n2];
                bl = false;
                if (tokenBuffer != null) {
                    bl = true;
                }
            }
        } else {
            TokenBuffer tokenBuffer = new TokenBuffer(jsonParser.getCodec());
            tokenBuffer.copyCurrentStructure(jsonParser);
            this._tokens[n2] = tokenBuffer;
            bl = false;
            if (object != null) {
                String string2 = this._typeIds[n2];
                bl = false;
                if (string2 != null) {
                    bl = true;
                }
            }
        }
        if (bl) {
            this._deserialize(jsonParser, deserializationContext, object, n2);
            this._typeIds[n2] = null;
            this._tokens[n2] = null;
        }
        return true;
    }

    public ExternalTypeHandler start() {
        return new ExternalTypeHandler(this);
    }

    public static class Builder {
        private final HashMap<String, Integer> _nameToPropertyIndex = new HashMap();
        private final ArrayList<ExtTypedProperty> _properties = new ArrayList();

        public void addExternal(SettableBeanProperty settableBeanProperty, String string) {
            Integer n = this._properties.size();
            this._properties.add((Object)new ExtTypedProperty(settableBeanProperty, string));
            this._nameToPropertyIndex.put((Object)settableBeanProperty.getName(), (Object)n);
            this._nameToPropertyIndex.put((Object)string, (Object)n);
        }

        public ExternalTypeHandler build() {
            return new ExternalTypeHandler((ExtTypedProperty[])this._properties.toArray((Object[])new ExtTypedProperty[this._properties.size()]), this._nameToPropertyIndex, null, null);
        }
    }

    private static final class ExtTypedProperty {
        private final SettableBeanProperty _property;
        private final String _typePropertyName;

        public ExtTypedProperty(SettableBeanProperty settableBeanProperty, String string) {
            this._property = settableBeanProperty;
            this._typePropertyName = string;
        }

        public SettableBeanProperty getProperty() {
            return this._property;
        }

        public String getTypePropertyName() {
            return this._typePropertyName;
        }

        public boolean hasTypePropertyName(String string) {
            return string.equals((Object)this._typePropertyName);
        }
    }

}

