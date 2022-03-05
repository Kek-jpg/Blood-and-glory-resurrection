/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.parsing.Parser;
import com.flurry.org.apache.avro.io.parsing.SkipParser;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import java.io.IOException;

public abstract class ParsingDecoder
extends Decoder
implements Parser.ActionHandler,
SkipParser.SkipHandler {
    protected final SkipParser parser;

    protected ParsingDecoder(Symbol symbol) throws IOException {
        this.parser = new SkipParser(symbol, (Parser.ActionHandler)this, (SkipParser.SkipHandler)this);
    }

    @Override
    public void skipAction() throws IOException {
        this.parser.popSymbol();
    }

    protected abstract void skipFixed() throws IOException;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void skipTopSymbol() throws IOException {
        Symbol symbol = this.parser.topSymbol();
        if (symbol == Symbol.NULL) {
            this.readNull();
        }
        if (symbol == Symbol.BOOLEAN) {
            this.readBoolean();
            return;
        } else {
            if (symbol == Symbol.INT) {
                this.readInt();
                return;
            }
            if (symbol == Symbol.LONG) {
                this.readLong();
                return;
            }
            if (symbol == Symbol.FLOAT) {
                this.readFloat();
                return;
            }
            if (symbol == Symbol.DOUBLE) {
                this.readDouble();
                return;
            }
            if (symbol == Symbol.STRING) {
                this.skipString();
                return;
            }
            if (symbol == Symbol.BYTES) {
                this.skipBytes();
                return;
            }
            if (symbol == Symbol.ENUM) {
                this.readEnum();
                return;
            }
            if (symbol == Symbol.FIXED) {
                this.skipFixed();
                return;
            }
            if (symbol == Symbol.UNION) {
                this.readIndex();
                return;
            }
            if (symbol == Symbol.ARRAY_START) {
                this.skipArray();
                return;
            }
            if (symbol != Symbol.MAP_START) return;
            {
                this.skipMap();
                return;
            }
        }
    }
}

