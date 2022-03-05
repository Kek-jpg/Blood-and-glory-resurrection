/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.String
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.Encoder;
import com.flurry.org.apache.avro.io.ParsingEncoder;
import com.flurry.org.apache.avro.io.parsing.Parser;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import com.flurry.org.apache.avro.io.parsing.ValidatingGrammarGenerator;
import com.flurry.org.apache.avro.util.Utf8;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ValidatingEncoder
extends ParsingEncoder
implements Parser.ActionHandler {
    protected Encoder out;
    protected final Parser parser;

    ValidatingEncoder(Schema schema, Encoder encoder) throws IOException {
        super(new ValidatingGrammarGenerator().generate(schema), encoder);
    }

    ValidatingEncoder(Symbol symbol, Encoder encoder) throws IOException {
        this.out = encoder;
        this.parser = new Parser(symbol, (Parser.ActionHandler)this);
    }

    public ValidatingEncoder configure(Encoder encoder) {
        this.parser.reset();
        this.out = encoder;
        return this;
    }

    @Override
    public Symbol doAction(Symbol symbol, Symbol symbol2) throws IOException {
        return null;
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void setItemCount(long l2) throws IOException {
        super.setItemCount(l2);
        this.out.setItemCount(l2);
    }

    @Override
    public void startItem() throws IOException {
        super.startItem();
        this.out.startItem();
    }

    @Override
    public void writeArrayEnd() throws IOException {
        this.parser.advance(Symbol.ARRAY_END);
        this.out.writeArrayEnd();
        this.pop();
    }

    @Override
    public void writeArrayStart() throws IOException {
        this.push();
        this.parser.advance(Symbol.ARRAY_START);
        this.out.writeArrayStart();
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this.parser.advance(Symbol.BOOLEAN);
        this.out.writeBoolean(bl);
    }

    @Override
    public void writeBytes(ByteBuffer byteBuffer) throws IOException {
        this.parser.advance(Symbol.BYTES);
        this.out.writeBytes(byteBuffer);
    }

    @Override
    public void writeBytes(byte[] arrby, int n2, int n3) throws IOException {
        this.parser.advance(Symbol.BYTES);
        this.out.writeBytes(arrby, n2, n3);
    }

    @Override
    public void writeDouble(double d2) throws IOException {
        this.parser.advance(Symbol.DOUBLE);
        this.out.writeDouble(d2);
    }

    @Override
    public void writeEnum(int n2) throws IOException {
        this.parser.advance(Symbol.ENUM);
        Symbol.IntCheckAction intCheckAction = (Symbol.IntCheckAction)this.parser.popSymbol();
        if (n2 < 0 || n2 >= intCheckAction.size) {
            throw new AvroTypeException("Enumeration out of range: max is " + intCheckAction.size + " but received " + n2);
        }
        this.out.writeEnum(n2);
    }

    @Override
    public void writeFixed(byte[] arrby, int n2, int n3) throws IOException {
        this.parser.advance(Symbol.FIXED);
        Symbol.IntCheckAction intCheckAction = (Symbol.IntCheckAction)this.parser.popSymbol();
        if (n3 != intCheckAction.size) {
            throw new AvroTypeException("Incorrect length for fixed binary: expected " + intCheckAction.size + " but received " + n3 + " bytes.");
        }
        this.out.writeFixed(arrby, n2, n3);
    }

    @Override
    public void writeFloat(float f2) throws IOException {
        this.parser.advance(Symbol.FLOAT);
        this.out.writeFloat(f2);
    }

    @Override
    public void writeIndex(int n2) throws IOException {
        this.parser.advance(Symbol.UNION);
        Symbol.Alternative alternative = (Symbol.Alternative)this.parser.popSymbol();
        this.parser.pushSymbol(alternative.getSymbol(n2));
        this.out.writeIndex(n2);
    }

    @Override
    public void writeInt(int n2) throws IOException {
        this.parser.advance(Symbol.INT);
        this.out.writeInt(n2);
    }

    @Override
    public void writeLong(long l2) throws IOException {
        this.parser.advance(Symbol.LONG);
        this.out.writeLong(l2);
    }

    @Override
    public void writeMapEnd() throws IOException {
        this.parser.advance(Symbol.MAP_END);
        this.out.writeMapEnd();
        this.pop();
    }

    @Override
    public void writeMapStart() throws IOException {
        this.push();
        this.parser.advance(Symbol.MAP_START);
        this.out.writeMapStart();
    }

    @Override
    public void writeNull() throws IOException {
        this.parser.advance(Symbol.NULL);
        this.out.writeNull();
    }

    @Override
    public void writeString(Utf8 utf8) throws IOException {
        this.parser.advance(Symbol.STRING);
        this.out.writeString(utf8);
    }

    @Override
    public void writeString(CharSequence charSequence) throws IOException {
        this.parser.advance(Symbol.STRING);
        this.out.writeString(charSequence);
    }

    @Override
    public void writeString(String string) throws IOException {
        this.parser.advance(Symbol.STRING);
        this.out.writeString(string);
    }
}

