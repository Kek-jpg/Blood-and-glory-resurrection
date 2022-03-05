/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashSet
 */
package com.flurry.org.codehaus.jackson.map.deser.std;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.deser.BeanDeserializer;
import com.flurry.org.codehaus.jackson.map.deser.SettableAnyProperty;
import com.flurry.org.codehaus.jackson.map.deser.SettableBeanProperty;
import com.flurry.org.codehaus.jackson.map.deser.ValueInstantiator;
import com.flurry.org.codehaus.jackson.map.deser.impl.BeanPropertyMap;
import com.flurry.org.codehaus.jackson.map.deser.impl.PropertyBasedCreator;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;
import java.util.HashSet;

public class ThrowableDeserializer
extends BeanDeserializer {
    protected static final String PROP_NAME_MESSAGE = "message";

    public ThrowableDeserializer(BeanDeserializer beanDeserializer) {
        super(beanDeserializer);
    }

    protected ThrowableDeserializer(BeanDeserializer beanDeserializer, boolean bl) {
        super(beanDeserializer, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingPropertyBased(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        boolean bl = this._valueInstantiator.canCreateFromString();
        boolean bl2 = this._valueInstantiator.canCreateUsingDefault();
        if (!bl && !bl2) {
            throw new JsonMappingException("Can not deserialize Throwable of type " + this._beanType + " without having a default contructor, a single-String-arg constructor; or explicit @JsonCreator");
        }
        Object object = null;
        Object[] arrobject = null;
        int n = 0;
        while (jsonParser.getCurrentToken() != JsonToken.END_OBJECT) {
            String string = jsonParser.getCurrentName();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find(string);
            jsonParser.nextToken();
            if (settableBeanProperty != null) {
                if (object != null) {
                    settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                } else {
                    if (arrobject == null) {
                        int n2 = this._beanProperties.size();
                        arrobject = new Object[n2 + n2];
                    }
                    int n3 = n + 1;
                    arrobject[n] = settableBeanProperty;
                    n = n3 + 1;
                    arrobject[n3] = settableBeanProperty.deserialize(jsonParser, deserializationContext);
                }
            } else if (PROP_NAME_MESSAGE.equals((Object)string) && bl) {
                object = this._valueInstantiator.createFromString(jsonParser.getText());
                if (arrobject != null) {
                    int n4 = n;
                    for (int i = 0; i < n4; i += 2) {
                        ((SettableBeanProperty)arrobject[i]).set(object, arrobject[i + 1]);
                    }
                    arrobject = null;
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains((Object)string)) {
                jsonParser.skipChildren();
            } else if (this._anySetter != null) {
                this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string);
            } else {
                this.handleUnknownProperty(jsonParser, deserializationContext, object, string);
            }
            jsonParser.nextToken();
        }
        if (object != null) return object;
        object = bl ? this._valueInstantiator.createFromString(null) : this._valueInstantiator.createUsingDefault();
        if (arrobject == null) return object;
        int n5 = 0;
        int n6 = n;
        while (n5 < n6) {
            ((SettableBeanProperty)arrobject[n5]).set(object, arrobject[n5 + 1]);
            n5 += 2;
        }
        return object;
    }

    @Override
    public JsonDeserializer<Object> unwrappingDeserializer() {
        if (this.getClass() != ThrowableDeserializer.class) {
            return this;
        }
        return new ThrowableDeserializer(this, true);
    }
}

