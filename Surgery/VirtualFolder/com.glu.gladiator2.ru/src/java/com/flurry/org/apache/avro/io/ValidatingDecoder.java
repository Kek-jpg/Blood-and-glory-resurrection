/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.NullPointerException
 *  java.lang.String
 *  java.nio.ByteBuffer
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.ParsingDecoder;
import com.flurry.org.apache.avro.io.parsing.Parser;
import com.flurry.org.apache.avro.io.parsing.SkipParser;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import com.flurry.org.apache.avro.io.parsing.ValidatingGrammarGenerator;
import com.flurry.org.apache.avro.util.Utf8;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ValidatingDecoder
extends ParsingDecoder
implements Parser.ActionHandler {
    protected Decoder in;

    ValidatingDecoder(Schema schema, Decoder decoder) throws IOException {
        super(ValidatingDecoder.getSymbol(schema), decoder);
    }

    ValidatingDecoder(Symbol symbol, Decoder decoder) throws IOException {
        super(symbol);
        this.configure(decoder);
    }

    private void checkFixed(int n2) throws IOException {
        this.parser.advance(Symbol.FIXED);
        Symbol.IntCheckAction intCheckAction = (Symbol.IntCheckAction)this.parser.popSymbol();
        if (n2 != intCheckAction.size) {
            throw new AvroTypeException("Incorrect length for fixed binary: expected " + intCheckAction.size + " but received " + n2 + " bytes.");
        }
    }

    private static Symbol getSymbol(Schema schema) {
        if (schema == null) {
            throw new NullPointerException("Schema cannot be null");
        }
        return new ValidatingGrammarGenerator().generate(schema);
    }

    @Override
    public long arrayNext() throws IOException {
        this.parser.processTrailingImplicitActions();
        long l2 = this.in.arrayNext();
        if (l2 == 0L) {
            this.parser.advance(Symbol.ARRAY_END);
        }
        return l2;
    }

    public ValidatingDecoder configure(Decoder decoder) throws IOException {
        this.parser.reset();
        this.in = decoder;
        return this;
    }

    @Override
    public Symbol doAction(Symbol symbol, Symbol symbol2) throws IOException {
        return null;
    }

    @Override
    public long mapNext() throws IOException {
        this.parser.processTrailingImplicitActions();
        long l2 = this.in.mapNext();
        if (l2 == 0L) {
            this.parser.advance(Symbol.MAP_END);
        }
        return l2;
    }

    @Override
    public long readArrayStart() throws IOException {
        this.parser.advance(Symbol.ARRAY_START);
        long l2 = this.in.readArrayStart();
        if (l2 == 0L) {
            this.parser.advance(Symbol.ARRAY_END);
        }
        return l2;
    }

    @Override
    public boolean readBoolean() throws IOException {
        this.parser.advance(Symbol.BOOLEAN);
        return this.in.readBoolean();
    }

    @Override
    public ByteBuffer readBytes(ByteBuffer byteBuffer) throws IOException {
        this.parser.advance(Symbol.BYTES);
        return this.in.readBytes(byteBuffer);
    }

    @Override
    public double readDouble() throws IOException {
        this.parser.advance(Symbol.DOUBLE);
        return this.in.readDouble();
    }

    @Override
    public int readEnum() throws IOException {
        this.parser.advance(Symbol.ENUM);
        Symbol.IntCheckAction intCheckAction = (Symbol.IntCheckAction)this.parser.popSymbol();
        int n2 = this.in.readEnum();
        if (n2 < 0 || n2 >= intCheckAction.size) {
            throw new AvroTypeException("Enumeration out of range: max is " + intCheckAction.size + " but received " + n2);
        }
        return n2;
    }

    @Override
    public void readFixed(byte[] arrby, int n2, int n3) throws IOException {
        ValidatingDecoder.super.checkFixed(n3);
        this.in.readFixed(arrby, n2, n3);
    }

    @Override
    public float readFloat() throws IOException {
        this.parser.advance(Symbol.FLOAT);
        return this.in.readFloat();
    }

    @Override
    public int readIndex() throws IOException {
        this.parser.advance(Symbol.UNION);
        Symbol.Alternative alternative = (Symbol.Alternative)this.parser.popSymbol();
        int n2 = this.in.readIndex();
        this.parser.pushSymbol(alternative.getSymbol(n2));
        return n2;
    }

    @Override
    public int readInt() throws IOException {
        this.parser.advance(Symbol.INT);
        return this.in.readInt();
    }

    @Override
    public long readLong() throws IOException {
        this.parser.advance(Symbol.LONG);
        return this.in.readLong();
    }

    @Override
    public long readMapStart() throws IOException {
        this.parser.advance(Symbol.MAP_START);
        long l2 = this.in.readMapStart();
        if (l2 == 0L) {
            this.parser.advance(Symbol.MAP_END);
        }
        return l2;
    }

    @Override
    public void readNull() throws IOException {
        this.parser.advance(Symbol.NULL);
        this.in.readNull();
    }

    @Override
    public Utf8 readString(Utf8 utf8) throws IOException {
        this.parser.advance(Symbol.STRING);
        return this.in.readString(utf8);
    }

    @Override
    public String readString() throws IOException {
        this.parser.advance(Symbol.STRING);
        return this.in.readString();
    }

    @Override
    public long skipArray() throws IOException {
        this.parser.advance(Symbol.ARRAY_START);
        long l2 = this.in.skipArray();
        while (l2 != 0L) {
            long l3 = l2;
            do {
                long l4 = l3 - 1L;
                if (l3 <= 0L) break;
                this.parser.skipRepeater();
                l3 = l4;
            } while (true);
            l2 = this.in.skipArray();
        }
        this.parser.advance(Symbol.ARRAY_END);
        return 0L;
    }

    @Override
    public void skipBytes() throws IOException {
        this.parser.advance(Symbol.BYTES);
        this.in.skipBytes();
    }

    @Override
    protected void skipFixed() throws IOException {
        this.parser.advance(Symbol.FIXED);
        Symbol.IntCheckAction intCheckAction = (Symbol.IntCheckAction)this.parser.popSymbol();
        this.in.skipFixed(intCheckAction.size);
    }

    @Override
    public void skipFixed(int n2) throws IOException {
        ValidatingDecoder.super.checkFixed(n2);
        this.in.skipFixed(n2);
    }

    @Override
    public long skipMap() throws IOException {
        this.parser.advance(Symbol.MAP_START);
        long l2 = this.in.skipMap();
        while (l2 != 0L) {
            long l3 = l2;
            do {
                long l4 = l3 - 1L;
                if (l3 <= 0L) break;
                this.parser.skipRepeater();
                l3 = l4;
            } while (true);
            l2 = this.in.skipMap();
        }
        this.parser.advance(Symbol.MAP_END);
        return 0L;
    }

    @Override
    public void skipString() throws IOException {
        this.parser.advance(Symbol.STRING);
        this.in.skipString();
    }
}

