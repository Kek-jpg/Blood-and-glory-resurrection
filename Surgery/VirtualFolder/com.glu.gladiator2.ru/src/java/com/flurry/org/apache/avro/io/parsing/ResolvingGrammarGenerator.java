/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Integer
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.apache.avro.io.parsing;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.BinaryEncoder;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.io.EncoderFactory;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import com.flurry.org.apache.avro.io.parsing.ValidatingGrammarGenerator;
import com.flurry.org.codehaus.jackson.JsonNode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResolvingGrammarGenerator
extends ValidatingGrammarGenerator {
    private static EncoderFactory factory = new EncoderFactory().configureBufferSize(32);

    /*
     * Enabled aggressive block sorting
     */
    private static int bestBranch(Schema schema, Schema schema2) {
        Schema.Type type = schema2.getType();
        int n2 = 0;
        Iterator iterator = schema.getTypes().iterator();
        do {
            if (!iterator.hasNext()) break;
            Schema schema3 = (Schema)iterator.next();
            if (type == schema3.getType()) {
                if (type != Schema.Type.RECORD && type != Schema.Type.ENUM && type != Schema.Type.FIXED) {
                    return n2;
                }
                String string = schema2.getFullName();
                String string2 = schema3.getFullName();
                if (string != null && string.equals((Object)string2) || string == string2 && type == Schema.Type.RECORD) {
                    return n2;
                }
            }
            ++n2;
        } while (true);
        int n3 = 0;
        Iterator iterator2 = schema.getTypes().iterator();
        while (iterator2.hasNext()) {
            Schema schema4 = (Schema)iterator2.next();
            block0 : switch (type) {
                case INT: {
                    switch (schema4.getType()) {
                        default: {
                            break block0;
                        }
                        case LONG: 
                        case DOUBLE: 
                    }
                    return n3;
                }
                case LONG: 
                case FLOAT: {
                    switch (schema4.getType()) {
                        default: {
                            break block0;
                        }
                        case DOUBLE: 
                    }
                    return n3;
                }
            }
            ++n3;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void encode(Encoder encoder, Schema schema, JsonNode jsonNode) throws IOException {
        switch (schema.getType()) {
            case RECORD: {
                for (Schema.Field field : schema.getFields()) {
                    String string = field.name();
                    JsonNode jsonNode2 = jsonNode.get(string);
                    if (jsonNode2 == null) {
                        jsonNode2 = field.defaultValue();
                    }
                    if (jsonNode2 == null) {
                        throw new AvroTypeException("No default value for: " + string);
                    }
                    ResolvingGrammarGenerator.encode(encoder, field.schema(), jsonNode2);
                }
                break;
            }
            case ENUM: {
                encoder.writeEnum(schema.getEnumOrdinal(jsonNode.getTextValue()));
                return;
            }
            case ARRAY: {
                encoder.writeArrayStart();
                encoder.setItemCount(jsonNode.size());
                Schema schema2 = schema.getElementType();
                Iterator<JsonNode> iterator = jsonNode.iterator();
                do {
                    if (!iterator.hasNext()) {
                        encoder.writeArrayEnd();
                        return;
                    }
                    JsonNode jsonNode3 = (JsonNode)iterator.next();
                    encoder.startItem();
                    ResolvingGrammarGenerator.encode(encoder, schema2, jsonNode3);
                } while (true);
            }
            case MAP: {
                encoder.writeMapStart();
                encoder.setItemCount(jsonNode.size());
                Schema schema3 = schema.getValueType();
                Iterator<String> iterator = jsonNode.getFieldNames();
                do {
                    if (!iterator.hasNext()) {
                        encoder.writeMapEnd();
                        return;
                    }
                    encoder.startItem();
                    String string = (String)iterator.next();
                    encoder.writeString(string);
                    ResolvingGrammarGenerator.encode(encoder, schema3, jsonNode.get(string));
                } while (true);
            }
            case UNION: {
                encoder.writeIndex(0);
                ResolvingGrammarGenerator.encode(encoder, (Schema)schema.getTypes().get(0), jsonNode);
                return;
            }
            case FIXED: {
                if (!jsonNode.isTextual()) {
                    throw new AvroTypeException("Non-string default value for fixed: " + jsonNode);
                }
                byte[] arrby = jsonNode.getTextValue().getBytes("ISO-8859-1");
                if (arrby.length != schema.getFixedSize()) {
                    byte[] arrby2 = new byte[schema.getFixedSize()];
                    int n2 = schema.getFixedSize() > arrby.length ? arrby.length : schema.getFixedSize();
                    System.arraycopy((Object)arrby, (int)0, (Object)arrby2, (int)0, (int)n2);
                    arrby = arrby2;
                }
                encoder.writeFixed(arrby);
                return;
            }
            case STRING: {
                if (!jsonNode.isTextual()) {
                    throw new AvroTypeException("Non-string default value for string: " + jsonNode);
                }
                encoder.writeString(jsonNode.getTextValue());
                return;
            }
            case BYTES: {
                if (!jsonNode.isTextual()) {
                    throw new AvroTypeException("Non-string default value for bytes: " + jsonNode);
                }
                encoder.writeBytes(jsonNode.getTextValue().getBytes("ISO-8859-1"));
                return;
            }
            case INT: {
                if (!jsonNode.isNumber()) {
                    throw new AvroTypeException("Non-numeric default value for int: " + jsonNode);
                }
                encoder.writeInt(jsonNode.getIntValue());
                return;
            }
            case LONG: {
                if (!jsonNode.isNumber()) {
                    throw new AvroTypeException("Non-numeric default value for long: " + jsonNode);
                }
                encoder.writeLong(jsonNode.getLongValue());
                return;
            }
            case FLOAT: {
                if (!jsonNode.isNumber()) {
                    throw new AvroTypeException("Non-numeric default value for float: " + jsonNode);
                }
                encoder.writeFloat((float)jsonNode.getDoubleValue());
                return;
            }
            case DOUBLE: {
                if (!jsonNode.isNumber()) {
                    throw new AvroTypeException("Non-numeric default value for double: " + jsonNode);
                }
                encoder.writeDouble(jsonNode.getDoubleValue());
                return;
            }
            case BOOLEAN: {
                if (!jsonNode.isBoolean()) {
                    throw new AvroTypeException("Non-boolean default for boolean: " + jsonNode);
                }
                encoder.writeBoolean(jsonNode.getBooleanValue());
                return;
            }
            case NULL: {
                if (!jsonNode.isNull()) {
                    throw new AvroTypeException("Non-null default value for null type: " + jsonNode);
                }
                encoder.writeNull();
                return;
            }
        }
    }

    private static byte[] getBinary(Schema schema, JsonNode jsonNode) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = factory.binaryEncoder((OutputStream)byteArrayOutputStream, null);
        ResolvingGrammarGenerator.encode(binaryEncoder, schema, jsonNode);
        binaryEncoder.flush();
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Symbol mkEnumAdjust(List<String> list, List<String> list2) {
        Object[] arrobject = new Object[list.size()];
        int n2 = 0;
        while (n2 < arrobject.length) {
            void var5_5;
            int n3 = list2.indexOf(list.get(n2));
            if (n3 == -1) {
                String string = "No match for " + (String)list.get(n2);
            } else {
                Integer n4 = new Integer(n3);
            }
            arrobject[n2] = var5_5;
            ++n2;
        }
        return new Symbol.EnumAdjustAction(list2.size(), arrobject);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Symbol resolveRecords(Schema schema, Schema schema2, Map<ValidatingGrammarGenerator.LitS, Symbol> map) throws IOException {
        LitS2 litS2 = new LitS2(schema, schema2);
        Symbol symbol = (Symbol)map.get((Object)litS2);
        if (symbol == null) {
            Iterator iterator;
            List<Schema.Field> list = schema.getFields();
            List<Schema.Field> list2 = schema2.getFields();
            Schema.Field[] arrfield = new Schema.Field[list2.size()];
            int n2 = 0;
            int n3 = 1 + list.size();
            Iterator iterator2 = list.iterator();
            do {
                if (!iterator2.hasNext()) break;
                Schema.Field field = schema2.getField(((Schema.Field)iterator2.next()).name());
                if (field == null) continue;
                int n4 = n2 + 1;
                arrfield[n2] = field;
                n2 = n4;
            } while (true);
            Iterator iterator3 = list2.iterator();
            do {
                if (!iterator3.hasNext()) break;
                Schema.Field field = (Schema.Field)iterator3.next();
                if (schema.getField(field.name()) != null) continue;
                if (field.defaultValue() == null) {
                    Symbol symbol2 = Symbol.error("Found " + schema.toString(true) + ", expecting " + schema2.toString(true));
                    map.put((Object)litS2, (Object)symbol2);
                    return symbol2;
                }
                int n5 = n2 + 1;
                arrfield[n2] = field;
                n3 += 3;
                n2 = n5;
            } while (true);
            Symbol[] arrsymbol = new Symbol[n3];
            int n6 = n3 - 1;
            Symbol.FieldOrderAction fieldOrderAction = new Symbol.FieldOrderAction(arrfield);
            arrsymbol[n6] = fieldOrderAction;
            symbol = Symbol.seq(arrsymbol);
            map.put((Object)litS2, (Object)symbol);
            Iterator iterator4 = list.iterator();
            do {
                if (!iterator4.hasNext()) {
                    iterator = list2.iterator();
                    break;
                }
                Schema.Field field = (Schema.Field)iterator4.next();
                Schema.Field field2 = schema2.getField(field.name());
                if (field2 == null) {
                    arrsymbol[--n6] = new Symbol.SkipAction(this.generate(field.schema(), field.schema(), map));
                    continue;
                }
                arrsymbol[--n6] = this.generate(field.schema(), field2.schema(), map);
            } while (true);
            while (iterator.hasNext()) {
                Schema.Field field = (Schema.Field)iterator.next();
                if (schema.getField(field.name()) != null) continue;
                byte[] arrby = ResolvingGrammarGenerator.getBinary(field.schema(), field.defaultValue());
                int n7 = n6 - 1;
                Symbol.DefaultStartAction defaultStartAction = new Symbol.DefaultStartAction(arrby);
                arrsymbol[n7] = defaultStartAction;
                int n8 = n7 - 1;
                arrsymbol[n8] = this.generate(field.schema(), field.schema(), map);
                n6 = n8 - 1;
                arrsymbol[n6] = Symbol.DEFAULT_END_ACTION;
            }
        }
        return symbol;
    }

    private Symbol resolveUnion(Schema schema, Schema schema2, Map<ValidatingGrammarGenerator.LitS, Symbol> map) throws IOException {
        List<Schema> list = schema.getTypes();
        int n2 = list.size();
        Symbol[] arrsymbol = new Symbol[n2];
        String[] arrstring = new String[n2];
        int n3 = 0;
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext()) {
                Symbol[] arrsymbol2 = new Symbol[]{Symbol.alt(arrsymbol, arrstring), new Symbol.WriterUnionAction()};
                return Symbol.seq(arrsymbol2);
            }
            Schema schema3 = (Schema)iterator.next();
            arrsymbol[n3] = this.generate(schema3, schema2, map);
            arrstring[n3] = schema3.getFullName();
            ++n3;
        } while (true);
    }

    public final Symbol generate(Schema schema, Schema schema2) throws IOException {
        Symbol[] arrsymbol = new Symbol[]{this.generate(schema, schema2, (Map<ValidatingGrammarGenerator.LitS, Symbol>)new HashMap())};
        return Symbol.root(arrsymbol);
    }

    /*
     * Exception decompiling
     */
    public Symbol generate(Schema var1, Schema var2_3, Map<ValidatingGrammarGenerator.LitS, Symbol> var3_2) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.b.a.a.b.as.a(SwitchReplacer.java:478)
        // org.benf.cfr.reader.b.a.a.b.as.a(SwitchReplacer.java:328)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:462)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1112)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:587)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }

    static class LitS2
    extends ValidatingGrammarGenerator.LitS {
        public Schema expected;

        public LitS2(Schema schema, Schema schema2) {
            super(schema);
            this.expected = schema2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block3 : {
                block2 : {
                    if (!(object instanceof LitS2)) break block2;
                    LitS2 litS2 = (LitS2)object;
                    if (this.actual == litS2.actual && this.expected == litS2.expected) break block3;
                }
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return super.hashCode() + this.expected.hashCode();
        }
    }

}

