/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.util.MinimalPrettyPrinter
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.NullPointerException
 *  java.lang.String
 *  java.lang.System
 *  java.nio.ByteBuffer
 *  java.util.BitSet
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.ParsingEncoder;
import com.flurry.org.apache.avro.io.parsing.JsonGrammarGenerator;
import com.flurry.org.apache.avro.io.parsing.Parser;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import com.flurry.org.apache.avro.util.Utf8;
import com.flurry.org.codehaus.jackson.JsonEncoding;
import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import com.flurry.org.codehaus.jackson.util.MinimalPrettyPrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.BitSet;

public class JsonEncoder
extends ParsingEncoder
implements Parser.ActionHandler {
    protected BitSet isEmpty;
    private JsonGenerator out;
    final Parser parser;

    JsonEncoder(Schema schema, JsonGenerator jsonGenerator) throws IOException {
        this.isEmpty = new BitSet();
        this.configure(jsonGenerator);
        this.parser = new Parser(new JsonGrammarGenerator().generate(schema), (Parser.ActionHandler)this);
    }

    JsonEncoder(Schema schema, OutputStream outputStream) throws IOException {
        super(schema, JsonEncoder.getJsonGenerator(outputStream));
    }

    private static JsonGenerator getJsonGenerator(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new NullPointerException("OutputStream cannot be null");
        }
        JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8);
        MinimalPrettyPrinter minimalPrettyPrinter = new MinimalPrettyPrinter();
        minimalPrettyPrinter.setRootValueSeparator(System.getProperty((String)"line.separator"));
        jsonGenerator.setPrettyPrinter((PrettyPrinter)minimalPrettyPrinter);
        return jsonGenerator;
    }

    private void writeByteArray(byte[] arrby, int n2, int n3) throws IOException {
        this.out.writeString(new String(arrby, n2, n3, "ISO-8859-1"));
    }

    public JsonEncoder configure(JsonGenerator jsonGenerator) throws IOException {
        if (jsonGenerator == null) {
            throw new NullPointerException("JsonGenerator cannot be null");
        }
        if (this.parser != null) {
            this.flush();
        }
        this.out = jsonGenerator;
        return this;
    }

    public JsonEncoder configure(OutputStream outputStream) throws IOException {
        this.configure(JsonEncoder.getJsonGenerator(outputStream));
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Symbol doAction(Symbol symbol, Symbol symbol2) throws IOException {
        if (symbol2 instanceof Symbol.FieldAdjustAction) {
            Symbol.FieldAdjustAction fieldAdjustAction = (Symbol.FieldAdjustAction)symbol2;
            this.out.writeFieldName(fieldAdjustAction.fname);
            do {
                return null;
                break;
            } while (true);
        }
        if (symbol2 == Symbol.RECORD_START) {
            this.out.writeStartObject();
            return null;
        }
        if (symbol2 == Symbol.RECORD_END || symbol2 == Symbol.UNION_END) {
            this.out.writeEndObject();
            return null;
        }
        if (symbol2 != Symbol.FIELD_END) throw new AvroTypeException("Unknown action symbol " + symbol2);
        return null;
    }

    public void flush() throws IOException {
        this.parser.processImplicitActions();
        if (this.out != null) {
            this.out.flush();
        }
    }

    @Override
    public void startItem() throws IOException {
        if (!this.isEmpty.get(this.pos)) {
            this.parser.advance(Symbol.ITEM_END);
        }
        super.startItem();
        this.isEmpty.clear(this.depth());
    }

    @Override
    public void writeArrayEnd() throws IOException {
        if (!this.isEmpty.get(this.pos)) {
            this.parser.advance(Symbol.ITEM_END);
        }
        this.pop();
        this.parser.advance(Symbol.ARRAY_END);
        this.out.writeEndArray();
    }

    @Override
    public void writeArrayStart() throws IOException {
        this.parser.advance(Symbol.ARRAY_START);
        this.out.writeStartArray();
        this.push();
        this.isEmpty.set(this.depth());
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this.parser.advance(Symbol.BOOLEAN);
        this.out.writeBoolean(bl);
    }

    @Override
    public void writeBytes(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.hasArray()) {
            this.writeBytes(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
            return;
        }
        byte[] arrby = new byte[byteBuffer.remaining()];
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            arrby[i2] = byteBuffer.get();
        }
        this.writeBytes(arrby);
    }

    @Override
    public void writeBytes(byte[] arrby, int n2, int n3) throws IOException {
        this.parser.advance(Symbol.BYTES);
        JsonEncoder.super.writeByteArray(arrby, n2, n3);
    }

    @Override
    public void writeDouble(double d2) throws IOException {
        this.parser.advance(Symbol.DOUBLE);
        this.out.writeNumber(d2);
    }

    @Override
    public void writeEnum(int n2) throws IOException {
        this.parser.advance(Symbol.ENUM);
        Symbol.EnumLabelsAction enumLabelsAction = (Symbol.EnumLabelsAction)this.parser.popSymbol();
        if (n2 < 0 || n2 >= enumLabelsAction.size) {
            throw new AvroTypeException("Enumeration out of range: max is " + enumLabelsAction.size + " but received " + n2);
        }
        this.out.writeString(enumLabelsAction.getLabel(n2));
    }

    @Override
    public void writeFixed(byte[] arrby, int n2, int n3) throws IOException {
        this.parser.advance(Symbol.FIXED);
        Symbol.IntCheckAction intCheckAction = (Symbol.IntCheckAction)this.parser.popSymbol();
        if (n3 != intCheckAction.size) {
            throw new AvroTypeException("Incorrect length for fixed binary: expected " + intCheckAction.size + " but received " + n3 + " bytes.");
        }
        JsonEncoder.super.writeByteArray(arrby, n2, n3);
    }

    @Override
    public void writeFloat(float f2) throws IOException {
        this.parser.advance(Symbol.FLOAT);
        this.out.writeNumber(f2);
    }

    @Override
    public void writeIndex(int n2) throws IOException {
        this.parser.advance(Symbol.UNION);
        Symbol.Alternative alternative = (Symbol.Alternative)this.parser.popSymbol();
        Symbol symbol = alternative.getSymbol(n2);
        if (symbol != Symbol.NULL) {
            this.out.writeStartObject();
            this.out.writeFieldName(alternative.getLabel(n2));
            this.parser.pushSymbol(Symbol.UNION_END);
        }
        this.parser.pushSymbol(symbol);
    }

    @Override
    public void writeInt(int n2) throws IOException {
        this.parser.advance(Symbol.INT);
        this.out.writeNumber(n2);
    }

    @Override
    public void writeLong(long l2) throws IOException {
        this.parser.advance(Symbol.LONG);
        this.out.writeNumber(l2);
    }

    @Override
    public void writeMapEnd() throws IOException {
        if (!this.isEmpty.get(this.pos)) {
            this.parser.advance(Symbol.ITEM_END);
        }
        this.pop();
        this.parser.advance(Symbol.MAP_END);
        this.out.writeEndObject();
    }

    @Override
    public void writeMapStart() throws IOException {
        this.push();
        this.isEmpty.set(this.depth());
        this.parser.advance(Symbol.MAP_START);
        this.out.writeStartObject();
    }

    @Override
    public void writeNull() throws IOException {
        this.parser.advance(Symbol.NULL);
        this.out.writeNull();
    }

    @Override
    public void writeString(Utf8 utf8) throws IOException {
        this.writeString(utf8.toString());
    }

    @Override
    public void writeString(String string) throws IOException {
        this.parser.advance(Symbol.STRING);
        if (this.parser.topSymbol() == Symbol.MAP_KEY_MARKER) {
            this.parser.advance(Symbol.MAP_KEY_MARKER);
            this.out.writeFieldName(string);
            return;
        }
        this.out.writeString(string);
    }
}

