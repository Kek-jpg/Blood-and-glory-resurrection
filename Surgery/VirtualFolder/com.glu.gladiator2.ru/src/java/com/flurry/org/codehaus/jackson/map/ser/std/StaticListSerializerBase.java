/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.String
 *  java.lang.reflect.Type
 *  java.util.Collection
 */
package com.flurry.org.codehaus.jackson.map.ser.std;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.BeanProperty;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.lang.reflect.Type;
import java.util.Collection;

public abstract class StaticListSerializerBase<T extends Collection<?>>
extends SerializerBase<T> {
    protected final BeanProperty _property;

    protected StaticListSerializerBase(Class<?> class_, BeanProperty beanProperty) {
        super(class_, false);
        this._property = beanProperty;
    }

    protected abstract JsonNode contentSchema();

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        ObjectNode objectNode = this.createSchemaNode("array", true);
        objectNode.put("items", this.contentSchema());
        return objectNode;
    }
}

