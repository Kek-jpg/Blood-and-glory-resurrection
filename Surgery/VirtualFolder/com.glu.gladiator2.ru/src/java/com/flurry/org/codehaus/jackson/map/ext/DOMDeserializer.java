/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Reader
 *  java.io.StringReader
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  javax.xml.parsers.DocumentBuilder
 *  javax.xml.parsers.DocumentBuilderFactory
 *  org.w3c.dom.Document
 *  org.w3c.dom.Node
 *  org.xml.sax.InputSource
 */
package com.flurry.org.codehaus.jackson.map.ext;

import com.flurry.org.codehaus.jackson.map.DeserializationContext;
import com.flurry.org.codehaus.jackson.map.deser.std.FromStringDeserializer;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public abstract class DOMDeserializer<T>
extends FromStringDeserializer<T> {
    static final DocumentBuilderFactory _parserFactory = DocumentBuilderFactory.newInstance();

    static {
        _parserFactory.setNamespaceAware(true);
    }

    protected DOMDeserializer(Class<T> class_) {
        super(class_);
    }

    @Override
    public abstract T _deserialize(String var1, DeserializationContext var2);

    protected final Document parse(String string) throws IllegalArgumentException {
        try {
            Document document = _parserFactory.newDocumentBuilder().parse(new InputSource((Reader)new StringReader(string)));
            return document;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Failed to parse JSON String as XML: " + exception.getMessage(), (Throwable)exception);
        }
    }

    public static class DocumentDeserializer
    extends DOMDeserializer<Document> {
        public DocumentDeserializer() {
            super(Document.class);
        }

        @Override
        public Document _deserialize(String string, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return this.parse(string);
        }
    }

    public static class NodeDeserializer
    extends DOMDeserializer<Node> {
        public NodeDeserializer() {
            super(Node.class);
        }

        @Override
        public Node _deserialize(String string, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return this.parse(string);
        }
    }

}

