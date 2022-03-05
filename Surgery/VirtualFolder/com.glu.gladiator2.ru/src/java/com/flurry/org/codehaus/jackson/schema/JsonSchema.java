/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.schema;

import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.annotate.JsonCreator;
import com.flurry.org.codehaus.jackson.annotate.JsonValue;
import com.flurry.org.codehaus.jackson.node.JsonNodeFactory;
import com.flurry.org.codehaus.jackson.node.ObjectNode;

public class JsonSchema {
    private final ObjectNode schema;

    @JsonCreator
    public JsonSchema(ObjectNode objectNode) {
        this.schema = objectNode;
    }

    public static JsonNode getDefaultSchemaNode() {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("type", "any");
        return objectNode;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block7 : {
            block6 : {
                if (object == this) break block6;
                if (object == null) {
                    return false;
                }
                if (!(object instanceof JsonSchema)) {
                    return false;
                }
                JsonSchema jsonSchema = (JsonSchema)object;
                if (this.schema != null) {
                    return this.schema.equals((Object)jsonSchema.schema);
                }
                if (jsonSchema.schema != null) break block7;
            }
            return true;
        }
        return false;
    }

    @JsonValue
    public ObjectNode getSchemaNode() {
        return this.schema;
    }

    public String toString() {
        return this.schema.toString();
    }
}

