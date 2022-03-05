/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.format.InputAccessor;
import com.flurry.org.codehaus.jackson.format.MatchStrength;
import com.flurry.org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

public class MappingJsonFactory
extends JsonFactory {
    public MappingJsonFactory() {
        this(null);
    }

    public MappingJsonFactory(ObjectMapper objectMapper) {
        super(objectMapper);
        if (objectMapper == null) {
            this.setCodec(new ObjectMapper((JsonFactory)this));
        }
    }

    @Override
    public final ObjectMapper getCodec() {
        return (ObjectMapper)this._objectCodec;
    }

    @Override
    public String getFormatName() {
        return "JSON";
    }

    @Override
    public MatchStrength hasFormat(InputAccessor inputAccessor) throws IOException {
        return this.hasJSONFormat(inputAccessor);
    }
}

