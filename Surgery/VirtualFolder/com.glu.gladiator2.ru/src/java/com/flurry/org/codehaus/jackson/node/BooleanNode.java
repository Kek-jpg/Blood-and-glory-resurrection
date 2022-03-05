/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.SerializerProvider
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.node;

import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonProcessingException;
import com.flurry.org.codehaus.jackson.JsonToken;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.node.ValueNode;
import java.io.IOException;

public final class BooleanNode
extends ValueNode {
    public static final BooleanNode FALSE;
    public static final BooleanNode TRUE;

    static {
        TRUE = new BooleanNode();
        FALSE = new BooleanNode();
    }

    private BooleanNode() {
    }

    public static BooleanNode getFalse() {
        return FALSE;
    }

    public static BooleanNode getTrue() {
        return TRUE;
    }

    public static BooleanNode valueOf(boolean bl) {
        if (bl) {
            return TRUE;
        }
        return FALSE;
    }

    public boolean asBoolean() {
        return this == TRUE;
    }

    public boolean asBoolean(boolean bl) {
        return this == TRUE;
    }

    public double asDouble(double d2) {
        if (this == TRUE) {
            return 1.0;
        }
        return 0.0;
    }

    public int asInt(int n) {
        return this == TRUE;
    }

    public long asLong(long l) {
        if (this == TRUE) {
            return 1L;
        }
        return 0L;
    }

    public String asText() {
        if (this == TRUE) {
            return "true";
        }
        return "false";
    }

    @Override
    public JsonToken asToken() {
        if (this == TRUE) {
            return JsonToken.VALUE_TRUE;
        }
        return JsonToken.VALUE_FALSE;
    }

    public boolean equals(Object object) {
        return object == this;
    }

    public boolean getBooleanValue() {
        return this == TRUE;
    }

    public boolean isBoolean() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        boolean bl = this == TRUE;
        jsonGenerator.writeBoolean(bl);
    }
}

