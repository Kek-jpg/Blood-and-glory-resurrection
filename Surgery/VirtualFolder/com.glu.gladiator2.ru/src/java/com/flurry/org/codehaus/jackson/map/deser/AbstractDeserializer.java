/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map.deser;

import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.JsonDeserializer;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.TypeDeserializer;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.io.IOException;

public class AbstractDeserializer
extends JsonDeserializer<Object> {
    protected final boolean _acceptBoolean;
    protected final boolean _acceptDouble;
    protected final boolean _acceptInt;
    protected final boolean _acceptString;
    protected final JavaType _baseType;

    /*
     * Enabled aggressive block sorting
     */
    public AbstractDeserializer(JavaType javaType) {
        boolean bl;
        block3 : {
            block2 : {
                this._baseType = javaType;
                Class<?> class_ = javaType.getRawClass();
                this._acceptString = class_.isAssignableFrom(String.class);
                boolean bl2 = class_ == Boolean.TYPE || class_.isAssignableFrom(Boolean.class);
                this._acceptBoolean = bl2;
                boolean bl3 = class_ == Integer.TYPE || class_.isAssignableFrom(Integer.class);
                this._acceptInt = bl3;
                if (class_ == Double.TYPE) break block2;
                boolean bl4 = class_.isAssignableFrom(Double.class);
                bl = false;
                if (!bl4) break block3;
            }
            bl = true;
        }
        this._acceptDouble = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Object _deserializeIfNatural(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        switch (1.$SwitchMap$org$codehaus$jackson$JsonToken[jsonParser.getCurrentToken().ordinal()]) {
            case 1: {
                if (!this._acceptString) return null;
                return jsonParser.getText();
            }
            case 2: {
                if (!this._acceptInt) return null;
                return jsonParser.getIntValue();
            }
            case 3: {
                if (!this._acceptDouble) return null;
                return jsonParser.getDoubleValue();
            }
            case 4: {
                if (!this._acceptBoolean) return null;
                return Boolean.TRUE;
            }
            default: {
                return null;
            }
            case 5: 
        }
        if (!this._acceptBoolean) return null;
        return Boolean.FALSE;
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        throw deserializationContext.instantiationException(this._baseType.getRawClass(), "abstract types can only be instantiated with additional type information");
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        Object object = this._deserializeIfNatural(jsonParser, deserializationContext);
        if (object != null) {
            return object;
        }
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

}

