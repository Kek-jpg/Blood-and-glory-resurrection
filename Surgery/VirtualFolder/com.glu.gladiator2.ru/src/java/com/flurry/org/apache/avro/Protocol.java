/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.map.ObjectMapper
 *  java.io.ByteArrayInputStream
 *  java.io.File
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.PrintStream
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.security.MessageDigest
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package com.flurry.org.apache.avro;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.SchemaParseException;
import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.map.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Protocol {
    private static final Set<String> MESSAGE_RESERVED = new HashSet();
    private static final Set<String> PROTOCOL_RESERVED;
    public static final Schema SYSTEM_ERROR;
    public static final Schema SYSTEM_ERRORS;
    public static final long VERSION = 1L;
    private String doc;
    private byte[] md5;
    private Map<String, Message> messages;
    private String name;
    private String namespace;
    Schema.Props props;
    private Schema.Names types;

    static {
        Collections.addAll(MESSAGE_RESERVED, (Object[])new String[]{"doc", "response", "request", "errors", "one-way"});
        SYSTEM_ERROR = Schema.create(Schema.Type.STRING);
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object)SYSTEM_ERROR);
        SYSTEM_ERRORS = Schema.createUnion((List<Schema>)arrayList);
        PROTOCOL_RESERVED = new HashSet();
        Collections.addAll(PROTOCOL_RESERVED, (Object[])new String[]{"namespace", "protocol", "doc", "messages", "types", "errors"});
    }

    private Protocol() {
        this.types = new Schema.Names();
        this.messages = new LinkedHashMap();
        this.props = new Schema.Props(PROTOCOL_RESERVED);
    }

    public Protocol(String string, String string2) {
        super(string, null, string2);
    }

    public Protocol(String string, String string2, String string3) {
        this.types = new Schema.Names();
        this.messages = new LinkedHashMap();
        this.props = new Schema.Props(PROTOCOL_RESERVED);
        this.name = string;
        this.doc = string2;
        this.namespace = string3;
    }

    static /* synthetic */ Set access$000() {
        return MESSAGE_RESERVED;
    }

    public static void main(String[] arrstring) throws Exception {
        System.out.println((Object)Protocol.parse(new File(arrstring[0])));
    }

    private static Protocol parse(JsonParser jsonParser) {
        try {
            Protocol protocol = new Protocol();
            protocol.parse(Schema.MAPPER.readTree(jsonParser));
            return protocol;
        }
        catch (IOException iOException) {
            throw new SchemaParseException(iOException);
        }
    }

    public static Protocol parse(File file) throws IOException {
        return Protocol.parse(Schema.FACTORY.createJsonParser(file));
    }

    public static Protocol parse(InputStream inputStream) throws IOException {
        return Protocol.parse(Schema.FACTORY.createJsonParser(inputStream));
    }

    public static Protocol parse(String string) {
        try {
            Protocol protocol = Protocol.parse(Schema.FACTORY.createJsonParser((InputStream)new ByteArrayInputStream(string.getBytes("UTF-8"))));
            return protocol;
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
    }

    private void parse(JsonNode jsonNode) {
        Protocol.super.parseNamespace(jsonNode);
        Protocol.super.parseName(jsonNode);
        Protocol.super.parseTypes(jsonNode);
        Protocol.super.parseMessages(jsonNode);
        Protocol.super.parseDoc(jsonNode);
        Protocol.super.parseProps(jsonNode);
    }

    private void parseDoc(JsonNode jsonNode) {
        this.doc = Protocol.super.parseDocNode(jsonNode);
    }

    private String parseDocNode(JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode.get("doc");
        if (jsonNode2 == null) {
            return null;
        }
        return jsonNode2.getTextValue();
    }

    private Message parseMessage(String string, JsonNode jsonNode) {
        String string2 = Protocol.super.parseDocNode(jsonNode);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<String> iterator = jsonNode.getFieldNames();
        while (iterator.hasNext()) {
            JsonNode jsonNode2;
            String string3 = (String)iterator.next();
            if (MESSAGE_RESERVED.contains((Object)string3) || !(jsonNode2 = jsonNode.get(string3)).isValueNode() || !jsonNode2.isTextual()) continue;
            linkedHashMap.put((Object)string3, (Object)jsonNode2.getTextValue());
        }
        JsonNode jsonNode3 = jsonNode.get("request");
        if (jsonNode3 == null || !jsonNode3.isArray()) {
            throw new SchemaParseException("No request specified: " + jsonNode);
        }
        ArrayList arrayList = new ArrayList();
        for (JsonNode jsonNode4 : jsonNode3) {
            JsonNode jsonNode5 = jsonNode4.get("name");
            if (jsonNode5 == null) {
                throw new SchemaParseException("No param name: " + jsonNode4);
            }
            JsonNode jsonNode6 = jsonNode4.get("type");
            if (jsonNode6 == null) {
                throw new SchemaParseException("No param type: " + jsonNode4);
            }
            arrayList.add((Object)new Schema.Field(jsonNode5.getTextValue(), Schema.parse(jsonNode6, this.types), null, jsonNode4.get("default")));
        }
        Schema schema = Schema.createRecord((List<Schema.Field>)arrayList);
        JsonNode jsonNode7 = jsonNode.get("one-way");
        boolean bl = false;
        if (jsonNode7 != null) {
            if (!jsonNode7.isBoolean()) {
                throw new SchemaParseException("one-way must be boolean: " + jsonNode);
            }
            bl = jsonNode7.getBooleanValue();
        }
        JsonNode jsonNode8 = jsonNode.get("response");
        if (!bl && jsonNode8 == null) {
            throw new SchemaParseException("No response specified: " + jsonNode);
        }
        JsonNode jsonNode9 = jsonNode.get("errors");
        if (bl) {
            if (jsonNode9 != null) {
                throw new SchemaParseException("one-way can't have errors: " + jsonNode);
            }
            if (jsonNode8 != null && Schema.parse(jsonNode8, this.types).getType() != Schema.Type.NULL) {
                throw new SchemaParseException("One way response must be null: " + jsonNode);
            }
            return (Protocol)this.new Message(string, string2, (Map)linkedHashMap, schema);
        }
        Schema schema2 = Schema.parse(jsonNode8, this.types);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add((Object)SYSTEM_ERROR);
        if (jsonNode9 != null) {
            if (!jsonNode9.isArray()) {
                throw new SchemaParseException("Errors not an array: " + jsonNode);
            }
            Iterator<JsonNode> iterator2 = jsonNode9.iterator();
            while (iterator2.hasNext()) {
                String string4 = ((JsonNode)iterator2.next()).getTextValue();
                Schema schema3 = this.types.get(string4);
                if (schema3 == null) {
                    throw new SchemaParseException("Undefined error: " + string4);
                }
                if (!schema3.isError()) {
                    throw new SchemaParseException("Not an error: " + string4);
                }
                arrayList2.add((Object)schema3);
            }
        }
        return new TwoWayMessage((Protocol)this, string, string2, (Map)linkedHashMap, schema, schema2, Schema.createUnion((List<Schema>)arrayList2), null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parseMessages(JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode.get("messages");
        if (jsonNode2 != null) {
            Iterator<String> iterator = jsonNode2.getFieldNames();
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                this.messages.put((Object)string, (Object)Protocol.super.parseMessage(string, jsonNode2.get(string)));
            }
        }
    }

    private void parseName(JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode.get("protocol");
        if (jsonNode2 == null) {
            throw new SchemaParseException("No protocol name specified: " + jsonNode);
        }
        this.name = jsonNode2.getTextValue();
    }

    private void parseNamespace(JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode.get("namespace");
        if (jsonNode2 == null) {
            return;
        }
        this.namespace = jsonNode2.getTextValue();
        this.types.space(this.namespace);
    }

    private void parseProps(JsonNode jsonNode) {
        Iterator<String> iterator = jsonNode.getFieldNames();
        while (iterator.hasNext()) {
            JsonNode jsonNode2;
            String string = (String)iterator.next();
            if (PROTOCOL_RESERVED.contains((Object)string) || !(jsonNode2 = jsonNode.get(string)).isValueNode() || !jsonNode2.isTextual()) continue;
            this.addProp(string, jsonNode2.getTextValue());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parseTypes(JsonNode jsonNode) {
        JsonNode jsonNode2 = jsonNode.get("types");
        if (jsonNode2 != null) {
            if (!jsonNode2.isArray()) {
                throw new SchemaParseException("Types not an array: " + jsonNode2);
            }
            for (JsonNode jsonNode3 : jsonNode2) {
                if (!jsonNode3.isObject()) {
                    throw new SchemaParseException("Type not an object: " + jsonNode3);
                }
                Schema.parse(jsonNode3, this.types);
            }
        }
    }

    public void addProp(String string, String string2) {
        void var4_3 = this;
        synchronized (var4_3) {
            this.props.add(string, string2);
            return;
        }
    }

    @Deprecated
    public Message createMessage(String string, String string2, Schema schema) {
        return this.createMessage(string, string2, (Map<String, String>)new LinkedHashMap(), schema);
    }

    @Deprecated
    public Message createMessage(String string, String string2, Schema schema, Schema schema2, Schema schema3) {
        return this.createMessage(string, string2, (Map<String, String>)new LinkedHashMap(), schema, schema2, schema3);
    }

    public Message createMessage(String string, String string2, Map<String, String> map, Schema schema) {
        return (Protocol)this.new Message(string, string2, map, schema);
    }

    public Message createMessage(String string, String string2, Map<String, String> map, Schema schema, Schema schema2, Schema schema3) {
        return new TwoWayMessage(this, string, string2, map, schema, schema2, schema3, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5 : {
            block4 : {
                if (object == this) break block4;
                if (!(object instanceof Protocol)) {
                    return false;
                }
                Protocol protocol = (Protocol)object;
                if (!this.name.equals((Object)protocol.name) || !this.namespace.equals((Object)protocol.namespace) || !this.types.equals((Object)protocol.types) || !this.messages.equals(protocol.messages) || !this.props.equals((Object)this.props)) break block5;
            }
            return true;
        }
        return false;
    }

    public String getDoc() {
        return this.doc;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public byte[] getMD5() {
        if (this.md5 != null) return this.md5;
        try {
            this.md5 = MessageDigest.getInstance((String)"MD5").digest(this.toString().getBytes("UTF-8"));
        }
        catch (Exception exception) {
            throw new AvroRuntimeException(exception);
        }
        return this.md5;
    }

    public Map<String, Message> getMessages() {
        return this.messages;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getProp(String string) {
        void var4_2 = this;
        synchronized (var4_2) {
            String string2 = (String)this.props.get((Object)string);
            return string2;
        }
    }

    public Map<String, String> getProps() {
        return Collections.unmodifiableMap((Map)this.props);
    }

    public Schema getType(String string) {
        return this.types.get(string);
    }

    public Collection<Schema> getTypes() {
        return this.types.values();
    }

    public int hashCode() {
        return this.name.hashCode() + this.namespace.hashCode() + this.types.hashCode() + this.messages.hashCode() + this.props.hashCode();
    }

    public void setTypes(Collection<Schema> collection) {
        this.types = new Schema.Names();
        for (Schema schema : collection) {
            this.types.add(schema);
        }
    }

    void toJson(JsonGenerator jsonGenerator) throws IOException {
        this.types.space(this.namespace);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("protocol", this.name);
        jsonGenerator.writeStringField("namespace", this.namespace);
        if (this.doc != null) {
            jsonGenerator.writeStringField("doc", this.doc);
        }
        this.props.write(jsonGenerator);
        jsonGenerator.writeArrayFieldStart("types");
        Schema.Names names = new Schema.Names(this.namespace);
        for (Schema schema : this.types.values()) {
            if (names.contains(schema)) continue;
            schema.toJson(names, jsonGenerator);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeObjectFieldStart("messages");
        for (Map.Entry entry : this.messages.entrySet()) {
            jsonGenerator.writeFieldName((String)entry.getKey());
            ((Message)entry.getValue()).toJson(jsonGenerator);
        }
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }

    public String toString() {
        return this.toString(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString(boolean bl) {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = Schema.FACTORY.createJsonGenerator((Writer)stringWriter);
            if (bl) {
                jsonGenerator.useDefaultPrettyPrinter();
            }
            this.toJson(jsonGenerator);
            jsonGenerator.flush();
            return stringWriter.toString();
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
    }

    public class Message {
        private String doc;
        private String name;
        private final Schema.Props props = new Schema.Props((Set<String>)Protocol.access$000());
        private Schema request;

        private Message(String string, String string2, Map<String, String> map, Schema schema) {
            this.name = string;
            this.doc = string2;
            this.request = schema;
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    this.addProp((String)entry.getKey(), (String)entry.getValue());
                }
            }
        }

        public void addProp(String string, String string2) {
            void var4_3 = this;
            synchronized (var4_3) {
                this.props.add(string, string2);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5 : {
                block4 : {
                    if (object == this) break block4;
                    if (!(object instanceof Message)) {
                        return false;
                    }
                    Message message = (Message)object;
                    if (!this.name.equals((Object)message.name) || !this.request.equals(message.request) || !this.props.equals((Object)message.props)) break block5;
                }
                return true;
            }
            return false;
        }

        public String getDoc() {
            return this.doc;
        }

        public Schema getErrors() {
            return Schema.createUnion((List<Schema>)new ArrayList());
        }

        public String getName() {
            return this.name;
        }

        public String getProp(String string) {
            void var4_2 = this;
            synchronized (var4_2) {
                String string2 = (String)this.props.get((Object)string);
                return string2;
            }
        }

        public Map<String, String> getProps() {
            return Collections.unmodifiableMap((Map)this.props);
        }

        public Schema getRequest() {
            return this.request;
        }

        public Schema getResponse() {
            return Schema.create(Schema.Type.NULL);
        }

        public int hashCode() {
            return this.name.hashCode() + this.request.hashCode() + this.props.hashCode();
        }

        public boolean isOneWay() {
            return true;
        }

        void toJson(JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeStartObject();
            if (this.doc != null) {
                jsonGenerator.writeStringField("doc", this.doc);
            }
            this.props.write(jsonGenerator);
            jsonGenerator.writeFieldName("request");
            this.request.fieldsToJson(Protocol.this.types, jsonGenerator);
            this.toJson1(jsonGenerator);
            jsonGenerator.writeEndObject();
        }

        void toJson1(JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeStringField("response", "null");
            jsonGenerator.writeBooleanField("one-way", true);
        }

        public String toString() {
            try {
                StringWriter stringWriter = new StringWriter();
                JsonGenerator jsonGenerator = Schema.FACTORY.createJsonGenerator((Writer)stringWriter);
                this.toJson(jsonGenerator);
                jsonGenerator.flush();
                String string = stringWriter.toString();
                return string;
            }
            catch (IOException iOException) {
                throw new AvroRuntimeException(iOException);
            }
        }
    }

    private class TwoWayMessage
    extends Message {
        private Schema errors;
        private Schema response;
        final /* synthetic */ Protocol this$0;

        private TwoWayMessage(Protocol protocol, String string, String string2, Map<String, String> map, Schema schema, Schema schema2, Schema schema3) {
            this.this$0 = protocol;
            super(string, string2, map, schema);
            this.response = schema2;
            this.errors = schema3;
        }

        /* synthetic */ TwoWayMessage(Protocol protocol, String string, String string2, Map map, Schema schema, Schema schema2, Schema schema3, 1 var8) {
            super(protocol, string, string2, (Map<String, String>)map, schema, schema2, schema3);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block3 : {
                block2 : {
                    if (!super.equals(object) || !(object instanceof TwoWayMessage)) break block2;
                    TwoWayMessage twoWayMessage = (TwoWayMessage)object;
                    if (this.response.equals(twoWayMessage.response) && this.errors.equals(twoWayMessage.errors)) break block3;
                }
                return false;
            }
            return true;
        }

        @Override
        public Schema getErrors() {
            return this.errors;
        }

        @Override
        public Schema getResponse() {
            return this.response;
        }

        @Override
        public int hashCode() {
            return super.hashCode() + this.response.hashCode() + this.errors.hashCode();
        }

        @Override
        public boolean isOneWay() {
            return false;
        }

        @Override
        void toJson1(JsonGenerator jsonGenerator) throws IOException {
            jsonGenerator.writeFieldName("response");
            this.response.toJson(this.this$0.types, jsonGenerator);
            List<Schema> list = this.errors.getTypes();
            if (list.size() > 1) {
                Schema schema = Schema.createUnion((List<Schema>)list.subList(1, list.size()));
                jsonGenerator.writeFieldName("errors");
                schema.toJson(this.this$0.types, jsonGenerator);
            }
        }
    }

}

