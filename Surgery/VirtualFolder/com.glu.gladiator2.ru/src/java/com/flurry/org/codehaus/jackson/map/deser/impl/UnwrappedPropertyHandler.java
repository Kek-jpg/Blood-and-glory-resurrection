/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.util.ArrayList
 */
package com.flurry.org.codehaus.jackson.map.deser.impl;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;

public class UnwrappedPropertyHandler {
    protected final ArrayList<SettableBeanProperty> _properties = new ArrayList();

    public void addProperty(SettableBeanProperty settableBeanProperty) {
        this._properties.add((Object)settableBeanProperty);
    }

    public Object processUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, TokenBuffer tokenBuffer) throws IOException, JsonProcessingException {
        int n = this._properties.size();
        for (int i = 0; i < n; ++i) {
            SettableBeanProperty settableBeanProperty = (SettableBeanProperty)this._properties.get(i);
            JsonParser jsonParser2 = tokenBuffer.asParser();
            jsonParser2.nextToken();
            settableBeanProperty.deserializeAndSet(jsonParser2, deserializationContext, object);
        }
        return object;
    }
}

