/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Type
 *  org.w3c.dom.DOMImplementation
 *  org.w3c.dom.Node
 *  org.w3c.dom.bootstrap.DOMImplementationRegistry
 *  org.w3c.dom.ls.DOMImplementationLS
 *  org.w3c.dom.ls.LSSerializer
 */
package com.flurry.org.codehaus.jackson.map.ext;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.std.SerializerBase;
import com.flurry.org.codehaus.jackson.node.ObjectNode;
import java.io.IOException;
import java.lang.reflect.Type;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class DOMSerializer
extends SerializerBase<Node> {
    protected final DOMImplementationLS _domImpl;

    public DOMSerializer() {
        DOMImplementationRegistry dOMImplementationRegistry;
        super(Node.class);
        try {
            dOMImplementationRegistry = DOMImplementationRegistry.newInstance();
        }
        catch (Exception exception) {
            throw new IllegalStateException("Could not instantiate DOMImplementationRegistry: " + exception.getMessage(), (Throwable)exception);
        }
        this._domImpl = (DOMImplementationLS)dOMImplementationRegistry.getDOMImplementation("LS");
    }

    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return this.createSchemaNode("string", true);
    }

    @Override
    public void serialize(Node node, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (this._domImpl == null) {
            throw new IllegalStateException("Could not find DOM LS");
        }
        jsonGenerator.writeString(this._domImpl.createLSSerializer().writeToString(node));
    }
}

